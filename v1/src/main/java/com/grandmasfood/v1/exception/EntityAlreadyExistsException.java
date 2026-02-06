package com.grandmasfood.v1.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String entity, String identification) {
        super(entity + " already exists by the following id: " + identification);
    }
}