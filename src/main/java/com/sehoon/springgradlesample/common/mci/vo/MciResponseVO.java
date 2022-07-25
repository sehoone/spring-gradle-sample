package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciResponseVO{
    private MciHeaderVO tgrmCmnnhddvValu;

    private MciCommMsgVO tgrmMsdvValu;

    private Object tgrmDtdvValu;
}
