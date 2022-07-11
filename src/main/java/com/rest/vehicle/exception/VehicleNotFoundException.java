package com.rest.vehicle.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VehicleNotFoundException extends RuntimeException{

    public VehicleNotFoundException(String message){
        super(message);
    }
}
