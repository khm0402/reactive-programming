package com.example.reactive.future;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureEx3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 200);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 300);

        List<CompletableFuture<Integer>> futures = Arrays.asList(future1, future2, future3);

        int result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join()
                .stream()
                .reduce(0, Integer::sum);

        log.info("result -> [{}]", result);

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);

    }

}
