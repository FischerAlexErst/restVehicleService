package com.rest.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(){
    }

    public VehicleNotFoundException(String message){
        super(message);
    }
}
