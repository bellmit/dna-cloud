package com.bazl.dna.common.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import feign.codec.Encoder;

@Configuration
public class FeignSupportConfig {

	@Bean
	@Primary
	@Scope("prototype")
	public Encoder feignFormEncoder() {
		return new FeignEncoder();
	}
}
