package com.rest.vehicle.service;

import com.rest.vehicle.domain.Vehicle;
import com.rest.vehicle.dto.VehicleDto;
import com.rest.vehicle.exception.VehicleExistException;
import com.rest.vehicle.exception.VehicleNotFoundException;
import com.rest.vehicle.repository.VehicleRepository;
import com.rest.vehicle.repository.VehicleRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    public static final String ID = "id";
    public static final String VIN = "vin";
    public static final String LPN = "LPN";
    private VehicleService vehicleService;
    @Mock // // Instruct Mockito to mock this object
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleRepositoryCustom vehicleRepositoryCustom;

    @BeforeEach
    public void initEach(){
        vehicleService = new VehicleService(vehicleRepository, vehicleRepositoryCustom);
    }

    @Test
    public void getVehicleByIdWithNonExistingId_ExpectedVehicleNotFoundException() {
        when(vehicleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> {
            vehicleService.getVehicleById(ID);
        });

    }

    @Test
    public void getVehicleByIdWithExistingId() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        VehicleDto result = vehicleService.getVehicleById(ID);
        assertEquals(result.getId(), ID);
    }

    @Test
    public void createVehicleWithExistingVin_ExpectedVehicleExistException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        when(vehicleRepository.findByVin(any())).thenReturn(Optional.of(vehicle));

        assertThrows(VehicleExistException.class, () -> {
            vehicleService.createVehicle(new VehicleDto());
        });

    }

    @Test
    public void createVehicleWithExistingLicensePlateNumber_ExpectedVehicleExistException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        when(vehicleRepository.findByLicensePlateNumber(any())).thenReturn(Optional.of(vehicle));
        assertThrows(VehicleExistException.class, () -> {
            vehicleService.createVehicle(new VehicleDto());
        });
    }

    @Test
    public void createVehicleWithCorrectData() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        VehicleDto result = vehicleService.createVehicle(new VehicleDto());
        assertEquals(result.getId(), ID);
    }

    @Test
    public void updateVehicleWithNonExistingId_ExpectedVehicleNotFoundException() {
        when(vehicleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> {
            vehicleService.updateVehicle(ID, new VehicleDto());
        });

    }

    @Test
    public void updateVehicleWithExistingVin_ExpectedVehicleExistException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        vehicle.setVin(VIN);
        vehicle.setLicensePlateNumber(LPN);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(ID);
        vehicleDto.setVin(VIN + "1");
        vehicleDto.setLicensePlateNumber(LPN);
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.findByVin(any())).thenReturn(Optional.of(vehicle));

        assertThrows(VehicleExistException.class, () -> {
            vehicleService.updateVehicle(ID, vehicleDto);
        });

    }

    @Test
    public void updateVehicleWithExistingLicensePlateNumber_ExpectedVehicleExistException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        vehicle.setVin(VIN);
        vehicle.setLicensePlateNumber(LPN);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(ID);
        vehicleDto.setVin(VIN);
        vehicleDto.setLicensePlateNumber(LPN + "1");
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.findByLicensePlateNumber(any())).thenReturn(Optional.of(vehicle));

        assertThrows(VehicleExistException.class, () -> {
            vehicleService.updateVehicle(ID, vehicleDto);
        });
    }

    @Test
    public void updateVehicleWithCorrectData() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        vehicle.setVin(VIN);
        vehicle.setLicensePlateNumber(LPN);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(ID);
        vehicleDto.setVin(VIN);
        vehicleDto.setLicensePlateNumber(LPN);
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        VehicleDto result = vehicleService.updateVehicle(ID, vehicleDto);
        assertEquals(result.getId(), ID);
    }

    @Test
    public void deleteVehicleByIdWithNonExistingId_ExpectedVehicleNotFoundException() {
        when(vehicleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> {
            vehicleService.deleteVehicleById(ID);
        });

    }

    @Test
    public void deleteVehicleByIdWithCorrectData() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        vehicle.setVin(VIN);
        vehicle.setLicensePlateNumber(LPN);

        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        vehicleService.deleteVehicleById(ID);
        verify(vehicleRepository, atLeastOnce()).deleteById(any());
    }

    @Test
    public void findAllVehicleWithSortingAndFilters() {
        when(vehicleRepositoryCustom.findAll(any(),any())).thenReturn(new PageImpl(new ArrayList<>(), PageRequest.of(0, 6) , 0));

        vehicleService.findAllVehicle(1,6, "name:ASC&property.maxPrice:DESC&name=ap&property.maxPrice=gte:10.0&property.minPrice=lte:10.0&property.minPrice=equals:10.0");
    }
}