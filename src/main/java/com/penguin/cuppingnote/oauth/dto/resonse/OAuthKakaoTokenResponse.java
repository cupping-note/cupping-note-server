package com.penguin.cuppingnote.oauth.dto.resonse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String scope;

    // todo: Bearer spring security 도입 후 상수 처리
    // https://docs.spring.io/spring-security/oauth/apidocs/constant-values.html
    public String getAccessTokenForAuthorization() {
        return "Bearer " + this.accessToken;
    }
}
