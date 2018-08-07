/**
 * 项目名：greenpass
 * 包名：com.k12.greenpass.common.util
 * 文件名：ErrorCode.java *
 * 日期：2015-9-25 下午14:17:54
 * Copyright 宏坤供应链管理有限公司 2015
 * 版权所有
 */
package com.k12.common.exception;

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
	//发送短信错误
	public static final int SENDSMS_ERROR = 170001;
	//校验验证码错误
	public static final int AUTHCODE_ERROR = 170002;
	//请求 
	public static final int REQUEST_ERROR = 170003;
	//注册错误
	public static final int REGISTER_ERROR = 170004;
	//参数错误
	public static final int PARAM_ERROR = 170005;
	//密码错误
	public static final int PASSWORD_ERROR = 170006;
	//同步错误
	public static final int SYNC_ERROR = 170007;
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
	//手机号错误
    public static final int PHONE_ERROR = 170011;
    //发送短信时验证码错误
    public static final int SENDSMS_CODE_ERROR = 170012;
    //ftp错误
    public static final int FTP_ERROR = 170013;
    
    /**
     * 没有数据错误
     */
    public static final int NO_DATASET_ERROR = 170014;
}