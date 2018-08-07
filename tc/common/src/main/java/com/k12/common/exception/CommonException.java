package com.k12.common.exception;

public class CommonException extends Exception{
	private static final long serialVersionUID = 845932203955500382L;
	//错误码
    private Integer errorCode;
    //返回错误消息
    private String retMsg;
    public CommonException (int errorCode, String retMsg) {
        this.retMsg = retMsg;
        this.errorCode = errorCode;
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
