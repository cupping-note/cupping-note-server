package com.penguin.coffeebeanserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.penguin.coffeebeanserver")
                )
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "ILoveCoffeeBean API",
                "ILoveCoffeeBean API Descripion",
                "1.0",
                "Coffee Bean Dictionary",
                new Contact("Contact Me", "https://github.com/ILoveCoffeeBean/coffee-bean-server", "it@i-love-coffee-bean.com"),
                "Licenses",
                "https://github.com/ILoveCoffeeBean/coffee-bean-server",
                Collections.emptyList()
        );
    }
}
