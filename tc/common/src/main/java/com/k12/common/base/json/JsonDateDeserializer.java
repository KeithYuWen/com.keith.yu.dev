/** 
 * 文件名：JsonDateDeserializer.java <br>
 * 版本信息： V1.0<br>
 * 日期：2016年2月27日 <br>
 * Copyright: Corporation 2016 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.base.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/** 
 * 项目名称：greenpass-api <br>
 * 类名称：JsonDateDeserializer <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2016年2月27日 下午7:51:01 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年2月27日 下午7:51:01 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
public class JsonDateDeserializer  extends JsonDeserializer<Date>{

	private Logger logger = LoggerFactory.getLogger(JsonDateDeserializer.class);
	
	@Override
	public Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
			throws IOException, JsonProcessingException {
		Date date = null;
		String value = paramJsonParser.getText();
        try {
        	if (StringUtils.isEmpty(value)) {
        		return null;
        	}
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(value);  
        } catch (ParseException e) {
        	logger.error("转换时间出错，formatter=yyyy/MM/dd HH:mm:ss，value=" + value, e);
        }
		return date;
	}
}
