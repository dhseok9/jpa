package com.example.demo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Scheduler;
import com.example.demo.domain.repository.SchedulerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling 
//@Configuration
public class BatchScheduler implements SchedulingConfigurer {

	@Autowired
	private Runnable [] runnableTask;
	
	@Autowired
	SchedulerRepository schedulerRepository;
	
	private ScheduledTaskRegistrar scheduledTaskRegistrar;
	private ScheduledFuture future;
	
	private Map<String, ScheduledFuture> futureMap = new ConcurrentHashMap<>(); // for the moment it has only class name
	private Map<String, String> futureCronMap = new HashMap<String, String>();
	
	List<Scheduler> oldList = new ArrayList<>();
	List<Scheduler> newList;
	List<Scheduler> delList;
	List<Scheduler> addList = new ArrayList<>();
	List<Scheduler> removeList = new ArrayList<>();

	private final int POOL_SIZE = 10;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("batchJob-");
		threadPoolTaskScheduler.initialize();
		
	    if (scheduledTaskRegistrar == null) {
	        scheduledTaskRegistrar = taskRegistrar;
	    }
	    if (taskRegistrar.getScheduler() == null) {
//	        taskRegistrar.setScheduler(taskScheduler);
	        taskRegistrar.setScheduler(threadPoolTaskScheduler);
	    }
	    updateScheduleList();
	
	}
	
	//@Scheduled(fixedDelay = 30000)
	public void updateScheduleList() {
//	    newList = schedulerRepository.selectUptList() == null ? new ArrayList<>() : schedulerRepository.selectUptList();
		newList = schedulerRepository.selectUptList();
	    
//	    delList = schedulerRepository.selectDelList() == null ? new ArrayList<>() : schedulerRepository.selectDelList();
	    
//	    log.info("newList ======" + newList);
//	    log.info("oldList ======" + oldList);
//	    addList.clear();
//	    removeList.clear();
	
//	    if (!newList.isEmpty()) {
//	        //compare new List with oldList
//	        if (!oldList.isEmpty()) {
//	            addList = newList.stream().filter(e -> !oldList.contains(e)).collect(Collectors.toList());
//	            removeList = oldList.stream().filter(e -> !newList.contains(e)).collect(Collectors.toList());
//	        	
////	        	Arrays.stream(tsks).forEach(task -> {
////                    addList.stream().filter(conf -> task.getClass().getName().contains(conf.getTaskName())).forEach(conf -> {
////                        log.info("find " + task.getClass().getName() + " to add to scheduler");
////                        future = scheduledTaskRegistrar.getScheduler().schedule(task, (TriggerContext a) -> {
////                            CronTrigger crontrigger = new CronTrigger(conf.getConfigValue());
////                            return crontrigger.nextExecutionTime(a);
////                        });
////                        futureMap.put(task.getClass().getName().substring(task.getClass().getName().lastIndexOf('.') + 1), future);
////                    });
////                });
////	        	
////	            removeList.stream().filter(conf -> {
////	                future = futureMap.get(conf.getTaskName());
////	                return future != null;
////	            }).forEach((conf) -> {
////	                log.info("cancelling task " + conf.getTaskName() + " ...");
////	                future.cancel(true);
////	                log.info(conf.getTaskName() + " isCancelled = " + future.isCancelled());
////	            });
//	        } else {
//	            addList = new ArrayList<>(newList); // nothing to remove
//	        }
//	    } else { // nothing to add
//	        if (!oldList.isEmpty()) {
//	            removeList = new ArrayList<>(oldList);
//	        } // else removeList = 0
//	    }
	    
//	    if (!delList.isEmpty()) {
//	        //compare new List with oldList
////	        if (!oldList.isEmpty()) {
////	            addList = newList.stream().filter(e -> !oldList.contains(e)).collect(Collectors.toList());
//	            removeList = removeList.stream().filter(e -> !newList.contains(e)).collect(Collectors.toList());
//	    }
	    
	    log.info("newList="+ newList.toString());
	    log.info("removeList="+ removeList.toString());
	    //re-schedule here
	
//	    for ( Scheduler conf : removeList ) {
//	        if ( !futureMap.isEmpty()){
//	            future = futureMap.get(conf.getTaskName());
//	            if (future != null) {
//	                log.info("cancelling task "+conf.getTaskName() +" ...");
//	                future.cancel(true);
//	                log.info(conf.getTaskName() + " isCancelled = " + future.isCancelled());
//	                futureMap.remove(conf.getTaskName());
//	            }
//	        }
//	    }
	    
	    for ( Scheduler conf : newList ) {
	    	log.info("tsks="+ runnableTask);
	    	if (runnableTask != null) {
		        for (Runnable o: runnableTask) {
		            if (o.getClass().getName().contains(conf.getTaskName())) { // o has fqn whereas conf has class name only
		            	
		                log.info("find " + o.getClass().getName() + " to add to scheduler");
		                future = scheduledTaskRegistrar.getScheduler().schedule(o, (TriggerContext a) -> { 
		                    CronTrigger crontrigger = new CronTrigger(conf.getCronValue());
		                    return crontrigger.nextExecutionTime(a);
		                });
		                
		                futureMap.put(o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.')+1), future);
		                log.info("futureCronMap ="+ futureCronMap);
		               
		                futureCronMap.put(conf.getTaskName(), conf.getCronValue());
		                
//		                int uCnt = schedulerRepository.updateRegScheduler(conf.getTaskName());
//		                log.info("taskRegistrar ="+ futureMap);
		            }
		        }
	    	}
	    }
	
	    log.info("futureMap ="+ futureMap);
	    log.info("futureCronMap ="+ futureCronMap);
	    
//	    oldList.clear();
//	    oldList= newList;
	}
	
	public int addSchedule(String taskName, String cronValue) {
		int result = 0;
		boolean addMode = false;
		try {
//			log.info("taskName==" + taskName);
//			log.info("futureMap==" + futureMap);
//			log.info("futureCronMap==" + futureCronMap);
			if (runnableTask != null) {
		        for (Runnable o: runnableTask) {
		            if (o.getClass().getName().contains(taskName)) { // o has fqn whereas conf has class name only
//		            	if ( !futureMap.isEmpty()){
		    	            future = futureMap.get(taskName);
		    	            if (future != null) {
		    	            	if (!futureCronMap.isEmpty() && !cronValue.equalsIgnoreCase(futureCronMap.get(taskName))) {
		    	            		log.info("exists task delete="+ taskName);
		    	            		future.cancel(true);
		    	            		addMode = true;
		    	            	} 
		    	            } else {
		    	            	addMode = true;
		    	            }
//		            	} else {
//		            		addMode = true;
//		            	}
		            	
		            	if (addMode) {
		            		log.info("addMode="+ addMode);
			            	future = scheduledTaskRegistrar.getScheduler().schedule(o, (TriggerContext a) -> { 
			                    CronTrigger crontrigger = new CronTrigger(cronValue);
			                    return crontrigger.nextExecutionTime(a);
			                });
			                
			                futureMap.put(o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.')+1), future);
			                futureCronMap.put(taskName, cronValue);
		            	}
		            	
		                result = 1;
		            	break;
		            } 
		        }
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
		
	public int deleteSchedule(String taskName) {
		int result = 0;
		
		try {
//			log.info("taskName==" + taskName);
//			log.info("futureMap==" + futureMap);
//			log.info("futureCronMap==" + futureCronMap);
			for (Runnable o: runnableTask) {
	            if (o.getClass().getName().contains(taskName)) { // o has fqn whereas conf has class name only
					if (!futureMap.isEmpty()){
			            future = futureMap.get(taskName);
			            if (future != null) {
			                log.info("cancelling task "+ taskName +" ...");
			                future.cancel(true);
			                log.info(taskName + " isCancelled = " + future.isCancelled());
			                
			                futureMap.remove(taskName);
			                futureCronMap.remove(taskName);
			                
			            }
			        }
					
	                result = 1;
					break;
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}