package com.rest.vehicle.repository;

import com.rest.vehicle.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Query;

public interface VehicleRepositoryCustom {
    Page<Vehicle> findAll(Query query, PageRequest pageable);
}
