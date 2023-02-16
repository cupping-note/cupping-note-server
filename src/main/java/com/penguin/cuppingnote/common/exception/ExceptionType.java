package com.penguin.cuppingnote.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    // JWT
    USER_INFORMATION_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보가 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    CANNOT_SET_TOKEN(HttpStatus.BAD_REQUEST, "토큰 상태를 변경할 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "서버에서 발급한 토큰이 아닙니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
