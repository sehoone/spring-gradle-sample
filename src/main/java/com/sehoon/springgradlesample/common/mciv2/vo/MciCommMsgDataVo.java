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
public class MciCommMsgDataVO{
    private int _offset;

    public MciCommMsgDataVO(){
        this._offset = 0;
    }

    public MciCommMsgDataVO(int iOffset){
        this._offset = iOffset;
    }

    private String msgCd;
    private String msgPrnAttrCd;
    private String msgCt;
    private String anxMsgCt;

    public byte[] marshalFld(){
        return marshalFld( "UTF-8" ); 
    }

	public byte[] marshalFld(String encode){
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write( MciUtil.strToSpBytes(this.msgCd , 8, encode ) );
            out.write( MciUtil.strToSpBytes(this.msgPrnAttrCd , 1, encode ) );
            out.write( MciUtil.strToSpBytes(this.msgCt , 200, encode ) );
            out.write( MciUtil.strToSpBytes(this.anxMsgCt , 200, encode ) );
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
        this.msgCd = MciUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.msgPrnAttrCd = MciUtil.getTrimmedString(bytes, _offset, 1, encode);
        _offset += 1;
        this.msgCt = MciUtil.getTrimmedString(bytes, _offset, 200, encode);
        _offset += 200;
        this.anxMsgCt = MciUtil.getTrimmedString(bytes, _offset, 200, encode);
        _offset += 200;
    }
}