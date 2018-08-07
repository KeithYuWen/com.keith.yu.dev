/** 
 * 文件名：Length.java <br>
 * 版本信息： V1.0<br>
 * 日期：2016年2月17日 <br>
 * Copyright: Corporation 2016 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 项目名称：greenpass-api <br>
 * 类名称：Length <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2016年2月17日 下午1:16:17 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年2月17日 下午1:16:17 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
	int value() default 0;
	boolean must() default true;
	String name();
	/**
	 * 只适用于String类型
	 */
	String dflt() default "";
}
