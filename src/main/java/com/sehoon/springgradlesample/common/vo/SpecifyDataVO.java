package com.sehoon.springgradlesample.common.vo;

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
public class SpecifyDataVO{
    private int _offset;

    public SpecifyDataVO(){
        this._offset = 0;
    }

    public SpecifyDataVO(int iOffset){
        this._offset = iOffset;
    }

    private String cno;
    private String userName;
    private String userPhone;
    private String age;

    public byte[] marshalFld(){
        return marshalFld( "UTF-8" ); 
    }

	public byte[] marshalFld(String encode){
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write( CcFwUtil.strToSpBytes(this.cno , 8, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.userName , 40, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.userPhone , 20, encode ) );
            out.write( CcFwUtil.strToSpBytes(this.age , 5, encode ) );
            
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
        this.cno = CcFwUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.userName = CcFwUtil.getTrimmedString(bytes, _offset, 40, encode);
        _offset += 40;
        this.userPhone = CcFwUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.age = CcFwUtil.getTrimmedString(bytes, _offset, 5, encode);
        _offset += 5;
        
    }
}
