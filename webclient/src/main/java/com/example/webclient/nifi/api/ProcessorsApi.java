package com.example.webclient.nifi.api;

import com.example.webclient.nifi.DefaultApiClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessorsApi {

    private WebClient webClient = new DefaultApiClient().getInstance();

    public Map getProcessor(String processorId) {
        String uri = new StringBuilder("/processors/")
                .append(processorId)
                .toString();

        return this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(
                        Map.class
                ).block();
    }

    public Map updateProcessor(String id, Map body) {
        String uri = new StringBuilder("/processors/")
                .append(id)
                .toString();

        return this.webClient
                .put()
                .uri(uri)
                .body(Mono.just(body), Map.class)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
