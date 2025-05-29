package com.example.msexchange.decoder;

import com.example.msexchange.exception.CustomFeignException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;


import static com.example.msexchange.decoder.JsonNodeFieldName.MESSAGE;
import static com.example.msexchange.exception.ErrorMessage.CLIENT_EXCEPTION;
import static com.example.msexchange.exception.ErrorMessage.CUSTOM_FEIGN_EXCEPTION;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String message = CUSTOM_FEIGN_EXCEPTION.getMessage() + response.reason();

        return new CustomFeignException(message, response.status());
    }
}
