package com.djavafactory.pttech.rrm.conf;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

/**
 * This class is to reset the cron scheduler.
 *
 * @author Carine Leong
 */
public class DynamicScheduler extends CronTrigger implements Trigger {

    private volatile TaskScheduler scheduler;
    private Runnable runnableTask;
    private ScheduledFuture<?> batchTask;

    /**
     * Constructor
     *
     * @param scheduler    Task Scheduler
     * @param cronExpr     Cron Expression
     * @param runnableTask Current scheduled task
     */
    public DynamicScheduler(TaskScheduler scheduler, String cronExpr, Runnable runnableTask) {
        super(cronExpr);
        this.scheduler = scheduler;
        this.runnableTask = runnableTask;
        reset(cronExpr);
    }

    /**
     * Constructor
     *
     * @param scheduler    Task Scheduler
     * @param cronExpr     Cron Expression
     * @param runnableTask Current Task
     * @param batchTask    New scheduled task
     */
    public DynamicScheduler(TaskScheduler scheduler, String cronExpr, Runnable runnableTask, ScheduledFuture<?> batchTask) {
        super(cronExpr);
        this.scheduler = scheduler;
        this.runnableTask = runnableTask;
        this.batchTask = batchTask;
        reset(cronExpr);
    }

    /**
     * Method to reset the scheduled task
     *
     * @param cronExpr Cron Expression
     */
    public void resetScheduler(String cronExpr) {
        new DynamicScheduler(scheduler, cronExpr, runnableTask, batchTask);
    }

    /**
     * Method to stop the scheduled task
     */
    public void stopScheduler() {
        if (batchTask != null) {
            batchTask.cancel(true);
        }
    }

    private void reset(String cronExpr) {
        if (batchTask != null) {
            batchTask.cancel(true);
        }
        batchTask = scheduler.schedule(runnableTask, this);
    }

}
