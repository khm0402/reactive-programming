package com.example.reactive.webflux;

import com.example.reactive.ReactiveApplication;
import org.springframework.boot.SpringApplication;

public class ReactiveApplication2 {

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("server.tomcat.max-threads", "10000");
        SpringApplication.run(ReactiveApplication.class, args);
    }
}
