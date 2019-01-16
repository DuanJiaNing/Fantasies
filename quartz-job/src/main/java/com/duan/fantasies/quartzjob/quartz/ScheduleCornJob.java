package com.duan.fantasies.quartzjob.quartz;


import com.duan.fantasies.quartzjob.job.JobIdentity;
import com.duan.fantasies.quartzjob.job.ScheduleJob;

/**
 * Created on 2018/6/29.
 *
 * @author DuanJiaNing
 */
public class ScheduleCornJob extends ScheduleJob {

    private String cron;

    public ScheduleCornJob(JobIdentity identity, String cron) {
        super(identity);
        this.cron = cron;
    }

    public ScheduleCornJob(JobIdentity identity, String cron, Object data) {
        super(identity, data);
        this.cron = cron;
    }

    public String getCron() {
        return cron;
    }
}