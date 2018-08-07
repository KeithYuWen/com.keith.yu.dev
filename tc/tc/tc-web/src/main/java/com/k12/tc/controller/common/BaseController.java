package com.k12.tc.controller.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.k12.common.plugin.PageView;
import com.k12.common.util.common.Common;
import com.k12.common.util.common.StringUtils;
import com.k12.common.util.form.FormMap;


public class BaseController {
	
	public PageView pageView = null;
	
	public PageView getPageView(String pageNow,String pageSize,String orderby) {
		if (Common.isEmpty(pageNow)) {
			pageView = new PageView(1);
		} else {
			pageView = new PageView(Integer.parseInt(pageNow));
		}
		if (Common.isEmpty(pageSize)) {
			pageSize = "10";
		} 
		pageView.setPageSize(Integer.parseInt(pageSize));
		if(Common.isNotEmpty(orderby)){
			pageView.setOrderby(orderby);
		}
		return pageView;
	}
	
	public <T> T toFormMap(T t,String pageNow,String pageSize,String orderby){
		@SuppressWarnings("unchecked")
		FormMap<String, Object> formMap = (FormMap<String, Object>) t;
		formMap.put("paging", getPageView(pageNow, pageSize,orderby));
		return t;
	}
	
	/**
	 * findUserSessionId(获取登录账号的ID) 
	 * @param request
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public  String findUserSessionId(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("userSessionId");
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * getPara(获取页面传递的某一个参数值) 
	 * @param key
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	/**
	 * getParaValues(获取页面传递的某一个数组值) 
	 * @param key
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String[] getParaValues(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameterValues(key);
	}
	/*
	 * @ModelAttribute
	 * 这个注解作用.每执行controllor前都会先执行这个方法
	 * @author lanyuan
	 * Email：mmm333zzz520@163.com
	 * date：2015-4-05
	 * @param request
	 * @throws Exception 
	 * @throws  
	 */
	/*@ModelAttribute
	public void init(HttpServletRequest request){
		String path = Common.BACKGROUND_PATH;
		Object ep = request.getSession().getAttribute("basePath");
		if(ep!=null){
			if(!path.endsWith(ep.toString())){
				Common.BACKGROUND_PATH = "/WEB-INF/jsp/background"+ep;
			}
		}
		
	}*/
	
