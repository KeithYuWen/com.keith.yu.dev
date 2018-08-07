package com.k12.tc.common.interceptor;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.k12.tc.common.init.ErrorCode;
import com.k12.tc.common.utils.JsonUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.k12.tc.common.init.SystemParamInit;
import com.k12.tc.common.utils.TRestUtil;
import com.k12.tc.common.vo.ResponseVo;

/**
 * 项目名称：tc 
 * 类名称：LoginInterceptor 
 * 类描述：JsHuaZhao，处理页面提交的请求。 
 * 类描述：LoginInterceptor。 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：孙海滨
 * 创建时间：2016年6月24日 上午10:40:20 
 * 修改人：孙海滨 
 * 修改时间：2016年6月24日 上午10:40:20 
 * 修改备注：
 * @version 1.0*
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession().getAttribute("userName") != null) {
			return true;
		}

		//判断是否为ajax请求
		if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
			//System.out.println("Ajax request...");
			ResponseVo result = new ResponseVo();
			result.setMsg("session expired");
			result.setErrorCode(ErrorCode.SESSION_EXPIRED);
            TRestUtil.writeNotCode(response, JsonUtil.toString(result));
            return false;
		}
		
		// 设置cookie保存跳转前的页面
		String requestUri = request.getRequestURI();
		Enumeration names = request.getParameterNames();
		String parameter = "";
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			parameter += name;
			parameter += "=";
			parameter += request.getParameter(name);
			parameter += "&";
		}
		if (parameter.length() > 1) {
			parameter = "?" + parameter.substring(0, parameter.length() - 1);
		}

		String allPages = SystemParamInit.getReplacePageWhenSetCookie().split(";")[0];
		String toPage = SystemParamInit.getReplacePageWhenSetCookie().split(";")[1];
		String[] pages = allPages.split(",");
		boolean needReplace = false;
		for (int i = 0; i < pages.length; i++) {
			if (requestUri.contains(pages[i])) {
				needReplace = true;
				break;
			}
		}
		if (needReplace) {
			requestUri = toPage;
		}else{
			requestUri = requestUri + parameter;
		}
		System.out.println("original requestUri:" + request.getRequestURI()	+ " final requestUri==" + requestUri);
		Cookie cookie = new Cookie("nextPage", requestUri);
		cookie.setPath("/");
		// 设置cookie的有效期为一分钟
		cookie.setMaxAge(60);
		response.addCookie(cookie);

		
		request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
		return false;

	}

}
