package com.sehoon.springgradlesample.common.mci.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.sehoon.springgradlesample.common.mci.constant.MciChannelConst;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVO;

public class DpMciClientUtil {
	public static <T> T mciCallSerivce(Object inVo, Class<T> outClass, String itrfId, String rcvSvcId, String inqrTraTypeCd) throws Exception {
        // 필수값 체크
		Assert.hasText(itrfId, "인터페이스ID is empty");
		Assert.hasText(rcvSvcId, "서비스ID is empty");
		Assert.hasText(inqrTraTypeCd, "조회거래유형코드 is empty");

        return send(inVo,outClass,itrfId,rcvSvcId,inqrTraTypeCd,MciUtil.getCurrentDate("yyyyMMdd"));
	}

    private static <T> T send(Object inVo, Class<T> outClass, String itrfId, String rcvSvcId, String inqrTraTypeCd, String strYmd) throws Exception {
	
		// set 헤더 vo 비즈니스
		MciCommHeaderVO tgrmCmnnhddvValu = new MciCommHeaderVO();
		Method getHeaderVo = inVo.getClass().getMethod("getTgrmCmnnhddvValu", new Class[0]);
		MciCommHeaderVO getHeader = (MciCommHeaderVO) getHeaderVo.invoke(inVo, new Object[0]);
		MciUtil.mergeVo(tgrmCmnnhddvValu, getHeader);
		
		makeMciHeader(tgrmCmnnhddvValu, itrfId, rcvSvcId, inqrTraTypeCd, strYmd);

		Method toMethod = inVo.getClass().getMethod("setTgrmCmnnhddvValu", MciCommHeaderVO.class);
		toMethod.invoke(inVo, tgrmCmnnhddvValu);

		// set 수신 채널
		MciChannelConst channel = MciChannelConst.MCI_INNER;
		if(rcvSvcId.indexOf("ONRIA") >= 0 ) {
        	channel = MciChannelConst.MCI_OUTER;
        }

		return MciClientUtil.send(channel, inVo, outClass);
	}

	private static void makeMciHeader(MciCommHeaderVO tgrmCmnnhddvValu, String itrfId, String rcvSvcId, String inqrTraTypeCd, String strYmd){
		// 헤더필수값 설정 후 inVo에 입력
		tgrmCmnnhddvValu.setItrfId(itrfId);
		tgrmCmnnhddvValu.setRcvSvcId(rcvSvcId);
		tgrmCmnnhddvValu.setInqrTraTypeCd(inqrTraTypeCd);		// 조회거래유형코드 등록(C), 조회(R), 변경(U), 삭제(D), 인쇄(P), 다운로드(E) 
		tgrmCmnnhddvValu.setStrYmd(strYmd);
	
		// 어플리케이션상세업무코드 - 고객채널 업무상세구분코드 3째자리 일련번호 규칙 : 앱(0), PC(1), 모바일웹(2)
		/*	홈페이지(PC) DH1, 홈페이지(모바일웹) DH2, 디지털창구(PC) DA1, 디지털창구(모바일웹) DA2, 디지털보험(PC) DI1
			디지털보험(모바일웹) DI2, 디지털플랫폼앱 DA0
		*/
		String appliDtptDutjCd = MciUtil.getMciProp("mci.appli-dtpt-dutj-cd");
		tgrmCmnnhddvValu.setAppliDtptDutjCd(appliDtptDutjCd);
		if (StringUtils.isEmpty(tgrmCmnnhddvValu.getUserId())) {
			tgrmCmnnhddvValu.setUserId("99999999");		// 신한생명
		}
		
		// 디지털창구(모바일웹) - DA2, 디지털플랫폼앱 - DA0, 디지털창구(PC) - DA1	
		if("DA2".equals(appliDtptDutjCd) || "DA0".equals(appliDtptDutjCd)) {
			if (StringUtils.isEmpty(tgrmCmnnhddvValu.getDrtmCd())) {
				tgrmCmnnhddvValu.setDrtmCd("0995145");		// 본사-스마트창구
			}

		} else if("DA1".equals(appliDtptDutjCd)) {
			if (StringUtils.isEmpty(tgrmCmnnhddvValu.getDrtmCd())) {
				tgrmCmnnhddvValu.setDrtmCd("0995350");		// 본사-사이버창구
			}

		// DI1 : 디지털보험 PC || DI2 : 디지털보험 MOBILE WEB || DI0 : 디지털보험 APP
		} else if("DI1".equals(appliDtptDutjCd) || "DI2".equals(appliDtptDutjCd) || "DI0".equals(appliDtptDutjCd)){
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
}
