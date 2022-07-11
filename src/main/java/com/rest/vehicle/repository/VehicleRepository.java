package com.rest.vehicle.repository;

import com.rest.vehicle.domain.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Optional<Vehicle> findByVin(String vin);
    Optional<Vehicle> findByLicensePlateNumber(String licensePlateNumber);
}
