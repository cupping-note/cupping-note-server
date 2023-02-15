package com.penguin.cuppingnote.user.controller;

import com.penguin.cuppingnote.oauth.dto.OAuthKakaoUserResponse;
import com.penguin.cuppingnote.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/callback/kakao")
    public ResponseEntity<OAuthKakaoUserResponse> loginByKakao(
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
}
