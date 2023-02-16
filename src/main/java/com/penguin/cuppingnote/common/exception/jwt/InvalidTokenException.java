package com.penguin.cuppingnote.common.exception.jwt;

import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class InvalidTokenException extends ApplicationException {

    public InvalidTokenException() {
        super(ExceptionType.INVALID_TOKEN.getHttpStatus(), ExceptionType.INVALID_TOKEN.getMessage());
    }
}
