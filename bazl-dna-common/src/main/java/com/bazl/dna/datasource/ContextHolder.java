package com.bazl.dna.datasource;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;

/**
 * 数据源类型设置
 * 
 * @author zhaoyong
 */
public class ContextHolder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextHolder.class);

	private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();
	
	public static Map<Object, Object> dataSourceMap = Maps.newHashMap();

	/**
	 * Generate constructor
	 */
	private ContextHolder() {
	}

	public static void setDataSource(String dbType) {
		LOGGER.info("切换到 [{}] 数据源", dbType);
		HOLDER.set(dbType);
	}

	public static String getDataSource() {
		return HOLDER.get();
	}

	public static void clearDataSource() {
		HOLDER.remove();
	}
	
	public static DruidDataSource builder(Map<String, String> map) {
		try (DruidDataSource datasource = new DruidDataSource();) {
			datasource.setUrl(map.get("url"));
			datasource.setUsername(map.get("user_name"));
			datasource.setPassword(map.get("password"));
			datasource.setDriverClassName(map.get("driver_name"));
			datasource.setDbType("com.alibaba.druid.pool.DruidDataSource");

//			datasource.setInitialSize(30);
//			datasource.setMinIdle(50);
//			datasource.setMaxActive(200);
//			datasource.setMaxWait(60000);
//			datasource.setTimeBetweenEvictionRunsMillis(60000);
//			datasource.setMinEvictableIdleTimeMillis(300000);
//			datasource.setValidationQuery("SELECT 1 FROM nt_sys_sequence");
//			datasource.setTestWhileIdle(true);
//			datasource.setTestOnBorrow(false);
//			datasource.setTestOnReturn(false);
			ContextHolder.dataSourceMap.put(map.get("connect_name"), datasource);
			return datasource;
		}
	}
}
