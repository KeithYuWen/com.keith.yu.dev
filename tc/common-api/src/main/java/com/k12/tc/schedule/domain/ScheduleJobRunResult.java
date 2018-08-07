package com.k12.tc.schedule.domain;

import java.io.Serializable;
import java.util.Date;

public class ScheduleJobRunResult implements Serializable {
    private Integer id;

    /**
     * 任务Id
     */
    private Integer jobId;

    /**
     * 运行状态 1：成功 2：失败
     */
    private Byte runStatus;

    /**
     * 运行时间
     */
    private Date runTime;

    /**
     * 失败原因
     */
    private String failReason;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return job_id 任务Id
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * @param jobId Integer 任务Id
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * @return run_status 运行状态 1：成功 2：失败
     */
    public Byte getRunStatus() {
        return runStatus;
    }

    /**
     * @param runStatus Byte 运行状态 1：成功 2：失败
     */
    public void setRunStatus(Byte runStatus) {
        this.runStatus = runStatus;
    }

    /**
     * @return run_time 运行时间
     */
    public Date getRunTime() {
        return runTime;
    }

    /**
     * @param runTime Date 运行时间
     */
    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    /**
     * @return fail_reason 失败原因
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * @param failReason String 失败原因
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", jobId=").append(jobId);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", runTime=").append(runTime);
        sb.append(", failReason=").append(failReason);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}