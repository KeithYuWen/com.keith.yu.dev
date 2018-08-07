/** 
 * 文件名：BusinessException.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月19日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParseException extends RuntimeException {

	private static final Logger logger = LoggerFactory.getLogger(JsonParseException.class);

	private static final long serialVersionUID = -5771568110674413033L;

	// 错误码
	/**
	 * 错误码<br>
	 * {@link ErrorCode}
	 */
	private Integer errorCode;
	// 返回错误消息
	private String retMsg;

	public JsonParseException(int errorCode, String retMsg) {
		super(retMsg);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
		logger.error(retMsg);
	}

	public JsonParseException(int errorCode, String retMsg, String message) {
		super(message);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
		logger.error(retMsg);
	}


	public JsonParseException(int errorCode, String retMsg, Throwable cause) {
		super(cause);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
		logger.error(retMsg);
	}

	public JsonParseException() {
		super();
	}

	public JsonParseException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.retMsg = message;
	}

	public JsonParseException(String message, Throwable cause) {
		super(message, cause);
		this.retMsg = message;
	}

	public JsonParseException(String message) {
		super(message);
		this.retMsg = message;
	}

	public JsonParseException(Throwable cause) {
		super(cause);
		this.retMsg = cause.getMessage();
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
