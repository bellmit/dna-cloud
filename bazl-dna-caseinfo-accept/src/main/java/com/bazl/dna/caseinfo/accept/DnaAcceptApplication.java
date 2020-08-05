package com.bazl.dna.caseinfo.accept;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableScheduling
@MapperScan("com.bazl.dna.lims.dao")
@ComponentScan({ "com.bazl.dna.common.filter", "com.bazl.dna.caseinfo.accept", "com.bazl.dna.lims" })
public class DnaAcceptApplication {
	public static void main(String[] args) {
		SpringApplication.run(DnaAcceptApplication.class, args);
	}
}