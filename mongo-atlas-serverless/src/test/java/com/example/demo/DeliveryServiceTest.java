package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

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
    deliveryRepository.deleteAll().block(); // 모든 데이터 다 삭제
    var savedList = deliveryRepository.findAll().collectList().block(); // findAll 블로킹해서 empty DB 체크
    Assertions.assertEquals(0, savedList.size()); // 현재 데이터베이스에 저장된 데이터가 없다.
    // given
    var delivery = new Delivery("delivery-arehaewr91712321"); // 새로 저장할 데이터
    // when
    var savedStream = deliveryService.saveDelivery(delivery); // 신규 저장
    // then
    StepVerifier.create(savedStream)
      .expectNext(delivery) // 데이터베이스에 성공적으로 저장된 Mono<Delivery> 를 리턴받는다.
      .verifyComplete();
  }
}