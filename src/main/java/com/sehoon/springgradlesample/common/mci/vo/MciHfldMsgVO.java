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
public class MciHfldMsgVO{
    private int _offset;

    public MciHfldMsgVO(){
        this._offset = 0;
    }

    public MciHfldMsgVO(int iOffset){
        this._offset = iOffset;
    }

    private MciCommMsgHdrVO msgHddvValu;

    private MciCommMsgDataVO msgDtdvValu;

    public int getOffset(){
        return _offset;
    }

    public byte[] marshalFld(){
        return marshalFld( "UTF-8" ); 
    }

	public byte[] marshalFld(String encode){
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write( this.msgHddvValu.marshalFld() );
            out.write( this.msgDtdvValu.marshalFld() );
            
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
        this.msgHddvValu = new MciCommMsgHdrVO();
        this.msgHddvValu.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, 11));
        _offset += 11;
        this.msgDtdvValu = new MciCommMsgDataVO();
        this.msgDtdvValu.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, 409));
        _offset += 409;
    }
}