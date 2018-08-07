package com.k12.common.util.rtx;


/**
 * 项目名称：rtx-api 
 * 类名称：RtxSenderInfo 
 * 类描述：处理页面提交的请求。 
 * 类描述：Bean,响应Action请求，对数据库进行操作，并把结果返回到Action。 
 * Copyright: Copyright (c) 2016 by  k12
 * Company: k12
 * 创建人：sunhaibin
 * 创建时间：2016年11月22日 下午5:27:30 
 * 修改人：sunhaibin 
 * 修改时间：2016年11月22日 下午5:27:30 
 * 修改备注：
 * @version 1.0*
 */
public class RtxSenderInfoVo {
	
	//要发送的信息
	private String sendMsg;
	//信息的接收者（多个接收者用,号分离）
	private String receivers;
	//主题
	private String title;
	
	
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	public String getReceivers() {
		return receivers;
	}
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
	

}
