package com.sehoon.springgradlesample.common.vo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sehoon.springgradlesample.common.mci.util.MciUtil;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVO;
import com.sehoon.springgradlesample.common.mci.vo.MciHfldMsgVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class SAMPLE00005IVO{
    private int _offset;

    public SAMPLE00005IVO(){
        this._offset = 0;
    }

    public SAMPLE00005IVO(int iOffset){
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
            out.write( MciUtil.strToSpBytes(this.cno , 8, encode ) );
            out.write( MciUtil.strToSpBytes(this.userName , 40, encode ) );
            out.write( MciUtil.strToSpBytes(this.userPhone , 20, encode ) );
            out.write( MciUtil.strToSpBytes(this.age , 5, encode ) );
            
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
        this.cno = MciUtil.getTrimmedString(bytes, _offset, 8, encode);
        _offset += 8;
        this.userName = MciUtil.getTrimmedString(bytes, _offset, 40, encode);
        _offset += 40;
        this.userPhone = MciUtil.getTrimmedString(bytes, _offset, 20, encode);
        _offset += 20;
        this.age = MciUtil.getTrimmedString(bytes, _offset, 5, encode);
        _offset += 5;
    }
}
