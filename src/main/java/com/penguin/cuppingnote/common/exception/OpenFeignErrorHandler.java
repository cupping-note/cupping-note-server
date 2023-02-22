package com.penguin.cuppingnote.common.exception;

import com.penguin.cuppingnote.common.exception.openfeign.OpenFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OpenFeignErrorHandler implements ErrorDecoder {
    private final StringDecoder stringDecoder;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            return new OpenFeignException(stringDecoder.decode(response, String.class).toString());
        } catch (IOException e) {
            throw new OpenFeignException(response.reason());
        }
    }
}
