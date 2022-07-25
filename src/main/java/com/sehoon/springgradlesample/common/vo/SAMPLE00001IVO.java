package com.sehoon.springgradlesample.common.vo;

import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SAMPLE00001IVO{
    private MciCommHeaderVo tgrmCmnnhddvValu;

    private SpecifyDataVO tgrmDtdvValu;
}
