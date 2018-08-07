package com.k12.common.constants;

/**
 * 错误操作日志记录
 * 项目名称：oss-common <br>
 * 类名称：ErrorOperTypeEnum <br>
 * 类描述：<br>
 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2016年4月8日 下午2:12:53 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年4月8日 下午2:12:53 <br>
 * 修改备注：<br>
 * @version 1.0
 * @author arlen
 */
public enum ErrorOperTypeEnum {

	/**
	 * 1 同步订单状态
	 */
	SYNC_ORDER_PAY((byte)1, "同步订单状态"),
	// 之前的谁加了值不往枚举里加？？！！
	/**
	 * 10 同步订单状态
	 */
	CATCH_CUSTOMS_501((byte)10, "抓取海关501报文");

	private byte code;
	private String name;

	private ErrorOperTypeEnum(byte code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public byte getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}
}
