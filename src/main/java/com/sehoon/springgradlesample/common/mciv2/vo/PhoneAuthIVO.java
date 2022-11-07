package com.sehoon.springgradlesample.common.mciv2.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhoneAuthIVO {
    private String rsrso;
    private String name;
    private String carrier;
    private String phoneNumber;

    private String authNumber;
    private String authDateTime;
    private String transNumber;
}
