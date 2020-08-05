package com.bazl.dna.common.filter;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.util.ClassesUtil;

/**
 * Cache Aspect
 * @author zhaoyong
 *
 */
@Aspect
@Component
public class RedisCacheAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

	@Autowired
	@Qualifier("jsonRedisTemplate")	
	private RedisTemplate<String, Object> jsonRedisTemplate;
	
	@Value("${spring.redis.enable}")
	private boolean redisEnabled; 

	@Pointcut("@annotation(com.bazl.dna.annotation.RedisCachePut)")
	public void annotationPut() {
	}

	@Pointcut("@annotation(com.bazl.dna.annotation.RedisCacheAble)")
	public void annotationAble() {
	}
	
	@Pointcut("@annotation(com.bazl.dna.annotation.RedisCacheEvict)")
	public void annotationEvict() {
	}
	
	/**
	 * annotationAble
	 * 
	 * @param joinPoint
	 * @param rd
	 * @return
	 * @throws Throwable
	 */
	@Around("annotationAble()&& @annotation(rd)")
	public Object redisCacheAble(ProceedingJoinPoint joinPoint, RedisCacheAble rd) throws Throwable {
		
		String key = "";
		if (redisEnabled) {
			String preKey = rd.value();
			String arg0 = joinPoint.getArgs()[0].toString();
	
			Class<?> returnClassType = ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();
			key = preKey + ":" + arg0;
			String rtObject = (String) jsonRedisTemplate.opsForValue().get(key);
		
			// Return Cache
			if (rtObject != null) {
				LOGGER.info("get cache: {}", key);
				return JSON.parseObject(rtObject, returnClassType);
			}
		}

		Object sourceObject = joinPoint.proceed();
		if (sourceObject == null) {
			return null;
		}
		
		if (redisEnabled) {
			// Set cache
			jsonRedisTemplate.opsForValue().set(key, JSON.toJSONString(sourceObject), PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
		}
		return sourceObject;
	}
	
	/**
	 * annotationEvict
	 * @param joinPoint
	 * @param rd
	 * @throws Throwable
	 */
	@After("annotationEvict()&& @annotation(rd)")
	public void redisCacheEvict(JoinPoint joinPoint, RedisCacheEvict rd) throws Throwable {
		if (redisEnabled) {
			String preKey = rd.value();
			String arg0 = joinPoint.getArgs()[0].toString();
	
			String key = preKey + ":" + arg0;
			LOGGER.info("delete key: {}", key);
			jsonRedisTemplate.delete(key);
		}
	}
	
	/**
	 * annotationPut
	 * @param joinPoint
	 * @param resultValue
	 * @param rd
	 * @throws Throwable
	 */
	@AfterReturning(returning = "resultValue", pointcut = "annotationPut()&& @annotation(rd)")
	public void redisCachePut(JoinPoint joinPoint, Object resultValue, RedisCachePut rd) throws Throwable {

		if (resultValue == null) {
			return;
		}
		if (redisEnabled) {
			String key = getRedisKey(rd, resultValue);
			LOGGER.info("delete key: {}", key);
			jsonRedisTemplate.delete(key);
	
			// Set cache
			jsonRedisTemplate.opsForValue().set(key, JSON.toJSONString(resultValue), PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
		}
	}

	private static String getRedisKey(RedisCachePut rd, Object sourceObject) {
		String key = rd.key();
		key = key.substring(1);

		String firstLetter = key.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + key.substring(1);
		Object keyValue = ClassesUtil.invokeMethod(sourceObject, getter, new Object[] {});

		return rd.value() + ":" + keyValue.toString();
	}

}
