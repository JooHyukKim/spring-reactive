package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeliveryService {
  @Autowired
  DeliveryRepository deliveryRepository;

  public Mono<Delivery> saveDelivery(Delivery delivery) {
    return deliveryRepository.save(delivery);
  }
}
