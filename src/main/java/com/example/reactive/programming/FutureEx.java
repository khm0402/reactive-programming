package com.example.reactive.programming;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class FutureEx {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();

        // Future = Blocking.
        Future<String> f = es.submit(() -> {
            Thread.sleep(2000L);
            log.debug("Async");
            return "Hello";
        });

        log.debug("EXIT - 11");
        log.debug("f >> {}", f.get()); // Future 의 결과가 전달될 때 까지 Blocking.
        log.debug("EXIT - 22"); // Blocking 으로 인한 2초 후 log 출력.
    }
}
