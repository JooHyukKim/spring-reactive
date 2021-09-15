package com.example.webclient.nifi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpInfos;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Component
public class DefaultApiClient {

    private String nifiNodeApiUrl = "http://localhost:7070/nifi-api";

    private WebClient processorWebClient;

    public WebClient getInstance(){
        if (this.processorWebClient != null) {
            return this.processorWebClient;
        }
        return WebClient.builder()
                .baseUrl(this.nifiNodeApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

                .clientConnector(
                        new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(5)))
                )
                .build();
    }
}
