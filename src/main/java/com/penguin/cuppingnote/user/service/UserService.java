package com.penguin.cuppingnote.user.service;

import com.penguin.cuppingnote.user.domain.User;
import com.penguin.cuppingnote.user.domain.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public OAuthKakaoUserResponse loginByKakao(
            String thisUrl,
            String authorizationCode
    ) {
        OAuthKakaoTokenResponse oAuthKakaoToken = oAuthTokenClient.getOAuthKakaoToken(
          oAuthKakaoProperties.toOAuthKakaoTokenRequestWith(thisUrl, authorizationCode)
        );

        OAuthKakaoUserResponse kakaoUserEntity = oAuthUserClient.getKakaoUserEntity(oAuthKakaoToken.getAccessTokenForAuthorization());

        User user = userRepository.findByEmail(kakaoUserEntity.getKakaoAccount().getEmail())
                .orElseGet(() -> userRepository.save(kakaoUserEntity.toUserEntity()));

        return null;

        // jwt 발행

        // jwt 반환
    }
}
