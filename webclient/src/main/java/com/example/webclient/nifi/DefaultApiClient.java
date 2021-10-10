package com.example.webclient.nifi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Component
public class DefaultApiClient {

    private static final String nifiNodeApiUrl = "http://localhost:7070/nifi-api";

    private static final WebClient processorWebClient;

    private DefaultApiClient() {
    }

    static {
        processorWebClient = WebClient.builder()
                .baseUrl(nifiNodeApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

                .clientConnector(
                        new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(5)))
                )
                .build();
    }

    public static WebClient getInstance() {
        return processorWebClient;
    }

}
