package com.k12.tc.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.k12.tc.common.vo.PageView;
import com.k12.tc.controller.common.BaseController;
import com.k12.tc.schedule.domain.ScheduleJob;
import com.k12.tc.schedule.service.api.IScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.k12.common.util.common.JsonUtil;
import com.k12.common.util.response.TRestUtil;

@Controller
@RequestMapping("/tc")
public class TcController extends BaseController
{
	private static final Logger LOG = LoggerFactory.getLogger(TcController.class);
	
	@Resource
	private IScheduleService scheduleService;
	
	/**
	 * getListData(这里用一句话描述这个方法的作用) 
	 * @param req
	 * @param res
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ResponseBody
	@RequestMapping(value="/show-page-data")
	public PageView getListData(HttpServletRequest req, HttpServletResponse res){
		ScheduleJob scheduleJob = getFormMap(ScheduleJob.class);
		PageView pageView = scheduleService.getPage(scheduleJob);
		return pageView;
	}
	
	/**
	 * toScheduleList(跳转任务列表) 
	 * @param req
	 * @param res
	 * @return
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/to-schedule-list")
	public String  toScheduleList(HttpServletRequest req, HttpServletResponse res, Model model){
		return "/schedule/schedule-list";
	}

	/**
	 * toScheduleInput(跳转添加任务页面) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/schedule-input")
	public String  toScheduleInput(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		return "/schedule/schedule-input";
	}
	
	
	/**
	 * saveSchedule(添加任务) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/save-schedule",method=RequestMethod.POST)
	public void  saveSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			ScheduleJob scheduleJob = new ScheduleJob();
			String scheduleJobStr = req.getParameter("scheduleJob");
			scheduleJob = JsonUtil.toBean(scheduleJobStr, ScheduleJob.class);
			scheduleJob.setJobStatus("1");
			scheduleJob.setCreateTime(new Date());
			int flag = scheduleService.insert(scheduleJob);
			if(flag >0){
				resultMap.put("success", true);
				resultMap.put("message","任务信息入库成功");
			}else{
				resultMap.put("success",false);
				resultMap.put("message","任务信息入库失败");
			}
		}catch(Exception e){
			resultMap.put("success",false);
			resultMap.put("message","创建任务失败");
		}
		TRestUtil.writePage(res, JsonUtil.toString(resultMap));
	}
	
	/**
	 * deleteSchedule(删除定时任务) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/delete-schedule",method=RequestMethod.POST)
	public void deleteSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String jobId = req.getParameter("id");
			if("".equals(jobId)){
				resultMap.put("success",false);
				resultMap.put("message","获取任务信息失败");
			}else{
				int scheduleJobId = Integer.parseInt(jobId);
				scheduleService.delete(scheduleJobId);
				resultMap.put("success",true);
				resultMap.put("message","删除任务信息成功");
			}
		}catch(Exception e){
			resultMap.put("success",false);
			resultMap.put("message","删除任务失败");
		}
		TRestUtil.writePage(res, JsonUtil.toString(resultMap));
	}
	
	
	
	/**
	 * resumeSchedule(恢复定时任务) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/resume-schedule",method=RequestMethod.POST)
	public void resumeSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String jobId = req.getParameter("id");
			if("".equals(jobId)){
				resultMap.put("success",false);
				resultMap.put("message","获取任务信息失败");
			}else{
				int scheduleJobId = Integer.parseInt(jobId);
				scheduleService.resumeJob(scheduleJobId);
				resultMap.put("success",true);
				resultMap.put("message","恢复任务信息成功");
			}
		}catch(Exception e){
			resultMap.put("success",false);
			resultMap.put("message","恢复任务失败");
		}
		TRestUtil.writePage(res, JsonUtil.toString(resultMap));
	}
	
	/**
	 * runonceSchedule(运行一次定时任务) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/runonce-schedule",method=RequestMethod.POST)
	public void runonceSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String jobId = req.getParameter("id");
			if("".equals(jobId)){
				resultMap.put("success",false);
				resultMap.put("message","获取任务信息失败");
			}else{
				int scheduleJobId = Integer.parseInt(jobId);
				scheduleService.runOnce(scheduleJobId);
				resultMap.put("success",true);
				resultMap.put("message","立即运行一次成功");
			}
		}catch(Exception e){
			resultMap.put("success",false);
			resultMap.put("message","立即运行一次成功");
		}
		TRestUtil.writePage(res, JsonUtil.toString(resultMap));
	}
	
	
	/**
	 * pauseSchedule(暂停定时任务) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/pause-schedule",method=RequestMethod.POST)
	public void pauseSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String jobId = req.getParameter("id");
			if("".equals(jobId)){
				resultMap.put("success",false);
				resultMap.put("message","获取任务信息失败");
			}else{
				int scheduleJobId = Integer.parseInt(jobId);
				scheduleService.pauseJob(scheduleJobId);
				resultMap.put("success",true);
				resultMap.put("message","暂停任务信息成功");
			}
		}catch(Exception e){
			resultMap.put("success",false);
			resultMap.put("message","暂停任务失败");
		}
		TRestUtil.writePage(res, JsonUtil.toString(resultMap));
	}
	
	
	/**
	 * editSchedule(编辑任务) 
	 * @param req
	 * @param res
	 * @param session 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/edit-schedule",method=RequestMethod.GET)
	public String editSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		String jobId=req.getParameter("id");
		ScheduleJob scheduleJob = scheduleService.get(Integer.parseInt(jobId));
		req.setAttribute("scheduleJob", scheduleJob);
		return "/schedule/schedule-input";
	}
	
	/**
	 * scheduleDetail(详情) 
	 * @param req
	 * @param res
	 * @param session
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value="/schedule-detail",method=RequestMethod.GET)
	public String scheduleDetail(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		String jobId=req.getParameter("id");
		ScheduleJob scheduleJob = scheduleService.get(Integer.parseInt(jobId));
		req.setAttribute("scheduleJob", scheduleJob);
		req.setAttribute("isDetail", "true");
		return "/schedule/schedule-input";
	}
	
	@RequestMapping(value = "for-test", method = RequestMethod.GET)
	  public void fortest(HttpServletRequest req,HttpServletResponse res,HttpSession session) {
	    	System.out.println("for test");
	  }
	/**
	 * 
	 * @Title:        toNewSchedule 
	 * @Description:  TODO(这里用一句话描述这个方法的作用) 
	 * @param:        @param req
	 * @param:        @param res
	 * @param:        @param session
	 * @param:        @return    
	 * @return:       String    
	 * @throws 
	 * @author        wangwenbin
	 * @Date          2017-1-23 上午11:38:20
	 */
	@RequestMapping(value="/new-schedule")
	public String  toNewSchedule(HttpServletRequest req,HttpServletResponse res,HttpSession session){
		return "/schedule/new-schedule";
	}

}
