package com.k12.tc.schedule.domain;

import java.util.Date;

import com.k12.tc.common.vo.PageView;

public class ScheduleJob extends PageView {

	/**
     * 任务id
     */
    private Integer jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务别名
     */
    private String aliasName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 触发器
     */
    private String jobTrigger;

    /**
     * 0禁用1启用2删除
     */
    private String jobStatus;

    /**
     * 任务运行时间表达式
     */
    private String cronExpression;

    /**
     * 0 否 1 是
     */
    private String isSync;

    /**
     * 服务URL
     */
    private String serviceUrl;

    /**
     * 请求方式
     */
    private String submitMethod;

    /**
     * 是否包含参数
     */
    private String isParam;

    /**
     * 请求参数
     */
    private String serviceParam;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * @return 任务id
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * @param jobId 
	 *            任务id
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * @return 任务名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName 
	 *            任务名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return 任务别名
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * @param aliasName 
	 *            任务别名
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * @return 任务分组
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup 
	 *            任务分组
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * @return 触发器
     */
    public String getJobTrigger() {
        return jobTrigger;
    }

    /**
     * @param jobTrigger 
	 *            触发器
     */
    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger;
    }

    /**
     * @return 0禁用1启用2删除
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus 
	 *            0禁用1启用2删除
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @return 任务运行时间表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression 
	 *            任务运行时间表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * @return 0 否 1 是
     */
    public String getIsSync() {
        return isSync;
    }

    /**
     * @param isSync 
	 *            0 否 1 是
     */
    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    /**
     * @return 服务URL
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * @param serviceUrl 
	 *            服务URL
     */
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * @return 请求方式
     */
    public String getSubmitMethod() {
        return submitMethod;
    }

    /**
     * @param submitMethod 
	 *            请求方式
     */
    public void setSubmitMethod(String submitMethod) {
        this.submitMethod = submitMethod;
    }

    /**
     * @return 是否包含参数
     */
    public String getIsParam() {
        return isParam;
    }

    /**
     * @param isParam 
	 *            是否包含参数
     */
    public void setIsParam(String isParam) {
        this.isParam = isParam;
    }

    /**
     * @return 请求参数
     */
    public String getServiceParam() {
        return serviceParam;
    }

    /**
     * @param serviceParam 
	 *            请求参数
     */
    public void setServiceParam(String serviceParam) {
        this.serviceParam = serviceParam;
    }

    /**
     * @return 任务描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            任务描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 
	 *            更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    

}