package com.penguin.cuppingnote.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {

    @Schema(
            description = "회원 가입 및 로그인 성공 이후 발급한 jwt refresh token - 유효 기간 1달",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoyLCJyb2xlcyI6WyJVU0VSIl0s..."
    )
    private String refreshToken;

    @Schema(
            description = "회원 가입 및 로그인 성공 이후 발급한 jwt access token - 유효 기간 1일",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoyLCJyb2xlcyI6WyJVU0VSIl0s..."
    )
    private String accessToken;

    @Schema(
            description = "서버 내 사용자 식별에 사용되는 사용자 ID",
            example = "1"
    )
    private Long userId;

    public static UserLoginResponseDto succeed(
            String refreshToken,
            String accessToken,
            Long userId
    ) {
        return UserLoginResponseDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .userId(userId)
                .build();
    }
}
