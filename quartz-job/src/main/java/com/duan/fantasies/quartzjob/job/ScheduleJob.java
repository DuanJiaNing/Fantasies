package com.duan.fantasies.quartzjob.job;

import java.io.Serializable;

/**
 * Created on 2019/1/9.
 *
 * @author DuanJiaNing
 */
public class ScheduleJob implements Serializable {

    private final JobIdentity identity;
    private Object data;

    public ScheduleJob(JobIdentity identity, Object data) {
        this.identity = identity;
        this.data = data;
    }

    public ScheduleJob(JobIdentity identity) {
        this.identity = identity;
    }

    public JobIdentity getIdentity() {
        return identity;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "identity=" + identity +
                ", data=" + data +
                '}';
    }
}
