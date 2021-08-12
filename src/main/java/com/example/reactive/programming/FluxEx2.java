package com.example.reactive.programming;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxEx2 {

    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(200))
                .take(5) // 지정한 갯수만큼 가져온다.
                .subscribe(s -> log.debug("onNext >> {}", s));

        log.debug("Exit");
        TimeUnit.SECONDS.sleep(5L);
    }

}
