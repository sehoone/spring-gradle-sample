package com.sehoon.springgradlesample.common.mci.util;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.sehoon.springgradlesample.common.mci.constant.MciChannelConst;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		// TODO 서비스 채널별로 MCI URL 셋팅
		// String chUrl = CcFwUtil.getElProp("MCI_PROP", channel.name()+"_BASE_URL");
		String chUrl = CcFwUtil.getMciProp("mci.url");
		log.info("mciUrl " + chUrl);

		// 공통헤더 디폴트 값 셋팅
		// 공통헤더 얻기
		MciCommHeaderVo tgrmCmnnhddvValu = _invokeGetter(inVo,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
				
		// validation
		if (tgrmCmnnhddvValu == null) throw new Exception("NL MCI전문 공통헤더 설정안됨");
		
		String itrfId = tgrmCmnnhddvValu.getItrfId();
		if (itrfId == null || itrfId.length() < 1) throw new Exception("NL MCI전문 공통헤더에 인터페이스ID(itrfId) 설정안됨");
		
		String glbId = tgrmCmnnhddvValu.getGlbId();
		if (glbId == null || glbId.length() < 1) {
			String appliDutjCd = itrfId.substring(0, 3); // 어플리케이션업무코드(3)
			tgrmCmnnhddvValu.setGlbId(_nextNlGlbId(appliDutjCd));
			// v1.03: 어플리케이션업무코드 - 글로벌ID에 포함되어 있지만 업무팀에서 별도사용이 용이하도록 명시적으로 분리 추가
			tgrmCmnnhddvValu.setAppliDutjCd(appliDutjCd);
		}
		
		// v1.05: 어플리케이션상세업무코드 - 고객채널 업무상세구분코드 3째자리 일련번호 규칙 : 앱(0), PC(1), 모바일웹(2)
		/*			홈페이지(PC)		DH1
					홈페이지(모바일웹)	DH2
					디지털창구(PC)		DA1
					디지털창구(모바일웹)	DA2
					디지털보험(PC)		DI1
					디지털보험(모바일웹)	DI2
					디지털플랫폼앱		DA0
		*/
		String appliDtptDutjCd = tgrmCmnnhddvValu.getAppliDtptDutjCd();
		if (appliDtptDutjCd == null || appliDtptDutjCd.length() < 3) {
			throw new Exception("NL MCI전문 공통헤더의 어플리케이션상세업무코드(appliDtptDutjCd)는 필수 컬럼입니다.");
		}

		// v1.03: 진행일련번호 - 글로벌ID에서 분리됨, 없으면 001 세팅
		String pgrsSriaNo = tgrmCmnnhddvValu.getPgrsSriaNo();
		if (pgrsSriaNo == null || pgrsSriaNo.length() < 1) {
			tgrmCmnnhddvValu.setPgrsSriaNo("001");
		}
		
		String inqrTraTypeCd = tgrmCmnnhddvValu.getInqrTraTypeCd();
		if (inqrTraTypeCd == null || inqrTraTypeCd.length() < 1) {
			// 조회거래유형코드 등록(C), 조회(R), 변경(U), 삭제(D), 인쇄(P), 다운로드(E) )
			throw new Exception("NL MCI전문 공통헤더의 조회거래유형코드(inqrTraTypeCd)는 필수 컬럼입니다.");
		}
		
		// TODO 환경유형코드 P:REAL(RUN), T:TEST(TST), D: 개발(DEV)
		tgrmCmnnhddvValu.setEnvrTypeCd("D");
		// String envrTypeCd = tgrmCmnnhddvValu.getEnvrTypeCd();
		// if (envrTypeCd == null || envrTypeCd.length() == 0) {
		// 	envrTypeCd = ElConfig.getServerMode().substring(0, 1); // R:REAL(RUN), T:TEST(TST), D: 개발(DEV)
		// 	if ("R".equals(envrTypeCd)) {
		// 		envrTypeCd = "P";		// 20211126 (이준호C) 최초에는 'R'로 가이드 받았는데 최신 버전 보니 'P' 로 변경
		// 	}
		// 	tgrmCmnnhddvValu.setEnvrTypeCd(envrTypeCd);
		// }

		tgrmCmnnhddvValu.setReqTgrmTnsmDtptDt(CcFwUtil.getCurrentDate("yyyyMMddHHmmssSSS"));
		// TODO client ip 정보 set
		// tgrmCmnnhddvValu.setUserIpAddr(elHeader.getClientIp());

	    // http로 서비스 호출
	    chUrl = chUrl.replace("{0}", itrfId); // ifId

		String paramStr = CcFwUtil.toJsonString(inVo);
		String bbStr = "";
		try {
			byte[] bb = CcFwUtil.sendPostUrl(chUrl, paramStr.getBytes("UTF-8"), timeoutMs);
			bbStr = new String(bb, "UTF-8");

			T outData;
			try {
				  outData = CcFwUtil.fromJsonString(bbStr, outClass);
			} catch(JsonParseException ee) {
				throw new JsonParseException(null, "응답형식이 json이 아님");
			}

			// Exception ue = null;
			try {
				// 공통헤더 얻기
				MciCommHeaderVo outHeader = _invokeGetter(outData,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
				
				if (!"0".equals(outHeader.getTgrmDalRsltCd())) {
					// ue = new Exception("오류수신:"+outHeader.getTgrmDalRsltCd());
					throw new Exception("오류수신:"+outHeader.getTgrmDalRsltCd());
				} 
			} catch(NullPointerException ee) {
			} catch(Exception ee) {
			}
			
			return outData;
		} catch(NullPointerException e){
			// 전문 Logging - 실패시
			throw e;
		} catch(Exception e){
			// 전문 Logging - 실패시
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
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

	private static String s_nlGlbId = null;
	static synchronized String _nextNlGlbId(String appliDutjCd) throws Exception {
		String hostname = StringUtils.defaultString(CcFwUtil.getMciProp("hostname"), "");

		StringBuilder sb = new StringBuilder();
		sb.append(CcFwUtil.getCurrentDate("yyyyMMddHHmmssSSS"));	// 17
		sb.append((hostname+"________").substring(0,9));	// 9
		sb.append((appliDutjCd+"___").substring(0,3));		// 3 어플리케이션 업무코드
		//		sb.append(("00000001"));							// 8 전문채번번호
		String next = sb.toString();
		int seq = 1;
		if (s_nlGlbId != null && s_nlGlbId.startsWith(next)) {
			seq = CcFwUtil.parseToInt(s_nlGlbId.substring(29)) + 1;
		}
		next += CcFwUtil.intToString(seq, 8);
		s_nlGlbId = next;
		
		return next;
	}
}
