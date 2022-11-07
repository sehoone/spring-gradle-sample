package com.sehoon.springgradlesample.common.mci.vo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sehoon.springgradlesample.common.mci.util.MciUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommHeaderVO{

    private int _offset;

    public MciCommHeaderVO(){
        this._offset = 0;
    }

    public MciCommHeaderVO(int iOffset){
        this._offset = iOffset;
    }

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

    public byte[] marshalFld(){
        return marshalFld( "UTF-8" ); 
    }

	public byte[] marshalFld(String encode){
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write( MciUtil.strToSpBytes(this.tgrmLencn , 8, encode ) );
            out.write( MciUtil.strToSpBytes(this.glbId , 37, encode ) );
            out.write( MciUtil.strToSpBytes(this.pgrsSriaNo , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.trgmVrsnInfoValu , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.tgrmEncrYn , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.gpcoCd , 4, encode ) );
            out.write( MciUtil.strToSpBytes(this.appliDutjCd , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.appliDtptDutjCd , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.frbuCd , 4, encode ) );
            out.write( MciUtil.strToSpBytes(this.cmouDutjCd , 4, encode ) );
            out.write( MciUtil.strToSpBytes(this.cmouCssfCd , 8, encode ) );
            out.write( MciUtil.strToSpBytes(this.cmouTraCd , 32, encode ) );
            out.write( MciUtil.strToSpBytes(this.rcvSvcId , 120, encode ) );
            out.write( MciUtil.strToSpBytes(this.rsltRcvSvcId , 120, encode ) );
            out.write( MciUtil.strToSpBytes(this.tgrmCreaChnnTypeCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.lnggDvsnCd , 2, encode ) );
            out.write( MciUtil.strToSpBytes(this.simulTraYn , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.itrfId , 20, encode ) );
            out.write( MciUtil.strToSpBytes(this.reqRspnScCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.tnsmTypeCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.envrTypeCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.inqrTraTypeCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.reqTgrmTnsmDtptDt , 17, encode ) );
            out.write( MciUtil.strToSpBytes(this.strYmd , 8, encode ) );
            out.write( MciUtil.strToSpBytes(this.scrnId , 40, encode ) );
            out.write( MciUtil.strToSpBytes(this.scrnBttnId , 20, encode ) );
            out.write( MciUtil.strToSpBytes(this.userIpAddr , 40, encode ) );
            out.write( MciUtil.strToSpBytes(this.drtmCd , 10, encode ) );
            out.write( MciUtil.strToSpBytes(this.userId , 20, encode ) );
            out.write( MciUtil.strToSpBytes(this.indvCtinRoleCd , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.acntOgnzNo , 7, encode ) );
            out.write( MciUtil.strToSpBytes(this.rspnTgrmTnsmDtptDt , 17, encode ) );
            out.write( MciUtil.strToSpBytes(this.tgrmDalRsltCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.ognzAsrtCd , 2, encode ) );
            out.write( MciUtil.strToSpBytes(this.ognzLeveCd , 2, encode ) );
            out.write( MciUtil.strToSpBytes(this.psmrAsrtCd , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.sbsnRulpAsrtCd , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.bsduCd , 3, encode ) );
            out.write( MciUtil.strToSpBytes(this.bsquCd , 2, encode ) );
            out.write( MciUtil.strToSpBytes(this.linkPrafDutyCd , 10, encode ) );
            out.write( MciUtil.strToSpBytes(this.indvInfoLogWritYn , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.prepImhdNm , 213, encode ) );
            return bout.toByteArray();
        } catch (IOException e) {
            log.error("marshalFld Error:["+ toString()+"]", e);
        }
        return null;
    }

    public void unMarshalFld( byte[] bytes ) throws Exception{
        unMarshalFld( bytes, "UTF-8" ); 
    }

    public void unMarshalFld(byte[] bytes, String encode) throws Exception {
        this.tgrmLencn = MciUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.glbId = MciUtil.getTrimmedString(bytes, _offset, 37, encode);
        _offset += 37;
        this.pgrsSriaNo = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.trgmVrsnInfoValu = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.tgrmEncrYn = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.gpcoCd = MciUtil.getTrimmedString(bytes, _offset, 4, encode);
        _offset += 4;
        this.appliDutjCd = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.appliDtptDutjCd = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.frbuCd = MciUtil.getTrimmedString(bytes, _offset, 4, encode);
        _offset += 4;
        this.cmouDutjCd = MciUtil.getTrimmedString(bytes, _offset, 4, encode);
        _offset += 4;
        this.cmouCssfCd = MciUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.cmouTraCd = MciUtil.getTrimmedString(bytes, _offset, 32, encode);
        _offset += 32;
        this.rcvSvcId = MciUtil.getTrimmedString(bytes, _offset, 120, encode);
        _offset += 120;
        this.rsltRcvSvcId = MciUtil.getTrimmedString(bytes, _offset, 120, encode);
        _offset += 120;
        this.tgrmCreaChnnTypeCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.lnggDvsnCd = MciUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.simulTraYn = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.itrfId = MciUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.reqRspnScCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.tnsmTypeCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.envrTypeCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.inqrTraTypeCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.reqTgrmTnsmDtptDt = MciUtil.getTrimmedString(bytes, _offset, 17, encode);
        _offset += 17;
        this.strYmd = MciUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.scrnId = MciUtil.getTrimmedString(bytes, _offset, 40, encode);
        _offset += 40;
        this.scrnBttnId = MciUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.userIpAddr = MciUtil.getTrimmedString(bytes, _offset, 40, encode);
        _offset += 40;
        this.drtmCd = MciUtil.getTrimmedString(bytes, _offset, 10, encode);
        _offset += 10;
        this.userId = MciUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.indvCtinRoleCd = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.acntOgnzNo = MciUtil.getTrimmedString(bytes, _offset, 7, encode);
        _offset += 7;
        this.rspnTgrmTnsmDtptDt = MciUtil.getTrimmedString(bytes, _offset, 17, encode);
        _offset += 17;
        this.tgrmDalRsltCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.ognzAsrtCd = MciUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.ognzLeveCd = MciUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.psmrAsrtCd = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.sbsnRulpAsrtCd = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.bsduCd = MciUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.bsquCd = MciUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.linkPrafDutyCd = MciUtil.getTrimmedString(bytes, _offset, 10, encode);
        _offset += 10;
        this.indvInfoLogWritYn = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.prepImhdNm = MciUtil.getTrimmedString(bytes, _offset, 213, encode);
        _offset += 213;
    }
}
