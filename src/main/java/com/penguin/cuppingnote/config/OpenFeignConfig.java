package com.penguin.cuppingnote.config;

import feign.codec.StringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {

    @Bean
    public StringDecoder stringDecoder() {
        return new StringDecoder();
    }
}
