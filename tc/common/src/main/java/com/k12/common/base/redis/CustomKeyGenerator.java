package com.k12.common.base.redis;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
/**
 * 项目名称：greenpass-api 
 * 类名称：CustomKeyGenerator 
 * 类描述：Jsk12，处理页面提交的请求。
 * 类描述：redis使用注解时，缓存自定义生成key
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年6月23日 下午5:24:03 
 * 修改人：徐洪杰 
 * 修改时间：2016年6月23日 下午5:24:03 
 * 修改备注：
 * @version 1.0*
 */
public class CustomKeyGenerator implements KeyGenerator{

    @Override
    public Object generate(Object paramObject, Method paramMethod, Object... paramArrayOfObject) {
        StringBuilder sb = new StringBuilder();  
        sb.append(paramObject.getClass().getName()).append("_");  
        sb.append(paramMethod.getName());  
        for (Object obj : paramArrayOfObject) {  
            sb.append("_").append(obj.toString());  
        }  
        return sb.toString(); 
    }

}
