package com.example.webclient.nifi.api;

import com.example.webclient.nifi.DefaultApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowApi {

    private WebClient webClient = DefaultApiClient.getInstance();


    public Map scheduleComponents(String id, Map requestBody) {
        String uri = new StringBuilder("/flow/")
                .append("process-groups/")
                .append(id)
                .toString();

        return this.webClient
                .put()
                .uri(uri)
                .body(Mono.just(requestBody), Map.class)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
