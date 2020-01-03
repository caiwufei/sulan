package com.yilanjiaju.sulan.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author caiwufei
 */
@SpringBootConfiguration
@EnableAsync
public class AsyncTaskConfig implements AsyncConfigurer {
    // 核心线程数（默认线程数）
    private static final int CORE_POOL_SIZE = 5;
    // 最大线程数
    private static final int MAX_POOL_SIZE = 100;
    // 允许线程空闲时间（单位：默认为秒）
    private static final int KEEP_ALIVE_TIME = 30;
    // 缓冲队列数
    private static final int QUEUE_CAPACITY = 2000;
    // 线程池名前缀
    private static final String THREAD_NAME_PREFIX = "Task-Executor-";

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }
}