package com.k12.tc.schedule.quartz;

import com.alibaba.fastjson.JSON;
import com.k12.common.exception.JsonParseException;
import com.k12.common.vo.ResponseVo;
import com.k12.tc.common.base.rest.RestClient;
import com.k12.tc.schedule.dao.IScheduleJobRunResultDao;
import com.k12.tc.schedule.domain.ScheduleJob;
import com.k12.tc.schedule.domain.ScheduleJobRunResult;
import com.k12.tc.schedule.vo.ScheduleJobVo;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


@DisallowConcurrentExecution
public class JobFactory implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(JobFactory.class);

    @Resource
    private IScheduleJobRunResultDao iScheduleJobRunResultDao;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("JobFactory execute");

        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(
                ScheduleJobVo.JOB_PARAM_KEY);

        LOG.info("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
        LOG.info("Job start#########");
        String ret;
        String message = null;
        ResponseVo responseVo = null;
        ScheduleJobRunResult scheduleJobRunResult = new ScheduleJobRunResult();
        scheduleJobRunResult.setRunStatus((byte)1);
        try {
            RestClient restClient = new RestClient(scheduleJob.getServiceUrl());
            restClient.setMethod(scheduleJob.getSubmitMethod());
            restClient.setData(scheduleJob.getServiceParam());
            ret = restClient.execute();

            try {
                responseVo = JSON.parseObject(ret, ResponseVo.class);
            }catch (Exception e){
                throw new JsonParseException("接口调用成功，返回数据解析失败。");
            }
        } catch (JsonParseException e1) {
            message = "接口调用成功，返回数据解析失败。";
            LOG.error("定时任务运行失败(Exception)：" + message);
        } catch (Exception e2) {
            message = e2.getMessage();
            scheduleJobRunResult.setRunStatus((byte)2);
            LOG.error("定时任务运行失败(Exception)：" + message);
        }

        /* save run result */
        if(responseVo != null){
            if(!responseVo.isSuccess()){
                scheduleJobRunResult.setRunStatus((byte)2);
                message = "定时任务在目标服务器执行失败：";
                message += responseVo.getMessage();
                LOG.error("定时任务运行失败(Result Error)：" + message);
            }
        }

        scheduleJobRunResult.setJobId(scheduleJob.getJobId());
        scheduleJobRunResult.setFailReason(message);
        iScheduleJobRunResultDao.insert(scheduleJobRunResult);
        LOG.info("Job end#########");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
