package com.duan.fantasies.quartzjob.job;

import java.io.Serializable;

/**
 * Created on 2018/10/15.
 *
 * @author DuanJiaNing
 */
public class JobIdentity implements Serializable {

    private final String name;
    private final String group;

    public JobIdentity(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "JobIdentity{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
