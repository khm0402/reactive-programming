package com.example.reactive.future;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class FutureEx {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService excutor = Executors.newSingleThreadExecutor();

        Future<Integer> future = excutor.submit(() -> {
            log.debug("Start -> {}", LocalTime.now());
            int sum = 1 + 1;
            Thread.sleep(3000);
            return sum;
        });

        log.debug("Exit ");
        Integer result = future.get(); // Future 의 결과가 전달될 때 까지 Blocking.
        log.debug("Result : [{}]", result);

    }
}
