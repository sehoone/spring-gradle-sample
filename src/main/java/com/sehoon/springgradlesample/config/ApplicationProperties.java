package com.sehoon.springgradlesample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Properties specific
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String customVal;
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
}

