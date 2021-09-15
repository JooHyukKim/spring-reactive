package com.example.webclient.nifi.api;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class FlowApiTest {

    @Autowired
    FlowApi flowApi;

    @Test
    public void scheduleComponents() {
        String processgroupid = "017b10ba-4237-1700-cecc-11b3db126432";
        String state = "STOPPED";

        Map requestBody = this.createMap(processgroupid, state);
        Map res = flowApi.scheduleComponents(processgroupid, requestBody);

        log.info("FlowApiTest > scheduleComponents > response > id {}, state {}"
                , res.get("id")
                , res.get("state")
        );

        Assertions.assertEquals(processgroupid, res.get("id"));
        Assertions.assertEquals(state, res.get("state"));

    }

    private Map createMap(String processGroupId, String state) {
        JSONObject jo = new JSONObject();
        jo.put("id", processGroupId);
        jo.put("state", state);
        return jo.toMap();
    }

}
