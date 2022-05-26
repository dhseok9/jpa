package com.example.demo.scheduler.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.OrderService;
import com.example.demo.config.BatchScheduler;
import com.example.demo.domain.entity.OrderProduct;
import com.example.demo.scheduler.service.DynamicSchedulerService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JobController {
//	@Autowired
//    private DynamicSchedulerService ds;
	
	@Autowired
    private BatchScheduler batchScheduler;
	
//	@Autowired
//    private SchedulerService schedulerService;
	
	@RequestMapping(value="/addSchedule", method={RequestMethod.GET})
	@ResponseBody
    public void addSchedule() throws InterruptedException {
		log.info("Current Thread : {}", Thread.currentThread().getName());
//		ds.stop();
//		Thread.sleep(1000);
////        Thread.sleep(2000);
//        ds.changeCron("*/3 * * * * *");
//        ds.start();
		
		int i = batchScheduler.addSchedule("TestTask", "*/10 * * * * *");
		
		if (i == 1) {
			log.info("success");
		} else {
			log.info("fail");
		}
    }
	
	@RequestMapping(value="/deleteSchedule", method={RequestMethod.GET})
	@ResponseBody
    public void deleteSchedule() throws InterruptedException {
		log.info("Current Thread : {}", Thread.currentThread().getName());
////		Thread.sleep(1000);
//        ds.stop();
				
		int i = batchScheduler.deleteSchedule("TestTask");

		if (i == 1) {
			log.info("success");
		} else {
			log.info("fail");
		}
    }
}