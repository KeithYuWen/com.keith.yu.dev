/** 
 * 文件名：BusinessException.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月19日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.exception;


/** 
 * 项目名称：greenpass <br>
 * 类名称：FTPClientException <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：xuhongjie
 * 创建时间：2015年10月19日 下午4:05:18 <br>
 * 修改人：xuhongjie
 * 修改时间：2015年10月19日 下午4:05:18 <br>
 * 修改备注：<br>
 * {@link ErrorCode}
 * @version 1.0<br>
 * @author xuhongjie
 */
public class FTPClientException extends RuntimeException {

	private static final long serialVersionUID = -5771568110674413033L;

	// 错误码
	/**
	 * 错误码<br>
	 * {@link ErrorCode}
	 */
	private Integer errorCode;
	// 返回错误消息
	private String retMsg;

	public FTPClientException(int errorCode, String retMsg) {
		super(retMsg);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
	}

	public FTPClientException(int errorCode, String retMsg, String message) {
		super(message);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
	}

	
	public FTPClientException(int errorCode, String retMsg, Throwable cause) {
		super(cause);
		this.retMsg = retMsg;
		this.errorCode = errorCode;
	}

	public FTPClientException() {
		super();
	}

	public FTPClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FTPClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPClientException(String message) {
		super(message);
	}

	public FTPClientException(Throwable cause) {
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
