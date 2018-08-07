package com.k12.tc.schedule.dao;

import java.util.List;

import com.k12.tc.schedule.domain.ScheduleJob;


public interface IScheduleJobDao {
    /**
     * 根据条件查询记录总数
     */
    int countBySelective(ScheduleJob record);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer jobId);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ScheduleJob record);

    /**
     * 根据主键查询记录
     */
    ScheduleJob selectByPrimaryKey(Integer jobId);

    /**
     * 获取列表
     */
    List<ScheduleJob> selectBySelective(ScheduleJob record);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKey(ScheduleJob record);
    
    
    /**
     * 获取列表
     */
    List<ScheduleJob> getPage(ScheduleJob record);
}