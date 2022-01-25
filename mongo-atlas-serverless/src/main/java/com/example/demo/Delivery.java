package com.example.demo;

import org.reactivestreams.Subscription;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@Document
public class Delivery {

  @Id private String id;

  // getter & setter & constructor....

  public Delivery(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
