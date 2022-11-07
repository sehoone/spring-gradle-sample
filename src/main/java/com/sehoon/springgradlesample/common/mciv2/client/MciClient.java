package com.sehoon.springgradlesample.common.mciv2.client;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Value;

import com.sehoon.springgradlesample.common.mciv2.message.header.MciHeader;
import com.sehoon.springgradlesample.common.mciv2.message.header.MciHfldMessage;
import com.sehoon.springgradlesample.common.mciv2.message.header.MciHfldMessageHeader;
import com.sehoon.springgradlesample.common.mciv2.message.header.MciMarshalable;
import com.sehoon.springgradlesample.common.mciv2.message.header.MciMessage;
import com.sehoon.springgradlesample.common.mciv2.util.MciUtil;

@Component
public class MciClient {

	private final Logger log = LoggerFactory.getLogger("LOG_MCI");

	@Value("${mci.hostname:}")
	private String mciHostname;

	@Value("${mci.appliDtptDutjCd:}")
	private String mciAppliDtptDutjCd;

	@Value("${mci.envrTypeCd:}")
	private String mciEnvrTypeCd;

	@Value("${mci.outerBaseUrl:}")
	private String mciOuterBaseUrl;

	@Value("${mci.outerFrbuCd:}")
	private String mciOuterFrbuCd;

	@Value("${mci.outerCmouDutjCd:}")
	private String mciOuterCmouDutjCd;

	@Value("${mci.outerEncode:}")
	private String mciOuterEncode;

	private String glbId = null;

	/**
	 * MCI 연동. only MCI대외계를 통해서만 MCI대내계와 통신하므로 fixed HTTP통신만 처리
	 * 
	 * @param <T>
	 * @param inVo
	 * @param outClass
	 * @param itrfId
	 * @param rcvSvcId
	 * @param inqrTraTypeCd
	 * @param cmouTraCd
	 * @return
	 * @throws Exception
	 */
	public <T extends MciMarshalable> MciMessage<T> mciCallSerivce(MciMessage<?> inVo, Class<T> outClass, String itrfId,
			String rcvSvcId, String inqrTraTypeCd, String cmouTraCd) throws Exception {
		// 필수값 체크
		Assert.hasText(itrfId, "인터페이스ID is empty");
		Assert.hasText(rcvSvcId, "서비스ID is empty");
		Assert.hasText(inqrTraTypeCd, "조회거래유형코드 is empty");
		Assert.hasText(cmouTraCd, "대외거래코드 is empty");

		// set 헤더VO
		MciHeader header = new MciHeader();
		MciHeader getHeader = inVo.getHeader();
		MciUtil.mergeVo(header, getHeader);

		makeMciHeader(header, itrfId, rcvSvcId, inqrTraTypeCd, MciUtil.getCurrentDate("yyyyMMdd"), cmouTraCd);

		inVo.setHeader(header);

		// set 메세지VO
		MciHfldMessage hfldMessage = new MciHfldMessage();
		MciHfldMessage getHfldMsgHeader = inVo.getHfldMessage();
		MciUtil.mergeVo(hfldMessage, getHfldMsgHeader);

		makeMciMsgHeader(hfldMessage);

		inVo.setHfldMessage(hfldMessage);

		try {
			return sendHFLD(inVo, outClass, 60000);
		} catch (Exception e) {
			log.error("mciCallSerivce exception", e);
			MciMessage<T> outData = new MciMessage<T>();
			header = outData.getHeader();
			header.setTgrmDalRsltCd("9999");
			return outData;
		}
	}

	private <T extends MciMarshalable> MciMessage<T> sendHFLD(MciMessage<?> inVo, Class<T> outClass, int timeoutMs)
			throws Exception {

		// 요청파라메터로 변경
		byte[] inVoBytes = inVo.marshalFld();
		log.info("inData " + new String(inVoBytes, "euc-kr"));

		// MCI 서비스 HTTP 호출
		byte[] bb = MciUtil.sendPostUrl(this.mciOuterBaseUrl, inVoBytes, timeoutMs);
		log.info("outData " + new String(bb));

		// 응답객체 얻기
		MciMessage<T> outData = new MciMessage<T>();
		T bodyData = outClass.getDeclaredConstructor().newInstance();
		outData.setData(bodyData);
		outData.unMarshalFld(bb, this.mciOuterEncode);

		MciHfldMessage oMsgHdr = outData.getHfldMessage();
		if (oMsgHdr.getOffset() == 0) {
			throw new Exception("오류수신: 공통메세지 없음");
		}

		MciHeader outHeader = outData.getHeader();
		if (!"0".equals(outHeader.getTgrmDalRsltCd())) {
			new Exception("오류수신:" + outHeader.getTgrmDalRsltCd());
		}

		return outData;

	}

