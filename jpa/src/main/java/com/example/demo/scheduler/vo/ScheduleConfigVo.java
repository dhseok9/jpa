package com.example.demo.scheduler.vo;

import java.util.Objects;

public class ScheduleConfigVo {
//some constructors, getter/setters
    private String  taskName;
    private String  cronValue; // like */10 * * * * * for cron

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleConfigVo that = (ScheduleConfigVo) o;
        return taskName.equals(that.taskName) &&
        		cronValue.equals(that.cronValue) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, cronValue);
    }
}