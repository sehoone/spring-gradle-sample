package com.sehoon.springgradlesample.common.mciv2.exception;

public class MismatchFormatException extends Exception {
    public MismatchFormatException(String fieldName) {
        super(String.format("The format of '%s' dose not match.", fieldName));
    }
}
