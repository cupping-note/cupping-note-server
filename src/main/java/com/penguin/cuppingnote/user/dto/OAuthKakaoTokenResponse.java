package com.penguin.cuppingnote.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Kakao Oauth 토큰 반환값
 * docs url : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token-response
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthKakaoTokenResponse {
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    private String scope;

    // todo: Bearer spring security 도입 후 상수 처리
    // https://docs.spring.io/spring-security/oauth/apidocs/constant-values.html
    public String getAccessTokenForAuthorization() {
        return "Bearer " + this.accessToken;
    }
}
