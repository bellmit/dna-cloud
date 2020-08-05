package com.bazl.dna.database.core.datasource;

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
	
	@Bean(name = "secondDataSource")
	@ConfigurationProperties(prefix = "spring.lims.druid")
	public DataSource secondDataSource() {
		return new DruidDataSource();
	}

	@Primary
	@Bean(name = "dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		DataSource ds1 = primaryDataSource();
		dynamicDataSource.setDefaultTargetDataSource(ds1);
		DataSource ds2 = secondDataSource();
		// 配置多数据源
		ContextHolder.dataSourceMap.put(DataSourceConstants.DataSourceType.PRIMARY.name(), ds1);
		ContextHolder.dataSourceMap.put(DataSourceConstants.DataSourceType.SECOND.name(), ds2);
		
		dynamicDataSource.setTargetDataSources(ContextHolder.dataSourceMap);
		return dynamicDataSource;
	}
	
}
