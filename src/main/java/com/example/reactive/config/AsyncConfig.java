package com.example.reactive.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configurable
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        /**
         * 최초 접근 시 10개 할당
         */
        threadPoolTaskExecutor.setCorePoolSize(10);

        /**
         * CorePoolSize 가 가득 찬다고 해서 MaxPoolSize 만큼 늘려주는 개념이 아니다.
         * CorePoolSize 가 가득 차면 QueueCapacity 가 먼저 차고 Queue 가 가득 차면 QueueCapacity 만큼 늘려준다.
         * CorePoolSize -> QueueCapacity -> MaxPoolSize
         */
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(200);
        threadPoolTaskExecutor.setThreadNamePrefix("async-task-");
        return threadPoolTaskExecutor;
    }

}
