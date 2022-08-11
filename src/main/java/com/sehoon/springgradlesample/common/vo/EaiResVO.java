package com.sehoon.springgradlesample.common.vo;

import com.sehoon.springgradlesample.common.mciv2.vo.MciCommHeaderVO;
import com.sehoon.springgradlesample.common.mciv2.vo.MciCommMsgVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EaiResVO<T>{
    private MciCommHeaderVO tgrmCmnnhddvValu;

    private MciCommMsgVO tgrmMsdvValu;

    private T tgrmDtdvValu;
}
