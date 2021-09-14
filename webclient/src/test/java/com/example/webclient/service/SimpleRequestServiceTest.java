package com.example.webclient.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest
public class SimpleRequestServiceTest {

    @Autowired
    SimpleRequestService service;

    private final String processorId = "3d183c36-017b-1000-60c2-db0656f84b6a";

    // 성공적으로 들어올시에
    @Test
    public void getProcessorNonBLockingTest(){
        Map<String,String> res = service.getProcessor(processorId);

        log.info("getProcessorNonBLockingTest() >>> {}",res.get("id"));

        Assertions.assertEquals(processorId, res.get("id"));
    }

}