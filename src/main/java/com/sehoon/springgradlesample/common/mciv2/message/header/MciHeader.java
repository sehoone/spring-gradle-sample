package com.sehoon.springgradlesample.common.mciv2.message.header;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MciHeader extends MciAbstractMessage {

    @MciDataField(length = 8)
    private String tgrmLencn = "00000000";

    @MciDataField(length = 37)
    private String glbId;

    @MciDataField(length = 3)
    private String pgrsSriaNo;

    @MciDataField(length = 3)
    private String trgmVrsnInfoValu = "001";

    @MciDataField(length = 1)
    private String tgrmEncrYn = "0";

    @MciDataField(length = 4)
    private String gpcoCd = "S016";

    @MciDataField(length = 3)
    private String appliDutjCd;

    @MciDataField(length = 3)
    private String appliDtptDutjCd = "CHW";

    @MciDataField(length = 4)
    private String frbuCd = "SHL";

    @MciDataField(length = 4)
    private String cmouDutjCd = "ORC1";

    @MciDataField(length = 8)
    private String cmouCssfCd = "1000";

    @MciDataField(length = 32)
    private String cmouTraCd;

    @MciDataField(length = 120)
    private String rcvSvcId;

    @MciDataField(length = 120)
    private String rsltRcvSvcId;

    @MciDataField(length = 1)
    private String tgrmCreaChnnTypeCd = "1";

    @MciDataField(length = 2)
    private String lnggDvsnCd = "KR";

    @MciDataField(length = 1)
    private String simulTraYn = "N";

    @MciDataField(length = 20)
    private String itrfId;

    @MciDataField(length = 1)
    private String reqRspnScCd = "S";

    @MciDataField(length = 1)
    private String tnsmTypeCd = "S";

    @MciDataField(length = 1)
    private String envrTypeCd;

    @MciDataField(length = 1)
    private String inqrTraTypeCd;

    @MciDataField(length = 17)
    private String reqTgrmTnsmDtptDt;

    @MciDataField(length = 8)
    private String strYmd;

    @MciDataField(length = 40)
    private String scrnId;

    @MciDataField(length = 20)
    private String scrnBttnId;

    @MciDataField(length = 40)
    private String userIpAddr;

    @MciDataField(length = 10)
    private String drtmCd;

    @MciDataField(length = 20)
    private String userId;

    @MciDataField(length = 3)
    private String indvCtinRoleCd;

    @MciDataField(length = 7)
    private String acntOgnzNo;

    @MciDataField(length = 17)
    private String rspnTgrmTnsmDtptDt;

    @MciDataField(length = 1)
    private String tgrmDalRsltCd;

    @MciDataField(length = 2)
    private String ognzAsrtCd;

    @MciDataField(length = 2)
    private String ognzLeveCd = "ZZ";

    @MciDataField(length = 3)
    private String psmrAsrtCd;

    @MciDataField(length = 3)
    private String sbsnRulpAsrtCd;

    @MciDataField(length = 3)
    private String bsduCd;

    @MciDataField(length = 2)
    private String bsquCd;

    @MciDataField(length = 10)
    private String linkPrafDutyCd;

    @MciDataField(length = 1)
    private String indvInfoLogWritYn = "N";

    @MciDataField(length = 213)
    private String prepImhdNm;
}
