package com.penguin.cuppingnote.user.controller;

import com.penguin.cuppingnote.user.dto.request.UserKakaoLoginRequestDto;
import com.penguin.cuppingnote.user.dto.response.UserLoginResponseDto;
import com.penguin.cuppingnote.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/kakao/login")
    public ResponseEntity<UserLoginResponseDto> loginByKakao(@Valid @RequestBody UserKakaoLoginRequestDto userKakaoLoginRequestDto) {
        return ResponseEntity.ok(
                userService.loginByKakao(userKakaoLoginRequestDto)
        );
    }
}
