package com.seoplog.exception.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    @Builder
    private ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static ErrorResponse of(String code, String message, BindingResult bindingResult) {
        ErrorResponse response = of(code, message);
        bindingResult.getFieldErrors()
                .forEach(error -> response.addValidation(error.getField(), error.getDefaultMessage()));
        return response;
    }
}
