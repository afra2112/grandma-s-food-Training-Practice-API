package com.grandmasfood.v1.config.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    ERR001("ENTITY_ALREADY_EXISTS"),
    ERR002("SPRING_VALIDATION_EXCEPTION"),
    ERR003("SERVER_ERROR"),
    ERR004("ENTITY_NOT_FOUND"),
    ERR005("CONSTRAINT_VALIDATION_EXCEPTION"),
    ERR006("SAME_REQUEST_DATA_COMPARED_TO_DB"),
    ERR007("INVALID_UUID_INPUT");

    private String code;

    private ErrorCodeEnum(String code){
        this.code = code;
    }

}
