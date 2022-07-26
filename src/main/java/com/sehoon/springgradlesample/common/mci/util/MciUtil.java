package com.sehoon.springgradlesample.common.mci.util;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.sehoon.springgradlesample.common.mci.constant.MciChannelConst;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;
import com.sehoon.springgradlesample.common.mci.vo.MciHfldMsgVO;

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
				return sendHFLD(channel, inVo, outClass, timeoutMs);
			default:
				throw new Exception(channel.name()+" NL MCI에서 처리가능한 타입이 아님("+chType+")");
		}
	}

	private static <T> T sendJSON(MciChannelConst channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		// String chUrl = CcFwUtil.getElProp("MCI_PROP", channel.name()+"_BASE_URL");
		String chUrl = CcFwUtil.getMciProp("mci.url");
		log.info("mciUrl " + chUrl);

		// 공통헤더 디폴트 값 셋팅
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
		
		// 환경유형코드 P:REAL(RUN), T:TEST(TST), D: 개발(DEV)
		tgrmCmnnhddvValu.setEnvrTypeCd(CcFwUtil.getMciProp("envr-type-cd"));
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

			try {
				// 공통헤더 얻기
				MciCommHeaderVo outHeader = _invokeGetter(outData,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
				
				if (!"0".equals(outHeader.getTgrmDalRsltCd())) {
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

	private static <T> T sendHFLD(MciChannelConst channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		// inVo/outVo 모두 FLD여부 Y 여야 하며 컬럼 구조는 다음과 같아야 함
		// tgrmCmnnhddvValu	MciCommHeaderVo
		// tgrmMsdvValu		MciCmouMsgVo
		// 이하 데이터컬럼들 또는 대내MCI Vo와 동일한 구조로 tgrmDtdvValu에 해당하는 Vo에 데이터컬럼들을 넣어야 함(FLD여부는 Y여야함)

		String chUrl = CcFwUtil.getMciProp("mci.url");
		
		// 대외 MCI 전용 파라메터 추가
		String frbuCd = CcFwUtil.getMciProp(channel.name()+"-frbu-cd");		// 대외기관코드
		String cmouDutjCd = CcFwUtil.getMciProp(channel.name()+"-cmou-dutj-cd");	// 대외업무코드
		String chEnc = CcFwUtil.getMciProp(channel.name()+"-encode");	// FLD 인코딩 - 디폴트는 EUC-KR

		if (frbuCd.length() < 1) {
			throw new Exception("대외 MCI 프로퍼티의 대외기관코드("+channel.name()+"_FRBU_CD)는 반드시 설정되어있어야 합니다.");
		}
		if (cmouDutjCd.length() < 1) {
			throw new Exception("대외 MCI 프로퍼티의 대외업무코드("+channel.name()+"_CMOU_DUTJ_CD)는 반드시 설정되어있어야 합니다.");
		}

		// inVo/outVo 모두 FLD여부 Y 여야 하며 컬럼 구조는 다음과 같아야 함
		// tgrmCmnnhddvValu	MciCommHeaderVo
		// tgrmMsdvValu		MciCmouMsgVo
		// 이하 데이터컬럼들 또는 대내MCI Vo와 동일한 구조로 tgrmDtdvValu에 해당하는 Vo에 데이터컬럼들을 넣어야 함(FLD여부는 Y여야함)

		// 공통헤더 얻기
		MciCommHeaderVo tgrmCmnnhddvValu = _invokeGetter(inVo,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
				
		// validation
		if (tgrmCmnnhddvValu == null) throw new Exception("대외 MCI전문 공통헤더 설정안됨");
		
		String itrfId = tgrmCmnnhddvValu.getItrfId();
		if (itrfId == null || itrfId.length() < 1) throw new Exception("NL MCI전문 공통헤더에 인터페이스ID(itrfId) 설정안됨");
		
		String glbId = tgrmCmnnhddvValu.getGlbId();
		if (glbId == null || glbId.length() < 1) {
			String appliDutjCd = itrfId.substring(0, 3); // 어플리케이션업무코드(3)
			tgrmCmnnhddvValu.setGlbId(MciUtil._nextNlGlbId(appliDutjCd));		// Mci 대내 전문과 중복되지않도록 MciUtil 글로벌ID를 공유함 !!!!
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
		
		tgrmCmnnhddvValu.setEnvrTypeCd(CcFwUtil.getMciProp("envr-type-cd"));
		tgrmCmnnhddvValu.setReqTgrmTnsmDtptDt(CcFwUtil.getCurrentDate("yyyyMMddHHmmssSSS"));
		// tgrmCmnnhddvValu.setUserIpAddr(elHeader.getClientIp());
		// tgrmCmnnhddvValu.setUserId(elHeader.getUserId());

		// 대외 MCI 전용 파라메터 세팅
		tgrmCmnnhddvValu.setFrbuCd(frbuCd);
		tgrmCmnnhddvValu.setCmouDutjCd(cmouDutjCd);
		
		// 공통메시지 얻기
		MciHfldMsgVO tgrmMsdvValu = _invokeGetter(inVo,"getTgrmMsdvValu", MciHfldMsgVO.class);

		// validation
		if (tgrmMsdvValu == null) throw new Exception("대외 MCI전문 공통메시지 설정안됨");
		tgrmMsdvValu.getMsgHddvValu().setMsgRpttCc(1);	// 건수 1로
		
		// 요청파라메터로 변경
	    byte[] inVoBytes = null;
	    
		Method marshalMethod = inVo.getClass().getMethod("marshalFld", String.class );
		inVoBytes = (byte[])marshalMethod.invoke(inVo, chEnc);
		
	    // https로 서비스 호출
		try {
			byte[] bb = CcFwUtil.sendPostUrl(chUrl, inVoBytes, timeoutMs);
			
			// 응답객체 얻기
			T outData = outClass.getDeclaredConstructor().newInstance();
			Exception ve = null;
			try {
				Method unmarshalMethod = outData.getClass().getMethod("unMarshalFld", byte[].class, String.class );
				unmarshalMethod.invoke(outData, bb, chEnc);
			} catch(NullPointerException ee) {	// sparrow
				ve = ee;
			} catch(Exception ee) {
				ve = ee;
			}
			if (ve != null) {
				// 공통메시지 까지는 수신한 경우 에러처리 안함(에러응답시 본문은 안오는 상태일 수 있음)
				MciHfldMsgVO oMsgHdr = _invokeGetter(outData,"getTgrmMsdvValu", MciHfldMsgVO.class);
				if (oMsgHdr.getOffset() == 0) {
					throw ve;
				}
			}

			try {
				// 공통헤더 얻기
				MciCommHeaderVo outHeader = _invokeGetter(outData,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
				if (!"0".equals(outHeader.getTgrmDalRsltCd())) {
					new Exception("오류수신:"+outHeader.getTgrmDalRsltCd());
				} 
			} catch(NullPointerException ee) {	// sparrow
				
			} catch(Exception ee) {
				
			}

			// 전문 Logging - 성공시

			return outData;
		} catch(NullPointerException e){ // sparrow
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
