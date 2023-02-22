package com.penguin.cuppingnote.user.service;

import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.jwt.JwtAuthenticationToken;
import com.penguin.cuppingnote.oauth.service.OAuthService;
import com.penguin.cuppingnote.user.domain.User;
import com.penguin.cuppingnote.user.domain.UserRepository;
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

    @Transactional
    public UserLoginResponseDto loginByKakao(UserKakaoLoginRequestDto userKakaoLoginRequestDto) {
        final OAuthKakaoUserResponse kakaoUserEntity = oAuthService.getKakaoUserEntity(userKakaoLoginRequestDto);

        final User user = userRepository.findByEmail(kakaoUserEntity.getKakaoAccount().getEmail())
                .orElseGet(() -> userRepository.save(kakaoUserEntity.toUserEntity()));

        final JwtAuthentication authentication = getJwtBy(user.getEmail());

        return UserLoginResponseDto.succeed(
                authentication.token,
                user.getId()
        );
    }

    private JwtAuthentication getJwtBy(String email) {
        final JwtAuthenticationToken authToken = new JwtAuthenticationToken(email);

        final Authentication resultToken = authenticationManager.authenticate(authToken);

        return (JwtAuthentication) resultToken.getPrincipal();
    }
}
