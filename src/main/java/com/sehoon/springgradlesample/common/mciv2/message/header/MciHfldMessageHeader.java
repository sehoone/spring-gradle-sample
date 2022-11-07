package com.sehoon.springgradlesample.common.mciv2.message.header;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MciHfldMessageHeader extends MciAbstractMessage {

    @MciDataField(length = 1)
    private String msgTnsmTypeCd;

    @MciDataField(length = 8)
    private String msdvLencn;

    @MciDataField(length = 2)
    private String msgRpttCc;
}
