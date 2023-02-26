package com.penguin.cuppingnote.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {

    @Schema(
            description = "회원 가입 및 로그인 성공 이후 발급한 jwt refresh token",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoyLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6ImN1cHBpbmdfbm90ZSIsImV4cCI6MTY3OTkwMDE1MCwiaWF0IjoxNjc3MzA4MTUwLCJlbWFpbCI6ImhhbWEyMjI5NjY4QG5hdmVyLmNvbSJ9.KVHrzOwj1DNZ3uhBUc1U5KloKYo-EoYd1MEWwVK9dsE-Ggvm12dw0BUSJeAlL-uzSieMAOPDhNVuyEb-fPRRRA"
    )
    private String refreshToken;

    @Schema(
            description = "회원 가입 및 로그인 성공 이후 발급한 jwt access token",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoyLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6ImN1cHBpbmdfbm90ZSIsImV4cCI6MTY3OTkwMDE1MCwiaWF0IjoxNjc3MzA4MTUwLCJlbWFpbCI6ImhhbWEyMjI5NjY4QG5hdmVyLmNvbSJ9.KVHrzOwj1DNZ3uhBUc1U5KloKYo-EoYd1MEWwVK9dsE-Ggvm12dw0BUSJeAlL-uzSieMAOPDhNVuyEb-fPRRRA"
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
