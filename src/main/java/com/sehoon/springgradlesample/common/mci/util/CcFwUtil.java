package com.sehoon.springgradlesample.common.mci.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CcFwUtil {
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
		// return getObjectMapper().readValue(str, toValueType);
        return null;
	}
}
