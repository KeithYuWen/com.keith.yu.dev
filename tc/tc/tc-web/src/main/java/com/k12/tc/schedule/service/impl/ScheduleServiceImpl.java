package com.k12.tc.schedule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.k12.tc.common.vo.PageView;
import com.k12.tc.schedule.dao.IScheduleJobDao;
import com.k12.tc.schedule.domain.ScheduleJob;
import com.k12.tc.schedule.service.api.IScheduleService;
import com.k12.tc.schedule.utils.ScheduleUtils;


@Service("scheduleServiceImpl")
public class ScheduleServiceImpl implements IScheduleService
{
	@Autowired
    private Scheduler scheduler;
    
    @Resource
    private IScheduleJobDao scheduleJobDaoImpl;

	@Override
	public void initScheduleJob()
	{
		ScheduleJob record = new ScheduleJob();
		record.setJobStatus("1");
		List<ScheduleJob> scheduleJobList = scheduleJobDaoImpl.selectBySelective(record);
		if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
                scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
	}

	@Override
	public int insert(ScheduleJob scheduleJob)
	{
		int flag=0;
		if(scheduleJob.getJobId() !=null){
			//更新
			try{
				flag=1;
				delUpdate(scheduleJob);
			}catch(Exception e){
				flag=0;
				e.printStackTrace();
			}
		}else{
			//新增
			 ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			 flag =  scheduleJobDaoImpl.insertSelective(scheduleJob);
		}
		return flag;
		 
	}

	@Override
	public int update(ScheduleJob scheduleJob)
	{
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		return scheduleJobDaoImpl.updateByPrimaryKey(scheduleJob);
	}

	@Override
	public void delUpdate(ScheduleJob scheduleJob)
	{
		 //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
//        scheduleJobDaoImpl.updateByPrimaryKey(scheduleJob);
        scheduleJobDaoImpl.deleteByPrimaryKey(scheduleJob.getJobId());
        scheduleJobDaoImpl.insertSelective(scheduleJob);
        
	}

	@Override
	public void delete(int scheduleJobId)
	{
		ScheduleJob scheduleJob = scheduleJobDaoImpl.selectByPrimaryKey(scheduleJobId);
		//删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduleJobDaoImpl.deleteByPrimaryKey(scheduleJobId);
	}

	@Override
	public void runOnce(int scheduleJobId)
	{
		ScheduleJob scheduleJob = scheduleJobDaoImpl.selectByPrimaryKey(scheduleJobId);
		ScheduleUtils.runOnce(scheduler, scheduleJob);
	}

	@Override
	public void pauseJob(int scheduleJobId)
	{
		ScheduleJob scheduleJob = scheduleJobDaoImpl.selectByPrimaryKey(scheduleJobId);
		scheduleJob.setJobStatus("0");
		ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduleJobDaoImpl.updateByPrimaryKey(scheduleJob);
	}

	@Override
	public void resumeJob(int scheduleJobId)
	{
		ScheduleJob scheduleJob = scheduleJobDaoImpl.selectByPrimaryKey(scheduleJobId);
		scheduleJob.setJobStatus("1");
		ScheduleUtils.resumeJob(scheduler, scheduleJob);
		scheduleJobDaoImpl.updateByPrimaryKey(scheduleJob);

	}

	@Override
	public ScheduleJob get(int scheduleJobId)
	{
		ScheduleJob scheduleJob = scheduleJobDaoImpl.selectByPrimaryKey(scheduleJobId);
		return scheduleJob;
	}

	@Override
	public List<ScheduleJob> queryList(ScheduleJob scheduleJob)
	{
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<ScheduleJob> queryExecutingJobList()
	{
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public PageView getPage(ScheduleJob scheduleJob)
	{
		int count =scheduleJobDaoImpl.countBySelective(scheduleJob);
		if(count >0){
 			List<ScheduleJob> list = scheduleJobDaoImpl.getPage(scheduleJob);
 			scheduleJob.setRowCount(count);
 			scheduleJob.setRecords(list);;
		}
		return scheduleJob;
	}

}
