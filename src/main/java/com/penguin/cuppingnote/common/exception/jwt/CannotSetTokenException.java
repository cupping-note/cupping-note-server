package com.penguin.cuppingnote.common.exception.jwt;


import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class CannotSetTokenException extends ApplicationException {

    public CannotSetTokenException() {
        super(ExceptionType.CANNOT_SET_TOKEN.getHttpStatus(), ExceptionType.CANNOT_SET_TOKEN.getMessage());
    }
}
