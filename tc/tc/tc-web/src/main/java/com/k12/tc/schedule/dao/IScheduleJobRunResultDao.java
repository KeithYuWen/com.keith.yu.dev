package com.k12.tc.schedule.dao;


import java.util.List;

import com.k12.tc.schedule.domain.ScheduleJobRunResult;
import com.k12.tc.schedule.domain.ScheduleJobRunResultQuery;
import org.apache.ibatis.annotations.Param;

public interface IScheduleJobRunResultDao {
    int countByExample(ScheduleJobRunResultQuery example);

    int deleteByExample(ScheduleJobRunResultQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleJobRunResult record);

    int insertSelective(ScheduleJobRunResult record);

    List<ScheduleJobRunResult> selectByExampleWithBLOBs(ScheduleJobRunResultQuery example);

    List<ScheduleJobRunResult> selectByExample(ScheduleJobRunResultQuery example);

    ScheduleJobRunResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScheduleJobRunResult record, @Param("example") ScheduleJobRunResultQuery example);

    int updateByExampleWithBLOBs(@Param("record") ScheduleJobRunResult record, @Param("example") ScheduleJobRunResultQuery example);

    int updateByExample(@Param("record") ScheduleJobRunResult record, @Param("example") ScheduleJobRunResultQuery example);

    int updateByPrimaryKeySelective(ScheduleJobRunResult record);

    int updateByPrimaryKeyWithBLOBs(ScheduleJobRunResult record);

    int updateByPrimaryKey(ScheduleJobRunResult record);

    int batchInsertSelective(List<ScheduleJobRunResult> recordList);

    int batchUpdateByPrimaryKeySelective(List<ScheduleJobRunResult> recordList);
}