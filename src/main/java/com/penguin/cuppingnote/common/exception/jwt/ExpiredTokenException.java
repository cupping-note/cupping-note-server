package com.penguin.cuppingnote.common.exception.jwt;

import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class ExpiredTokenException extends ApplicationException {

    public ExpiredTokenException() {
        super(ExceptionType.EXPIRED_TOKEN.getHttpStatus(), ExceptionType.EXPIRED_TOKEN.getMessage());
    }
}
