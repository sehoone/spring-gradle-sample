package com.sehoon.springgradlesample.common.mciv2.message.header;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MciHfldMessage extends MciAbstractMessage {

    @MciDataField(length = 11)
    private MciHfldMessageHeader header = new MciHfldMessageHeader();

    @MciDataField(length = 409)
    private MciHfldMessageData data = new MciHfldMessageData();
}