/** 
 * 文件名：TypeUtils.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月22日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.util.verify;

import java.math.BigDecimal;
import java.util.Date;

/** 
 * 项目名称：greenpass <br>
 * 类名称：TypeUtils <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2015年10月22日 下午3:33:31 <br>
 * 修改人：arlen<br>
 * 修改时间：2015年10月22日 下午3:33:31 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
public class TypeUtils {

	private TypeUtils() {}
	@SuppressWarnings("rawtypes")
	private final static Class[] baseClazzArr = new Class[] { Boolean.class, Byte.class, Character.class, Integer.class, Long.class, Double.class, Float.class, String.class, Date.class, BigDecimal.class };
	private final static String[] baseTypeArr = new String[] { "boolean", "byte", "char", "int", "long", "double", "float" };

	public static boolean isInstanceOfBaseType(Class<?> clazz) {
		for (Class<?> baseClazz : baseClazzArr) {
			if (clazz.equals(baseClazz)) {
				return true;
			}
		}
		for (String baseType : baseTypeArr) {
			if (baseType.equals(clazz.toString())) {
				return true;
			}
		}
		return false;
	}

}
