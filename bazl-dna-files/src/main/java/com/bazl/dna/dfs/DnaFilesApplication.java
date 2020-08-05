package com.bazl.dna.dfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import com.github.tobato.fastdfs.FdfsClientConfig;

/**
 * 通用文件模块启动类
 * Created by lizhihua on 2020-04-18.
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@Import(FdfsClientConfig.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
@EnableHystrix
@ComponentScan({"com.bazl.dna.common.filter","com.bazl.dna.dfs"})
public class DnaFilesApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DnaFilesApplication.class, args);
    }

}
