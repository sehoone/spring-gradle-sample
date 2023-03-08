package com.sehoon.springgradlesample.common.advice;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sehoon.springgradlesample.common.dto.ApiExceptionDTO;
import com.sehoon.springgradlesample.common.enums.ExceptionEnum;
import com.sehoon.springgradlesample.common.exception.ApiException;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(HttpServletRequest request, final ApiException e) {

        String message = StringUtils.defaultString(e.getMessage(), e.getError().getMessage());

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionDTO.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(message)
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.BAD_REQUEST_EXCEPTION.getStatus())
                .body(ApiExceptionDTO.builder()
                        .errorCode(ExceptionEnum.BAD_REQUEST_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionDTO.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }
    
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(HttpServletRequest request, final MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.ARGUMENT_NOT_VALIDATION_EXCEPTION.getStatus())
                .body(ApiExceptionDTO.builder()
                        .errorCode(ExceptionEnum.ARGUMENT_NOT_VALIDATION_EXCEPTION.getCode())
                        .errorMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(HttpServletRequest request, final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionDTO.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }
}