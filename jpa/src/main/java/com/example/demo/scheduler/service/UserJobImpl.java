package com.example.demo.scheduler.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.scheduler.controller.JobController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
public class UserJobImpl implements BatchJob { 
	// ... 
	@Override 
	public void execute() { 
		// 실행 내용 
		log.info("Current Thread : {}", Thread.currentThread().getName());
	}
}

