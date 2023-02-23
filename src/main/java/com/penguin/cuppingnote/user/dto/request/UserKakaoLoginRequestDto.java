package com.penguin.cuppingnote.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKakaoLoginRequestDto {

    @NotBlank(message = "redirect uri가 입력되지 않았습니다.")
    private String redirectUri;

    @NotBlank(message = "authorization code가 입력되지 않았습니다.")
    private String authorizationCode;
}
