package com.example.reactive.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureEx2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("future-1");
            return 1;
        })
        .thenApply(v -> {
            log.info("future-1 thenApply [{}]", v);
            return v + 1;
        })
        .thenCompose(v -> {
            log.info("future-1 thenCompose [{}]", v);
            return CompletableFuture.supplyAsync(() -> v + 1);
        })
        .exceptionally(e -> -10);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("future-2");
            return 100;
        });

        future2.thenCombine(future1, Integer::sum)
                .thenAccept(v -> log.info("thenCombine [{}]", v));

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);

    }

}
