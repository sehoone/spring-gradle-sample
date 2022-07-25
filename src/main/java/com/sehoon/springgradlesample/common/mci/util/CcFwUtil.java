package com.sehoon.springgradlesample.common.mci.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CcFwUtil {

	private static ObjectMapper jacksonOm = null;
	
	/**
	 * getObjectMapper 필요시 초기화후 ElMappingJacksonObjectMapper를 얻어냄
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

    public static byte[] sendPostUrl(String stUrl, byte[] postData,int timeoutMs) throws Exception {
		HttpURLConnection conn = null;
		OutputStream out = null;
		InputStream is = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		int readCount = 0;
		byte[] rtnArray = null;
		byte[] buff = new byte[1024];
		try
		{
	
			URL url = new URL(stUrl);

			conn = (HttpURLConnection)url.openConnection();
			
			if (timeoutMs > 0) {
				conn.setConnectTimeout(timeoutMs);
				conn.setReadTimeout(timeoutMs);
			}
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");// application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setRequestProperty("Proworks-Body","Y");  // HTTP Body로 JSON 객체를 전달하기 위한 여부
			conn.setRequestProperty("Proworks-Lang","ko");  // 다국어 처리를 위한 언어 설정 (미설정 시 프레임워크에서 ko 세팅)

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
			try { if (out != null) out.close(); } catch (IOException e) { log.debug("BeIgnore"); } catch (Exception e) { log.debug("BeIgnore"); }	
			try { if (is != null) is.close(); } catch (IOException e) { log.debug("BeIgnore"); } catch (Exception e) { log.debug("BeIgnore"); }	
			try { if (conn != null) conn.disconnect(); } catch (NullPointerException e) { log.debug("BeIgnore"); } catch (Exception e) { log.debug("BeIgnore"); }	
		}
	}

    public static <T> T fromJsonString(String str, Class<T> toValueType) throws Exception {
		return getObjectMapper().readValue(str, toValueType);
	}

		/**
	 * toJson Object를 JsonString으로 변환
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toJsonString(Object obj) throws Exception {
		return getObjectMapper().writeValueAsString(obj);
	}

	public static void mergeVo(Object obj, Object update) {
		if (obj == null || update == null)
		  return; 
		if (!obj.getClass().isAssignableFrom(update.getClass()))
		  return; 
		Method[] methods = obj.getClass().getMethods();
		byte b;
		int i;
		Method[] arrayOfMethod1;
		for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) {
		  Method fromMethod = arrayOfMethod1[b];
		  if (fromMethod.getDeclaringClass().equals(obj.getClass()) && fromMethod.getName().startsWith("get") && !"getOffset".equals(fromMethod.getName())) {
			String fromName = fromMethod.getName();
			String toName = fromName.replaceFirst("get", "set");
			try {
			  Method toMetod = obj.getClass().getMethod(toName, new Class[] { fromMethod.getReturnType() });
			  Object value = fromMethod.invoke(update, null);
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

	public static String getCurrentDate(String format) {
		LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return dateObj.format(formatter);
	}  

	public static int parseToInt(String inputValue) throws Exception {
		int value = 0;
		try {
		  value = Integer.parseInt(inputValue.trim());
		} catch (NumberFormatException ne) {
		} 
		return value;
	}

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
}
