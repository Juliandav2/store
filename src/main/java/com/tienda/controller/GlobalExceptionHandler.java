package com.tienda.controller;

import com.tienda.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for the REST layer.
 *
 * <p>
 * Intercepts exceptions thrown by any controller and maps them
 * to appropriate HTTP responses with a consistent error format.
 * </p>
 *
 * <p>Layer: Interface / Presentation</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles all domain exceptions extending BusinessException.
     *
     * <p>Maps each subtype to its appropriate HTTP status code.</p>
     *
     * @param ex the business exception thrown
     * @return structured error response
     */

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        log.warn("Business exception: {}", ex.getMessage());
        if (ex instanceof OrderNotFoundException) {
            return buildResponse(404, "Not Found", ex.getMessage());
        }
        if (ex instanceof InvalidOrderStateException) {
            return buildResponse(409, "Conflict", ex.getMessage());
        }
        if (ex instanceof EmptyOrderException) {
            return buildResponse(422, "Unprocessable Entity", ex.getMessage());
        }
        return buildResponse(400, "Bad Request", ex.getMessage());
    }

    /**
     * Handles IllegalArgumentException → 400 BAD REQUEST
     *
     * @param ex the exception thrown
     * @return structured error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        return buildResponse(400, "Bad Request", ex.getMessage());
    }

    /**
     * Handles any unexpected exception → 500 INTERNAL SERVER ERROR
     *
     * @param e the exception thrown
     * @return structured error response
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        log.error("Unhandled exception: {}", e.getMessage(), e);
        Map<String, Object> error = new java.util.HashMap<>();
        error.put("timestamp", java.time.LocalDateTime.now().toString());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Builds a consistent error response body.
     *
     * @param status  HTTP status code
     * @param error   HTTP status description
     * @param message error description
     * @return ResponseEntity with structured error map
     */

    private ResponseEntity<Map<String, Object>> buildResponse(int status, String error, String message) {
        log.debug("Building error response: status={}, error={}, message={}", status, error, message);
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", status,
                "error", error,
                "message", message
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler (org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation (MethodArgumentNotValidException e) {
        log.warn("Validation failed: {}", e.getMessage());
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", 400);
        errors.put("error", "Validation failed");
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("fields", e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return ResponseEntity.status(400).body(errors);

    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFound(
            org.springframework.web.servlet.resource.NoResourceFoundException e) {
        log.warn("Resource not found: {}", e.getMessage());
        return buildResponse(404, "Not Found", e.getMessage());
    }
}