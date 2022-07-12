package com.rest.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto{
    private String id;
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotBlank
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotBlank
    private String vin;
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotBlank
    private String licensePlateNumber;
    private Map<String, Object> properties;
}