	/**
	 * MCI Header set
	 * 
	 * @param header
	 * @param itrfId
	 * @param rcvSvcId
	 * @param inqrTraTypeCd
	 * @param strYmd
	 * @throws Exception
	 */
	private void makeMciHeader(MciHeader header, String itrfId, String rcvSvcId, String inqrTraTypeCd, String strYmd,
			String cmouTraCd) throws Exception {
		/** 1. MCI 연동 필수값 셋팅 */
		// tgrmCmnnhddvValu.setTgrmLencn("");
		header.setItrfId(itrfId); // 인터페이스ID
		header.setRcvSvcId(rcvSvcId); // 수신서비스ID
		header.setInqrTraTypeCd(inqrTraTypeCd); // 조회거래유형코드 (C:등록, R:조회, U:변경, D:삭제, P:인쇄, E:다운로드)
		header.setStrYmd(strYmd); /// 기준일자
		header.setEnvrTypeCd(this.mciEnvrTypeCd); // 환경유형코드 (P:REAL(RUN), T:TEST(TST), D: 개발(DEV))
		header.setReqTgrmTnsmDtptDt(MciUtil.getCurrentDateTime("yyyyMMddHHmmssSSS")); // 요청전문정송상세일시
		header.setAppliDtptDutjCd(this.mciAppliDtptDutjCd); // 어플리케이션상세업무코드

		// 대외 MCI 전용 파라메터 세팅
		header.setFrbuCd(this.mciOuterFrbuCd);
		header.setCmouDutjCd(this.mciOuterCmouDutjCd);
		header.setCmouTraCd(cmouTraCd);

		header.setUserIpAddr(InetAddress.getLocalHost().getHostAddress());

		String glbId = header.getGlbId(); // 글로벌 전문통신 ID
		if (StringUtils.isEmpty(glbId)) {
			String appliDutjCd = header.getItrfId().substring(0, 3); // 어플리케이션업무코드(3)
			header.setGlbId(_nextNlGlbId(appliDutjCd)); // set 글로벌 전문통신 ID
			header.setAppliDutjCd(appliDutjCd); // 어플리케이션업무코드 - 글로벌ID에 포함되어 있지만 업무팀에서 별도사용이 용이하도록 명시적으로 분리 추가
		}

		String pgrsSriaNo = header.getPgrsSriaNo(); // 진행일련번호 - 글로벌ID에서 분리됨, 없으면 001 세팅
		if (StringUtils.isEmpty(pgrsSriaNo)) {
			header.setPgrsSriaNo("001");
		}

	}

	/**
	 * MCI Header Message set
	 * 
	 * @param hfldMessage
	 * @throws Exception
	 */
	private void makeMciMsgHeader(MciHfldMessage hfldMessage) throws Exception {
		/** 1. MCI 연동 메세지 필수값 셋팅 */
		MciHfldMessageHeader hfldMessageHeader = hfldMessage.getHeader();
		String msgRpttCc = hfldMessageHeader.getMsgRpttCc();
		if (StringUtils.isEmpty(msgRpttCc)) {
			hfldMessageHeader.setMsgRpttCc("1");
			hfldMessage.setHeader(hfldMessageHeader);
		}
	}

	/**
	 * global id create
	 */
	private String _nextNlGlbId(String appliDutjCd) throws Exception {
		String hostname = StringUtils.defaultString(this.mciHostname, "");

		StringBuilder sb = new StringBuilder();
		sb.append(MciUtil.getCurrentDateTime("yyyyMMddHHmmssSSS")); // 17
		sb.append((hostname + "________").substring(0, 9)); // 9
		sb.append((appliDutjCd + "___").substring(0, 3)); // 3 어플리케이션 업무코드
		// sb.append(("00000001")); // 8 전문채번번호
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
