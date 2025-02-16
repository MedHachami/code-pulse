package com.yc.code_pulse_api.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


import lombok.Builder;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String errorCode;
    private List<String> details;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> additionalData;
}
