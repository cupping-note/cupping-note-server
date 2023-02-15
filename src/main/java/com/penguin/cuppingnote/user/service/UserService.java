package com.penguin.cuppingnote.user.service;

import com.penguin.cuppingnote.oauth.service.OAuthService;
import com.penguin.cuppingnote.user.domain.User;
import com.penguin.cuppingnote.user.domain.UserRepository;
import com.penguin.cuppingnote.oauth.dto.OAuthKakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final OAuthService oAuthService;
    private final UserRepository userRepository;

    @Transactional
    public OAuthKakaoUserResponse loginByKakao(
            String thisUrl,
            String authorizationCode
    ) {
        OAuthKakaoUserResponse kakaoUserEntity = oAuthService.getKakaoUserEntity(thisUrl, authorizationCode);

        User user = userRepository.findByEmail(kakaoUserEntity.getKakaoAccount().getEmail())
                .orElseGet(() -> userRepository.save(kakaoUserEntity.toUserEntity()));

        return null;

        // jwt 발행

        // jwt 반환
    }
}
