package com.example.reactive.future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Future 의 .get() 메소드의 Blocking 을 해결할 수 있으나
 * ListenableFuture 는 callback 을 기본적으로 사용하는 구조이기 때문에 callback hell 을 경험하게 된다.
 */
@Slf4j
public class ListenableFutureEx {

    public static void main(String[] args) throws InterruptedException {

        toCompletableFuture(getDataListenableFuture("test1"))
                .thenCompose(v1 -> {
                    log.info("thenCompose1 -> [{}]", v1);
                    return toCompletableFuture(getDataListenableFuture(v1 + "2"));
                })
                .thenCompose(v2 -> {
                    log.info("thenCompose -> [{}]", v2);
                    return toCompletableFuture(getDataListenableFuture(v2 + "3"));
                })
                .thenAcceptAsync(v3 -> log.info("thenAccept -> [{}]", v3), new SimpleAsyncTaskExecutor("myThread-"));

//        final ListenableFuture<String> listenableFuture1 = getDataListenableFuture("test1");
//        listenableFuture1.addCallback(v1 -> {
//            log.info("listenableFuture1 addCallback -> [{}]", v1);
//
//            final ListenableFuture<String> listenableFuture2 = getDataListenableFuture(v1 + "2");
//            listenableFuture2.addCallback(v2 -> {
//                log.info("listenableFuture2 addCallback -> [{}]", v2);
//
//                final ListenableFuture<String> listenableFuture3 = getDataListenableFuture(v2 + "3");
//                listenableFuture3.addCallback(v3 -> {
//                    log.info("listenableFuture3 addCallback -> [{}]", v3);
//                }, e -> {
//                    log.error("listenableFuture3 error", e);
//                });
//            }, e -> {
//                log.error("listenableFuture2 error", e);
//            });
//        }, e -> {
//            log.error("listenableFuture1 error", e);
//        });

        log.info("Exit");
    }

    public static ListenableFuture<String> getDataListenableFuture(String data) {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        return simpleAsyncTaskExecutor.submitListenable(() -> {
            SECONDS.sleep(3);
            return data;
        });
    }

    public static CompletableFuture<String> toCompletableFuture(ListenableFuture<String> listenableFuture) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        listenableFuture.addCallback(completableFuture::complete, completableFuture::completeExceptionally);
        return completableFuture;
    }

}
