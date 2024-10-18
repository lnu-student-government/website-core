package org.sglnu.userservice.configuration.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.sglnu.userservice.exception.ClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = getResponseBody(response);

        if (response.status() >= 400 && response.status() <= 499) {
            logger.error("Client error for method [{}]: status {} - {}", methodKey, response.status(), responseBody);
            return new ClientErrorException(responseBody, response.status());
        } else if (response.status() >= 500 && response.status() <= 599) {
            logger.error("Server error for method [{}]: status {} - {}", methodKey, response.status(), responseBody);
            return new IllegalStateException("Unexpected error occurred while communicating with another service: " + responseBody);
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }

    private String getResponseBody(Response response) {
        if (response.body() == null) {
            return "Response body is missing";
        }
        try {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to read response body", e);
            return "Error reading response body";
        }
    }
}
