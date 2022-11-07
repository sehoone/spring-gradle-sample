package com.sehoon.springgradlesample.common.mciv2.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class MciValues {

    /**
     * 통신사 인증(SCI 평가 정보)
     */
    @Value("${mci.ONCSC4050.betrCd:}")
    private String betrCd;

    private static class Holder {
        private static final MciValues INSTANCE = new MciValues();
    }

    @Bean
    public static MciValues getInstance() {
        return Holder.INSTANCE;
    }
}
