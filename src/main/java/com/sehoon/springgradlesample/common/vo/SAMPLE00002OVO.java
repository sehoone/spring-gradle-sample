package com.sehoon.springgradlesample.common.vo;

import com.sehoon.springgradlesample.common.mci.util.CcFwUtil;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;
import com.sehoon.springgradlesample.common.mci.vo.MciHfldMsgVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SAMPLE00002OVO{
    private int _offset;

    public SAMPLE00002OVO(){
        this._offset = 0;
    }

    public SAMPLE00002OVO(int iOffset){
        this._offset = iOffset;
    }
    
    private MciCommHeaderVo tgrmCmnnhddvValu;

    private MciHfldMsgVO tgrmMsdvValu;

    private SpecifyDataVO tgrmDtdvValu;

    public void unMarshalFld( byte[] bytes ) throws Exception{
        unMarshalFld( bytes, "UTF-8" ); 
    }

    public void unMarshalFld(byte[] bytes, String encode) throws Exception {
        this.tgrmCmnnhddvValu = new MciCommHeaderVo();
        this.tgrmCmnnhddvValu.unMarshalFld(CcFwUtil.bytesToByte(bytes, _offset, 800));
        _offset += 800;
        this.tgrmMsdvValu = new MciHfldMsgVO();
        this.tgrmMsdvValu.unMarshalFld(CcFwUtil.bytesToByte(bytes, _offset, 420));
        _offset += 420;
        this.tgrmDtdvValu = new SpecifyDataVO();
        this.tgrmDtdvValu.unMarshalFld(CcFwUtil.bytesToByte(bytes, _offset, 73));
        _offset += 73;
    }
}