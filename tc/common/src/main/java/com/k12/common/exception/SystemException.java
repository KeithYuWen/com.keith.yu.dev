/** 
 * 文件名：SystemException.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月19日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.exception;

/**
 * 项目名称：greenpass <br>
 * 类名称：SystemException <br>
 * 类描述： 系统异常<br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br>
 * 创建人：arlen<br>
 * 创建时间：2015年10月19日 下午4:03:51 <br>
 * 修改人：arlen<br>
 * 修改时间：2015年10月19日 下午4:03:51 <br>
 * 修改备注：<br>
 * 
 * @version 1.0<br>
 * @author arlen<br>
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -2565271004860670219L;

	// 错误码
	private Integer errorCode;
	// 返回错误消息
	private String retMsg;

	public SystemException(int errorCode, String retMsg, Throwable cause) {
		super(cause);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
	}
	
	public SystemException(int errorCode, String retMsg) {
		super(retMsg);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
	}
	
	public SystemException(int errorCode, String retMsg, String message) {
		super(message);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
	}

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}
