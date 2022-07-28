package com.sehoon.springgradlesample.common.mciv2.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sehoon.springgradlesample.common.mci.manager.MciPropManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MciUtil {

	private static ObjectMapper jacksonOm = null;

	/**
	 * get ObjectMapper
	 * @return
	 */
	private static synchronized ObjectMapper getObjectMapper() {
		if (jacksonOm == null) {
			jacksonOm = new ObjectMapper();
			jacksonOm.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jacksonOm.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		}
		return jacksonOm;
	}

	/**
	 * http post 요청
	 * @param stUrl
	 * @param postData
	 * @param timeoutMs
	 * @return
	 * @throws Exception
	 */
	public static byte[] sendPostUrl(String stUrl, byte[] postData, int timeoutMs) throws Exception {
		log.info("length " + Integer.toString(postData.length));
		HttpURLConnection conn = null;
		OutputStream out = null;
		InputStream is = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		int readCount = 0;
		byte[] rtnArray = null;
		byte[] buff = new byte[1024];

		try {

			URL url = new URL(stUrl);

			conn = (HttpURLConnection) url.openConnection();

			if (timeoutMs > 0) {
				conn.setConnectTimeout(timeoutMs);
				conn.setReadTimeout(timeoutMs);
			}
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");// application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setRequestProperty("Proworks-Body", "Y"); // HTTP Body로 JSON 객체를 전달하기 위한 여부
			conn.setRequestProperty("Proworks-Lang", "ko"); // 다국어 처리를 위한 언어 설정 (미설정 시 프레임워크에서 ko 세팅)

			out = conn.getOutputStream();

			out.write(postData);

			is = conn.getInputStream();
			byteArrayOutputStream = new ByteArrayOutputStream();
			while ((readCount = is.read(buff)) != -1) {
				byteArrayOutputStream.write(buff, 0, readCount);
			}
			byteArrayOutputStream.flush();
			rtnArray = byteArrayOutputStream.toByteArray();
			return rtnArray;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.debug("BeIgnore");
			} catch (Exception e) {
				log.debug("BeIgnore");
			}
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.debug("BeIgnore");
			} catch (Exception e) {
				log.debug("BeIgnore");
			}
			try {
				if (conn != null)
					conn.disconnect();
			} catch (NullPointerException e) {
				log.debug("BeIgnore");
			} catch (Exception e) {
				log.debug("BeIgnore");
			}
		}
	}

	/**
	 * JSON String to Object
	 * @param <T>
	 * @param str
	 * @param toValueType
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromJsonString(String str, Class<T> toValueType) throws Exception {
		return getObjectMapper().readValue(str, toValueType);
	}

	/**
	 * Object to JSON String
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toJsonString(Object obj) throws Exception {
		return getObjectMapper().writeValueAsString(obj);
	}

	/**
	 * Object merge
	 * @param obj
	 * @param update
	 */
	public static void mergeVo(Object obj, Object update) {
		if (obj == null || update == null)
			return;
		if (!obj.getClass().isAssignableFrom(update.getClass()))
			return;
		Method[] methods = obj.getClass().getMethods();
		byte b;
		int i;
		Method[] arrayOfMethod1;
		for (i = (arrayOfMethod1 = methods).length, b = 0; b < i;) {
			Method fromMethod = arrayOfMethod1[b];
			if (fromMethod.getDeclaringClass().equals(obj.getClass()) && fromMethod.getName().startsWith("get")) {
				String fromName = fromMethod.getName();
				String toName = fromName.replaceFirst("get", "set");
				try {
					Method toMetod = obj.getClass().getMethod(toName, new Class[] { fromMethod.getReturnType() });
					Object value = fromMethod.invoke(update);
					if (value != null)
						toMetod.invoke(obj, new Object[] { value });
				} catch (NoSuchMethodException noSuchMethodException) {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			b++;
		}
	}

	/**
	 * 현재시간 by format
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return dateObj.format(formatter);
	}

	/**
	 * int to String. 파라메터의 길이만큼 문자열을 채움
	 * @param val
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String intToString(int val, int length) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bout);
		try {
			String val_str = String.valueOf(val);
			if (val < 0)
				val_str = val_str.substring(1);
			int str_size = val_str.length();
			if (val < 0)
				str_size++;
			for (int i = str_size; i < length; i++)
				val_str = "0" + val_str;
			if (val < 0)
				val_str = "-" + val_str;
			out.write(val_str.getBytes());
		} catch (Exception e) {
			throw new Exception("ERROR.SYS.010", e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				throw new Exception("ERROR.SYS.010", e);
			}
		}
		return bout.toString();
	}

	/**
	 * get mci properties value
	 * @param key
	 * @return
	 */
	public static String getMciProp(String key) {
		return MciPropManager.getInstance().getProp(key);
	}

	/**
	 * String to byte. by encode UTF-8
	 * @param str
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] strToSpBytes(String str, int len) throws UnsupportedEncodingException {
		return strToSpBytes(str, len, "UTF-8");
	}

	/**
	 * String to byte
	 * @param str
	 * @param len
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] strToSpBytes(String str, int len, String encode) throws UnsupportedEncodingException {
		if (len < 1)
			return null;
		byte[] bytes = new byte[len];
		if (str == null) {
			for (int j = 0; j < len; j++)
				bytes[j] = 32;
			return bytes;
		}
		byte[] strBytes = null;

		if (encode != null && !"null".equalsIgnoreCase(encode) && !"".equalsIgnoreCase(encode)) {
			strBytes = str.getBytes(encode);
		} else {
			strBytes = str.getBytes();
		}

		int strLen = strBytes.length;
		if (strLen > len)
			strLen = len;
		System.arraycopy(strBytes, 0, bytes, 0, strLen);
		for (int i = strLen; i < len; i++)
			bytes[i] = 32;
		return bytes;
	}

	/**
	 * get trimmed String by specify buffer byte
	 * @param buf
	 * @param offset
	 * @param length
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static String getTrimmedString(byte[] buf, int offset, int length, String encode) throws Exception {
		byte[] bb = new byte[length];
		try {
			System.arraycopy(buf, offset, bb, 0, length);
			if (encode != null && !"null".equalsIgnoreCase(encode) && !"".equalsIgnoreCase(encode))
				return (new String(bb, encode)).trim();
			return (new String(bb)).trim();
		} catch (Exception e) {
			log.error("ERROR TypeConversionUtil", e);
			throw new Exception("ERROR.SYS.010", e);
		}
	}

	public static byte[] bytesToByte(byte[] buf, int offset, int length) {
		byte[] bb = new byte[length];
		System.arraycopy(buf, offset, bb, 0, length);
		return bb;
	}

	public static String getTrimmedString(byte[] buf, int offset, int length) throws Exception {
		return getTrimmedString(buf, offset, length, "UTF-8");
	}

	public static int bytesToInt(byte[] buf, int offset, int length, String encode) throws Exception {
		String str = getTrimmedString(buf, offset, length, encode);
		return parseToInt(str);
	}

	public static int bytesToInt(byte[] buf, int offset, int length) throws Exception {
		String str = getTrimmedString(buf, offset, length, "UTF-8");
		return parseToInt(str);
	}

	/**
	 * String to int
	 * @param inputValue
	 * @return
	 * @throws Exception
	 */
	public static int parseToInt(String inputValue) throws Exception {
		int value = 0;
		try {
			value = Integer.parseInt(inputValue.trim());
		} catch (NumberFormatException ne) {
		}
		return value;
	}

}
