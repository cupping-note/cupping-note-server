package com.penguin.cuppingnote.oauth.service;

import com.penguin.cuppingnote.oauth.feign.OAuthTokenClient;
import com.penguin.cuppingnote.oauth.feign.OAuthUserClient;
import com.penguin.cuppingnote.oauth.dto.OAuthKakaoProperties;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoTokenResponse;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.user.dto.request.UserKakaoLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final OAuthKakaoProperties oAuthKakaoProperties;
    private final OAuthTokenClient oAuthTokenClient;
    private final OAuthUserClient oAuthUserClient;

    public OAuthKakaoUserResponse getKakaoUserEntity(UserKakaoLoginRequestDto userKakaoLoginRequestDto) {
        OAuthKakaoTokenResponse oAuthKakaoToken = oAuthTokenClient.getOAuthKakaoToken(
                oAuthKakaoProperties.toOAuthKakaoTokenRequestWith(userKakaoLoginRequestDto)
        );

        return oAuthUserClient.getKakaoUserEntity(oAuthKakaoToken.getAccessTokenForAuthorization());
    }
}
