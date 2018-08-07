package com.k12.common.aes;


/**
 * 项目名称：greenpass 
 * 类名称：StaticProperty 
 * 类描述：Jsk12，系统变量。
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2015年10月8日 下午7:30:55 
 * 修改人：徐洪杰 
 * 修改时间：2015年10月8日 下午7:30:55 
 * 修改备注：
 * @version 1.0*
 */
public class StaticProperty {
	//电商注册
	public static final int ENTRUST_REGISTER = 170001;
	// 默认的加载时长
    public static final int OP_LOG_LOAD_COST_DEFAULT = -1;
    // 默认的系统操作人
    public static final int OPERATOR_SYSTEM_ID = 39;
    
    public static final String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
    public static final String token = "pamtest";
    public static final String appId = "wxb11529c136998cb6";
    
    //电商
    public static final int ENTRUST_COMPANY = 1;
    //货代
    public static final int LOGISTICS_COMPANY = 2;
    
}
