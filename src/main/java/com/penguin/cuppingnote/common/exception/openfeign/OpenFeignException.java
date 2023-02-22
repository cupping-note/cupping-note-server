package com.penguin.cuppingnote.common.exception.openfeign;

import com.penguin.cuppingnote.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class OpenFeignException extends ApplicationException {

    public OpenFeignException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
