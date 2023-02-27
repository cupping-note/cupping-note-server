package com.penguin.cuppingnote.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshAccessTokenRequestDto {

    @Schema(
            description = "로그인 시 전달받은 refresh token",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoyLCJyb2xlcyI6WyJVU0VSIl0s...",
            required = true
    )
    @NotBlank(message = "refresh token을 입력해주세요.")
    private String refreshToken;
}
