package com.penguin.cuppingnote.oauth.controller;

import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.user.dto.response.UserLoginResponseDto;
import com.penguin.cuppingnote.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final UserService userService;

    @GetMapping("/callback/kakao")
    public ResponseEntity<UserLoginResponseDto> loginByKakao(
            HttpServletRequest request,
            @RequestParam("code") String authorizationCode
    ) {
        return ResponseEntity.ok(
                userService.loginByKakao(
                        request.getRequestURL().toString(),
                        authorizationCode
                )
        );
    }

    @GetMapping
    public String test(
            @AuthenticationPrincipal final JwtAuthentication jwtAuthentication
    ) {
        return jwtAuthentication.userId + "\n" + jwtAuthentication.email + "\n" + jwtAuthentication.token;
    }
}