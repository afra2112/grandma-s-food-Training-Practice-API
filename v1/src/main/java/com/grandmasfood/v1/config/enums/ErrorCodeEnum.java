package com.grandmasfood.v1.config.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    USER_ALREADY_EXISTS("ERR001"),
    SPRING_VALIDATION_EXCEPTION("ERR002"),
    SERVER_ERROR("ERROO3"),
    ENTITY_NOT_FOUND("ERR004"),
    CONSTRAINT_VALIDATION_EXCEPTION("ERR005");

    private String code;

    private ErrorCodeEnum(String code){
        this.code = code;
    }

}
