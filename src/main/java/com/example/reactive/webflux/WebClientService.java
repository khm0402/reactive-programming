package com.example.reactive.webflux;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Service
public class WebClientService {

    private static final int CONNECT_TIMEOUT_MILLIS = (int) SECONDS.toMillis(10L);

    private static final int WRITE_TIMEOUT_SECONDS = (int) SECONDS.toSeconds(20L);

    private static final int READ_TIMEOUT_SECONDS = (int) SECONDS.toSeconds(30L);

    private static final String URL1 = "http://localhost:8081/content";

    public Mono<String> retrieve() {
        return webClientBuilder().baseUrl(URL1).build()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("failed to retrieve : {}", error.getMessage()))
                .doOnNext(response1 -> log.info("success to retrieve response1 : {}", response1))
                .flatMap(response2 -> {
                    log.info("success to retrieve response2 : {}", response2);
                    return Mono.fromCompletionStage(workContent(response2));
                });
    }

    public CompletableFuture<String> workContent(String content) {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.supplyAsync(() -> content + " 비동기");
    }

    private static WebClient.Builder webClientBuilder() {
        final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(c -> c.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient()));
    }

    private static HttpClient httpClient() {
        return HttpClient
                .from(tcpClient())
                .compress(true);
    }

    private static TcpClient tcpClient() {
        return TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
                .doOnConnected(c -> c
                        .addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT_SECONDS))
                        .addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS))
                );
    }
}
