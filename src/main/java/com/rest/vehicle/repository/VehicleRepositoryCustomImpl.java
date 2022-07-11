package com.rest.vehicle.repository;

import com.rest.vehicle.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepositoryCustomImpl implements VehicleRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    public VehicleRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Vehicle> findAll(Query query, PageRequest pageable) {
        long count = mongoTemplate.count(query, Vehicle.class);
        query.with(pageable);
        List<Vehicle> listProducts = mongoTemplate.find(query, Vehicle.class);
        return new PageImpl(listProducts, pageable, count);
    }
}
