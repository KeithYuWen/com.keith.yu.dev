package com.k12.tc.common.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;



/**
 * 项目名称：tc-api 
 * 类名称：SystemParamInit 
 * 类描述：JsHuaZhao，处理页面提交的请求。 
 * 类描述：SystemParamInit。 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：孙海滨
 * 创建时间：2016年7月6日 上午9:31:39 
 * 修改人：孙海滨 
 * 修改时间：2016年7月6日 上午9:31:39 
 * 修改备注：
 * @version 1.0*
 */
@Lazy(value=false)
@Component
public class SystemParamInit {
	
	//加密
    private static String encodingAesKey;
    private static String token;
    private static String appId;
    
  //在session失效后，重新登陆时有哪些页面需要重定向
    private static String replacePageWhenSetCookie;
    
    
    public static String getEncodingAesKey() {
		return encodingAesKey;
	}
	public static String getToken() {
		return token;
	}
	public static String getAppId() {
		return appId;
	}
	
	public static String getReplacePageWhenSetCookie() {
		return replacePageWhenSetCookie;
	}
    
    @Value("#{configProperties['encodingAesKey']}")
	public  void setEncodingAesKey(String encodingAesKey) {
		SystemParamInit.encodingAesKey = encodingAesKey;
	}
	@Value("#{configProperties['token']}")
	public  void setToken(String token) {
		SystemParamInit.token = token;
	}
	@Value("#{configProperties['appId']}")
	public  void setAppId(String appId) {
		SystemParamInit.appId = appId;
	}
	
	@Value("#{configProperties['replacePageWhenSetCookie']}")
	public void setReplacePageWhenSetCookie(String replacePageWhenSetCookie) {
		SystemParamInit.replacePageWhenSetCookie = replacePageWhenSetCookie;
	}
	


}
