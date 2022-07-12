package com.rest.vehicle.service;

import com.rest.vehicle.domain.Vehicle;
import com.rest.vehicle.dto.VehicleDto;
import com.rest.vehicle.exception.VehicleExistException;
import com.rest.vehicle.exception.VehicleNotFoundException;
import com.rest.vehicle.repository.VehicleRepository;
import com.rest.vehicle.repository.VehicleRepositoryCustom;
import com.rest.vehicle.util.SortAndFilterUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleRepositoryCustom vehicleRepositoryCustom;

    public VehicleService(VehicleRepository vehicleRepository, VehicleRepositoryCustom vehicleRepositoryCustom) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleRepositoryCustom = vehicleRepositoryCustom;
    }

    public VehicleDto getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException("message.notFound"));
        return outbound(vehicle);
    }

    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        vehicleDto.setProperties(convertPropertyIntoRealFormat(vehicleDto.getProperties()));
        Vehicle vehicle = inbound(vehicleDto);
        vehicle.setId(null);
        checkVehicle(vehicle);
        vehicle = vehicleRepository.save(vehicle);
        return outbound(vehicle);
    }

    private Map<String, Object> convertPropertyIntoRealFormat(Map<String, Object> properties) {
        if(Optional.ofNullable(properties).isEmpty()){
            return null;
        }
        return properties.entrySet().stream().map(entry -> {
            if(entry.getValue() instanceof Integer){
                return entry;
            }
            if(NumberUtils.isParsable((String)entry.getValue())){
                entry.setValue(Double.parseDouble((String)entry.getValue()));
            }
            return entry;
        }).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public VehicleDto updateVehicle(String id, VehicleDto vehicleDto) {
        vehicleDto.setProperties(convertPropertyIntoRealFormat(vehicleDto.getProperties()));
        Vehicle vehicleFound = inbound(getVehicleById(id));
        Vehicle vehiclePotential = inbound(vehicleDto);
        checkUpdatableVehicle(vehicleFound, vehiclePotential);
        vehicleFound.setName(vehiclePotential.getName());
        vehicleFound.setVin(vehiclePotential.getVin());
        vehicleFound.setLicensePlateNumber(vehiclePotential.getLicensePlateNumber());
        vehicleFound.setProperties(vehiclePotential.getProperties());
        Vehicle vehicle = vehicleRepository.save(vehicleFound);
        return outbound(vehicle);
    }

    public void deleteVehicleById(String id) {
        getVehicleById(id);
        vehicleRepository.deleteById(id);
    }

    public Page<VehicleDto> findAllVehicle(Integer page, Integer size, String sort) {
        Query query = new Query();
        SortAndFilterUtil.addQueryCriteria(query, sort);
        Page<Vehicle> pageProduct = vehicleRepositoryCustom.findAll(query, PageRequest.of(page, size));
        return new PageImpl(
                pageProduct
                        .getContent()
                        .stream()
                        .map(this::outbound)
                        .collect(Collectors.toList()),
                pageProduct.getPageable(),
                pageProduct.getTotalPages()
        );
    }

    private VehicleDto outbound(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setVin(vehicle.getVin());
        vehicleDto.setName(vehicle.getName());
        vehicleDto.setLicensePlateNumber(vehicle.getLicensePlateNumber());
        vehicleDto.setProperties(vehicle.getProperties());
        return vehicleDto;
    }

    private Vehicle inbound(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setName(vehicleDto.getName());
        vehicle.setLicensePlateNumber(vehicleDto.getLicensePlateNumber());
        vehicle.setProperties(vehicleDto.getProperties());
        return vehicle;
    }

    private void checkVehicle(Vehicle vehicle) {
        checkVehicleVin(vehicle);
        checkVehicleLicensePlateNumber(vehicle);
    }

    private void checkUpdatableVehicle(Vehicle vehicleFound, Vehicle vehiclePotential) {
        if(!vehicleFound.getVin().equals(vehiclePotential.getVin())) {
            checkVehicleVin(vehiclePotential);
        }
        if(!vehicleFound.getLicensePlateNumber().equals(vehiclePotential.getLicensePlateNumber())) {
            checkVehicleLicensePlateNumber(vehiclePotential);
        }
    }

    private void checkVehicleVin(Vehicle vehicle) {
        if(vehicleRepository.findByVin(vehicle.getVin()).isPresent()){
            throw new VehicleExistException("message.vinExist");
        }
    }

    private void checkVehicleLicensePlateNumber(Vehicle vehicle) {
        if(vehicleRepository.findByLicensePlateNumber(vehicle.getLicensePlateNumber()).isPresent()){
            throw new VehicleExistException("message.licensePlateNumberExist");
        }
    }

}
