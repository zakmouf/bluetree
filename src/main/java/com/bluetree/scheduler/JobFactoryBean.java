package com.bluetree.scheduler;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class JobFactoryBean implements JobFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Job newJob(TriggerFiredBundle bundle) throws SchedulerException {
        String jobBeanName = (String) bundle.getJobDetail().getJobDataMap()
                .get("jobBeanName");
        Job job = (Job) applicationContext.getBean(jobBeanName);
        return job;
    }

}
