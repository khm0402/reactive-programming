package com.example.reactive.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureEx {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // n개의 작업을 동시에 실행해서 값에 대한 조합이 가능하다.
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("runAsync");
//                    if (1==1) throw new RuntimeException();
                    return 1;
                })
                .thenCompose(s -> {
                    log.info("thenApply1 -> [{}]", s);
                    return CompletableFuture.completedFuture(s + 1);
                })
                .thenApply(s -> {
                    log.info("thenApply2 -> [{}]", s);
                    return s * 3;
                })
                .exceptionally(e -> -10)
                .thenAccept(s2 -> log.info("thenAccept -> [{}]", s2));
        log.info("Exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

}
