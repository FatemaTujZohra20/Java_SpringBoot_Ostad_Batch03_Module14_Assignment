package com.assignment14.ProductInventoryAPI.exceptions;

public class InvalidSkuFormatException extends RuntimeException {
    public InvalidSkuFormatException (String message) {
        super(message);
    }
}
