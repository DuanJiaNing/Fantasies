package com.duan.fantasies.quartzjob.quartz;

/**
 * Created on 2018/10/15.
 *
 * @author DuanJiaNing
 */
public enum JobStatus {

    /**
     * 运行中
     */
    RUNNING,

    /**
     * 闲置
     */
    IDLE,

    /**
     * 暂停
     */
    PAUSE,

    /**
     * 删除
     */
    DELETED

}
