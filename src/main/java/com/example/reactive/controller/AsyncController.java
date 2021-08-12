package com.example.reactive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@RestController
public class AsyncController {

    @RequestMapping("/callable")
    public Callable<String> callable() throws InterruptedException {
        log.info("callable");
        return () ->  {
            log.info("async");
            Thread.sleep(2000L);
            return "hello";
        };
    }
}
