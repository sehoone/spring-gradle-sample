package com.sehoon.springgradlesample.common.mciv2.constant;

public enum PhoneAuthResultCdEnum {
    SUCCESS("NME0000", "정상"),
    FAIL_AUTH_CODE("NME0070", "인증번호불일치");

    private final String code;
    private final String message;

    PhoneAuthResultCdEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
