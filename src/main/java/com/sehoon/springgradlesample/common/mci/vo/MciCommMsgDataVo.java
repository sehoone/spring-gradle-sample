package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommMsgDataVo{
    private String msgCd;
    private String msgPrnAttrCd;
    private String msgCt;
    private String anxMsgCt;
}