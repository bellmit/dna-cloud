package com.bazl.dna.database.lims.converter.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class DnaAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DnaAsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		LOG.error("Exception message:", ex);
		LOG.error("Method name:", method.getName());
		for (Object param : params) {
			LOG.error("Parameter value:", param);
		}
	}

}
