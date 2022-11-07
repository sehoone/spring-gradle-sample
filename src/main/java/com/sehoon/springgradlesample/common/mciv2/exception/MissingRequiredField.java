package com.sehoon.springgradlesample.common.mciv2.exception;

public class MissingRequiredField extends Exception {
    public MissingRequiredField(String fieldName) {
        super(String.format("'%s' is required field.", fieldName));
    }
}
