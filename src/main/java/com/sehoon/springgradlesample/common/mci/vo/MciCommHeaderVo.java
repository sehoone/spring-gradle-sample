package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommHeaderVo{
    private String tgrmLencn = "00000000";

    private String glbId;

    private String pgrsSriaNo;

    private String trgmVrsnInfoValu = "001";

    private String tgrmEncrYn = "0";

    private String gpcoCd = "S016";

    private String appliDutjCd;

    private String appliDtptDutjCd = "DH1";

    private String frbuCd;

    private String cmouDutjCd;

    private String cmouCssfCd;

    private String cmouTraCd;

    private String rcvSvcId;

    private String rsltRcvSvcId;

    private String tgrmCreaChnnTypeCd = "1";

    private String lnggDvsnCd = "KR";

    private String simulTraYn = "N";

    private String itrfId;

    private String reqRspnScCd = "S";

    private String tnsmTypeCd = "S";

    private String envrTypeCd;

    private String inqrTraTypeCd;

    private String reqTgrmTnsmDtptDt;

    private String strYmd;

    private String scrnId;

    private String scrnBttnId;

    private String userIpAddr;

    private String drtmCd;

    private String userId;

    private String indvCtinRoleCd;

    private String acntOgnzNo;

    private String rspnTgrmTnsmDtptDt;

    private String tgrmDalRsltCd;

    private String ognzAsrtCd;

    private String ognzLeveCd = "ZZ";

    private String psmrAsrtCd;

    private String sbsnRulpAsrtCd;

    private String bsduCd;

    private String bsquCd;

    private String linkPrafDutyCd;

    private String indvInfoLogWritYn = "N";

    private String prepImhdNm;

}
