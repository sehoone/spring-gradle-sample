package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommMsgHdrVo{
    private int msgTnsmTypeCd;
    private int msdvLencn;
    private int msgRpttCc;
}