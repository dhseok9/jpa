package com.example.demo.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.example.demo.scheduler.service.BatchJob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
//@Configuration 
@EnableScheduling 
@RequiredArgsConstructor 
public class DynamicSchedulingConfig implements SchedulingConfigurer { 
	
	private final GenericWebApplicationContext context; 
	private final Environment env; 
//	@Value("${server.number:0}") 
//	private Integer serverNumber; 
	
	@Bean 
	public ScheduledExecutorService poolScheduler() { 
		return Executors.newScheduledThreadPool(5); 
	}
	
//	 @Bean 
//	 public TaskScheduler poolScheduler() { 
//		 ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler(); 
//		 threadPoolTaskScheduler.setPoolSize(5); 
//		 threadPoolTaskScheduler.setThreadNamePrefix("batchJob-"); 
//		 
//		 return threadPoolTaskScheduler; 
//	 } 
	
	@Override 
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) { 
		log.info("########################################################"); 

		taskRegistrar.setScheduler(poolScheduler()); 
		String[] beanDefinitionNames = context.getBeanDefinitionNames(); 
		for (String beanName : beanDefinitionNames) { 
			if (beanName.endsWith("JobImpl")) { 
				try { 
					String cronExpr = "*/5 * * * * *";
					String cronExpr2 = "*/3 * * * * *";
					BatchJob scheduleJob = context.getBean(beanName, BatchJob.class); 
//					taskRegistrar.addTriggerTask(scheduleJob::execute, new CronTrigger(cronExpr)); 
					taskRegistrar.addCronTask(scheduleJob::execute, cronExpr); 
					log.info("## execute schedule job : {} - {}", beanName, cronExpr); 
				} catch (Exception e) { 
					log.info("## unexecuted schedule job : {}", beanName);
				} 
			} 
		} 
		log.info("Task size : " + taskRegistrar.getCronTaskList().size());
		log.info("########################################################"); 
	} 
}


