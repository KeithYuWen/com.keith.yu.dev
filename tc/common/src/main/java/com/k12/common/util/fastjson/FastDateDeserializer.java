/** 
 * 文件名：FastDateDeserializer.java <br>
 * 版本信息： V1.0<br>
 * 日期：2016年3月8日 <br>
 * Copyright: Corporation 2016 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.util.fastjson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;

/** 
 * 项目名称：oss-common <br>
 * 类名称：FastDateDeserializer <br>
 * 类描述：自定义日期反序列化，用SimpleDateFormat <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2016年3月8日 下午4:31:29 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年3月8日 下午4:31:29 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
public class FastDateDeserializer extends DateDeserializer {

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {

        if (val == null) {
            return null;
        }

        if (val instanceof java.util.Date) {
            return (T) val;
        } else if (val instanceof Number) {
            return (T) new java.util.Date(((Number) val).longValue());
        } else if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }

            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    
                    if (clazz == Calendar.class) {
                        return (T) calendar;
                    }
                    
                    return (T) calendar.getTime();
                }
            } finally {
                dateLexer.close();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(parser.getDateFomartPattern());
            try {
                return (T) dateFormat.parse(strVal);
            } catch (ParseException e) {
                // skip
            	e.printStackTrace();
            }

            long longVal = Long.parseLong(strVal);
            return (T) new java.util.Date(longVal);
        }
        throw new JSONException("parse error");
	}

}
