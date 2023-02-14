package com.penguin.cuppingnote.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthKakaoTokenRequest {
    private String grantType;
    private String clientId;
    private String redirectUri;
    private String authorizationCode;
    private String clientSecret;
}
