package com.bazl.dna.mix;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;

import feign.Request;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix

@MapperScan("com.bazl.dna.mix.dao")
@ComponentScan({"com.bazl.dna.common.filter","com.bazl.dna.mix"})
@EnableScheduling   // 开启定时任务
@EnableAsync    // 2.开启多线程
public class ILabStRmixApplication {

	/**t
	 * 文件上传配置
	 * @return
     */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
//		//文件最大10M,DataUnit提供5中类型B,KB,MB,GB,TB
//		factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
//		/// 设置总上传数据总大小10M
//		factory.setMaxRequestSize(DataSize.of(10, DataUnit.MEGABYTES));
		return factory.createMultipartConfig();
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public static Request.Options requestOptions(ConfigurableEnvironment env) {
		int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 700000);
		int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 600000);

		return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
	}

	public static void main(String[] args) {
		SpringApplication.run(ILabStRmixApplication.class, args);
	}
	
}
