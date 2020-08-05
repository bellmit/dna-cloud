package com.bazl.dna.mix.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: lzn
 * @Date: 2019/8/18 14:55
 * @Version: 1.0
 */
@Configuration
@EnableAsync
public class MyThread {
    //线程池维护线程的最少数量
    private int corePoolSize = 10;
    //线程池维护线程的最大数量
    private int maxPoolSize = 30;
    //缓存队列
    private int queueCapacity = 8;
    //允许的空闲时间
    private int keepAlive = 60;

    @Bean
    public Executor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("mqExecutor-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(keepAlive);
        //执行初始化
        executor.initialize();
        return executor;
    }

}
