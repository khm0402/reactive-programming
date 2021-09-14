package com.example.reactive.webflux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContentController {

    private static final String URL1 = "http://localhost:8081/content";

    private final WebClient webClient = WebClient.create();

    private final ContentService contentService;

    private final WebClientService webClientService;

    @GetMapping("/rest")
    public Mono<String> rest() {
        return webClient.get()
                .uri(URL1)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }

    @GetMapping("/rest2")
    public Mono<String> rest2() {
        return webClient.get()
                .uri(URL1)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
                .map(response -> response + "100")
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }

    @GetMapping("/rest3")
    public Mono<String> rest3() {
        Mono<String> result = webClientService.retrieve();
        log.info("rest3 Exit");
        return result;
    }

    @GetMapping("/content")
    public String content() throws InterruptedException {
        return contentService.getReactiveContent();
    }

}
