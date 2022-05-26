package com.example.demo.scheduler.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestTask implements Runnable {
    @Override
    public void run() {
        log.info("Task2 is running...");
    }
}