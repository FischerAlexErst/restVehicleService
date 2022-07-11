package com.rest.vehicle.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VehicleExistException  extends RuntimeException{

    public VehicleExistException(String message){
        super(message);
    }
}
