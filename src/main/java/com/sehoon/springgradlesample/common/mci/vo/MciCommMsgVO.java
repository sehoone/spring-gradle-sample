package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommMsgVO{
    private MciCommMsgHdrVo msgHddvValu;

    private MciCommMsgDataVo msgDtdvValu;
}