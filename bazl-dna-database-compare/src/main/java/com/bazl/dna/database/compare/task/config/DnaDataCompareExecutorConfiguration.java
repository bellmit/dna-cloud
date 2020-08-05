package com.bazl.dna.database.compare.task.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lizhihua on 2020-01-28.
 */
@Configuration
@EnableAsync
public class DnaDataCompareExecutorConfiguration implements AsyncConfigurer {

    @Autowired
    TaskExecutorConfig taskExecutorConfig;

    @Override
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(taskExecutorConfig.getThreadNamePrefix());
        executor.setMaxPoolSize(taskExecutorConfig.getMaxPoolSize());
        executor.setCorePoolSize(taskExecutorConfig.getCorePoolSize());
        executor.setQueueCapacity(taskExecutorConfig.getQueueCapacity());
        executor.setKeepAliveSeconds(taskExecutorConfig.getKeepAliveSeconds());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    		return new DnaAsyncExceptionHandler();
    }
}
