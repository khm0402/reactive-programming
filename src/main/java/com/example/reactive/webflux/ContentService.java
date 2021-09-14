package com.example.reactive.webflux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

    public String getReactiveContent() throws InterruptedException {
        Thread.sleep(3000L);
        return "content";
    }

}
