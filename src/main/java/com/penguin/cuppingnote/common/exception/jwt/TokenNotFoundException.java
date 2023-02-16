package com.penguin.cuppingnote.common.exception.jwt;


import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class TokenNotFoundException extends ApplicationException {

    public TokenNotFoundException() {
        super(ExceptionType.TOKEN_NOT_FOUND.getHttpStatus(), ExceptionType.TOKEN_NOT_FOUND.getMessage());
    }
}
