package com.example.webclient.service;

import com.example.webclient.nifi.exception.Client4xxException;
import com.example.webclient.nifi.exception.OtherException;
import com.example.webclient.nifi.exception.Server5XXException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleRequestService {


    private String nifiNodeApiUrl = "http://localhost:7070/nifi-api";

    private WebClient processorWebClient = WebClient.builder()
            .baseUrl(nifiNodeApiUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(
                    new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(5)))
            )
            .build();

    public Map<String, String> getProcessor(String processorId) {

        String uri = new StringBuilder().append("/processors/").append(processorId).toString();
        Map<String,String> res = this.processorWebClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().onStatus(
                        HttpStatus::is4xxClientError
                        , response -> {
                            log.info("cleint error >>> " + response);
                            return Mono.error(new Client4xxException());
                        }
                ).onStatus(
                        HttpStatus::is5xxServerError
                        , response -> {
                            log.info("Server error >>> " + response);
                            return Mono.error(new Server5XXException());
                        }
                ).bodyToMono(
                        Map.class
                ).onErrorMap(
                        Predicate
                                .not(Client4xxException.class::isInstance)
                                .or(Server5XXException.class::isInstance)
                        , throwable -> {
                            log.info("onErrorMap >>>" + throwable.toString());
                            return new OtherException();
                        }
                ).block();
        return res;
    }
}
