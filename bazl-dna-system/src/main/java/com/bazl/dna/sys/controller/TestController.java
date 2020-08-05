package com.bazl.dna.sys.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.RedisConfig;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.sys.constants.SysMessageConstants;
import com.bazl.dna.sys.entity.SysMessage;
import com.bazl.dna.sys.rabbitmq.ISendService;
import com.bazl.dna.sys.service.ISysUserService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private ISendService sendService;
	
	@Autowired
	private RedisTemplate<String, Object> jsonRedisTemplate;
	
	@Autowired
	private RedisConfig redisConfig;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@GetMapping("sendMessage")
	public ResponseData sendMessage(String message) {
		SysMessage entity = new SysMessage();
		entity.setQueueType(SysMessageConstants.QUEUE_TYPE);
		entity.setQueueKey(SysMessageConstants.QUEUE_KEY);
		entity.setCreateTime(new Timestamp(new Date().getTime()));
		entity.setUpdateTime(new Timestamp(new Date().getTime()));
		
		entity.setMessageType("test");
		entity.setMessageName("测试");
		entity.setContext(message);
		entity.setSendUser("admin");
		entity.setReceiveUser("admin");
		sendService.send(JSON.toJSONString(entity));
		return new ResponseData(message);
	}
	
	/**
	 * 获取不同的数据源
	 */
	@GetMapping("getDatasource")
	public ResponseData getDatasource() {
		
		Map<String, Object> page1 = sysUserService.findList(new JSONObject());
		LOGGER.info("size:{}", page1.size());
		
		try {
			DataSource dataSource = (DataSource)ContextHolder.dataSourceMap.get("localhost");
			Connection connection = dataSource.getConnection();
			String sql = "select * from nt_sys_user";
			List<Map<String, String>> userList = DataSourceUtil.execute(connection, sql);
			LOGGER.info("size:{}", userList.size());
			connection.close();
		} catch (SQLException e) {
			LOGGER.error("sql error:", e);
		}
		
		Map<String, Object> page2 = sysUserService.findList(new JSONObject());
		LOGGER.info("size:{}", page2.size());
		
		
		return new ResponseData();
	}
	
	/**
	 * 获取不同的缓存
	 */
	@GetMapping("getRedis")
	public ResponseData getRedis() {
		String key = "Test:redis:";
		jsonRedisTemplate.opsForValue().set(key, "aaa");
		Object a = jsonRedisTemplate.opsForValue().get(key);
		LOGGER.info("219.7:{}", a);
		
		redisConfig.dynamicRedisConfig("redis_192.168.1.151");
		jsonRedisTemplate.opsForValue().set(key, "bbb");
		Object b = jsonRedisTemplate.opsForValue().get(key);
		LOGGER.info("localhost:{}", b);
		
		redisConfig.dynamicRedisConfig("redis_47.92.219.7");
		Object c = jsonRedisTemplate.opsForValue().get(key);
		LOGGER.info("219.7:{}", c);
		
		return new ResponseData();
	}
}
