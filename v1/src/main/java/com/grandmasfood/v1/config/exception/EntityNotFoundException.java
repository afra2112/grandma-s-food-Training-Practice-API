package com.grandmasfood.v1.config.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, String identification) {
        super(entity + " not found with id: " + identification + ".");
    }
}
