package com.penguin.cuppingnote.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum DataIntegrityViolationExceptionType {

    UNIQUE_USER_EMAIL("PUBLIC.USERS_PK_INDEX_4 ON PUBLIC.USERS(EMAIL NULLS FIRST)", ExceptionType.USER_ALREADY_EXIST),
    DEFAULT(null, ExceptionType.DB_VIOLATION);

    private final String errorMessage;
    private final ExceptionType exceptionType;

    public static DataIntegrityViolationExceptionType findByErrorMessage(final String errorMessage) {
        return Stream.of(values())
                .filter(exceptionType -> StringUtils.contains(errorMessage, exceptionType.errorMessage))
                .findFirst()
                .orElse(DEFAULT);
    }
}
