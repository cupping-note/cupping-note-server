package com.penguin.cuppingnote.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKakaoLoginRequestDto {
    private String authorizationCode;
}
