package com.sehoon.springgradlesample.common.mciv2.message.header;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.sehoon.springgradlesample.common.mciv2.exception.MismatchFormatException;
import com.sehoon.springgradlesample.common.mciv2.exception.MissingRequiredField;
import com.sehoon.springgradlesample.common.mciv2.util.MciUtil;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class MciAbstractMessage implements MciMarshalable {
    protected final int headerLength = 1220;
    protected String tgrmLencn = "00000000";
    protected int tgrmDtdvValuLencn = 0;
    private int _offset = 0;
    private List<Field> fields = new ArrayList<>();

    public MciAbstractMessage() {
        this._offset = 0;

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            MciDataField dataField = field.getAnnotation(MciDataField.class);
            if (dataField != null) {
                if (!dataField.optional())
                    tgrmDtdvValuLencn += dataField.length();
                field.setAccessible(true);
                this.fields.add(field);
            }
        }
        tgrmLencn = String.format("%08d", tgrmDtdvValuLencn + headerLength);
    }

    public MciAbstractMessage(int iOffset) {
        this._offset = iOffset;
    }

    private int getDataFieldLength(Field field) {
        MciDataField dataField = field.getAnnotation(MciDataField.class);
        if (dataField != null) {
            return dataField.length();
        }

        return 0;
    }

    private Boolean getDataFieldRequired(Field field) {
        MciDataField dataField = field.getAnnotation(MciDataField.class);
        return dataField.required();
    }

    private Boolean getDataFieldRight(Field field) {
        MciDataField dataField = field.getAnnotation(MciDataField.class);
        return dataField.right();
    }

    private String getDataFieldFormat(Field field) {
        MciDataField dataField = field.getAnnotation(MciDataField.class);
        return dataField.format();
    }

    public int getOffset() {
        return _offset;
    }

    public byte[] marshalFld() throws MissingRequiredField, MismatchFormatException {
        return marshalFld("euc-kr");
    }

    @SuppressWarnings("unchecked")
    public byte[] marshalFld(String encode) throws MissingRequiredField, MismatchFormatException {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);) {
            for (Field field : this.fields) {
                if (field.getType().equals(List.class)) {
                    List<MciAbstractMessage> list = (List<MciAbstractMessage>) field.get(this);
                    for (MciAbstractMessage element : list) {
                        out.write(element.marshalFld(encode));
                    }
                } else if (!field.getType().equals(String.class) && !field.getType().equals(Integer.class)) {
                    Method methodMarshalFld = field.getType().getMethod("marshalFld", String.class);
                    out.write((byte[]) methodMarshalFld.invoke(field.get(this), encode));
                } else {
                    String format = getDataFieldFormat(field);
                    Object value = field.get(this);
                    if (getDataFieldRequired(field) && (value == null || StringUtils.isBlank(value.toString()))) {
                        throw new MissingRequiredField(field.getName());
                    }

                    if (value != null && !StringUtils.isBlank(format)
                            && !Pattern.compile(format).matcher(field.get(this).toString()).matches()) {
                        throw new MismatchFormatException(field.getName());
                    }

                    if (getDataFieldRight(field)) {
                        out.write(MciUtil.objToSpBytesR(field.get(this), getDataFieldLength(field), encode));
                    } else {
                        out.write(MciUtil.objToSpBytes(field.get(this), getDataFieldLength(field), encode));
                    }
                }
            }

            return bout.toByteArray();
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException Error:[" + toString() + "]" + e);
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException Error:[" + toString() + "]" + e);
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException Error:[" + toString() + "]" + e);
        } catch (IOException e) {
            log.error("marshalFld Error:[" + toString() + "]" + e);
        }
        return null;
    }

    public void unMarshalFld(byte[] bytes) throws Exception {
        unMarshalFld(bytes, "euc-kr");
    }

    @SuppressWarnings("unchecked")
    public void unMarshalFld(byte[] bytes, String encode) throws Exception {
        Object value = null;
        int length = 0;

        for (Field field : this.fields) {
            length = getDataFieldLength(field);
            if (field.getType().equals(List.class)) {
                Optional<Field> countField = this.fields.stream()
                        .filter(e -> e.getName().equals(field.getName() + "Count")).findFirst();
                if (countField.isPresent()) {
                    byte[] bb;
                    int count = Integer.valueOf(countField.get().get(this).toString());
                    List<MciAbstractMessage> list = (List<MciAbstractMessage>) field.get(this);

                    ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                    Class<?> elementType = (Class<?>) genericType.getActualTypeArguments()[0];
                    Constructor<?> elementConstrictor = elementType.getConstructor();
                    Method methodUnMarshalFld = elementType.getMethod("unMarshalFld", byte[].class);
                    Object elementInstance;

                    for (int i = 0; i < count; i++) {
                        elementInstance = elementConstrictor.newInstance();
                        bb = new byte[length];
                        System.arraycopy(bytes, _offset, bb, 0, length);
                        methodUnMarshalFld.invoke(elementInstance, bb);
                        list.add((MciAbstractMessage) elementInstance);
                        _offset += length;
                    }
                }
            } else if (!field.getType().equals(String.class) && !field.getType().equals(Integer.class)) {
                Method methodUnMarshalFld = field.getType().getMethod("unMarshalFld", byte[].class);
                byte[] bb = new byte[length];
                System.arraycopy(bytes, _offset, bb, 0, length);
                methodUnMarshalFld.invoke(field.get(this), bb);
                _offset += length;
            } else {
                if (field.getType().equals(String.class)) {
                    value = MciUtil.getTrimmedString(bytes, _offset, length, encode);
                } else if (field.getType().equals(Integer.class)) {
                    value = MciUtil.bytesToInt(bytes, _offset, length, encode);
                }

                field.set(this, value);
                _offset += length;
            }
        }
    }
}
