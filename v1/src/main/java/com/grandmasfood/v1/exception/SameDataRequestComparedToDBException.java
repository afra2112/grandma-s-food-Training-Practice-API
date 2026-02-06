package com.grandmasfood.v1.exception;

public class SameDataRequestComparedToDBException extends RuntimeException {
    public SameDataRequestComparedToDBException(String message) {
        super(message);
    }
}
