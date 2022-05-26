package com.example.demo.scheduler.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
public class CarJobImpl implements BatchJob { 
	// ... 
	@Override 
	public void execute() { 
		// 실행 내용 
		log.info("Current Thread : {}", Thread.currentThread().getName());
	}
}

