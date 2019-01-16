package com.duan.fantasies.quartzjob.quartz;


import com.duan.fantasies.quartzjob.job.JobIdentity;
import com.duan.fantasies.quartzjob.job.ScheduleJob;

import java.util.Date;

/**
 * Created on 2018/6/29.
 *
 * @author DuanJiaNing
 */
public class ScheduleSimpleJob extends ScheduleJob {

    private Date date;

    public ScheduleSimpleJob(JobIdentity identity, Date date) {
        super(identity);
        this.date = date;
    }

    public ScheduleSimpleJob(JobIdentity identity, Date date, Object data) {
        super(identity, data);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}