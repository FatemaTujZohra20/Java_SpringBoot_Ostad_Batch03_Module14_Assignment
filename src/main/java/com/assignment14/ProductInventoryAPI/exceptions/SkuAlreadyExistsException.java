package com.assignment14.ProductInventoryAPI.exceptions;

public class SkuAlreadyExistsException extends RuntimeException {
    public SkuAlreadyExistsException (String message) {
        super(message);
    }
}
