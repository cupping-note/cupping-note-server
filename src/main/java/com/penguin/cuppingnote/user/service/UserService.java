package com.penguin.cuppingnote.user.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.penguin.cuppingnote.jwt.Jwt;
import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.jwt.JwtAuthenticationToken;
import com.penguin.cuppingnote.oauth.service.OAuthService;
import com.penguin.cuppingnote.user.domain.session.SessionRepository;
import com.penguin.cuppingnote.user.domain.user.User;
import com.penguin.cuppingnote.user.domain.user.UserRepository;
import com.penguin.cuppingnote.oauth.dto.resonse.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.user.dto.request.UserKakaoLoginRequestDto;
import com.penguin.cuppingnote.user.dto.response.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Jwt jwt;

    @Transactional
    public UserLoginResponseDto loginByKakao(UserKakaoLoginRequestDto userKakaoLoginRequestDto) {
        final OAuthKakaoUserResponse kakaoUserEntity = oAuthService.getKakaoUserEntity(userKakaoLoginRequestDto);

        final User user = userRepository.findByEmail(kakaoUserEntity.getKakaoAccount().getEmail())
                .orElseGet(() -> userRepository.save(kakaoUserEntity.toUserEntity()));

        final JwtAuthentication authentication = getJwtBy(user.getEmail());

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

    private JwtAuthentication getJwtBy(String email) {
        final JwtAuthenticationToken authToken = new JwtAuthenticationToken(email);

        final Authentication resultToken = authenticationManager.authenticate(authToken);

        return (JwtAuthentication) resultToken.getPrincipal();
    }

    private DecodedJWT decode(final JwtAuthentication authentication) {
        return jwt
                .getJwtVerifier()
                .verify(authentication.getRefreshToken());
    }
}
