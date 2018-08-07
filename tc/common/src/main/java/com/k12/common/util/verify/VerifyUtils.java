/**
 * 项目名: oss-service
 * 文件名：VerifyUtils.java 
 * 版本信息： V1.0
 * 日期：2016年4月19日 
 * Copyright: Corporation 2016 版权所有
 *
 */
package com.k12.common.util.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.k12.common.annotation.Length;

/** 
 * 项目名称：oss-service <br>
 * 类名称：VerifyUtils <br>
 * 类描述：参数校验，基于注解，兼容之前的NotNull、NotEmpty，后期会去掉；加入新注解Length，后期会加入Regex注解，用正则校验<br>
 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2016年4月19日 下午3:41:42 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年4月19日 下午3:41:42 <br>
 * 修改备注：<br>
 * @version 1.0
 * @author arlen
 */
public class VerifyUtils {

	private VerifyUtils() {}

	private final static Logger logger = LoggerFactory.getLogger(ParamVerifyUtils.class);
	
	private static String getDisplayFiledName(String fieldName, String fieldNameCn) {
		return (fieldNameCn == null || "".equals(fieldNameCn))? fieldName: fieldName+"("+fieldNameCn+")";
	}
	
	/**
	 * 校验参数是否正常，如果遇到一个null则返回校验结果 <br>
	 * 
	 * @param data
	 * @return 校验结果
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @since CodingExample　Ver1.0
	 */
	public static <T> ParamVerifyResult validate(T data) {
		
		if (data == null) {
			return new ParamVerifyResult("参数为空", "", false);
		}
		
		// 默认校验成功
		ParamVerifyResult result = new ParamVerifyResult();
		
		List<Field> fieldList = new ArrayList<Field>();
		Class<?> clazz = data.getClass(), superClazz = clazz;
		do {
			fieldList.addAll(Arrays.asList(superClazz.getDeclaredFields()));
		} while (null != (superClazz = superClazz.getSuperclass()));

		try {
			for (Field field : fieldList) {
				field.setAccessible(true);
				Object value = field.get(data);
				// 获取Length注解属性
				Length lengthAno = field.getAnnotation(Length.class);
				String fieldNameCn = null;
				String defaultValue = null;
				int length = 0;
				boolean must = false;
				if (null != lengthAno) {
					fieldNameCn = lengthAno.name();
					defaultValue = lengthAno.dflt();
					must = lengthAno.must();
					length = lengthAno.value();
				}
				String displayFiledName = getDisplayFiledName(field.getName(), fieldNameCn);
				
				// 兼容之前的NotNull、NotEmpty
				NotNull notNull = field.getAnnotation(NotNull.class);
				NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
				
				// 如果值可为空，直接下一步
				if (null == value) {
					if (null != notNull || null != notEmpty || must) {
						//throw new IllegalArgumentException(displayFiledName + "不能为空");
						return new ParamVerifyResult(displayFiledName + "不能为空", field.getName(), false);
					} else {
						continue;
					}
				}
				
				// 如果是字符串类型
				if (String.class.equals(field.getType())) {
					String valueStr = (String) value;
					if (null != notEmpty) {
						if (null == defaultValue && "".equals(valueStr)) {
							return new ParamVerifyResult(displayFiledName + "不能为空", field.getName(), false);
						} else {
							valueStr = defaultValue;
						}
					}
					// 判断转换后的长度
					if (length > 0 && valueStr.length() > length) {
						return new ParamVerifyResult(displayFiledName + "过长，限定"+length+"字符，目前为"+valueStr.length()+"字符。参数值为["+valueStr+"]", field.getName(), false);
					}
				}
				// 其他非一般类型
				else if (!TypeUtils.isInstanceOfBaseType(field.getType())) {
					if (value instanceof Collection) {
						Collection<?> collection = (Collection<?>) value;
						if (null != notEmpty && CollectionUtils.isEmpty(collection)) {
							return new ParamVerifyResult(displayFiledName + "不能为空", field.getName(), false);
						}
						Iterator<?> it = collection.iterator();
						while (it.hasNext()) {
							Object child = it.next();
							if (null == child)
								continue;
							result = validate(child);
							if (!result.isSuccess()) {
								return result;
							}
						}
					} else {
						result = validate(value);
						if (!result.isSuccess()) {
							return result;
						}
					}
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			logger.error("参数校验异常", e);
		}
		result.setSuccess(true);
		return result;
	}

	public static ParamVerifyResult validate(Object... datas) {
		if (datas == null || datas.length == 0) {
			return new ParamVerifyResult("参数为空", "", false);
		}
		for (Object data : datas) {
			ParamVerifyResult result = validate(data);
			if (!result.isSuccess) {
				return result;
			}
		}
		return new ParamVerifyResult("", "", true);
	}
	
	public static void main(String[] args) {
		// String ss = "{\"Indx\":\"126\",\"OrderSerialNo\":\"ORD15111100000003\",\"OrderNo\":\"FBA33J9FXY20151111002\",\"ActualGrossWeight\":\"\",\"ActualVol\":\"\",\"Tax\":\"\",\"IncPrice\":\"\",\"IncCurrency\":\"142\",\"IncCurrencyCN\":\"人民币\"}";
		// OrderLogisticsGoodsReceivedDto log = JsonUtil.toBean(ss, OrderLogisticsGoodsReceivedDto.class);
		// ParamVerifyResult result = new ParamVerifyResult();
		// result.setFieldName("sss");
		// //result.setMessage("mess");
		//
		// ParamVerifyResult result1 = new ParamVerifyResult();
		// result1.setFieldName("aaa");

		// List<ParamVerifyResult> list = new ArrayList<>();
		// list.add(result1);
		// result.setList(list);
		// System.out.println(JSON.toJSONString(validate(log)));
	}

	public static class ParamVerifyResult {
		@NotNull("校验信息")
		private String message;
		private String fieldName;
		private boolean isSuccess;

		public ParamVerifyResult() {}

		public ParamVerifyResult(String message, String fieldName, boolean isSuccess) {
			this.message = message;
			this.fieldName = fieldName;
			this.isSuccess = isSuccess;
		}

		public boolean isSuccess() {
			return isSuccess;
		}

		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}

}
