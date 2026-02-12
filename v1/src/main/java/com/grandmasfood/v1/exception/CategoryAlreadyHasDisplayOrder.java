package com.grandmasfood.v1.exception;

public class CategoryAlreadyHasDisplayOrder extends RuntimeException {
    public CategoryAlreadyHasDisplayOrder(Integer displayOrder) {
        super("One category already exists with the same display order: " + displayOrder + ". order cannot be repeated");
    }
}
