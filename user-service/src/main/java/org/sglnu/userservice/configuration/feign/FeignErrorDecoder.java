package org.sglnu.userservice.configuration.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.sglnu.userservice.exception.ClientErrorException;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new ClientErrorException(response.body().toString(), response.status());
        } else if (response.status() >= 500 && response.status() <= 599) {
            return new IllegalStateException("Unexpected error occurred while communicating to another service!");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }

}
