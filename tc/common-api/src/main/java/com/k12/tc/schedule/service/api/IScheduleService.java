package com.k12.tc.schedule.service.api;

import java.util.List;

import com.k12.tc.common.vo.PageView;
import com.k12.tc.schedule.domain.ScheduleJob;

public interface IScheduleService
{
	/**
     * 初始化定时任务
     */
    public void initScheduleJob();

    /**
     * 新增
     * 
     * @param scheduleJob
     * @return
     */
    public int insert(ScheduleJob scheduleJob);

    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     * 
     * @param scheduleJob
     */
    public int update(ScheduleJob scheduleJob);

    /**
     * 删除重新创建方式
     * 
     * @param scheduleJob
     */
    public void delUpdate(ScheduleJob scheduleJob);

    /**
     * 删除
     * 
     * @param scheduleJobId
     */
    public void delete(int scheduleJobId);

    /**
     * 运行一次任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void runOnce(int scheduleJobId);

    /**
     * 暂停任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void pauseJob(int scheduleJobId);

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void resumeJob(int scheduleJobId);

    /**
     * 获取任务对象
     * 
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(int scheduleJobId);

    /**
     * 查询任务列表
     * 
     * @param ScheduleJob
     * @return
     */
    public List<ScheduleJob> queryList(ScheduleJob scheduleJob);

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    public List<ScheduleJob> queryExecutingJobList();
    
    
    /**
     * 查询任务列表
     * 
     */

    public PageView getPage(ScheduleJob scheduleJob);

}
