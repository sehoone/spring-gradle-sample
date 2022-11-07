package com.sehoon.springgradlesample.module.sample.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sehoon.springgradlesample.common.mci.util.DpMciClientUtil;
import com.sehoon.springgradlesample.common.mciv2.client.MciClient;
import com.sehoon.springgradlesample.common.vo.SAMPLE00001IVO;
import com.sehoon.springgradlesample.common.vo.SAMPLE00001OVO;
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

    @Autowired
    private MciClient mciClient;
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

        log.info(word);
        return word;
    }

    @GetMapping("/mci/v1/json-type")
    public String jsonTypeV1() throws Exception {
        String word = applicationProperties.getCustomVal();

        SAMPLE00001IVO inVo = new SAMPLE00001IVO();
        SpecifyDataVO specifyDataVO = new SpecifyDataVO();
        specifyDataVO.setCno("33221443");
        inVo.setTgrmDtdvValu(specifyDataVO);

        SAMPLE00001OVO outVo = DpMciClientUtil.mciCallSerivce(inVo, SAMPLE00001OVO.class, "SAMPLE00001", "ONCSC1340", "R");
        // SAMPLE00001OVO outVo = MciClientUtil.send(MciChannelConst.MCI_INNER, inVo, SAMPLE00001OVO.class);
        

        log.info(outVo.toString());

        return word;
    }

    @GetMapping("/mci/v1/fld-type")
    public String fldTypeV1() throws Exception {
        String word = applicationProperties.getCustomVal();
        
        SAMPLE00002IVO inVo = new SAMPLE00002IVO();
        // inVo.getTgrmCmnnhddvValu().setAcntOgnzNo("tt!!");;
        SpecifyDataVO specifyDataVO = new SpecifyDataVO();
        specifyDataVO.setCno("12389120");
        inVo.setTgrmDtdvValu(specifyDataVO);

        SAMPLE00002OVO outVo = DpMciClientUtil.mciCallSerivce(inVo, SAMPLE00002OVO.class, "SAMPLE00002", "ONRIA2212", "R");
        // SAMPLE00002OVO outVo = MciClientUtil.send(MciChannelConst.MCI_OUTER, inVo, SAMPLE00002OVO.class);

        log.info(outVo.toString());

        return word;
    }

    // @GetMapping("/mci/v2/json-type")
    // public String jsonTypeV2() throws Exception {
    //     String word = applicationProperties.getCustomVal();
    //     log.info("dd1 "+mciClient.toString());
    //     SAMPLE00003IVO inVo = new SAMPLE00003IVO();
    //     SpecifyDataVO specifyDataVO = new SpecifyDataVO();
    //     specifyDataVO.setCno("33221443");
    //     inVo.setTgrmDtdvValu(specifyDataVO);

    //     SAMPLE00003OVO outVo = mciClient.mciCallSerivce(inVo, SAMPLE00003OVO.class, "SAMPLE00003", "ONCSC1340", "R");
    //     // SAMPLE00001OVO outVo = MciClientUtil.send(MciChannelConst.MCI_INNER, inVo, SAMPLE00001OVO.class);
    //     log.info("result code "+outVo.getTgrmCmnnhddvValu().getTgrmDalRsltCd());

    //     log.info(outVo.toString());

    //     return word;
    // }

    // @GetMapping("/mci/v2/fld-type")
    // public String fldTypeV2() throws Exception {
    //     String word = applicationProperties.getCustomVal();
    //     log.info("dd2 "+mciClient.toString());
    //     SAMPLE00004IVO inVo = new SAMPLE00004IVO();
    //     // inVo.getTgrmCmnnhddvValu().setAcntOgnzNo("tt!!");;
    //     SpecifyDataVO specifyDataVO = new SpecifyDataVO();
    //     specifyDataVO.setCno("12389120");
    //     inVo.setTgrmDtdvValu(specifyDataVO);

    //     SAMPLE00004OVO outVo = mciClient.mciCallSerivce(inVo, SAMPLE00004OVO.class, "SAMPLE00004", "ONRIA2212", "R");
    //     // SAMPLE00002OVO outVo = MciClientUtil.send(MciChannelConst.MCI_OUTER, inVo, SAMPLE00002OVO.class);
    //     log.info("result code "+outVo.getTgrmCmnnhddvValu().getTgrmDalRsltCd());
    //     log.info(outVo.toString());

    //     return word;
    // }

    // @GetMapping("/mci/v2/fld-type2")
    // public String fldTypeV22() throws Exception {
    //     String word = applicationProperties.getCustomVal();
    //     log.info("dd2 "+mciClient.toString());

    //     EaiReqVO<SAMPLE00005IVO> inVo = new EaiReqVO<SAMPLE00005IVO>(true);
    //     inVo.getTgrmDtdvValu().setCno("12389120");

    //     SAMPLE00004OVO outVo = mciClient.mciCallSerivce(inVo, SAMPLE00004OVO.class, "SAMPLE00004", "ONRIA2212", "R");

    //     // inVo.getTgrmCmnnhddvValu().setAcntOgnzNo("tt!!");;
    //     // SpecifyDataVO specifyDataVO = new SpecifyDataVO();
    //     // specifyDataVO.setCno("12389120");
    //     // inVo.setTgrmDtdvValu(specifyDataVO);

    //     // SAMPLE00004OVO outVo = mciClient.mciCallSerivce(inVo, SAMPLE00004OVO.class, "SAMPLE00004", "ONRIA2212", "R");
    //     // // SAMPLE00002OVO outVo = MciClientUtil.send(MciChannelConst.MCI_OUTER, inVo, SAMPLE00002OVO.class);
    //     // log.info("result code "+outVo.getTgrmCmnnhddvValu().getTgrmDalRsltCd());
    //     // log.info(outVo.toString());

    //     return word;
    // }

}
