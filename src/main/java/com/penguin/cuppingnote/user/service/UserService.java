package com.penguin.cuppingnote.user.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.penguin.cuppingnote.common.exception.jwt.ExpiredTokenException;
import com.penguin.cuppingnote.common.exception.jwt.TokenNotFoundException;
import com.penguin.cuppingnote.jwt.Jwt;
import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.jwt.JwtAuthenticationProvider;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.oauth.service.OAuthService;
import com.penguin.cuppingnote.user.domain.session.Session;
import com.penguin.cuppingnote.user.domain.session.SessionRepository;
import com.penguin.cuppingnote.user.domain.user.User;
import com.penguin.cuppingnote.user.domain.user.UserRepository;
import com.penguin.cuppingnote.user.dto.request.RefreshAccessTokenRequestDto;
import com.penguin.cuppingnote.user.dto.request.UserKakaoLoginRequestDto;
import com.penguin.cuppingnote.user.dto.response.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.penguin.cuppingnote.oauth.domain.OAuthPlatforms.KAKAO;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Jwt jwt;

    @Transactional
    public UserLoginResponseDto loginByKakao(UserKakaoLoginRequestDto userKakaoLoginRequestDto) {
        final OAuthKakaoUserResponse kakaoUserEntity = oAuthService.getKakaoUserEntity(userKakaoLoginRequestDto);

        final User user = userRepository.findByEmailAndOauthPlatform(kakaoUserEntity.getKakaoAccount().getEmail(), KAKAO)
                .orElseGet(() -> userRepository.save(kakaoUserEntity.toUserEntity()));

        final JwtAuthentication authentication = jwtAuthenticationProvider.issueJwtBy(user.getEmail());

        // refresh token으로 access token 갱신할 때 사용하기 위해 유효한 refresh token 저장
        sessionRepository.save(
                user.toSession(authentication, decode(authentication))
        );

        return UserLoginResponseDto.succeed(
                authentication.getRefreshToken(),
                authentication.getAccessToken(),
                user.getId()
        );
    }

    private DecodedJWT decode(final JwtAuthentication authentication) {
        return jwt
                .getJwtVerifier()
                .verify(authentication.getRefreshToken());
    }

    // 만료된 refresh token 일 때 exception(= unchecked) 발생 시킨 후 토큰 삭제하기 위해 roll back x
    @Transactional(dontRollbackOn = ExpiredTokenException.class)
    public String refreshAccessToken(RefreshAccessTokenRequestDto refreshAccessTokenRequestDto) {
        Session session = sessionRepository.findByRefreshToken(refreshAccessTokenRequestDto.getRefreshToken())
                .orElseThrow(TokenNotFoundException::new);

        if (session.isNotValid()) session.expires();

        return jwtAuthenticationProvider.issueJwtBy(session.getUser().getEmail())
                .getAccessToken();
    }
}
