package com.bazl.dna.common.filter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);
	
	public static Map<String, LettuceConnectionFactory> dataSourceMap = Maps.newHashMap();
	
	private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();
	
	@Autowired
	private RedisTemplate<String, Object> jsonRedisTemplate;
	
	public static void setRedis(String dbType) {
		LOGGER.info("切换到 [{}] Redis", dbType);
		HOLDER.set(dbType);
	}

	public static String getRedis() {
		return HOLDER.get();
	}

	public static void clearRedis() {
		HOLDER.remove();
	}
	
	@SuppressWarnings("deprecation")
	public void dynamicRedisConfig(String dataType) {
		RedisConfig.setRedis(dataType);
		LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) jsonRedisTemplate.getConnectionFactory();

		LettuceConnectionFactory factory = dataSourceMap.get(dataType);
		if (factory != null) {
			// 关闭连接池
			connectionFactory.destroy();

			connectionFactory.setDatabase(factory.getDatabase());
			connectionFactory.setHostName(factory.getHostName());
			connectionFactory.setPort(factory.getPort());
			if (factory.getPassword() != null) {
				connectionFactory.setPassword(factory.getPassword());
			}
			// 重新创建连接池
			connectionFactory.afterPropertiesSet();
			connectionFactory.resetConnection();
			jsonRedisTemplate.setConnectionFactory(connectionFactory);
		} else {
			LOGGER.error("dynamicRedisConfig error: {}", dataType);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void builder(Map<String, String> map) {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName(map.get("ip_address"));
        if (StringUtils.trimToNull(map.get("password")) != null) {
            connectionFactory.setPassword(map.get("password"));
        }
        connectionFactory.setPort(Integer.parseInt(map.get("port")));
        connectionFactory.setDatabase(Integer.parseInt(map.get("db_name")));

        RedisConfig.dataSourceMap.put(map.get("connect_name"), connectionFactory);
	}
	
	public static JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(200);
		poolConfig.setMinIdle(10);
		poolConfig.setMaxWaitMillis(20000);
		poolConfig.setMaxTotal(1024);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setNumTestsPerEvictionRun(10);
		poolConfig.setTimeBetweenEvictionRunsMillis(60000);
		
		return poolConfig;
	}

}
