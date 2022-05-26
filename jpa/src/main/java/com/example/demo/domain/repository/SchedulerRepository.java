package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.entity.Scheduler;

public interface SchedulerRepository extends JpaRepository<Scheduler, String>{
	
	public Scheduler findByTaskName(int taskName);
	
//	@Query("select p from order_product p where p.order_id = ?1 and p.product_id = ?2")
//	public OrderProduct findByOrderIdAndProductId(int orderId,  int productId);
	
	@Query(value = "SELECT * FROM scheduler p WHERE p.upt_yn = 'Y'", nativeQuery = true)
	public List<Scheduler> selectUptList();
	
	@Query(value = "SELECT * FROM scheduler p WHERE p.del_yn = 'Y'", nativeQuery = true)
	public List<Scheduler> selectDelList();
	
    @Query(value = "SELECT * FROM scheduler p WHERE p.task_name = :taskName", nativeQuery = true)
    public List<Scheduler> selectScheduler();
    
    @Modifying
    @Query(value = "INSERT INTO scheduler(task_name, config_value, use_yn) values (:#{#scheduler.taskName}, :#{#scheduler.configValue}, :#{#scheduler.useYn})", nativeQuery = true)
    public int insertScheduler(@Param("scheduler") Scheduler scheduler);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE scheduler set upt_yn = 'N' where task_name = :taskName", nativeQuery = true)
    public int updateRegScheduler(@Param("taskName") String taskName);
}