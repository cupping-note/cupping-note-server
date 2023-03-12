package com.penguin.cuppingnote.common.exception.pref;


import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class BadPrefRequestException extends ApplicationException {

    public BadPrefRequestException() {
        super(ExceptionType.BAD_PREF_REQUEST.getHttpStatus(), ExceptionType.BAD_PREF_REQUEST.getMessage());
    }
}
