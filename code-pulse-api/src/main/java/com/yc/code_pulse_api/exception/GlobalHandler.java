package com.yc.code_pulse_api.exception;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalHandler {
    private static final Logger log = LogManager.getLogger(GlobalHandler.class);
    
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        log.error("Unauthorized error: ", ex);
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), "UNAUTHORIZED_ERROR");
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex) {
        log.error("Forbidden error: ", ex);
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), "FORBIDDEN_ERROR");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        log.error("Resource not found: ", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "RESOURCE_NOT_FOUND");
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ApiError> handleValidation(ValidationException ex) {
        log.error("Validation error: ", ex);
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), "VALIDATION_ERROR");
    }

    @ExceptionHandler(RateLimitedException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<ApiError> handleRateLimit(RateLimitedException ex) {
        log.error("Rate limit exceeded: ", ex);
        return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage(), "RATE_LIMIT_EXCEEDED");
    }

    @ExceptionHandler(GitHubApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleGitHubApi(GitHubApiException ex) {
        log.error("GitHub API error: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "GITHUB_API_ERROR");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {
        log.error("Unexpected error occurred: ", ex);
        return buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred",
            "INTERNAL_SERVER_ERROR"
        );
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String message, String errorCode) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .errorCode(errorCode)
                .build();
        return new ResponseEntity<>(apiError, status);
    }

   
}