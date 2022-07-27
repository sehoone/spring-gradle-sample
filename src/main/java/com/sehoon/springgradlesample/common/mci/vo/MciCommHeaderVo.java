package com.sehoon.springgradlesample.common.mci.vo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sehoon.springgradlesample.common.mci.util.CcFwUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class MciCommHeaderVo{

    private int _offset;

    public MciCommHeaderVo(){
        this._offset = 0;
    }

    public MciCommHeaderVo(int iOffset){
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
            out.write( CcFwUtil.strToSpBytes(this.tgrmLencn , 8, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.glbId , 37, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.pgrsSriaNo , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.trgmVrsnInfoValu , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.tgrmEncrYn , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.gpcoCd , 4, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.appliDutjCd , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.appliDtptDutjCd , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.frbuCd , 4, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.cmouDutjCd , 4, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.cmouCssfCd , 8, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.cmouTraCd , 32, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.rcvSvcId , 120, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.rsltRcvSvcId , 120, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.tgrmCreaChnnTypeCd , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.lnggDvsnCd , 2, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.simulTraYn , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.itrfId , 20, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.reqRspnScCd , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.tnsmTypeCd , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.envrTypeCd , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.inqrTraTypeCd , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.reqTgrmTnsmDtptDt , 17, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.strYmd , 8, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.scrnId , 40, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.scrnBttnId , 20, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.userIpAddr , 40, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.drtmCd , 10, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.userId , 20, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.indvCtinRoleCd , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.acntOgnzNo , 7, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.rspnTgrmTnsmDtptDt , 17, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.tgrmDalRsltCd , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.ognzAsrtCd , 2, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.ognzLeveCd , 2, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.psmrAsrtCd , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.sbsnRulpAsrtCd , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.bsduCd , 3, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.bsquCd , 2, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.linkPrafDutyCd , 10, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.indvInfoLogWritYn , 1, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.prepImhdNm , 213, encode ) );
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
        this.tgrmLencn = CcFwUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.glbId = CcFwUtil.getTrimmedString(bytes, _offset, 37, encode);
        _offset += 37;
        this.pgrsSriaNo = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.trgmVrsnInfoValu = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.tgrmEncrYn = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.gpcoCd = CcFwUtil.getTrimmedString(bytes, _offset, 4, encode);
        _offset += 4;
        this.appliDutjCd = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.appliDtptDutjCd = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.frbuCd = CcFwUtil.getTrimmedString(bytes, _offset, 4, encode);
        _offset += 4;
        this.cmouDutjCd = CcFwUtil.getTrimmedString(bytes, _offset, 4, encode);
        _offset += 4;
        this.cmouCssfCd = CcFwUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.cmouTraCd = CcFwUtil.getTrimmedString(bytes, _offset, 32, encode);
        _offset += 32;
        this.rcvSvcId = CcFwUtil.getTrimmedString(bytes, _offset, 120, encode);
        _offset += 120;
        this.rsltRcvSvcId = CcFwUtil.getTrimmedString(bytes, _offset, 120, encode);
        _offset += 120;
        this.tgrmCreaChnnTypeCd = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.lnggDvsnCd = CcFwUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.simulTraYn = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.itrfId = CcFwUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.reqRspnScCd = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.tnsmTypeCd = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.envrTypeCd = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.inqrTraTypeCd = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.reqTgrmTnsmDtptDt = CcFwUtil.getTrimmedString(bytes, _offset, 17, encode);
        _offset += 17;
        this.strYmd = CcFwUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.scrnId = CcFwUtil.getTrimmedString(bytes, _offset, 40, encode);
        _offset += 40;
        this.scrnBttnId = CcFwUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.userIpAddr = CcFwUtil.getTrimmedString(bytes, _offset, 40, encode);
        _offset += 40;
        this.drtmCd = CcFwUtil.getTrimmedString(bytes, _offset, 10, encode);
        _offset += 10;
        this.userId = CcFwUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.indvCtinRoleCd = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.acntOgnzNo = CcFwUtil.getTrimmedString(bytes, _offset, 7, encode);
        _offset += 7;
        this.rspnTgrmTnsmDtptDt = CcFwUtil.getTrimmedString(bytes, _offset, 17, encode);
        _offset += 17;
        this.tgrmDalRsltCd = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.ognzAsrtCd = CcFwUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.ognzLeveCd = CcFwUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.psmrAsrtCd = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.sbsnRulpAsrtCd = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.bsduCd = CcFwUtil.getTrimmedString(bytes, _offset, 3, encode);
        _offset += 3;
        this.bsquCd = CcFwUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
        this.linkPrafDutyCd = CcFwUtil.getTrimmedString(bytes, _offset, 10, encode);
        _offset += 10;
        this.indvInfoLogWritYn = CcFwUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.prepImhdNm = CcFwUtil.getTrimmedString(bytes, _offset, 213, encode);
        _offset += 213;
    }
}
