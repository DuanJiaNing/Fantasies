package com.duan.fantasies.quartzjob;

import com.duan.fantasies.quartzjob.job.JobIdentity;
import com.duan.fantasies.quartzjob.job.ScheduleJob;
import com.duan.fantasies.quartzjob.quartz.ScheduleCornJob;
import com.duan.fantasies.quartzjob.quartz.ScheduleSimpleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created on 2018/6/29.
 *
 * @author DuanJiaNing
 */
@Component
public class QuartJobManager {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 只执行一次的任务
     */
    public Date addSimpleJob(TriggerKey triggerKey, JobKey jobKey, Class<? extends Job> job, Date date,
                             Map<String, Object> data, boolean replace) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = getJobDetail(scheduler, jobKey, data, job, replace);
        if (jobDetail != null) {
            scheduler.addJob(jobDetail, true);
        } else {
            return null;
        }

        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger != null) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            if (triggerState == Trigger.TriggerState.PAUSED) {
                scheduler.resumeTrigger(triggerKey);
            }
            return null;
        }

        trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey.getName(), triggerKey.getGroup())
                .startAt(date)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(0))
                .startNow()
                .build();

        // 关联触发器和任务
        return scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 只执行一次的任务
     */
    public Date addCornJob(TriggerKey triggerKey, JobKey jobKey, Class<? extends Job> job, String corn,
                           Map<String, Object> data, boolean replace) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobDetail jobDetail = getJobDetail(scheduler, jobKey, data, job, replace);
        if (jobDetail != null) {
            scheduler.addJob(jobDetail, true);
        } else {
            return null;
        }

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder.cronSchedule(corn);
        if (trigger == null) {

            // 新建 corn trigger
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey.getName(), triggerKey.getGroup())
                    .withSchedule(scheduleBuilder)
                    .build();

        } else { // trigger 已存在，那么更新相应的定时设置

            // 按新的 cronExpression 表达式重新构建 trigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
        }

        return scheduler.scheduleJob(jobDetail, trigger);

    }

    private JobDetail getJobDetail(Scheduler scheduler, JobKey jobKey, Map<String, Object> data, Class<? extends Job> job, boolean replace) throws SchedulerException {

        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null || (!jobDetail.getJobClass().equals(job) && replace)) {
            jobDetail = JobBuilder.newJob(job)
                    .withIdentity(jobKey.getName(), jobKey.getGroup())
                    .build();

            if (data != null && data.size() > 0) {
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    jobDataMap.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return jobDetail;
    }

    public Date addSimpleJob(ScheduleSimpleJob quartzSimpleJob, Class<? extends Job> clazz) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobIdentity identity = quartzSimpleJob.getIdentity();
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity(identity.getName(), identity.getGroup())
                .startAt(quartzSimpleJob.getDate())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(0))
                .build();

        return scheduleJob(clazz, identity, quartzSimpleJob, scheduler, simpleTrigger);
    }

    /**
     * 添加一个 corn 任务到 quartz
     */
    public Date addCronJob(ScheduleCornJob quartzCornJob, Class<? extends Job> clazz) {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobIdentity identity = quartzCornJob.getIdentity();
        TriggerKey triggerKey = TriggerKey.triggerKey(identity.getName(), identity.getGroup());
        CronTrigger trigger; // org.quartz.impl.triggers.CronTriggerImpl
        try {
            trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }

        // 不存在，创建一个
        if (null == trigger) {

            // 新建 corn trigger
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzCornJob.getCron());
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(identity.getName(), identity.getGroup())
                    .withSchedule(scheduleBuilder)
                    .build();

            return scheduleJob(clazz, identity, quartzCornJob, scheduler, trigger);

        } else { // trigger 已存在，那么更新相应的定时设置

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzCornJob.getCron());

            // 按新的 cronExpression 表达式重新构建 trigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();

            // 按新的 trigger 重新设置 quartzCornJob 执行
            try {
                return scheduler.rescheduleJob(triggerKey, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private Date scheduleJob(Class<? extends Job> clazz, JobIdentity identity, ScheduleJob
            scheduleJob, Scheduler scheduler,
                             Trigger trigger) {
        // Quartz 在每次执行 Job 时，都重新创建一个 Job 实例，所以它不直接接受一个 Job 的实例，相反它接收一个 Job 实现类
        JobDetail jobDetail = JobBuilder
                .newJob(clazz)
                .withIdentity(identity.getName(), identity.getGroup())
                .build();

        // 把任务参数绑定到 任务数据 map 中
        jobDetail.getJobDataMap().put("JOB_DATA_MAP_KEY", scheduleJob);

        // 关联触发器和任务
        try {
            return scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 暂停一个 job
     */
    public boolean pauseJob(JobIdentity identity) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey jobKey = JobKey.jobKey(identity.getName(), identity.getGroup());
        try {
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 恢复一个 job
     */
    public boolean resumeJob(JobIdentity identity) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey jobKey = JobKey.jobKey(identity.getName(), identity.getGroup());
        try {
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个 job
     */
    public boolean deleteJob(JobIdentity identity) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey jobKey = JobKey.jobKey(identity.getName(), identity.getGroup());
        try {
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 立即执行 job
     */
    public boolean triggerJob(JobIdentity identity) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey jobKey = JobKey.jobKey(identity.getName(), identity.getGroup());
        try {
            scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有计划中的任务列表
     */
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);

        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                JobIdentity identity = new JobIdentity(jobKey.getName(), jobKey.getGroup());
                ScheduleJob job = new ScheduleJob(identity);

                TriggerKey triggerKey = trigger.getKey();
                Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);

                jobList.add(job);
            }
        }

        return jobList;
    }

    /**
     * 所有正在运行的job
     */
    public List<ScheduleJob> getAllRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();

            JobIdentity identity = new JobIdentity(jobKey.getName(), jobKey.getGroup());
            ScheduleJob job = new ScheduleJob(identity);

            Trigger trigger = executingJob.getTrigger();
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

            jobList.add(job);
        }

        return jobList;
    }

}
