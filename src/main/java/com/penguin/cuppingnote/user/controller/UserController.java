package com.penguin.cuppingnote.user.controller;

import com.penguin.cuppingnote.user.dto.request.RefreshAccessTokenRequestDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "refresh token을 통한 access token 갱신", description = "유효한 refresh token을 통해 access token을 갱신합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "access token 갱신 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "refresh token을 입력하지 않은 경우"),
            @ApiResponse(responseCode = "401", description = "존재하지 않는 refresh token을 보낸 경우"),
            @ApiResponse(responseCode = "401", description = "refresh token이 만료된 경우")
    })
    @PostMapping("/token")
    public ResponseEntity<String> refreshAccessToken(@Valid @RequestBody RefreshAccessTokenRequestDto refreshAccessTokenRequestDto) {
        return new ResponseEntity<>(
                userService.refreshAccessToken(refreshAccessTokenRequestDto),
                HttpStatus.CREATED
        );
    }
}
