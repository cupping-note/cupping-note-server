package com.penguin.cuppingnote.common.exception.db;

import com.penguin.cuppingnote.common.exception.ApplicationException;
import com.penguin.cuppingnote.common.exception.ExceptionType;

public class DBViolationException extends ApplicationException {

    public DBViolationException() {
        super(ExceptionType.DB_VIOLATION.getHttpStatus(), ExceptionType.DB_VIOLATION.getMessage());
    }
}
