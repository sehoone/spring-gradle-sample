package com.sehoon.springgradlesample.module.sample.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.sehoon.springgradlesample.common.mci.manager.MciPropManager;
import com.sehoon.springgradlesample.common.mci.util.DpMciUtil;
import com.sehoon.springgradlesample.common.vo.SAMPLE00001IVO;
import com.sehoon.springgradlesample.common.vo.SAMPLE00001OVO;
import com.sehoon.springgradlesample.common.vo.SpecifyDataVO;
import com.sehoon.springgradlesample.config.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * sample 컨트롤러
 */
@RestController
@RequestMapping("/api/sample")
@Slf4j
public class SampleController {

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * {@code GET /api/sample/hello-world} : get hello-world text
     *
     * @param 
     * @return 
     * @throws IOException
     */
    @GetMapping("/hello-world")
    public String helloWorld() throws IOException {
        String word = "hello-world!!";
        String activeProfile = System.getProperty("spring.profiles.active");
		if (StringUtils.isBlank(activeProfile)) {
			activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
		}
        log.info(activeProfile);
        log.info(applicationProperties.getMciUrl());
        return word;
    }

    @GetMapping("/hello-world2")
    public String helloWorld2() throws Exception {
        String word = applicationProperties.getCustomVal();

        SAMPLE00001IVO inVo = new SAMPLE00001IVO();

        SpecifyDataVO specifyDataVO = new SpecifyDataVO();
        specifyDataVO.setCno("12389120");
        inVo.setTgrmDtdvValu(specifyDataVO);

        SAMPLE00001OVO outVo = DpMciUtil.mciCallSerivce(inVo, SAMPLE00001OVO.class, "SAMPLE00001", "ONCSC1340", "R");

        log.info(outVo.toString());

        return word;
    }
}
