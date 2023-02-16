package com.penguin.cuppingnote.oauth.dto.request;

import feign.Param;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthKakaoTokenRequest {
    private String grantType;
    private String clientId;
    private String redirectUri;
    private String authorizationCode;
    private String clientSecret;

    @Param("grant_type")
    public String getGrantType() {
        return grantType;
    }

    @Param("client_id")
    public String getClientId() {
        return clientId;
    }

    @Param("redirect_uri")
    public String getRedirectUri() {
        return redirectUri;
    }

    @Param("code")
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    @Param("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }
}
