package com.grandmasfood.v1.config.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    USER_ALREADY_EXISTS("ERR001"),
    SPRING_VALIDATION_EXCEPTION("ERR002");

    private String code;

    private ErrorCodeEnum(String code){
        this.code = code;
    }

}
