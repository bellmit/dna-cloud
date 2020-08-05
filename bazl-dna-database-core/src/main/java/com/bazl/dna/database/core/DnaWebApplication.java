package com.bazl.dna.database.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@MapperScan("com.bazl.dna.database.service.mapper")
@ComponentScan({"com.bazl.dna.common.filter","com.bazl.dna.database.core","com.bazl.dna.database.service"})
public class DnaWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(DnaWebApplication.class, args);
	}
	
}
