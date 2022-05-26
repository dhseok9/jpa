package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.context.support.GenericWebApplicationContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SchedulingConfig {
	private final int POOL_SIZE = 20;
	
	private ThreadPoolTaskScheduler threadPoolTaskScheduler ;
//	private final GenericWebApplicationContext context;
	
	
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
    	ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("job-");
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return taskScheduler;
    }
	
//	@Override
//	public void configureTasks (ScheduledTaskRegistrar scheduledTaskRegistrar) {
//		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
//		threadPoolTaskScheduler.setThreadNamePrefix("job-");
//		threadPoolTaskScheduler.initialize();
//		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
//	}

}