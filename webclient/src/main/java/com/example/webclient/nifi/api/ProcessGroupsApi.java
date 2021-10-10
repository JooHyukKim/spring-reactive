package com.example.webclient.nifi.api;

import com.example.webclient.nifi.DefaultApiClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessGroupsApi {

    private WebClient webClient = DefaultApiClient.getInstance();

    public Map getProcessGroup(@NonNull String id) {
        String uri = new StringBuilder("/process-groups/")
                .append(id)
                .toString();

        log.info("getProcessGroup > {}", uri);

        return this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map getProcessGroups(@NonNull String id) {
        String uri = new StringBuilder("/process-groups/")
                .append(id)
                .append("/process-groups")
                .toString();

        log.info("getProcessGroups > {}", uri);

        return this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

}
