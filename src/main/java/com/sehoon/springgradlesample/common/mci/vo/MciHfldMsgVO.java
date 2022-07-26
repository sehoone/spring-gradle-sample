package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciHfldMsgVO{
    private int _offset;

    public MciHfldMsgVO(){
        this._offset = 0;
    }

    public MciHfldMsgVO(int iOffset){
        this._offset = iOffset;
    }

    private MciCommMsgHdrVo msgHddvValu;

    private MciCommMsgDataVo msgDtdvValu;

    public int getOffset(){
        return _offset;
    }
}