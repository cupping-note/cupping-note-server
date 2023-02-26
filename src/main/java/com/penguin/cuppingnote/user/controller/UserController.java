package com.penguin.cuppingnote.user.controller;

import com.penguin.cuppingnote.jwt.JwtAuthentication;
import com.penguin.cuppingnote.user.dto.request.UserKakaoLoginRequestDto;
import com.penguin.cuppingnote.user.dto.response.UserLoginResponseDto;
import com.penguin.cuppingnote.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"인증 및 사용자를 관리하는 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "카카오 로그인을 통한 회원 가입 및 로그인", description = "신규 이메일이면 회원가입을, 기존 이메일이면 로그인을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 및 로그인 완료", content = @Content(schema = @Schema(implementation = UserLoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "redirect uri를 입력하지 않거나 유효하지 않은 경우"),
            @ApiResponse(responseCode = "400", description = "authorization code를 입력하지 않거나 유효하지 않은 경우")
    })
    @PostMapping("/kakao/login")
    public ResponseEntity<UserLoginResponseDto> loginByKakao(@Valid @RequestBody UserKakaoLoginRequestDto userKakaoLoginRequestDto) {
        return ResponseEntity.ok(
                userService.loginByKakao(userKakaoLoginRequestDto)
        );
    }

    @GetMapping
    public ResponseEntity<String> getUser(@AuthenticationPrincipal final JwtAuthentication authentication) {
        return ResponseEntity.ok(
                authentication.email + " " + authentication.userId
        );
    }
}
