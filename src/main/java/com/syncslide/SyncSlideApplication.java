package com.syncslide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SyncSlideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncSlideApplication.class, args);
    }
}
