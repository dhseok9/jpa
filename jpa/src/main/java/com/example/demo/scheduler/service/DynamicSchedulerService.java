package com.example.demo.scheduler.service;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class DynamicSchedulerService {
//	@Autowired
	private ThreadPoolTaskScheduler scheduler;
	private String cron = "*/2 * * * * *";

	public void start() {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		// scheduler setting 
		scheduler.schedule(getRunnable(), new CronTrigger(cron));
	}

	public void changeCron(String cron) {
		this.cron = cron;
	}

	public void stop() {
		scheduler.shutdown();
	}

	private Runnable getRunnable() {
		// do something
		return () -> {
			System.out.println(LocalDateTime.now().toString());
		};
	}

//	@PostConstruct
	public void init() {
		start();
	}

	@PreDestroy
	public void destroy() {
		stop();
	}
}