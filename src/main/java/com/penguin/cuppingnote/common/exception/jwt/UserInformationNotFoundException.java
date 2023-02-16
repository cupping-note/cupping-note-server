package com.penguin.cuppingnote.common.exception.jwt;


import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class UserInformationNotFoundException extends ApplicationException {

    public UserInformationNotFoundException() {
        super(ExceptionType.USER_INFORMATION_NOT_FOUND.getHttpStatus(), ExceptionType.USER_INFORMATION_NOT_FOUND.getMessage());
    }
}
