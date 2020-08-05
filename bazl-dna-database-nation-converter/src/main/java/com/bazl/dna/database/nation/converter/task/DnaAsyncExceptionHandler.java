package com.bazl.dna.database.nation.converter.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class DnaAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DataTask.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		LOG.info("Exception message - " + ex);
		LOG.info("Method name - " + method.getName());
		for (Object param : params) {
			LOG.info("Parameter value - " + param);
		}
	}

}
