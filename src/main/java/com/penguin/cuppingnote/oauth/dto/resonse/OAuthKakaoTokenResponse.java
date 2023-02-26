package com.penguin.cuppingnote.oauth.dto.resonse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor.BEARER;

/**
 * Kakao Oauth 토큰 반환값
 * docs url : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token-response
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OAuthKakaoTokenResponse {
    private String tokenType;
    private String accessToken;
    private String idToken;
    private Integer expiresIn;
    private String refreshToken;
    private Integer refreshTokenExpiresIn;
    private String scope;

    public String getAccessTokenForAuthorization() {
        return BEARER + " " + this.accessToken;
    }
}
