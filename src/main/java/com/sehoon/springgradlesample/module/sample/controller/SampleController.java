package com.sehoon.springgradlesample.module.sample.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sehoon.springgradlesample.common.mci.util.DpMciUtil;
import com.sehoon.springgradlesample.common.mci.vo.MciCommHeaderVo;
import com.sehoon.springgradlesample.common.vo.SAMPLE00002IVO;
import com.sehoon.springgradlesample.common.vo.SAMPLE00002OVO;
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
        MciCommHeaderVo mciCommHeaderVo = new MciCommHeaderVo();
        mciCommHeaderVo.setTgrmLencn("00002222");
        log.info(new String(mciCommHeaderVo.marshalFld(), "UTF-8"));
        log.info(applicationProperties.getMciUrl());
        return word;
    }

    @GetMapping("/hello-world2")
    public String helloWorld2() throws Exception {
        String word = applicationProperties.getCustomVal();

        SAMPLE00002IVO inVo = new SAMPLE00002IVO();
        // inVo.getTgrmCmnnhddvValu().setAcntOgnzNo("tt!!");;
        SpecifyDataVO specifyDataVO = new SpecifyDataVO();
        specifyDataVO.setCno("12389120");
        inVo.setTgrmDtdvValu(specifyDataVO);

        SAMPLE00002OVO outVo = DpMciUtil.mciCallSerivce(inVo, SAMPLE00002OVO.class, "SAMPLE00002", "ONCSC1340", "R");

        log.info(outVo.toString());

        return word;
    }
}
