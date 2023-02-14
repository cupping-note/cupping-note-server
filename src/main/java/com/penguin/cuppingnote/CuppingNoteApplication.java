package com.penguin.cuppingnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CuppingNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuppingNoteApplication.class, args);
    }

}
