package com.sehoon.springgradlesample.module.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     */
    @GetMapping("/hello-world")
    public String helloWorld() {
        String word = "hello-world!!";
        log.debug(word);
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
