package com.rest.vehicle.advice;


import com.rest.vehicle.exception.VehicleExistException;
import com.rest.vehicle.exception.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(VehicleNotFoundException.class)
    public final ResponseEntity<String> handleVehicleNotFoundException(VehicleNotFoundException vehicleNotFoundException, WebRequest webRequest){
        return new ResponseEntity<>(vehicleNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehicleExistException.class)
    public final ResponseEntity<String> handleVehicleExistException(VehicleExistException vehicleExistException, WebRequest webRequest){
        return new ResponseEntity<>(vehicleExistException.getMessage(), HttpStatus.CONFLICT);
    }
}
