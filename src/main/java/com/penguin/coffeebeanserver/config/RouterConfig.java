package com.penguin.coffeebeanserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Component
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/swagger"), request ->
                ServerResponse.temporaryRedirect(URI.create("swagger-ui/index.html")).build());
    }
}
