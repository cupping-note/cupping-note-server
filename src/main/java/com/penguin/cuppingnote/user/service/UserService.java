package com.penguin.cuppingnote.user.service;

import com.penguin.cuppingnote.user.dto.OAuthKakaoProperties;
import com.penguin.cuppingnote.user.dto.OAuthKakaoTokenResponse;
import com.penguin.cuppingnote.user.dto.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.user.feign.OAuthTokenClient;
import com.penguin.cuppingnote.user.feign.OAuthUserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final OAuthKakaoProperties oAuthKakaoProperties;
    private final OAuthTokenClient oAuthTokenClient;
    private final OAuthUserClient oAuthUserClient;

    @Transactional
    public OAuthKakaoUserResponse loginByKakao(
            String thisUrl,
            String authorizationCode
    ) {
        OAuthKakaoTokenResponse oAuthKakaoToken = oAuthTokenClient.getOAuthKakaoToken(
          oAuthKakaoProperties.toOAuthKakaoTokenRequestWith(thisUrl, authorizationCode)
        );

        return oAuthUserClient.getKakaoUserEntity(oAuthKakaoToken.getAccessTokenForAuthorization());

        // 회원 가입 vs 로그인 처리

        // jwt 발행

        // jwt 반환
    }
}
