package com.sehoon.springgradlesample.module.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sehoon.springgradlesample.common.mci.util.DpMciUtil;
import com.sehoon.springgradlesample.common.vo.CmciVO;
import com.sehoon.springgradlesample.common.vo.GenericVO;
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
        // CmciVO testVO = new CmciVO();

        // DpMciUtil.mciCallSerivce(testVO, "ee");

        GenericVO<SpecifyDataVO> genericVO = new GenericVO<SpecifyDataVO>();
        SpecifyDataVO specifyDataVO = new SpecifyDataVO();
        specifyDataVO.setAge(11);
        specifyDataVO.setUserName("sehoon");

        genericVO.setTgrmDtdvValu(specifyDataVO);

        log.info(genericVO.getTgrmDtdvValu().getUserName());

        log.info(word);
        return word;
    }

}
