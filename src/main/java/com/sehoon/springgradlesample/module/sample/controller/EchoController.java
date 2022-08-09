package com.sehoon.springgradlesample.module.sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * echo 컨트롤러
 * mci request 테스트를 위해 사용
 */
@RestController
@RequestMapping("/api/echo")
@Slf4j
public class EchoController {

	@RequestMapping(value = "/", consumes = MediaType.ALL_VALUE, produces = 
		MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
	public ResponseEntity<?> echoBack(@RequestBody(required = false) byte[] rawBody) {
        log.debug("echoBack start "+new String(rawBody));

		return ResponseEntity.status(HttpStatus.OK).body(new String(rawBody));
	}

}
