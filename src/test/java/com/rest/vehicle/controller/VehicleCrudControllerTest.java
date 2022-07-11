package com.rest.vehicle.controller;

import com.rest.vehicle.domain.Vehicle;
import com.rest.vehicle.dto.VehicleDto;
import com.rest.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VehicleCrudControllerTest {

    public static final String ID = "id";
    public static final String VIN = "vin";
    public static final String LPN = "LPN";
    public static final String NAME = "name";
    @InjectMocks
    VehicleCrudController vehicleCrudController;

    @Mock
    VehicleService vehicleService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllVehicle() {
    }

    @Test
    void getVehicleByIdWithExistingId() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(vehicleService.getVehicleById(anyString())).thenReturn(new VehicleDto());
        ResponseEntity<VehicleDto> responseEntity = vehicleCrudController.getVehicleById(ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void createVehicleWithSuitableData() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        vehicle.setVin(VIN);
        vehicle.setName(NAME);
        vehicle.setLicensePlateNumber(LPN);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVin(VIN);
        vehicleDto.setName(NAME);
        vehicleDto.setLicensePlateNumber(LPN);

        when(vehicleService.createVehicle(any())).thenReturn(vehicleDto);
        ResponseEntity<VehicleDto> responseEntity = vehicleCrudController.createVehicle(vehicleDto);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);

    }

    @Test
    void updateVehicleByIdWithSuitableData() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Vehicle vehicle = new Vehicle();
        vehicle.setId(ID);
        vehicle.setVin(VIN);
        vehicle.setName(NAME);
        vehicle.setLicensePlateNumber(LPN);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVin(VIN);
        vehicleDto.setName(NAME);
        vehicleDto.setLicensePlateNumber(LPN);

        when(vehicleService.updateVehicle(anyString(), any())).thenReturn(vehicleDto);
        ResponseEntity<VehicleDto> responseEntity = vehicleCrudController.updateVehicleById(ID, vehicleDto);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteVehicleByIdWithSuitableData() {
        ResponseEntity<Void> responseEntity = vehicleCrudController.deleteVehicleById(ID);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}