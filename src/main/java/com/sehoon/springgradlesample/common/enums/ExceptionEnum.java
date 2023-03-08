package com.sehoon.springgradlesample.common.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ExceptionEnum {
    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),
    ARGUMENT_NOT_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "E0004"),
    RESOURCE_IS_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "resource is already exist"),
    RESOURCE_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "resource not found");
    

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}