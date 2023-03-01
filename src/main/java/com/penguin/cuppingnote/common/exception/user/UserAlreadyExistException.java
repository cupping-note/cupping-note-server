package com.penguin.cuppingnote.common.exception.user;

import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ApplicationException {

    public UserAlreadyExistException(HttpStatus httpStatus, String detail) {
        super(ExceptionType.USER_ALREADY_EXIST.getHttpStatus(), ExceptionType.USER_ALREADY_EXIST.getMessage());
    }
}
