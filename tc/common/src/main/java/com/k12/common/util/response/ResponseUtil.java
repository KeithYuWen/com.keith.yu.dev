/** 
 * 文件名：ResponseUtil.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月21日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.util.response;

import com.k12.common.exception.ApiException;
import com.k12.common.exception.BusinessException;
import com.k12.common.exception.ErrorCode;
import com.k12.common.exception.SystemException;
import com.k12.common.vo.ResponseVo;

/** 
 * 项目名称：greenpass <br>
 * 类名称：ResponseUtil <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2015年10月21日 下午8:48:42 <br>
 * 修改人：arlen<br>
 * 修改时间：2015年10月21日 下午8:48:42 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
public class ResponseUtil {

	private ResponseUtil() {}

	/**
     * 根据结果获取responseVo实例
     * @param data 处理数据
     * @return {@link ResponseVo}实例
     */
    public static ResponseVo getResponseVo(Object data) {
        ResponseVo responseVo = new ResponseVo();
        Integer errorCode = ErrorCode.SUCCESS;
        
        // 如果data为null或不是Exception实例
        if (null == data || !(data instanceof Exception)) {
            responseVo.setSuccess(true);
            responseVo.setMsg("success");
            responseVo.setData(data);
            responseVo.setErrorCode(errorCode);
            return responseVo;
        }

        // 以下为处理异常
        // 异常信息
        String errorMsg = null;
        if (data instanceof Exception) {
            // 已定义的系统异常
            if (data instanceof SystemException) {
                SystemException systemException = (SystemException) data;
                errorCode = systemException.getErrorCode();
                errorMsg = systemException.getRetMsg();
            } else if (data instanceof BusinessException) { // 已定义的业务异常
                BusinessException businessException = (BusinessException) data;
                errorCode = businessException.getErrorCode();
                errorMsg = businessException.getRetMsg();
            } else if (data instanceof ApiException) {
            	ApiException apiException = (ApiException) data;
                errorCode = apiException.getErrorCode();
                errorMsg = apiException.getRetMsg();
            } else { // 未定义的系统异常
                errorCode = ErrorCode.UNDEFINED_ERROR;
                errorMsg = ErrorCode.UNDEFINED_ERROR_MSG;
            }
        }
        // 设置错误信息
        responseVo.setSuccess(false);
        if (null == errorCode) {
        	responseVo.setErrorCode(ErrorCode.UNDEFINED_ERROR);
        } else {
        	responseVo.setErrorCode(errorCode);
        }
        responseVo.setMsg(errorMsg);
        return responseVo;
    }
}
