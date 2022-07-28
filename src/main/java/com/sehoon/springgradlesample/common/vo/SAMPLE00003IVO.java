package com.sehoon.springgradlesample.common.vo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sehoon.springgradlesample.common.mciv2.util.MciUtil;
import com.sehoon.springgradlesample.common.mciv2.vo.MciCommHeaderVo;
import com.sehoon.springgradlesample.common.mciv2.vo.MciHfldMsgVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class SAMPLE00003IVO{
    private int _offset;

    public SAMPLE00003IVO(){
        this._offset = 0;
    }

    public SAMPLE00003IVO(int iOffset){
        this._offset = iOffset;
    }

    private MciCommHeaderVo tgrmCmnnhddvValu;
    private MciHfldMsgVO tgrmMsdvValu;
    private SpecifyDataVO tgrmDtdvValu;

    public byte[] marshalFld(){
        return marshalFld( "UTF-8" ); 
    }

	public byte[] marshalFld(String encode){
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write( this.tgrmCmnnhddvValu.marshalFld() );
            out.write( this.tgrmDtdvValu.marshalFld() );
            
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
        this.tgrmCmnnhddvValu.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, 800));
        _offset += 800;
        this.tgrmDtdvValu.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, 73));
        _offset += 73;
    }
}