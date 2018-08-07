/** 
 * 文件名：ParamVerifyUtils.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月20日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
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
import org.springframework.util.StringUtils;

/**
 * 项目名称：greenpass <br>
 * 类名称：ParamVerifyUtils <br>
 * 类描述：参数校验 <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br>
 * 创建人：arlen<br>
 * 创建时间：2015年10月20日 下午4:54:40 <br>
 * 修改人：arlen<br>
 * 修改时间：2015年10月20日 下午4:54:40 <br>
 * 修改备注：<br>
 * 
 * @version 1.0<br>
 * @author arlen<br>
 */
public final class ParamVerifyUtils {
	private ParamVerifyUtils() {}

	private final static Logger logger = LoggerFactory.getLogger(ParamVerifyUtils.class);
	
	/**
	 * 校验参数是否正常，如果遇到一个null则返回校验结果 <br>
	 * 
	 * @param data
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @since CodingExample　Ver1.0
	 */
	public static <T> ParamVerifyResult validate(T data) {
		
		if (data == null) {
			logger.error("校验参数，参数为空");
			ParamVerifyResult result = new ParamVerifyResult();
			result.setSuccess(false);
			result.setMessage("参数为空");
			result.setFieldName("");
			return result;
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
				
				NotNull notNull = field.getAnnotation(NotNull.class);
				NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
				
				// 如果值可为空，直接下一步
				if (null == value) {
					if (null != notNull || null != notEmpty) {
						String msg = notNull != null? notNull.value(): notEmpty.value();
						result.setMessage(msg + "不能为空");
						result.setFieldName(field.getName());
						result.setSuccess(false);
						return result;
					} else {
						continue;
					}
				}
				
				// 如果是字符串类型
				if (String.class.equals(field.getType())) {
					if (null != notEmpty && StringUtils.isEmpty(value)) {
						result.setMessage(notEmpty.value() + "不能为空");
						result.setFieldName(field.getName());
						result.setSuccess(false);
						return result;
					}
				}
				// 其他非一般类型
				else if (!TypeUtils.isInstanceOfBaseType(field.getType())) {
					if (value instanceof Collection) {
						Collection<?> collection = (Collection<?>) value;
						if (null != notEmpty && CollectionUtils.isEmpty(collection)) {
							result.setMessage(notEmpty.value() + "不能为空");
							result.setFieldName(field.getName());
							result.setSuccess(false);
							return result;
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
			logger.error("校验参数，参数为空");
			ParamVerifyResult result = new ParamVerifyResult();
			result.setSuccess(false);
			result.setMessage("参数为空");
			result.setFieldName("");
			return result;
		}
		ParamVerifyResult result = new ParamVerifyResult();
		for (Object data : datas) {
			result = validate(data);
			if (!result.isSuccess) {
				return result;
			}
		}
		result.setSuccess(true);
		return result;
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