	// added by renyarong
	public String getUrlWithParam() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String url = request.getScheme() + "://";
		url += request.getHeader("host");
		url += request.getRequestURI();
		if (request.getQueryString() != null) {
			url += "?" + request.getQueryString(); // 参数
		}
		return url;
	}
		
	/**
	 * getFormMap(获取传递的所有参数,反射实例化对象，再设置属性值通过泛型回传对象) 
	 * @param clazz
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFormMap(Class<T> clazz){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			JSONObject map=(JSONObject) JSONObject.toJSON(t);
			String order = "",sort="";
			String pageNow="",pageSize="";
			String[] filename=StringUtils.getFiledName(t);
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(nms.endsWith("[]")){
					String[] as = request.getParameterValues(nms);
					if(as!=null&&as.length!=0&&as.toString()!="[]"){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(nms.toUpperCase().indexOf(mname)+1);
							map.put(nms,as);
						}
					}
				}else{
					String as = request.getParameter(nms);
					if(!Common.isEmpty(as)){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(mname.length()+1);
							map.put(nms, as);
						}else{
							for (int i = 0; i < filename.length; i++) {
								if(nms.toUpperCase().equals(filename[i].toUpperCase())){
									map.put(nms, as);
									break;
								}
							}
						}
						if(nms.toLowerCase().equals("column")){
							order = as;
						}
						if(nms.toLowerCase().equals("sort")){
							sort = as;
						}
						if(nms.toLowerCase().equals("pagenow")){
							pageNow = as;
						}
						if(nms.toLowerCase().equals("pagesize")){
							pageSize = as;
						}
					}
				}
			}
			if(Common.isNotEmpty(pageNow)){
				map.put("pageNow", pageNow);
			}
			if(Common.isNotEmpty(pageSize)){
				map.put("pageSize", pageSize);
			}
			if(!Common.isEmpty(order) && !Common.isEmpty(sort)){
				map.put("orderby", " order by " + StringUtils.toCaseTableColumn(order) + " " + sort);
			}
			t=JSONObject.toJavaObject(map, clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  t;
	}
	/**
	 * getFormMap(获取传递的所有参数,反射实例化对象，再设置属性值通过泛型回传对象) 
	 * @param clazz
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings("unchecked")
	public <T> T getLoadFormMap(HttpServletRequest request,Class<T> clazz){
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			JSONObject map=(JSONObject) JSONObject.toJSON(t);
			String order = "",sort="";
			String pageNow="",pageSize="";
			String[] filename=StringUtils.getFiledName(t);
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(nms.endsWith("[]")){
					String[] as = request.getParameterValues(nms);
					if(as!=null&&as.length!=0&&as.toString()!="[]"){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(nms.toUpperCase().indexOf(mname)+1);
							map.put(nms,as);
						}
					}
				}else{
					String as = request.getParameter(nms);
					if(!Common.isEmpty(as)){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(mname.length()+1);
							map.put(nms, as);
						}else{
							for (int i = 0; i < filename.length; i++) {
								if(nms.toUpperCase().equals(filename[i].toUpperCase())){
									map.put(nms, as);
									break;
								}
							}
						}
						if(nms.toLowerCase().equals("column")){
							order = as;
						}
						if(nms.toLowerCase().equals("sort")){
							sort = as;
						}
						if(nms.toLowerCase().equals("pagenow")){
							pageNow = as;
						}
						if(nms.toLowerCase().equals("pagesize")){
							pageSize = as;
						}
					}
				}
			}
			if(Common.isNotEmpty(pageNow)){
				map.put("pageNow", pageNow);
			}
			if(Common.isNotEmpty(pageSize)){
				map.put("pageSize", pageSize);
			}
			if(!Common.isEmpty(order) && !Common.isEmpty(sort)){
				map.put("orderby", " order by " + StringUtils.toCaseTableColumn(order) + " " + sort);
			}
			t=JSONObject.toJavaObject(map, clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  t;
	}
	/**
	 * getFormMap(获取传递的所有参数,反射实例化对象，再设置属性值通过泛型回传对象) <br>
	 * 获取参数，包含父类字段<br> added by renyarong, 2016.2.19
	 * @param clazz
	 * @return clazz's instance
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @see getFormMap
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFormMapExtends(Class<T> clazz){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			JSONObject map=(JSONObject) JSONObject.toJSON(t);
			String order = "",sort="";
			String pageNow="",pageSize="";
			// modified by renyarong, 2016.2.19
			List<Field> fieldList = new ArrayList<Field>();
			Class<?> superClazz = clazz;
			do {
				fieldList.addAll(Arrays.asList(superClazz.getDeclaredFields()));
			} while (null != (superClazz = superClazz.getSuperclass()));
			
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(nms.endsWith("[]")){
					String[] as = request.getParameterValues(nms);
					if(as!=null&&as.length!=0&&as.toString()!="[]"){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(nms.toUpperCase().indexOf(mname)+1);
							map.put(nms,as);
						}
					}
				}else{
					String as = request.getParameter(nms);
					if(!Common.isEmpty(as)){
						if ("_empty".equals(as)) {
							continue;
						}
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(mname.length()+1);
							map.put(nms, as);
						}else{
							for (int i = 0; i < fieldList.size(); i++) {
								if(nms.toUpperCase().equals(fieldList.get(i).getName().toUpperCase())){
									map.put(nms, as);
									break;
								}
							}
						}
						if(nms.toLowerCase().equals("column")){
							order = as;
						}
						if(nms.toLowerCase().equals("sort")){
							sort = as;
						}
						if(nms.toLowerCase().equals("pagenow")){
							pageNow = as;
						}
						if(nms.toLowerCase().equals("pagesize")){
							pageSize = as;
						}
					}
				}
			}
			if(Common.isNotEmpty(pageNow)){
				map.put("pageNow", pageNow);
			}
			if(Common.isNotEmpty(pageSize)){
				map.put("pageSize", pageSize);
			}
			if(!Common.isEmpty(order) && !Common.isEmpty(sort)){
				map.put("orderby", " order by " + StringUtils.toCaseTableColumn(order) + " " + sort);
			}
			t=JSONObject.toJavaObject(map, clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  t;
	}
	
	/**
	 * findHasHMap(获取传递的所有参数,再设置属性值通过回传Map对象.) 
	 * @param clazz
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings("unchecked")
	public <T> T findHasHMap(Class<T> clazz){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			JSONObject map=(JSONObject) JSONObject.toJSON(t);
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(!"_t".equals(nms)){
					if(nms.endsWith("[]")){
						String[] as = request.getParameterValues(nms);
						if(as!=null&&as.length!=0&&as.toString()!="[]"){
							map.put( nms,as);
						}
					}else{
						String as = request.getParameter(nms);
						if(!Common.isEmpty(as)){
							map.put( nms, as);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	
	public PageView getPageView() {
		return pageView;
	}

	public void setPageView(PageView pageView) {
		this.pageView = pageView;
	}
}