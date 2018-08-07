/**
 * 项目名：greenpass
 * 包名：com.k12.greenpass.common.utils
 * 文件名：TRestUtil.java *
 * 日期：2015-9-28 下午12:13:30
 * Copyright 宏坤供应链有限公司2015
 * 版权所有
 */
package com.k12.common.util.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.k12.common.exception.ErrorCode;
import com.k12.common.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.k12.common.base.rest.RestClient;
import com.k12.common.base.rest.RestException;
import com.k12.common.util.base64.Base64util;

/**
 * 此类描述的是：
 * 
 * @author xuhongjie
 * 
 */

public class TRestUtil {

	private static final Logger LOG = LoggerFactory.getLogger(TRestUtil.class);

	/**
	 * 
	 * 此方法描述的是：response 返回
	 */
	public static void write(HttpServletResponse response, String responseStr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Request-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials","true");
		LOG.debug("return value:{}", responseStr);
		try {
			response.getWriter().write(Base64util.encode(responseStr));
			// added by renyarong, 2016.4.7
			response.getWriter().close();
		} catch (IOException e) {
			LOG.error(responseStr, e);
		}
	}

	/**
	 * 
	 * 此方法描述的是：response 返回
	 */
	public static void writeNotCode(HttpServletResponse response, String responseStr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		LOG.debug("return value:{}", responseStr);
		try {
			response.getWriter().write(responseStr);
			// added by renyarong, 2016.4.7
			response.getWriter().close();
		} catch (IOException e) {
			LOG.error(responseStr, e);
		}
	}
	
	/**
	 * 
	 * 此方法描述的是：response 返回for temp
	 */
	public static void writeForUpload(HttpServletResponse response, String responseStr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		if(responseStr.length()>1000){
			System.out.println(responseStr.length());
			LOG.debug("return value:{}", responseStr.substring(1000));
		}else{
			LOG.debug("return value:{}", responseStr);
		}
		try {
			response.getWriter().write(responseStr);
			// added by renyarong, 2016.4.7
			response.getWriter().close();
		} catch (IOException e) {
			LOG.error(responseStr, e);
		}
	}

	/**
	 * 
	 * 此方法描述的是：response 返回
	 */
	public static void print(HttpServletResponse response, String responseStr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		try {
			response.getWriter().print(Base64util.encode(responseStr));
			// added by renyarong, 2016.4.7
			response.getWriter().close();
		} catch (IOException e) {
			LOG.error(responseStr, e);
		}

	}

	/**
	 * 
	 * 此方法描述的是：rest客户端请求
	 * 
	 * @param url
	 *            请求的url
	 * @return String
	 */
	public static String execute(String url) throws RestException {
		return new RestClient(url).execute();
	}

	/**
	 * 
	 * 此方法描述的是：rest客户端请求
	 * 
	 * @param url
	 *            请求的url
	 * @param data
	 *            请求的参数
	 * @return String
	 */
	public static String execute(String url, String data) throws RestException {
		return new RestClient(url, data).execute();
	}

	/**
	 * 
	 * 此方法描述的是：rest客户端请求
	 * 
	 * @param url
	 *            请求的url
	 * @param method
	 *            请求的方法
	 * @param data
	 *            请求的参数
	 * @return String
	 */
	public static String execute(String url, String method, String data)
			throws RestException {
		return new RestClient(url, method, data).execute();
	}

	/**
	 * 
	 * 此方法描述的是：response返回
	 */
	public static void write(HttpServletResponse response, ResponseVo responseVo) {
		TRestUtil.write(response, JSON.toJSONString(responseVo));
	}

	/**
	 * 
	 * 此方法描述的是：分页数据返回
	 */
	public static void writePage(HttpServletResponse response, Object ob) {
		TRestUtil.writeNotCode(response, JSON.toJSONString(ob));
	}

