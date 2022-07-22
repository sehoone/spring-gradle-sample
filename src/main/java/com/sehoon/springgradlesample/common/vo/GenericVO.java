package com.sehoon.springgradlesample.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenericVO<T>{
    private MciHeaderVO tgrmCmnnhddvValu;

    private MciMsgVO tgrmMsdvValu;

    private T tgrmDtdvValu;
}
