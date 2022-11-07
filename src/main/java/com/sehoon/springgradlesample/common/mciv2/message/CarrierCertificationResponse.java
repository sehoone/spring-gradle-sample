package com.sehoon.springgradlesample.common.mciv2.message;

import com.sehoon.springgradlesample.common.mciv2.message.header.MciAbstractMessage;
import com.sehoon.springgradlesample.common.mciv2.message.header.MciDataField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 통신사 인증(SCI 평가 정보) - Response
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarrierCertificationResponse extends MciAbstractMessage {

    @MciDataField(length = 20)
    private String authDateTime;

    @MciDataField(length = 2)
    private String authResultCd;

    @MciDataField(length = 100)
    private String authResultCdName;

    @MciDataField(length = 100)
    private String traNoN01;

    @MciDataField(length = 20)
    private String traIdN02;

    @MciDataField(length = 20)
    private String traDateTime;

    @MciDataField(length = 88)
    private String linkInfoNo;

    @MciDataField(length = 5)
    private String passwordErrCnt;

    @MciDataField(length = 10)
    private String phoneAuthResultCd;
}
