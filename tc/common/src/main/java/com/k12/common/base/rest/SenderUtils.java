/** 
 * 文件名：SenderUtils.java <br>
 * 版本信息： V1.0<br>
 * 日期：2016年2月16日 <br>
 * Copyright: Corporation 2016 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.base.rest;

import com.k12.common.exception.ApiException;
import com.k12.common.exception.ErrorCode;
import com.k12.common.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/** 
 * 项目名称：greenpass-api <br>
 * 类名称：SenderUtils <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2016年2月16日 下午2:07:56 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年2月16日 下午2:07:56 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
public class SenderUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(SenderUtils.class);
	/**
	 * 重试次数，默认为3
	 */
	private final static int RETRY_COUNT = 3;

	public static String sendDataWithRetry(String param, String url, Integer retryCount) {
		RestClient restClient = new RestClient(url);
		restClient.setData(param);
		restClient.setMethod("POST");
		
		String result = null;
		int tryNum = 0;
		retryCount = (retryCount != null && retryCount > 0)? retryCount: RETRY_COUNT;
		boolean tryFlag = false;
		Exception lastException = null;
        while (tryNum++ < RETRY_COUNT) {
            logger.debug("第 "+ tryNum +" 次尝试调用接口：" + url);
			try {
				restClient.setData(param);
				result = restClient.encryptExecute();
				logger.info("调用接口返回报文：[" + result + "]");
				if (null == result) {
					logger.error("调用接口["+ url +"]失败，返回数据为null");
					tryFlag = true;
				} else {	
					return result;
				}
			} catch (RestException e) {
				tryFlag = true;
				lastException = e;
				logger.error("调用接口["+ url +"]失败", e);
			} catch (Exception e) {
				logger.error("调用接口["+ url +"]，微信加密失败", e);
				throw new SystemException(ErrorCode.REQUEST_ERROR, "网络调用失败", e);
			}
			
			if (tryFlag) {
                if (tryNum >= 3) {
                	if (null != lastException) {
                		throw new ApiException(ErrorCode.REQUEST_ERROR, "网络调用失败，请稍后再试", lastException);
                	} else {
                		break;
                	}
                } else {
                	tryFlag = false;
                    continue ;
                }
			}
        }
        throw new ApiException(ErrorCode.REQUEST_ERROR, "调用远程接口，尝试3次仍未成功");
	}
	
	public static String sendDataWithRetryReturnNoBase(String param, String url, Integer retryCount) {
		RestClient restClient = new RestClient(url);
		restClient.setData(param);
		restClient.setMethod("POST");
		
		String result = null;
		int tryNum = 0;
		retryCount = (retryCount != null && retryCount > 0)? retryCount: RETRY_COUNT;
		boolean tryFlag = false;
		Exception lastException = null;
        while (tryNum++ < RETRY_COUNT) {
            logger.debug("第 "+ tryNum +" 次尝试调用接口：" + url);
			try {
				restClient.setData(param);
				result = restClient.encryptExecuteReturnNoBase();
				logger.info("调用接口返回报文：[" + result + "]");
				if (null == result) {
					logger.error("调用接口["+ url +"]失败，返回数据为null");
					tryFlag = true;
				} else {	
					return result;
				}
			} catch (RestException e) {
				tryFlag = true;
				lastException = e;
				logger.error("调用接口["+ url +"]失败", e);
			} catch (Exception e) {
				logger.error("调用接口["+ url +"]，微信加密失败", e);
				throw new SystemException(ErrorCode.REQUEST_ERROR, "网络调用失败", e);
			}
			
			if (tryFlag) {
                if (tryNum >= 3) {
                	if (null != lastException) {
                		throw new ApiException(ErrorCode.REQUEST_ERROR, "网络调用失败，请稍后再试", lastException);
                	} else {
                		break;
                	}
                } else {
                	tryFlag = false;
                    continue ;
                }
			}
        }
        throw new ApiException(ErrorCode.REQUEST_ERROR, "调用远程接口，尝试3次仍未成功");
	}
	
	private static Map<String, String> getHeaderMap(String url, String method, String acceptType) {
		
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", acceptType);
		headerMap.put("User-Agent", "Mozilla 5.0");
		return headerMap;
	}
	
	public static String sendDataNoEncryotWithRetry(String param, String url,String reqMethod,String acceptType,Integer retryCount) {
		RestClient restClient = new RestClient(url);
		restClient.setData(param);
		if(reqMethod == null || "".equals(reqMethod)){
			restClient.setMethod("POST");
		}else{
			restClient.setMethod(reqMethod);
		}
		String result = null;
		if (acceptType == null || "".equals(acceptType)) {
			acceptType = "application/json";
		}
		Map<String, String> headerMap = getHeaderMap(url, "POST", acceptType);
		int tryNum = 0;
		retryCount = (retryCount != null && retryCount > 0)? retryCount: RETRY_COUNT;
		boolean tryFlag = false;
		Exception lastException = null;
        while (tryNum++ < RETRY_COUNT) {
            logger.debug("第 "+ tryNum +" 次尝试调用接口：" + url);
			try {
				restClient.setData(param);
				result = restClient.httpExecute(headerMap);;
				if (null == result) {
					logger.error("调用接口["+ url +"]失败，返回数据为null");
					tryFlag = true;
				} else {
					result = RestCodec.decodeData(result);
					return result;
				}
			} catch (RestException e) {
				tryFlag = true;
				lastException = e;
				logger.error("调用接口["+ url +"]失败", e);
			} catch (Exception e) {
				logger.error("调用接口["+ url +"]，微信加密失败", e);
				throw new SystemException(ErrorCode.REQUEST_ERROR, "网络调用失败", e);
			}
			
			if (tryFlag) {
                if (tryNum >= 3) {
                	if (null != lastException) {
                		throw new ApiException(ErrorCode.REQUEST_ERROR, "网络调用失败，请稍后再试", lastException);
                	} else {
                		break;
                	}
                } else {
                	tryFlag = false;
                    continue ;
                }
			}
        }
        throw new ApiException(ErrorCode.REQUEST_ERROR, "调用远程接口，尝试3次仍未成功");
	}
	
	
	public static String sendDataIsEncryotWithRetry(String param, String url,String reqMethod,String acceptType,Integer retryCount) {
		RestClient restClient = new RestClient(url);
		restClient.setData(param);
		if(reqMethod == null || "".equals(reqMethod)){
			restClient.setMethod("POST");
		}else{
			restClient.setMethod(reqMethod);
		}
		String result = null;
		if (acceptType == null || "".equals(acceptType)) {
			acceptType = "application/json";
		}
		Map<String, String> headerMap = getHeaderMap(url, "POST", acceptType);
		int tryNum = 0;
		retryCount = (retryCount != null && retryCount > 0)? retryCount: RETRY_COUNT;
		boolean tryFlag = false;
		Exception lastException = null;
        while (tryNum++ < RETRY_COUNT) {
            logger.info("第 "+ tryNum +" 次尝试调用接口：" + url);
			try {
				restClient.setData(param);
				result = restClient.encryptExecute(headerMap);;
				if (null == result) {
					logger.info("调用接口["+ url +"]失败，返回数据为null");
					tryFlag = true;
				} else {
					//result = RestCodec.decodeData(result);
					return result;
				}
			} catch (RestException e) {
				tryFlag = true;
				lastException = e;
				logger.error("调用接口["+ url +"]失败", e);
			} catch (Exception e) {
				logger.error("调用接口["+ url +"]，微信加密失败", e);
				throw new SystemException(ErrorCode.REQUEST_ERROR, "网络调用失败", e);
			}
			
			if (tryFlag) {
                if (tryNum >= 3) {
                	if (null != lastException) {
                		throw new ApiException(ErrorCode.REQUEST_ERROR, "网络调用失败，请稍后再试", lastException);
                	} else {
                		break;
                	}
                } else {
                	tryFlag = false;
                    continue ;
                }
			}
        }
        throw new ApiException(ErrorCode.REQUEST_ERROR, "调用远程接口，尝试3次仍未成功");
	}
}
