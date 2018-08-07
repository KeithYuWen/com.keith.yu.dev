/**
 * 项目名：tc
 * 包名：com.k12.tc.common.init
 * 文件名：ErrorCode.java *
 * 日期：2015-9-25 下午14:17:54
 * Copyright 宏坤供应链管理有限公司 2015
 * 版权所有
 */
package com.k12.tc.common.init;

/**
 * 
 * Description : 错误码配置信息
 * 
 */
public class ErrorCode {
	/** 成功 */
	public static final int SUCCESS = 170000;

	/** 未知错误 */
	public static final int UNDEFINED_ERROR = 179999;
	public static final String UNDEFINED_ERROR_MSG = "未知错误";
	/**有提示错误*/
	public static final int MESSAGE_ERROR = 99;
	//无法连接到接口或服务端内部错误
	public static final int CONFIRM_INTERFACE_ERROR = 12;
	/**
	 * 数据库错误
	 */
	public static final int DATABASE_ERROR = 170008;
	/**
	 * 校验错误
	 */
	public static final int VERIFY_ERROR = 170009;
	/**
	 * api调用错误
	 */
	public static final int API_ERROR = 170010;
	
    public static final int USERNAME_ERROR = 170011;
    
    public static final int PASSWORD_ERROR = 170012;
	
    public static final int SESSION_EXPIRED = 170013;
    
    //账号冻结
    public static final int ACCOUNT_FREEZE = 170014;
}