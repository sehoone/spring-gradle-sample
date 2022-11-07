package com.sehoon.springgradlesample.common.mciv2.message.header;

import com.sehoon.springgradlesample.common.mciv2.exception.MismatchFormatException;
import com.sehoon.springgradlesample.common.mciv2.exception.MissingRequiredField;

public interface MciMarshalable {
    byte[] marshalFld() throws MissingRequiredField, MismatchFormatException;

    void unMarshalFld(byte[] bytes) throws Exception;

    void unMarshalFld(byte[] bytes, String encode) throws Exception;

    String getTgrmLencn();

    int getTgrmDtdvValuLencn();

}
