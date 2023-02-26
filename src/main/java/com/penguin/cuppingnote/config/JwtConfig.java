package com.penguin.cuppingnote.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtConfig {
    private String header;
    private String issuer;
    private String clientSecret;
    private int refreshTokenExpirySeconds;
    private int accessTokenExpirySeconds;
}
