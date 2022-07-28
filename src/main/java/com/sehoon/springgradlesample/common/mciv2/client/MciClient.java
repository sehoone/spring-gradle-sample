package com.sehoon.springgradlesample.common.mciv2.client;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.sehoon.springgradlesample.common.mciv2.enumeration.MciChannelEnum;
import com.sehoon.springgradlesample.common.mciv2.util.MciUtil;
import com.sehoon.springgradlesample.common.mciv2.vo.MciCommHeaderVo;
import com.sehoon.springgradlesample.common.mciv2.vo.MciCommMsgDataVo;
import com.sehoon.springgradlesample.common.mciv2.vo.MciCommMsgHdrVo;
import com.sehoon.springgradlesample.common.mciv2.vo.MciHfldMsgVO;
import com.sehoon.springgradlesample.config.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MciClient {

    private String mciHostname;
    private String mciAppliDtptDutjCd;
    private String mciEnvrTypeCd;
    private String mciInnerType;
    private String mciInnerBaseUrl;
    private String mciOuterType;
    private String mciOuterBaseUrl;
    private String mciOuterFrbuCd;
    private String mciOuterCmouDutjCd;
    private String mciOuterEncode;
    private String glbId = null;

    public MciClient(ApplicationProperties applicationProperties) {
        this.mciHostname = applicationProperties.getMciHostname();
        this.mciAppliDtptDutjCd = applicationProperties.getMciAppliDtptDutjCd();
        this.mciEnvrTypeCd = applicationProperties.getMciEnvrTypeCd();
        this.mciInnerType = applicationProperties.getMciInnerType();
        this.mciInnerBaseUrl = applicationProperties.getMciInnerBaseUrl();
        this.mciOuterType = applicationProperties.getMciOuterType();
        this.mciOuterFrbuCd = applicationProperties.getMciOuterFrbuCd();
        this.mciOuterCmouDutjCd = applicationProperties.getMciOuterCmouDutjCd();
        this.mciOuterEncode = applicationProperties.getMciOuterEncode();
    }

    public <T> T mciCallSerivce(Object inVo, Class<T> outClass, String itrfId, String rcvSvcId, String inqrTraTypeCd) throws Exception {
        // 필수값 체크
		Assert.hasText(itrfId, "인터페이스ID is empty");
		Assert.hasText(rcvSvcId, "서비스ID is empty");
		Assert.hasText(inqrTraTypeCd, "조회거래유형코드 is empty");

        // set 헤더 vo 비즈니스
		MciCommHeaderVo tgrmCmnnhddvValu = new MciCommHeaderVo();
		Method getHeaderVo = inVo.getClass().getMethod("getTgrmCmnnhddvValu", new Class[0]);
		MciCommHeaderVo getHeader = (MciCommHeaderVo) getHeaderVo.invoke(inVo, new Object[0]);
		MciUtil.mergeVo(tgrmCmnnhddvValu, getHeader);
		
		makeMciHeader(tgrmCmnnhddvValu, itrfId, rcvSvcId, inqrTraTypeCd, MciUtil.getCurrentDate("yyyyMMdd"));

		Method toMethod = inVo.getClass().getMethod("setTgrmCmnnhddvValu", MciCommHeaderVo.class);
		toMethod.invoke(inVo, tgrmCmnnhddvValu);

		// set 수신 채널. 채널별로 연동타입(JSON/FLD 방식) 정의됨
		MciChannelEnum channel = MciChannelEnum.MCI_INNER;
		if(rcvSvcId.indexOf("ONRIA") >= 0 ) {
        	channel = MciChannelEnum.MCI_OUTER;
        }

		return send(channel, inVo, outClass);
	}

    public <T> T send(MciChannelEnum channel, Object inVo, Class<T> outClass) throws Exception {
		return send(channel, inVo, outClass, 0);
	}

	public <T> T send(MciChannelEnum channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		// 채널별로 연동 타입이 있음
		String chType = (channel == MciChannelEnum.MCI_INNER) ? this.mciInnerType : this.mciOuterType;

		switch(chType) {
			case "JSON":	// 대내 MCI용 JSON 방식
				return sendJSON(channel, inVo, outClass, timeoutMs);
			case "HFLD":	// 대외 MCI용 https + FLD 방식
				return sendHFLD(channel, inVo, outClass, timeoutMs);
			default:
				throw new Exception(channel.getValue()+" MCI에서 처리가능한 타입이 아님("+chType+")");
		}
	}

	private <T> T sendJSON(MciChannelEnum channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		
		// 공통헤더 디폴트 값 셋팅
		MciCommHeaderVo tgrmCmnnhddvValu = _invokeGetter(inVo,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
		
		String itrfId = tgrmCmnnhddvValu.getItrfId();
		if (itrfId == null || itrfId.length() < 1) throw new Exception("NL MCI전문 공통헤더에 인터페이스ID(itrfId) 설정안됨");
		
		String glbId = tgrmCmnnhddvValu.getGlbId();
		if (glbId == null || glbId.length() < 1) {
			String appliDutjCd = itrfId.substring(0, 3); // 어플리케이션업무코드(3)
			tgrmCmnnhddvValu.setGlbId(_nextNlGlbId(appliDutjCd));
			tgrmCmnnhddvValu.setAppliDutjCd(appliDutjCd);	// 어플리케이션업무코드 - 글로벌ID에 포함되어 있지만 업무팀에서 별도사용이 용이하도록 명시적으로 분리 추가
		}
		
		// 어플리케이션상세업무코드 - 고객채널 업무상세구분코드 3째자리 일련번호 규칙 : 앱(0), PC(1), 모바일웹(2)
		/*	홈페이지(PC) DH1, 홈페이지(모바일웹) DH2, 디지털창구(PC) DA1, 디지털창구(모바일웹) DA2, 디지털보험(PC) DI1
			디지털보험(모바일웹) DI2, 디지털플랫폼앱 DA0
		*/
		String appliDtptDutjCd = tgrmCmnnhddvValu.getAppliDtptDutjCd();
		if (appliDtptDutjCd == null || appliDtptDutjCd.length() < 3) {
			throw new Exception("NL MCI전문 공통헤더의 어플리케이션상세업무코드(appliDtptDutjCd)는 필수 컬럼입니다.");
		}

		// 진행일련번호 - 글로벌ID에서 분리됨, 없으면 001 세팅
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
		tgrmCmnnhddvValu.setEnvrTypeCd(this.mciEnvrTypeCd);
		tgrmCmnnhddvValu.setReqTgrmTnsmDtptDt(MciUtil.getCurrentDate("yyyyMMddHHmmssSSS"));
		// TODO client ip 정보 set
		// tgrmCmnnhddvValu.setUserIpAddr(elHeader.getClientIp());

	    // http로 서비스 호출
        String chUrl = this.mciInnerBaseUrl;
        log.info("mciUrl " + chUrl);
	    chUrl = chUrl.replace("{0}", itrfId); // ifId

		String paramStr = MciUtil.toJsonString(inVo);
		String bbStr = "";
		try {
			byte[] bb = MciUtil.sendPostUrl(chUrl, paramStr.getBytes("UTF-8"), timeoutMs);
			bbStr = new String(bb, "UTF-8");

			T outData;
			try {
				  outData = MciUtil.fromJsonString(bbStr, outClass);
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

	private <T> T sendHFLD(MciChannelEnum channel, Object inVo, Class<T> outClass, int timeoutMs) throws Exception {
		// String chUrl = this.mciOuterBaseUrl;
		
		// 공통헤더 얻기
		MciCommHeaderVo tgrmCmnnhddvValu = _invokeGetter(inVo,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
		
		String glbId = tgrmCmnnhddvValu.getGlbId();
		if (glbId == null || glbId.length() < 1) {
			String appliDutjCd = tgrmCmnnhddvValu.getItrfId().substring(0, 3); // 어플리케이션업무코드(3)
			tgrmCmnnhddvValu.setGlbId(_nextNlGlbId(appliDutjCd));		// Mci 대내 전문과 중복되지않도록 MciUtil 글로벌ID를 공유함 !!!!
			// 어플리케이션업무코드 - 글로벌ID에 포함되어 있지만 업무팀에서 별도사용이 용이하도록 명시적으로 분리 추가
			tgrmCmnnhddvValu.setAppliDutjCd(appliDutjCd);
		}
		
		// 어플리케이션상세업무코드 - 고객채널 업무상세구분코드 3째자리 일련번호 규칙 : 앱(0), PC(1), 모바일웹(2)
		/*	홈페이지(PC) DH1, 홈페이지(모바일웹) DH2, 디지털창구(PC) DA1, 디지털창구(모바일웹) DA2, 디지털보험(PC) DI1
			디지털보험(모바일웹) DI2, 디지털플랫폼앱 DA0
		*/
		String appliDtptDutjCd = tgrmCmnnhddvValu.getAppliDtptDutjCd();
		if (appliDtptDutjCd == null || appliDtptDutjCd.length() < 3) {
			throw new Exception("NL MCI전문 공통헤더의 어플리케이션상세업무코드(appliDtptDutjCd)는 필수 컬럼입니다.");
		}

		// 진행일련번호 - 글로벌ID에서 분리됨, 없으면 001 세팅
		String pgrsSriaNo = tgrmCmnnhddvValu.getPgrsSriaNo();
		if (pgrsSriaNo == null || pgrsSriaNo.length() < 1) {
			tgrmCmnnhddvValu.setPgrsSriaNo("001");
		}
		
		String inqrTraTypeCd = tgrmCmnnhddvValu.getInqrTraTypeCd();
		if (inqrTraTypeCd == null || inqrTraTypeCd.length() < 1) {
			// 조회거래유형코드 등록(C), 조회(R), 변경(U), 삭제(D), 인쇄(P), 다운로드(E) )
			throw new Exception("NL MCI전문 공통헤더의 조회거래유형코드(inqrTraTypeCd)는 필수 컬럼입니다.");
		}
		
		tgrmCmnnhddvValu.setEnvrTypeCd(this.mciEnvrTypeCd);
		tgrmCmnnhddvValu.setReqTgrmTnsmDtptDt(MciUtil.getCurrentDate("yyyyMMddHHmmssSSS"));
		// tgrmCmnnhddvValu.setUserIpAddr(elHeader.getClientIp());

		// 대외 MCI 전용 파라메터 세팅
		tgrmCmnnhddvValu.setFrbuCd(this.mciOuterFrbuCd);
		tgrmCmnnhddvValu.setCmouDutjCd(this.mciOuterCmouDutjCd);
		
		MciHfldMsgVO tgrmMsdvValu = new MciHfldMsgVO();
		Method getHeaderVo = inVo.getClass().getMethod("getTgrmMsdvValu", new Class[0]);
		MciHfldMsgVO getMsgHeader = (MciHfldMsgVO) getHeaderVo.invoke(inVo, new Object[0]);
		MciUtil.mergeVo(tgrmMsdvValu, getMsgHeader);

		if(tgrmMsdvValu.getMsgHddvValu() == null){
			MciCommMsgHdrVo mciCommMsgHdrVo = new MciCommMsgHdrVo();
			mciCommMsgHdrVo.setMsgRpttCc("1");
			tgrmMsdvValu.setMsgHddvValu(mciCommMsgHdrVo);
			tgrmMsdvValu.setMsgDtdvValu(new MciCommMsgDataVo()); 
		}

		Method toMethod = inVo.getClass().getMethod("setTgrmMsdvValu", MciHfldMsgVO.class);
		toMethod.invoke(inVo, tgrmMsdvValu);
		
		// 요청파라메터로 변경
	    byte[] inVoBytes = null;
	    
		Method marshalMethod = inVo.getClass().getMethod("marshalFld", String.class );
		inVoBytes = (byte[])marshalMethod.invoke(inVo, this.mciOuterEncode);
		
		log.info("inVoBytes " + new String(inVoBytes));
	    // https로 서비스 호출
		try {
            // TODO 연동가능할때 테스트필요
			// byte[] bb = CcFwUtil.sendPostUrl(chUrl, inVoBytes, timeoutMs);
			byte[] bb = inVoBytes;
			// 응답객체 얻기
			T outData = outClass.getDeclaredConstructor().newInstance();
			log.info("outData " + new String(bb) + " length " + new String(bb).length());

			Method unmarshalMethod = outData.getClass().getMethod("unMarshalFld", byte[].class, String.class );
			unmarshalMethod.invoke(outData, bb, this.mciOuterEncode);

			
			// 공통메시지 까지는 수신한 경우 에러처리 안함(에러응답시 본문은 안오는 상태일 수 있음)
			MciHfldMsgVO oMsgHdr = _invokeGetter(outData,"getTgrmMsdvValu", MciHfldMsgVO.class);
			if (oMsgHdr.getOffset() == 0) {
				throw new Exception("오류수신: 공통메세지 없음");
			}

			// 공통헤더 얻기
			MciCommHeaderVo outHeader = _invokeGetter(outData,"getTgrmCmnnhddvValu", MciCommHeaderVo.class);
			if (!"0".equals(outHeader.getTgrmDalRsltCd())) {
				new Exception("오류수신:"+outHeader.getTgrmDalRsltCd());
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
    
	private void makeMciHeader(MciCommHeaderVo tgrmCmnnhddvValu, String itrfId, String rcvSvcId, String inqrTraTypeCd, String strYmd){
		// 헤더필수값 설정 후 inVo에 입력
		tgrmCmnnhddvValu.setItrfId(itrfId);
		tgrmCmnnhddvValu.setRcvSvcId(rcvSvcId);
		tgrmCmnnhddvValu.setInqrTraTypeCd(inqrTraTypeCd);		// 조회거래유형코드 등록(C), 조회(R), 변경(U), 삭제(D), 인쇄(P), 다운로드(E) 
		tgrmCmnnhddvValu.setStrYmd(strYmd);
	
		// 어플리케이션상세업무코드 - 고객채널 업무상세구분코드 3째자리 일련번호 규칙 : 앱(0), PC(1), 모바일웹(2)
		/*	홈페이지(PC) DH1, 홈페이지(모바일웹) DH2, 디지털창구(PC) DA1, 디지털창구(모바일웹) DA2, 디지털보험(PC) DI1
			디지털보험(모바일웹) DI2, 디지털플랫폼앱 DA0
		*/
		tgrmCmnnhddvValu.setAppliDtptDutjCd(this.mciAppliDtptDutjCd);
		if (StringUtils.isEmpty(tgrmCmnnhddvValu.getUserId())) {
			tgrmCmnnhddvValu.setUserId("99999999");		// 신한생명
		}
		
		// 디지털창구(모바일웹) - DA2, 디지털플랫폼앱 - DA0, 디지털창구(PC) - DA1	
		if("DA2".equals(this.mciAppliDtptDutjCd) || "DA0".equals(this.mciAppliDtptDutjCd)) {
			if (StringUtils.isEmpty(tgrmCmnnhddvValu.getDrtmCd())) {
				tgrmCmnnhddvValu.setDrtmCd("0995145");		// 본사-스마트창구
			}

		} else if("DA1".equals(this.mciAppliDtptDutjCd)) {
			if (StringUtils.isEmpty(tgrmCmnnhddvValu.getDrtmCd())) {
				tgrmCmnnhddvValu.setDrtmCd("0995350");		// 본사-사이버창구
			}

		// DI1 : 디지털보험 PC || DI2 : 디지털보험 MOBILE WEB || DI0 : 디지털보험 APP
		} else if("DI1".equals(this.mciAppliDtptDutjCd) || "DI2".equals(this.mciAppliDtptDutjCd) || "DI0".equals(this.mciAppliDtptDutjCd)){
			if (StringUtils.isEmpty(tgrmCmnnhddvValu.getDrtmCd())) {
				tgrmCmnnhddvValu.setDrtmCd("0995350");
			}
			
			// 청약서발행입금등록(코드 13) 처리 시 
			List<String> sucoServiceId = Arrays.asList("ONNBA1560", "ONKCB0016","ONNBA0430", "ONNBC0320");
			// 해피콜 대상 조회 / 스크립트조회 / 결과 저장 / 스크립트 저장
			List<String> happyCallServiceId = Arrays.asList("ONNBC0320", "ONNBC0140", "ONNBC0330");
			if (sucoServiceId.contains(rcvSvcId) || happyCallServiceId.contains(rcvSvcId)) {
				tgrmCmnnhddvValu.setDrtmCd("0911000");	// 조직
				tgrmCmnnhddvValu.setUserId("99999971");	// 인사 > 신한생명
				tgrmCmnnhddvValu.setOgnzAsrtCd("12");	// 조직분류 > 스마트인터넷
				tgrmCmnnhddvValu.setOgnzLeveCd("3");	// 조직레벨 > 신한생명
				tgrmCmnnhddvValu.setAcntOgnzNo("0911000");
			}
			
			List<String> happyCallListServiceId = Arrays.asList("ONNBC0250");
			if (happyCallListServiceId.contains(rcvSvcId)) {
				tgrmCmnnhddvValu.setDrtmCd("0995500");	// 조직
				tgrmCmnnhddvValu.setUserId("99999997");	// 인사 > 신한생명
				tgrmCmnnhddvValu.setAcntOgnzNo("0995500");
			}		
		}

	}

    @SuppressWarnings("unchecked")
	private <T> T _invokeGetter(Object inVo, String getMethod, Class<T> outClass) throws Exception {
		T outVo = null;
		try {
			Method toMethod = inVo.getClass().getMethod(getMethod, new Class[0]);
			outVo = (T)toMethod.invoke(inVo, new Object[0]);
		} catch (Exception e) {
			log.error("_invokeGetter Exception", e);
		} 

		return outVo;
	}	

	/**
	 * global id create
	 */
	private String _nextNlGlbId(String appliDutjCd) throws Exception {
		String hostname = StringUtils.defaultString(this.mciHostname, "");

		StringBuilder sb = new StringBuilder();
		sb.append(MciUtil.getCurrentDate("yyyyMMddHHmmssSSS"));	// 17
		sb.append((hostname+"________").substring(0,9));	// 9
		sb.append((appliDutjCd+"___").substring(0,3));		// 3 어플리케이션 업무코드
		//		sb.append(("00000001"));							// 8 전문채번번호
		String next = sb.toString();
		int seq = 1;
		if (glbId != null && glbId.startsWith(next)) {
			seq = MciUtil.parseToInt(glbId.substring(29)) + 1;
		}
		next += MciUtil.intToString(seq, 8);
		glbId = next;
		
		return next;
	}
}
