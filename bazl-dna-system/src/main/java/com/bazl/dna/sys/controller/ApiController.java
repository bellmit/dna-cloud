package com.bazl.dna.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.password}")
	private String p;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@GetMapping("clean")
	public ResponseData clean(int index) {
		if (index == 0) {
			return new ResponseData("0号库不清理");
		}
		Jedis jedis = new Jedis(host, port);
		String auth = jedis.auth(p);
		LOGGER.info("info: {}", auth);
		if (jedis.isConnected()) {
			jedis.select(index);
			jedis.flushDB();
		}
		jedis.close(); 
		return new ResponseData();
	}
	
}
