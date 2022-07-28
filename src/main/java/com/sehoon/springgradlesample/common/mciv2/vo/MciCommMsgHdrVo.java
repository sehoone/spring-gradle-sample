package com.sehoon.springgradlesample.common.mciv2.vo;

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
public class MciCommMsgHdrVo{
    private int _offset;

    public MciCommMsgHdrVo(){
        this._offset = 0;
    }

    public MciCommMsgHdrVo(int iOffset){
        this._offset = iOffset;
    }

    private String msgTnsmTypeCd;
    private String msdvLencn;
    private String msgRpttCc;

    public byte[] marshalFld(){
        return marshalFld( "UTF-8" ); 
    }

	public byte[] marshalFld(String encode){
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write( MciUtil.strToSpBytes(this.msgTnsmTypeCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.msdvLencn , 8, encode ) );
            out.write( MciUtil.strToSpBytes(this.msgRpttCc , 2, encode ) );
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
        this.msgTnsmTypeCd = MciUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.msdvLencn = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.msgRpttCc = MciUtil.getTrimmedString(bytes, _offset, 2, encode);
        _offset += 2;
    }
}