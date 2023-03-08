package com.sehoon.springgradlesample.common.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiExceptionDTO {
    private String errorCode;
    private String errorMessage;

    @Builder
    public ApiExceptionDTO(HttpStatus status, String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}