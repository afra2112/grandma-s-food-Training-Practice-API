package com.grandmasfood.v1.exception;

import com.grandmasfood.v1.config.enums.ErrorCodeEnum;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String entity, String identification) {
        super(entity + " already exists by the following id: " + identification);
    }
}