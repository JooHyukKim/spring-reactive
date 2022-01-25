package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeliveryServiceTest {

  @Autowired
  DeliveryService deliveryService;
  @Autowired
  DeliveryRepository deliveryRepository;

  @Test
  void saveDelivery() {
    // given
    var newId = LocalDateTime.now().toString();
    var delivery = new Delivery(newId); // 새로 저장할 데이터
    var existingDelivery = deliveryRepository.findById(newId).block(); // 없는 key 값 확인
    assertNull(existingDelivery); // 현재 데이터베이스에 저장된 데이터가 없다.
    // when
    var savedStream = deliveryService.saveDelivery(delivery).log(); // 신규 저장
    // then
    StepVerifier.create(savedStream)
      .expectNext(delivery) // 데이터베이스에 성공적으로 저장된 Mono<Delivery> 를 리턴받는다.
      .then(() -> {
        assertEquals(newId, deliveryRepository.findById(newId).block().getId());
      })
      .verifyComplete();

  }
}