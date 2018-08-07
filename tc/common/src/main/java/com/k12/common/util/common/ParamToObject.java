package com.k12.common.util.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class ParamToObject {
	/**
	 * getFormMap(获取传递的所有参数,反射实例化对象，再设置属性值通过泛型回传对象) 
	 * @param clazz
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static <T> T getFormMap(HttpServletRequest request,Class<T> clazz){
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			JSONObject map=(JSONObject) JSONObject.toJSON(t);
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
					if(!StringUtils.isEmpty(as)){
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
						if(nms.toLowerCase().equals("currentNo")){
							pageNow = as;
						}
						if(nms.toLowerCase().equals("pageSize")){
							pageSize = as;
						}
					}
				}
			}
			if(!StringUtils.isEmpty(pageNow)){
				map.put("currentNo", pageNow);
			}
			if(!StringUtils.isEmpty(pageSize)){
				map.put("pageSize", pageSize);
			}
			t=JSONObject.toJavaObject(map, clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  t;
	}
}
