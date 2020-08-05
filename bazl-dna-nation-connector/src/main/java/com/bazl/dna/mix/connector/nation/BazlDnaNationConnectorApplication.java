package com.bazl.dna.mix.connector.nation;

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
@MapperScan("com.bazl.dna.mix.connector.nation.dao")
@ComponentScan({"com.bazl.dna.common.filter","com.bazl.dna.mix.connector.nation","com.bazl.dna.mix.connector.nation.service"})
public class BazlDnaNationConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BazlDnaNationConnectorApplication.class,args);
    }
    
}
