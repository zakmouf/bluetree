package com.bluetree.scheduler;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.spi.JobFactory;

public class SchedulerFactoryBean extends
        org.springframework.scheduling.quartz.SchedulerFactoryBean {

    private JobFactory jobFactory;

    public JobFactory getJobFactory() {
        return jobFactory;
    }

    public void setJobFactory(JobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    protected Scheduler createScheduler(SchedulerFactory schedulerFactory,
            String schedulerName) throws SchedulerException {
        Scheduler scheduler = super.createScheduler(schedulerFactory,
                schedulerName);
        scheduler.setJobFactory(jobFactory);
        return scheduler;
    }

}
