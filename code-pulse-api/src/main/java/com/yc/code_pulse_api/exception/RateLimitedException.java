package com.yc.code_pulse_api.exception;

public class RateLimitedException extends GitHubApiException {
    public RateLimitedException(String message) {
        super(message);
    }
}