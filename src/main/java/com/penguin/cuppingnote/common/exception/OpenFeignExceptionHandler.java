package com.penguin.cuppingnote.common.exception;

import com.penguin.cuppingnote.common.exception.openfeign.OpenFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OpenFeignExceptionHandler implements ErrorDecoder {
    private final StringDecoder stringDecoder = new StringDecoder();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            return new OpenFeignException(stringDecoder.decode(response, String.class).toString());
        } catch (IOException e) {
            return new OpenFeignException(response.reason());
        }
    }
}
