package com.k12.common.base.rest;

import com.alibaba.fastjson.JSONObject;
import com.k12.common.util.common.StringUtils;


public class RestHttpVo {
	
	/**
	 * 请求数据
	 */
	private String reqMsg;
	
	/**
	 * 请求银联报文
	 */
	private JSONObject paramJson;
	private String paramStr;
	
	/**
	 * 银联返回报文
	 */
	private JSONObject reJson;
	private String reStr;
	
	/**
	 * 返回数据
	 */
	private String msg;
	private String errorMsg;
	
	/**
	 * 请求类型
	 */
	private byte requestType;
	
	/**
	 * 流水号
	 */
	private String payId;
	
	/**
	 * 时长
	 */
	private long bTime=System.currentTimeMillis();

	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	public JSONObject getParamJson() {
		if(paramJson==null){
			if(!StringUtils.isEmpty(paramStr)){
				return JSONObject.parseObject(paramStr);
			}
		}
		return paramJson;
	}

	public void setParamJson(JSONObject paramJson) {
		this.paramJson = paramJson;
	}

	public String getParamStr() {
		if(StringUtils.isEmpty(paramStr)){
			if(paramJson!=null){
				return JSONObject.toJSONString(paramJson);
			}
		}
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	public JSONObject getReJson() {
		if(reJson==null){
			if(!StringUtils.isEmpty(reStr)){
				return JSONObject.parseObject(reStr);
			}
		}
		return reJson;
	}

	public void setReJson(JSONObject reJson) {
		this.reJson = reJson;
	}

	public String getReStr() {
		if(StringUtils.isEmpty(reStr)){
			if(reJson!=null){
				return JSONObject.toJSONString(reJson);
			}
		}
		return reStr;
	}

	public void setReStr(String reStr) {
		this.reStr = reStr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public byte getRequestType() {
		return requestType;
	}

	public void setRequestType(byte requestType) {
		this.requestType = requestType;
	}

	public long getbTime() {
		return bTime;
	}

	public void setbTime(long bTime) {
		this.bTime = bTime;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}
	
}
