package com.example.reactive.programming;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.TimeUnit;

public class PubSub {

    public static void main(String[] args) throws InterruptedException {
        // Publisher  <- Observable
        // Subscriber <- Observer

        Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5);
        ExecutorService executorService = Executors.newCachedThreadPool();

        Publisher p = new Publisher() {
            @Override
            public void subscribe(Subscriber subscriber) {

                Iterator<Integer> iterator = iterable.iterator();

                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        executorService.execute(() -> {
                            int i = 0;
                            while (i++ < n) {
                                if (iterator.hasNext()) {
                                    subscriber.onNext(iterator.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        });
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> s = new Subscriber<>() {

            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println(Thread.currentThread().getName() + " : " + "onSubscribe");
                this.subscription = subscription;
                this.subscription.request(1L); // back pressure 설정.
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(Thread.currentThread().getName() + " : " + "onNext -> " + item);
                this.subscription.request(1L);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError -> " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread().getName() + " : " + "onComplete");
            }
        };

        p.subscribe(s);
        executorService.awaitTermination(10, TimeUnit.HOURS);
        executorService.shutdown();
    }
}
