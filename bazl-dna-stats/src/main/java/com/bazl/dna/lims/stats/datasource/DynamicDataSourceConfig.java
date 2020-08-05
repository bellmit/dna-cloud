package com.bazl.dna.lims.stats.datasource;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.datasource.DynamicDataSource;

@Configuration
public class DynamicDataSourceConfig {
	
	@Bean(name = "primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	public DataSource primaryDataSource() {
		return new DruidDataSource();
	}
	
	@Bean(name = "jkbDataSource")
	@ConfigurationProperties(prefix = "spring.jkb.druid")
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
		
		ContextHolder.dataSourceMap.put(DataSourceConstants.DataSourceType.PRIMARY.name(), primaryDataSource());
		ContextHolder.dataSourceMap.put("jkb", dnaDataSource());
		
		return ds;
	}
	
}
