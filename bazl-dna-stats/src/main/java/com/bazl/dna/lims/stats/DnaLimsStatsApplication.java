package com.bazl.dna.lims.stats;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableScheduling
@MapperScan("com.bazl.dna.lims.dao")//不加会报NoSuchBeanDefinitionException
@ComponentScan({ "com.bazl.dna.common.filter", "com.bazl.dna.lims.stats", "com.bazl.dna.lims.service" })
public class DnaLimsStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnaLimsStatsApplication.class, args);
	}

}