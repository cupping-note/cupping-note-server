package com.penguin.cuppingnote.oauth.dto;

import com.penguin.cuppingnote.oauth.dto.request.OAuthKakaoTokenRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConstructorBinding
public class OAuthKakaoProperties {
    @Value("${oauth.kakao.grant-type}")
    private String grantType;

    @Value("${oauth.kakao.rest-api-key}")
    private String restAPIKey;

    @Value("${oauth.kakao.client-secret}")
    private String clientSecretKey;

    public OAuthKakaoTokenRequest toOAuthKakaoTokenRequestWith(
            String redirectUrl,
            String authorizationCode
    ) {
        return OAuthKakaoTokenRequest.builder()
                .grantType(grantType)
                .clientId(restAPIKey)
                .redirectUri(redirectUrl)
                .authorizationCode(authorizationCode)
                .clientSecret(clientSecretKey)
                .build();
    }
}
