package com.bazl.dna.database.nation.converter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@MapperScan("com.bazl.dna.database.nation.converter.dao")
@ComponentScan({ "com.bazl.dna.common.filter", "com.bazl.dna.database.nation.converter" })
@EnableScheduling   // 开启定时任务
public class BazlDnaDatabaseNationConverterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BazlDnaDatabaseNationConverterApplication.class, args);
		System.out.println("启动成功...");
	}

}
