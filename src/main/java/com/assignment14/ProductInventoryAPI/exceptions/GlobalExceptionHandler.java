package com.assignment14.ProductInventoryAPI.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        log.error("Validation failed: {}", errors);
        return errors;
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(ProductNotFoundException ex) {
        log.warn("Client error: {}", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }
    
    @ExceptionHandler(InvalidSkuFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidSku(InvalidSkuFormatException ex) {
        log.error("Invalid SKU: {}", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }
    
    @ExceptionHandler(SkuAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleSkuExists(SkuAlreadyExistsException ex) {
        log.error("Conflict: {}", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }
}
