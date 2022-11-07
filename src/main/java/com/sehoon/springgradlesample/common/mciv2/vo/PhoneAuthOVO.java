package com.sehoon.springgradlesample.common.mciv2.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhoneAuthOVO {
    private String authDateTime;
    private String transNumber;
    private String authResultCode;
    private String authResultMessage;
    private String linkInfoNo;
}
