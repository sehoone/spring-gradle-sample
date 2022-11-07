package com.sehoon.springgradlesample.common.mciv2.message;

import com.sehoon.springgradlesample.common.mciv2.message.header.MciAbstractMessage;
import com.sehoon.springgradlesample.common.mciv2.message.header.MciDataField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 통신사 인증(SCI 평가 정보) - Request
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarrierCertificationRequest extends MciAbstractMessage {

    @MciDataField(length = 1, required = true)
    private String dalScCd;

    @MciDataField(length = 12)
    private String csNo;

    @MciDataField(length = 13, required = true)
    private String rtrtNo;

    @MciDataField(length = 200, required = true)
    private String cstHanNm;

    @MciDataField(length = 2, required = true)
    private String mbccScCd;

    @MciDataField(length = 50, required = true)
    private String cephNo;

    @MciDataField(length = 6)
    private String ahrzNo;

    @MciDataField(length = 20)
    private String selfAhrzDt;

    @MciDataField(length = 100)
    private String traNoN01;

    @MciDataField(length = 8)
    private String betrCd;
}
