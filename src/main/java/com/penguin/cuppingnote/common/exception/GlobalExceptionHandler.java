package com.penguin.cuppingnote.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static java.util.Objects.requireNonNull;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String VALID_LOG_FORMAT = "Class : {}, Error : {}, ErrorMessage : {}";
    private static final String LOG_FORMAT = "Class : {}, ErrorMessage : {}";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        final String errorMessage = requireNonNull(e.getFieldError())
                .getDefaultMessage();

        log.warn(VALID_LOG_FORMAT, e.getClass().getSimpleName(), "@Valid", errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            final NoHandlerFoundException e
    ) {
        final String errorMessage = e.getMessage();

        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException e
    ) {
        final String errorMessage = e.getMessage();

        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(
            final ApplicationException e
    ) {
        final String errorMessage = e.getMessage();

        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrityViolationException(
            final DataIntegrityViolationException e
    ) {
       final String errorMessage = e.getMessage();

       log.error(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

        ExceptionType exceptionType = DataIntegrityViolationExceptionType.findByErrorMessage(errorMessage)
                .getExceptionType();

        return ResponseEntity
               .status(exceptionType.getHttpStatus())
                .body(new ErrorResponse(exceptionType.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> dataAccessException(
            final DataAccessException e
    ) {
        final String errorMessage = e.getMessage();

        log.error(LOG_FORMAT, e.getClass().getSimpleName(), errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(
            final RuntimeException e
    ) {
        log.error(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SERVER_ERROR"));
    }
}
