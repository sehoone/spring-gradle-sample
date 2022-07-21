package com.sehoon.springgradlesample.module.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String helloWorld2() {
        String word = applicationProperties.getCustomVal();
        log.debug(word);
        return word;
    }

}
