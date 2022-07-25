package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommMsgHdrVo{
    private String msgTnsmTypeCd;
    private String msdvLencn;
    private String msgRpttCc;
}

// CDPNCSO00016IVo inVo = new CDPNCSO00016IVo();
// CcFwUtil.initializeMembers(inVo);
     
// if( inVo.getTgrmDtdvValu() != null) {
//  IndvCsinDtptInqrInVo indvCsinDtptInqrInDto = new IndvCsinDtptInqrInVo();
//  indvCsinDtptInqrInDto.setInqrScCd("99");
//  indvCsinDtptInqrInDto.setCstInqrCt(csNo);
//  inVo.getTgrmDtdvValu().getIndvCsinDtptInqrInDto().add(indvCsinDtptInqrInDto);
// }

// CDPNCSO00016OVo outVo = DpMciUtil.mciCallSerivce(inVo, CDPNCSO00016OVo.class, "CDPNCSO00016", "ONCSC1340", "R");