package com.example.webclient.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import okhttp

@SpringBootTest
public class SimpleRequestServiceTest {

    @Autowired
    SimpleRequestService service;

    @Test
    public void getProcessorBLockingTest(){
        PersonResponse personResponse = service.getProcessor("hi","hello").block();
    }

    private MockWebServer mockWebServer;


    @BeforeEach
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();

        TwilioClientProperties properties = new TwilioClientProperties();
        properties.setBaseUrl(mockWebServer.url("/").url().toString());
        properties.setAccountSid("ACd936ed6d");

        twilioClient = new TwilioClient(WebClient.create(), properties);
    }

    @Test
    public void getProcessorNonBLockingTest(){
        service.getProcessor("hi","hello")
                .doOnNext(personResponse -> {
                    Assertions.assertEquals("hello", personResponse);
                }).subscribe();
    }
}
