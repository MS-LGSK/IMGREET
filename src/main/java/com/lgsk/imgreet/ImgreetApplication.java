package com.lgsk.imgreet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ImgreetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgreetApplication.class, args);
    }

}
