package com.sehoon.springgradlesample.common.vo;

import com.sehoon.springgradlesample.common.mci.vo.MciCommMsgVO;
import com.sehoon.springgradlesample.common.mci.vo.MciHeaderVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenericVO<T>{
    private MciHeaderVO tgrmCmnnhddvValu;

    private MciCommMsgVO tgrmMsdvValu;

    private T tgrmDtdvValu;
}