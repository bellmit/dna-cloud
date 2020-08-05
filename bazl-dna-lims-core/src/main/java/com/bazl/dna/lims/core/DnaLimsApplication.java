package com.bazl.dna.lims.core;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableScheduling
@Import(FdfsClientConfig.class)
@MapperScan("com.bazl.dna.lims.core.dao")//不加会报NoSuchBeanDefinitionException
@ComponentScan({ "com.bazl.dna.common.filter", "com.bazl.dna.lims.core" })

@ImportResource("classpath:applicationContext-cxf-servlet.xml")
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
//@EnableRedisHttpSession
public class DnaLimsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnaLimsApplication.class, args);
	}

}

