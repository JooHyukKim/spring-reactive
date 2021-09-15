package com.example.webclient.nifi.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class ProcessGroupsApiTest {

    @Autowired
    private ProcessGroupsApi processGroupApi;

    @ParameterizedTest
    @ValueSource(strings = {"d47b1e7b-1030-1179-6665-3573bca8f43f"})
    public void getProcessGroup(String id) {
        Map aProcessGroup = processGroupApi.getProcessGroup(id);
        log.info("getProcessGroup > response > id > {} > toSTring() > {}", aProcessGroup.get("id"), aProcessGroup);

        Assertions.assertEquals("d47b1e7b-1030-1179-6665-3573bca8f43f", aProcessGroup.get("id"));
    }


    // 성공적으로 들어올시에
    @ParameterizedTest
    @ValueSource(strings = {"root"})
    public void getProcessGroups(String id) {

        Map res = processGroupApi.getProcessGroups(id);

        List<Map> processGroups = (List<Map>) res.get("processGroups");

        processGroups.stream().forEach(pg -> {
            log.info(
                    "getProcessGroups > response > ProcessGroup of id {} > {}"
                    , pg.get("id")
                    , pg.toString());
            Assertions.assertNotNull(pg);

        });
    }


}
