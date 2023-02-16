package com.penguin.cuppingnote.common.exception.user;


import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException() {
        super(ExceptionType.USER_NOT_FOUND.getHttpStatus(), ExceptionType.USER_NOT_FOUND.getMessage());
    }
}
