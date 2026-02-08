package com.grandmasfood.v1.config.enums;

import lombok.Getter;

@Getter
public enum OrderByEnum {
    DOCUMENT("document"),
    NAME("nameAndSurname"),
    ADDRESS("shippingAddress");

    private final String dbFieldName;

    OrderByEnum(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }
}
