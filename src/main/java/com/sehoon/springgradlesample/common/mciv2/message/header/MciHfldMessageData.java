package com.sehoon.springgradlesample.common.mciv2.message.header;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MciHfldMessageData extends MciAbstractMessage {

    @MciDataField(length = 8)
    private String msgCd;

    @MciDataField(length = 1)
    private String msgPrnAttrCd;

    @MciDataField(length = 200)
    private String msgCt;

    @MciDataField(length = 200)
    private String anxMsgCt;
}