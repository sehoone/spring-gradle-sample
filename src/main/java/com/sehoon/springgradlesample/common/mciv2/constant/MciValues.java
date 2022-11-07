package com.sehoon.springgradlesample.common.mciv2.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class MciValues {

    /**
     * ONCSC4050 - 통신사 인증(SCI 평가 정보)
     */
    @Value("${mci.ONCSC4050.betrCd:}")
    private String betrCd;

    /**
     * ONCRS2351 - 신한 플러스 - 멤버십 가입
     */
    @Value("${mci.ONCRS2351.insPlnrDalCsgnAgrYn:}")
    private String insPlnrDalCsgnAgrYn;

    @Value("${mci.ONCRS2351.unfcPlfMemTypeCd:}")
    private String unfcPlfMemTypeCd;

    @Value("${mci.ONCRS2351.unfcPlfEntChnnCd:}")
    private String unfcPlfEntChnnCd;

    @Value("${mci.ONCRS2351.unfcPlfPushMsgGpcoDvsnCd:}")
    private String unfcPlfPushMsgGpcoDvsnCd;

    @Value("${mci.ONCRS2351.unfcPlfUseAgrYn:}")
    private String unfcPlfUseAgrYn;

    @Value("${mci.ONCRS2351.unfcPlfUseAgrRclwYn:}")
    private String unfcPlfUseAgrRclwYn;

    @Value("${mci.ONCRS2351.unfcPlfGpcoCd:}")
    private String unfcPlfGpcoCd;

    @Value("${mci.ONCRS2351.unfcPlfCtinClsdAgrYn:}")
    private String unfcPlfCtinClsdAgrYn;

    @Value("${mci.ONCRS2351.unfcPlfCtinOfeAgrYn:}")
    private String unfcPlfCtinOfeAgrYn;

    @Value("${mci.ONCRS2351.unfcPlfPoinUseAgrYn:}")
    private String unfcPlfPoinUseAgrYn;

    @Value("${mci.ONCRS2351.unfcPlfAlcoOfeAgrYn:}")
    private String unfcPlfAlcoOfeAgrYn;

    @Value("${mci.ONCRS2351.unfcPlfIndvInfoClsdAgrYn:}")
    private String unfcPlfIndvInfoClsdAgrYn;

    @Value("${mci.ONCRS2351.mebsInsPlnrDalCsgnAgrYn:}")
    private String mebsInsPlnrDalCsgnAgrYn;

    @Value("${mci.ONCRS2351.mebsUseAgrYn:}")
    private String mebsUseAgrYn;

    @Value("${mci.ONCRS2351.mebsCtinClsdAgrYn:}")
    private String mebsCtinClsdAgrYn;

    @Value("${mci.ONCRS2351.mebsOfeYn:}")
    private String mebsOfeYn;

    @Value("${mci.ONCRS2351.mebsPoinUseAgrYn:}")
    private String mebsPoinUseAgrYn;

    @Value("${mci.ONCRS2351.mebsAlcoOfeYn:}")
    private String mebsAlcoOfeYn;

    @Value("${mci.ONCRS2351.mebsIndvInfoClsdAgrYn:}")
    private String mebsIndvInfoClsdAgrYn;

    /**
     * ONCRS5230 - 신한 플러스 - 포인트 적립 요청
     */
    @Value("${mci.ONCRS5230.poinRrvRsDtptCd:}")
    private String poinRrvRsDtptCd;

    @Value("${mci.ONCRS5230.unfcRwdPoinChnnCursCd:}")
    private String unfcRwdPoinChnnCursCd;

    @Value("${mci.ONCRS5230.unfcRwdPoinKdDtptCd:}")
    private String unfcRwdPoinKdDtptCd;

    @Value("${mci.ONCRS5230.unfcRwdPoinKdCd:}")
    private String unfcRwdPoinKdCd;

    @Value("${mci.ONCRS5230.unfcRwdRrvTypeCd:}")
    private String unfcRwdRrvTypeCd;

    @Value("${mci.ONCRS5230.unfcRwdRrvpCd:}")
    private String unfcRwdRrvpCd;

    @Value("${mci.ONCRS5230.unfcRwdRrvpNm:}")
    private String unfcRwdRrvpNm;

    @Value("${mci.ONCRS5230.evntNm:}")
    private String evntNm;

    /**
     * ONILC0420 - 문자 발송 - SMS(SMS)
     */
    @Value("${mci.ONILC0420.msdiTphnNo:}")
    private String msdiTphnNo;

    @Value("${mci.ONILC0420.duesOgnzNo:}")
    private String duesOgnzNo;

    @Value("${mci.ONILC0420.duesPrafNo:}")
    private String duesPrafNo;

    @Value("${mci.ONILC0420.ntleCd:}")
    private String ntleCd;

    @Value("${mci.ONILC0420.notiPmlAplcOgnzNo:}")
    private String notiPmlAplcOgnzNo;

    @Value("${mci.ONILC0420.notiPmlAplcPrafNo:}")
    private String notiPmlAplcPrafNo;

    /**
     * OCRMB2101 - CRM 마케팅 동의 정보 등록
     */
    @Value("${mci.OCRMB2101.mkcnsDcnuNo:}")
    private String mkcnsDcnuNo;

    @Value("${mci.OCRMB2101.mkcnsDcmnAsrtCd:}")
    private String mkcnsDcmnAsrtCd;

    @Value("${mci.OCRMB2101.mkcnsDtptDcmnReqYn:}")
    private String mkcnsDtptDcmnReqYn;

    @Value("${mci.OCRMB2101.thpsnInfoOfeAgrYn1:}")
    private String thpsnInfoOfeAgrYn1;

    @Value("${mci.OCRMB2101.cstInflPrgrId:}")
    private String cstInflPrgrId;

    @Value("${mci.OCRMB2101.cstAgrInflSystCd:}")
    private String cstAgrInflSystCd;

    @Value("${mci.OCRMB2101.cstAgrDujTypeCd:}")
    private String cstAgrDujTypeCd;

    private static class Holder {
        private static final MciValues INSTANCE = new MciValues();
    }

    @Bean
    public static MciValues getInstance() {
        return Holder.INSTANCE;
    }
}
