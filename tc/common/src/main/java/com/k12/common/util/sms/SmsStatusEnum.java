package com.k12.common.util.sms;
/**
 * 类描述：短信校验码
 * 类名称：SmsStatusEnum 
 * 项目名称：greenpass-webapp 
 * Copyright: Copyright (c) 2014 by 江苏宏坤供应链有限公司 
 * Company: k12 Supply Chain MGT.
 * 创建人：刘大周 
 * 创建时间：2016-3-22 下午2:10:03 
 * 修改人：刘大周 
 * 修改时间：2016-3-22 下午2:10:03 
 * 修改备注：
 * @version 1.0*
 */
public enum SmsStatusEnum {
	
	STATUS_0("0","提交成功"),
	STATUS_100("100","发送失败"),
	STATUS_101("101","用户账号不存在或密码错误"),
	STATUS_102("102","账号已禁用"),
	STATUS_105("105","短信内容超过300字或为空、或内容编码格式不正确"),
	STATUS_106("106","手机号码超过100个或有错误号码"),
	STATUS_108("108","余额不足"),
	STATUS_109("109","ip错误"),
	STATUS_110("110","短信内容存在系统保留关键词"),
	STATUS_114("114","模板短信序号不存在"),
	STATUS_115("115","短信签名标签序号不存在"),
	STATUS_116("116","认证码不正确"),
	STATUS_117("117","未开通此接入方式");
	
	private String code;
	private String name;
	
	private SmsStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static boolean contains(String type){
		for (SmsStatusEnum p : SmsStatusEnum.values()) {
			if(p.getCode()== type){
				return true;
			}
		}
		return false;
	}
	
	public static String getNameByCode(String type){
		for (SmsStatusEnum p : SmsStatusEnum.values()) {
			if(p.getCode()== type){
				return p.getName();
			}
		}
		return "发送失败";
	}
	
	
	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}
	
	public static String getName(String type) {
		for (SmsStatusEnum p : SmsStatusEnum.values()) {
			if(p.getCode()== type){
				return p.getName();
			}
		}
		return "";
	}
	
}

