package com.bazl.dna.common.filter;

import com.bazl.dna.util.RequestUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeginClientConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeginClientConfig.class);
	
    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                    requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    try {
						requestTemplate.header("Authorization", RequestUtils.getToken());
					} catch (Exception e) {
						LOGGER.error("Interceptor:", e);
					}
            }
        };
    }
 
}