	/**
	 * 
	 * 此方法描述的是：response 返回
	 */
	public static void write2(HttpServletResponse response, String responseStr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		LOG.debug("return value:{}", responseStr);
		try {
			response.getWriter().write(responseStr);
			// added by renyarong, 2016.4.7
			response.getWriter().close();
		} catch (IOException e) {
			LOG.error(responseStr, e);
		}
	}
	
	/**
	 * 
	 * 此方法描述的是：response返回数据
	 */
	public static void writeRV(HttpServletResponse response, ResponseVo responseVo) {
		responseVo.setMessage(null);
		TRestUtil.writeNotCode(response, JSON.toJSONString(responseVo));
	}
	
	/**
	 * 
	 * 此方法描述的是：response返回 成功数据
	 */
	public static void writeSucObj(HttpServletResponse response, Object ob) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(true);
		responseVo.setData(ob);
		TRestUtil.writeNotCode(response, JSON.toJSONString(responseVo));
	}

	/**
	 * 
	 * 此方法描述的是：response返回 错误数据
	 */
	public static void writeErrObj(HttpServletResponse response, Object ob,Integer errorCode) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		if(errorCode==null){
			responseVo.setErrorCode(ErrorCode.UNDEFINED_ERROR);
		}else{
			responseVo.setErrorCode(errorCode);
		}
		responseVo.setData(ob);
		TRestUtil.writeNotCode(response, JSON.toJSONString(responseVo));
	}
	
	/**
	 * 
	 * 此方法描述的是：response返回 错误提示
	 */
	public static void writeErrStr(HttpServletResponse response, String msg,Integer errorCode) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setMessage(msg);
		if(errorCode==null){
			responseVo.setErrorCode(ErrorCode.UNDEFINED_ERROR);
		}else{
			responseVo.setErrorCode(errorCode);
		}
		TRestUtil.writeNotCode(response, JSON.toJSONString(responseVo));
	}
	
	/**
	 * 
	 * 此方法描述的是：response返回 错误提示
	 */
	public static void writeErrStr(HttpServletResponse response, String msg) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setMessage(msg);
		responseVo.setErrorCode(ErrorCode.MESSAGE_ERROR);
		TRestUtil.writeNotCode(response, JSON.toJSONString(responseVo));
	}
	
	/**
	 * 
	 * 此方法描述的是：response返回
	 */
	public static void print(HttpServletResponse response, ResponseVo responseVo) {
		TRestUtil.print(response, JSON.toJSONString(responseVo));
	}

	/**
	 * 设置错误信息
	 * 
	 * @param responseVo
	 *            :
	 * @param errorCode
	 *            :
	 * @param msg
	 *            :
	 */
	public static void setError(ResponseVo responseVo, int errorCode, String msg) {
		responseVo.setSuccess(false);
		responseVo.setErrorCode(errorCode);
		responseVo.setMsg(msg);
	}

	/**
	 * 
	 * 此方法描述的是：rest客户端请求,并将结果转化为responseVo
	 * 
	 * @param url
	 *            请求的url
	 * @param method
	 *            请求的方法
	 * @param data
	 *            请求的参数
	 * @return ResponseVo
	 */
	public static ResponseVo executeToVo(String url, String method, String data) {
		try {
			return JSONObject.parseObject(new RestClient(url, method, data).execute(),
					ResponseVo.class);
		} catch (RestException e) {
			LOG.error(url, e);
			return getErrorResp();
		}
	}

	/**
	 * 
	 * 此方法描述的是：无法调用接口时的返回
	 * 
	 */
	private static ResponseVo getErrorResp() {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(false);
		responseVo.setErrorCode(ErrorCode.CONFIRM_INTERFACE_ERROR);
		responseVo.setMsg("无法连接到接口或服务端内部错误");
		return responseVo;
	}
	
	/**
	 * getParamMap(获取request中的参数) 
	 * @param req
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParamMap(HttpServletRequest req){
		Map<String, String> map=new HashMap<String, String>();
		Map<String, String[]> pa=req.getParameterMap();
		if(pa!=null){
			for (String s : pa.keySet()) {
				map.put(s, pa.get(s)[0]);
			}
		}
		return map;
	}
}
