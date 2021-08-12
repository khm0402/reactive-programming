package com.example.reactive.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncService {

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> hello(String value) throws InterruptedException {
        log.debug("hello -> {}", value);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(value);
    }
}
