package com.bazl.dna.database.transfer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@MapperScan("com.bazl.dna.database.transfer.mapper")
@ComponentScan({"com.bazl.dna.common.filter","com.bazl.dna.database.transfer", "com.bazl.dna.database.transfer.service"})
@EnableTransactionManagement
public class DnaDataTransferApplication {
	public static void main(String[] args) {
		SpringApplication.run(DnaDataTransferApplication.class, args);
	}

}
