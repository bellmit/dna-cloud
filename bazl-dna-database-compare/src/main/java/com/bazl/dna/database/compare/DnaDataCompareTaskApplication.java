package com.bazl.dna.database.compare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@MapperScan("com.bazl.dna.database.service.mapper")
@ComponentScan({"com.bazl.dna.common.filter", "com.bazl.dna.database.compare", "com.bazl.dna.database.service"})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class DnaDataCompareTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(DnaDataCompareTaskApplication.class, args);
	}
	
}

