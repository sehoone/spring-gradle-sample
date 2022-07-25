package com.sehoon.springgradlesample.common.vo;

import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;
import com.sehoon.springgradlesample.common.mci.vo.MciCommMsgVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SAMPLE00001OVO{
    private MciCommHeaderVo tgrmCmnnhddvValu;

    private MciCommMsgVO tgrmMsdvValu;

    private SpecifyDataVO tgrmDtdvValu;
}
