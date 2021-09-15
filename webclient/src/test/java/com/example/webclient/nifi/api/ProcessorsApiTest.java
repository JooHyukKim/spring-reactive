package com.example.webclient.nifi.api;

import com.example.webclient.nifi.api.ProcessorsApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class ProcessorsApiTest {

    @Autowired
    private ProcessorsApi api;

    private final String processorId = "3d183c36-017b-1000-60c2-db0656f84b6a";

    // 성공적으로 들어올시에
    @Test
    public void getProcessor() {
        Map res = api.getProcessor(processorId);
        Map revision = (Map) res.get("revision");

        log.info("ProcessorsApiTest > getProcessor > response > processor of id > {} > {}", res.get("id"), res);

        Assertions.assertEquals(processorId, res.get("id"));
    }


    @Test
    public void updateProcessor() throws JsonProcessingException {
        JSONObject keyValuePair = new JSONObject().put("test_property", "test_value");

        Map res = api.getProcessor(processorId);
        Map revision = (Map) res.get("revision");
        int version = (int) revision.get("version");

        log.info("ProcessorsApiTest > getProcessor > response > processor of id > {}", res.get("id"));


        Map requestBody = this.createProcessorEntity(version, processorId, keyValuePair);
        Map updateRes = api.updateProcessor(processorId, requestBody);
        log.info("ProcessorsApiTest > updateProcessor > response > processor of id > {} > object {}", processorId, updateRes);

        // check if version went up by one
        Map updatedRevision = (Map) updateRes.get("revision");
        int updatedVersion = (int) updatedRevision.get("version");
        int expectedVersion = version + 1;
        Assertions.assertEquals(expectedVersion, updatedVersion);

        // check if id is the same.
        Assertions.assertEquals(processorId, updateRes.get("id"));
    }

    public Map createProcessorEntity(int version, String processorId, JSONObject property) {
        return new JSONObject()
                .put("revision"
                        , new JSONObject().put("version", version)
                )
                .put("component", new JSONObject()
                        .put("id", processorId)
                        .put("config", new JSONObject()
                                .put("properties", property
                                ))
                ).toMap();
    }


}