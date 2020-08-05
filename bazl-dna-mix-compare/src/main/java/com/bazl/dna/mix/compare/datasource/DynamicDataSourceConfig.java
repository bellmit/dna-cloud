package com.bazl.dna.mix.compare.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.bazl.dna.common.filter.RedisConfig;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.datasource.DynamicDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Configuration
public class DynamicDataSourceConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceConfig.class);
	
	@Bean(name = "primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	public DataSource primaryDataSource() {
		return new DruidDataSource();
	}
	
	@Bean(name = "dnaDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.dna")
	public DataSource dnaDataSource() {
		return new DruidDataSource();
	}

	@Primary
	@Bean(name = "dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 配置默认数据源
		DataSource ds = primaryDataSource();
		dynamicDataSource.setDefaultTargetDataSource(ds);

		// 配置多数据源
		DataSource dnaSource = dnaDataSource();
		HashMap<Object, Object> dataSourceMap = Maps.newHashMap();
		dataSourceMap.put(DataSourceConstants.DataSourceType.PRIMARY.name(), primaryDataSource());
		
		// 从默认数据库中加载数据源 如属性文件请自行添加
		List<Map<String, String>> list = Lists.newArrayList();
		try {
			String sql = "select `connect_name`, `db_name`, `ip_address`, `url`, `user_name`, `password`, `port`, `driver_name`, `ds_type` from nt_data_source_config";
			list = DataSourceUtil.execute(dnaSource.getConnection(), sql);
		} catch (Exception e) {
			LOGGER.error("dynamicDataSource error:", e);
		}
		
		for (Map<String, String> map : list) {
			try (DruidDataSource datasource = new DruidDataSource();) {
				if (DataSourceConstants.DS_TYPE_DB.equals(map.get("ds_type"))) {
					// 动态数据源
					dataSourceMap.put(map.get("connect_name"), ContextHolder.builder(map));
				} else if (DataSourceConstants.DS_TYPE_CACHE.equals(map.get("ds_type"))) {
					// 动态redis缓存
					RedisConfig.builder(map);
				}
			}
		}
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		return dynamicDataSource;
	}
	
}
