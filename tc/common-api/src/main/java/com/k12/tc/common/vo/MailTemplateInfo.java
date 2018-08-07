package com.k12.tc.common.vo;
/**
 * 项目名称：greenpass 
 * 类名称：MailTemplateInfo 
 * 类描述：邮件模板类
 * 类描述：MailTemplateInfo。 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：孙海滨
 * 创建时间：2015年11月16日 下午2:18:20 
 * 修改人：孙海滨 
 * 修改时间：2015年11月16日 下午2:18:20 
 * 修改备注：
 * @version 1.0*
 */
public class MailTemplateInfo extends  MailSenderInfo
{
	private  String orgName;
	
	private  String phoneNumber;
	
	private int temNo;
	
	private String orgCode;

	
	
	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public int getTemNo()
	{
		return temNo;
	}

	public void setTemNo(int temNo)
	{
		this.temNo = temNo;
	}
	
	
	
	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}
	
	



	public String getTemplateInfo(int temNo){
		StringBuilder sb = new  StringBuilder();
		String sbStr = "";
		if(temNo == 1){
			//电商
			sb.append("亲爱的");
			sb.append(this.orgName);
			sb.append(":<br/>");
			sb.append("感谢您加入跨易达（www.greenpass-cn.com）！有想法的跨境电商都在这儿！<br/>");
			sb.append("现在起，您的通关、物流、退税、结汇、保险、融资难题都能在跨易达在线解决！<br/>");
			sb.append("还能追政策、找干货、觅伙伴！<br/>");
			sb.append("足不出户，货物直达境外买家~<br/>");
			sb.append("还等什么？");
			sb.append("<a href='http://www.greenpass-cn.com/member/member-center.html'>点我一键发货</a>");
			sb.append("(链接首次登陆后个人中心页面)");
		}else if(temNo ==2){
			//货代
			sb.append("亲爱的");
			sb.append(this.orgName);
			sb.append(":<br/>");
			sb.append("感谢您加入跨易达（www.greenpass-cn.com）！好线路让更多货主看到！<br/>");
			sb.append("这里聚集海量高信用等级电商企业，货主多的停不下来！<br/>");
			sb.append("告别跑业务，一键直连海量货主！<br/>");
			sb.append("还等什么？");
			sb.append("<a href='http://www.greenpass-tc.com/login-sp.htm'>点我一键发布线路</a>");
			sb.append("(链接货代个人中心的发布线路页面)");
		}else{
			//驳回
			sb.append("亲爱的");
			sb.append(this.phoneNumber);
			sb.append(":<br/>");
			sb.append("您在跨易达（www.greenpass-cn.com）注册提交的企业信息未通过审核，请点击下面的链接，<br/>");
			sb.append("根据提示修改您的信息，以便尽快通过审核，谢谢！<br/>");
			sb.append("<a href='http://www.greenpass-cn.com/user/toReginInfo.html'>点击链接到信息完善页面</a>");	
		}
		sb.append("<br/>如您有疑问可以联系【24小时不下班管理员】：services@greenpass-cn.com");
		sb.append("<br/>(本邮件由系统自动发送，请勿回复。)");
	    sbStr = sb.toString();
		return sbStr;
	}

}
