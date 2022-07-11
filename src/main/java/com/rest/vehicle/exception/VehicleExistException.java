package com.rest.vehicle.exception;

public class VehicleExistException  extends RuntimeException{
    public VehicleExistException(){
    }

    public VehicleExistException(String message){
        super(message);
    }
}
