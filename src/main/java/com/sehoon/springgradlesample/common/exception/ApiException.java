package com.sehoon.springgradlesample.common.exception;

import lombok.Getter;
import com.sehoon.springgradlesample.common.enums.ExceptionEnum;

@Getter
public class ApiException extends RuntimeException {
    private ExceptionEnum error;

    public ApiException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }

    public ApiException(ExceptionEnum e, String message) {
        super(message);
        this.error = e;
    }

}