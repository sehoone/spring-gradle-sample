package com.sehoon.springgradlesample.module.sample.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * sample 컨트롤러
 */
@RestController
@RequestMapping("/api/sample")
@Slf4j
public class SampleController {

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


}
