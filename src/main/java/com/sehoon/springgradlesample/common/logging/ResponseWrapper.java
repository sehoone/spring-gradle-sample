package com.sehoon.springgradlesample.common.logging;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingResponseWrapper;

public class ResponseWrapper extends ContentCachingResponseWrapper {
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
