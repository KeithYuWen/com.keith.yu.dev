/**
 * 项目名：greenpass
 * 包名：com.k12.greenpass.common.vo
 * 文件名：ResponseVo.java *
 * 日期：2015-9-29 下午4:21:54
 * Copyright 宏坤供应链有限公司 2015
 * 版权所有
 */
package com.k12.common.vo;

import java.io.Serializable;

import com.k12.common.exception.ErrorCode;


/**
 * 此类描述的是：请求返回的公用数据
 * 
 * @author xuhongjie
 */

public class ResponseVo implements Serializable{
	private static final long serialVersionUID = -6530806376548564877L;
	// 成功标记
    private boolean success = true;
    // 提示信息
    private String msg = "";
    // 错误码
    private int errorCode = ErrorCode.SUCCESS;
    // 返回的具体数据
    private Object data;
    //消息队列也会用此字段作为临时存储变量
    private String message;

    public ResponseVo() {
		super();
	}

	public ResponseVo(boolean success, String msg, int errorCode, Object data) {
		super();
		this.success = success;
		this.msg = msg;
		this.errorCode = errorCode;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            :
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     *            :
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            :
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data
     *            :
     */
    public void setData(Object data) {
        this.data = data;
    }

}
