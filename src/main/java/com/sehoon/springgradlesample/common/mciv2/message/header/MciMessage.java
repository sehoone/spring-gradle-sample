package com.sehoon.springgradlesample.common.mciv2.message.header;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sehoon.springgradlesample.common.mciv2.exception.MismatchFormatException;
import com.sehoon.springgradlesample.common.mciv2.exception.MissingRequiredField;
import com.sehoon.springgradlesample.common.mciv2.util.MciUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class MciMessage<T extends MciMarshalable> {
    private int _offset;

    public MciMessage() {
        this._offset = 0;
        init();
    }

    public MciMessage(int iOffset) {
        this._offset = iOffset;
    }

    private void init() {
        this.header = new MciHeader();
        this.hfldMessage = new MciHfldMessage();
    }

    private MciHeader header;
    private MciHfldMessage hfldMessage;
    private T data;

    public byte[] marshalFld() throws MissingRequiredField, MismatchFormatException {
        return marshalFld("euc-kr");
    }

    public byte[] marshalFld(String encode) throws MissingRequiredField, MismatchFormatException {
        this.header.setTgrmLencn(this.data.getTgrmLencn());
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            out.write(this.header.marshalFld());
            out.write(this.hfldMessage.marshalFld());
            out.write(this.data.marshalFld());

            return bout.toByteArray();
        } catch (IOException e) {
            log.error("marshalFld Error:[" + toString() + "]", e);
        }
        return null;
    }

    public void unMarshalFld(byte[] bytes) throws Exception {
        unMarshalFld(bytes, "euc-kr");
    }

    public void unMarshalFld(byte[] bytes, String encode) throws Exception {
        this.header.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, 800));
        _offset += 800;
        this.hfldMessage.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, 420));
        _offset += 420;

        if ("0".equals(this.header.getTgrmDalRsltCd())) {
            this.data.unMarshalFld(MciUtil.bytesToByte(bytes, _offset, bytes.length - _offset));
            _offset += bytes.length - _offset;
        }
    }
}
