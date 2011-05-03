package com.djavafactory.pttech.rrm.service;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;


public class DynamicScheduler extends CronTrigger implements Trigger {

    private volatile TaskScheduler scheduler;
    private Runnable runnableTask;
    private ScheduledFuture<?> batchTask;

    public DynamicScheduler(TaskScheduler scheduler, String cronExpr, Runnable runnableTask) {
        super(cronExpr);
        this.scheduler = scheduler;
        this.runnableTask = runnableTask;
        reset(cronExpr);
    }

    public DynamicScheduler(TaskScheduler scheduler, String cronExpr, Runnable runnableTask, ScheduledFuture<?> batchTask) {
        super(cronExpr);
        this.scheduler = scheduler;
        this.runnableTask = runnableTask;
        this.batchTask = batchTask;
        reset(cronExpr);
    }

    public void resetScheduler(String cronExpr) {
        new DynamicScheduler(scheduler, cronExpr, runnableTask, batchTask);
    }

    private void reset(String cronExpr) {
        if (batchTask != null) {
            batchTask.cancel(true);
        }
        batchTask = scheduler.schedule(runnableTask, this);
    }

}
