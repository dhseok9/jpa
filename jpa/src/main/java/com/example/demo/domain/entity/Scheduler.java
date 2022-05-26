package com.example.demo.domain.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.scheduler.vo.ScheduleConfigVo;

import lombok.Data;

@Data
@Entity
@Table(name = "scheduler")
public class Scheduler {
 
    @Id
    @Column(name = "task_name")
    private String taskName;
    
    @Column(name = "config_value")
    private String cronValue;

    @Column(name = "upt_yn")
    private String uptYn;

    @Column(name = "del_yn")
    private String delYn;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Scheduler that = (Scheduler) o;
//        return taskName.equals(that.taskName) && configValue.equals(that.configValue) ;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, cronValue);
    }
}
