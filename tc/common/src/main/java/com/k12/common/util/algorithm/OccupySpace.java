/**
 * 项目名: oss-common
 * 文件名：OccupySpace.java 
 * 版本信息： V1.0
 * 日期：2016年4月5日 
 * Copyright: Corporation 2016 版权所有
 *
 */
package com.k12.common.util.algorithm;

import java.util.List;
import java.util.Map;

/** 
 * 项目名称：oss-common <br>
 * 类名称：OccupySpace <br>
 * 类描述：<br>
 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2016年4月5日 下午4:46:31 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年4月5日 下午4:46:31 <br>
 * 修改备注：<br>
 * @version 1.0
 * @author arlen
 */
public interface OccupySpace {
	/** 
	 * 装箱资源所占空间
	 * getOccupySpace(这里用一句话描述这个方法的作用) 
	 * @return int
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	int getOccupySpace();
	/**
	 * 装箱资源名，可返回空
	 * getPrdName(这里用一句话描述这个方法的作用) 
	 * @return 资源名
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	String getPrdName();
	/**
	 * 装箱资源的其他限制条件，可为空
	 * getOtherCondition(这里用一句话描述这个方法的作用) 
	 * @return 其他限制条件字符串
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	String getOtherCondition();
	/**
	 * 装箱资源的容量因子。因订单合并的特殊性，箱子的容量并不是由商品数决定，而是得合并商品，是一个动态因子，故此加此方法，方便实现<br>
	 * 比如一个订单中有20个货号，但是税则号只有10个，那么容量因子的大小一般为10，具体见品名合并规则。<br>
	 * eg. Map&lt;税则号, [货号1, 货号2, 货号3]&gt;
	 * @return 容量因子Map
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	Map<Integer, List<String>> getCapacityFactorMap();
}
