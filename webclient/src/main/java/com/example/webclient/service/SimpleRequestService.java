package com.example.webclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class SimpleRequestService {


    public Mono<PersonResponse> getProcessor(String urr, String uri) {
        return this.processorWebClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError
                        , response -> Mono.error(new GeneralException())
                ).onStatus(
                        HttpStatus::is5xxServerError
                        , response -> Mono.error(new GeneralException())
                ).bodyToMono(
                        PersonResponse.class
                ).onErrorMap(
                        Predicate.not(GeneralException.class::isInstance)
                        , throwable -> {
                            return new CustomException();
                        }
                );
    }
}


class GeneralException extends Exception {

}

class General5XXException extends Exception {

}

class CustomException extends Exception {

}

class PersonResponse {

}
