package com.sehoon.springgradlesample.common.mci.util;

import java.lang.reflect.Method;

import com.fasterxml.jackson.core.JsonParseException;
import com.sehoon.springgradlesample.common.mci.constant.MciChannelConst;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;

public class MciUtil {
		/**
	 * MCI채널에 전문 송신하고 응답받기(NL용) 공통함수
	 * @throws Exception
	 */
	public static <T> T send(MciChannelConst channel, Object inVo, Class<T> outClass) throws Exception {
		return send(channel, inVo, outClass, 0);
	}

	public static <T> T send(MciChannelConst channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		// 채널별로 연동 타입이 있음
		String chType = "JSON";

		switch(chType) {
			case "JSON":	// 대내 MCI용 JSON 방식
				return sendJSON(channel, inVo, outClass, timeoutMs);
			case "HFLD":	// 대외 MCI용 https + FLD 방식
				// return sendHFLD(channel, inVo, outClass, timeoutMs);
			default:
				throw new Exception(channel.name()+" NL MCI에서 처리가능한 타입이 아님("+chType+")");
		}
	}

	private static <T> T sendJSON(MciChannelConst channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		// String chUrl = CcFwUtil.getElProp("MCI_PROP", channel.name()+"_BASE_URL");
		String chUrl = "http://localhost:8080/nlJsonRecv.do";

		// 공통헤더 디폴트 값 셋팅
		// String paramStr = toJsonString(inVo);
		String bbStr = "";
		try {
			// byte[] bb = CcFwUtil.sendPostUrl(chUrl, paramStr.getBytes(ElConfig.getReqResEncode()), timeoutMs);
			// bbStr = new String(bb,ElConfig.getReqResEncode());

			T outData;
			try {
				  outData = CcFwUtil.fromJsonString(bbStr, outClass);
			} catch(JsonParseException ee) {
				throw new JsonParseException("응답형식이 json이 아님", null);
			}

			Exception ue = null;
			try {
				// 공통헤더 얻기
				MciCommHeaderVo outHeader = _invokeGetter(outData,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
				
				if (!"0".equals(outHeader.getTgrmDalRsltCd())) {
					ue = new Exception("오류수신:"+outHeader.getTgrmDalRsltCd());
				} 
			} catch(NullPointerException ee) {	// sparrow
			} catch(Exception ee) {
			}
			
			return outData;
		} catch(NullPointerException e){ // sparrow
			// 전문 Logging - 실패시

			throw e;
		} catch(Exception e){
			// 전문 Logging - 실패시

			throw e;
		}
	}

	private static <T> T _invokeGetter(Object inVo, String getMethod, Class<T> outClass) throws Exception {
		Exception re1 = null;
		T outVo = null;
		try {
			Method toMethod = inVo.getClass().getMethod(getMethod, new Class[0]);
			outVo = (T)toMethod.invoke(inVo, new Object[0]);
		} catch (NullPointerException e) {
			re1 = e;
		} catch (Exception e) {
			re1 = e;
		} 
		if (re1 != null) {	// sparrow
			if (re1.getCause() != null) {
				throw (Exception)re1.getCause();
			} else {
				throw re1;
			}			
		}
		return outVo;
	}	
}
