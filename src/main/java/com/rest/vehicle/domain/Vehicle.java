package com.rest.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle{
    @Id
    private String id;
    private String name;
    private String vin;
    private String licensePlateNumber;
    private Map<String, Object> properties;
}
