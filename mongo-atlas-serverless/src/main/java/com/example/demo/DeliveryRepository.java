package com.example.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DeliveryRepository extends ReactiveMongoRepository<Delivery, String> {
}
