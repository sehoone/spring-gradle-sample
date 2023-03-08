package com.sehoon.springgradlesample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.sehoon.springgradlesample.common.constant.ApplicationConstant;
import com.sehoon.springgradlesample.common.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfig {

    @Bean
    @Profile({ApplicationConstant.SPRING_PROFILE_DEVELOPMENT, ApplicationConstant.SPRING_PROFILE_LOCAL})
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
