package com.sehoon.springgradlesample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sehoon.springgradlesample.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class SpringGradleSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGradleSampleApplication.class, args);
	}

}
