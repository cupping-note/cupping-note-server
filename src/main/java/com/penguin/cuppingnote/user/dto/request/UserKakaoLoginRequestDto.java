package com.penguin.cuppingnote.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKakaoLoginRequestDto {

    @Schema(
            description = "Kakao Console에서 설정한 authorization code를 발급 받는 redirect uri",
            example = "http://localhost:8080/api/oauth/callback/kakao",
            required = true
    )
    @NotBlank(message = "redirect uri가 입력되지 않았습니다.")
    private String redirectUri;

    @Schema(
            description = "Kakao auth server로 부터 발급 받은 authorization code",
            example = "EWl56PKWIV3y6H6vdigH3gc7wldwjXKtDUdIj4d16SgEk-C4THbcA6jOBPnDAiHgSHjKcAo9cuoAAAGGh3z07g",
            required = true
    )
    @NotBlank(message = "authorization code가 입력되지 않았습니다.")
    private String authorizationCode;
}
