package com.k12.common.constants;

/**
 * 
 * @author yuwen
 * 
 */
public interface Constants {

	// ============================= 公共模块 =============================
	int YES = 1;
	byte YES_BYTE = 1;
	String YES_STR = "1";
	String YES_CN = "是";
	String IMPORT_CODE = "I";
	String EXPORT_CODE = "E";

	int NO = 0;
	byte NO_BYTE = 0;
	String NO_STR = "0";
	String NO_CN = "否";

	String USER_ID = "UID";
	String USER_NAME = "UNAME";
	String USER_ACCOUNT = "UACOUNT";

	String CURRENCY_AMERICA_CODE = "502";
	String CURRENCY_AMERICA_EN = "USD";
	String CURRENCY_EUROPE_CODE = "300";
	String CURRENCY_EUROPE_EN = "EUR";
	String CURRENCY_JAPAN_CODE = "116";
	String CURRENCY_JAPAN_EN = "JPY";
	String CURRENCY_CHINA_CODE = "114";
	String CURRENCY_CHINA_EN = "CNY";

	String COUNTRY_CN_NAME = "中国";
	String COUNTRY_CN_CODE = "142";

	String CRITERIA_ADD_TIME_DESC = "add_time desc";
	
	/** 日期格式配比 */
	String[] DATE_PATTERNS = new String[] { "yyyy",
			"yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd",
			"yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss",
			"yyyy/MM/dd HH:mm:ss" };
	
	/**
	 * bigdecimal默认0使用
	 */
	String ZERO_STR = "0";
	/**
	 * 数据库未删除标识
	 */
	byte DEL_FLAG_N = 0;
	/**
	 * 数据库已删除标识
	 */
	byte DEL_FLAG_Y = 1;

	// ============================各系统业务常量================================ 
	// ===============  归类商品状态，0：商品待审核；1：商品已审核；2：待归类(已支付)；3：要素待审核(已归类)；4：已完成归类；5:已驳回；
	/*** 归类商品状态，0：商品待审核 */
	byte PENDING_AUDIT = 0;
	/*** 归类商品状态，1：商品已审核 */
	byte AUDITED = 1;
	/*** 归类商品状态，2：待归类(已支付) */
	byte WAIT_CLASSIY = 2;
	/*** 归类商品状态，3：要素待审核(已归类) */
	byte FACTOR_AUDIT = 3;
	/*** 归类商品状态，4：已完成归类 */
	byte COMPLETE_CLASSIFY = 4;
	/*** 归类商品状态，5:已驳回 */
	byte REJECT = 5;

	// =============== 全国通关商品状态  =============== 
	/*** 待确认 */
	Integer DEC_WAIT_CONFIRM = 0;
	/*** 已确认 */
	Integer DEC_CONFIRM = 1;
	/*** 已取消 */
	Integer DEC_CANCLE = 2;
	
	// =============== 订单相关非流程状态  ===============
	/*** 未锁账 */
	byte NO_LOCK_ACCOUNT = 1;
	/*** 已锁账 */
	byte YES_LOCK_ACCOUNT = 2;
	/*** 未提成兑现 */
	byte NO_CASHING = 1;
	/*** 已提成兑现 */
	byte YES_CASHING = 2;

	//	=============== 费用相关  ===================
	/*** 订单费用项未支付 */
	byte ORDER_FEE_UNPAID = 0;
	/*** 订单费用项已支付 */
	byte ORDER_FEE_PAID = 1;
	/**	对账单操作 */
	int SOA_OPREATE_ACCOUNT = 1;//对账
	int SOA_OPREATE_CANCEL_ACCOUNT = 2;//取消对账
	int SAO_OPERATE_AUDIT = 3;//审核对账单
	int SAO_OPERATE_CANCEL_AUDIT = 4;//取消审核对账单
	int SAO_OPERATE_VOID = 5;//作废对账单
	/** 开票操作 */
	int IN_OPERATE_SUBMIT = 1;//开票
	int IN_OPERATE_REVIEW = 2;//复核
	int IN_OPERATE_CANCL_REVIEW = 3;//取消复核
	int IN_OPERATE_WRITE_OFF = 4;//销账
	int IN_OPERATE_CANCEL_WRITE_OFF = 5;//取消销账
	int IN_OPERATE_VOID = 6;//作废开票
	/** IM聊天类型用户  */
	String RECORD_CUTOMER_TYPE = "customer";
	/** IM聊天类型客服  */
	String RECORD_SERVICE_TYPE = "service";
	
	// =============== 总公司与子公司所属id  ===================
	/**
	 * 南京总部 = 17
	 */
	int HEADQUARTERS = 17;
	/**
	 * 福建分部 = 18
	 */
	int FUJIAN_BRANCH = 18;
	/**
	 * 河南分部 = 19 
	 */
	int HENAN_BRANCH = 19;
	/**
	 * 湖南分部 = 20
	 */
	int HUNAN_BRANCH = 20;
	/**
	 * 浙江分部 = 21
	 */
	int ZHEJIANG_BRANCH = 21;
	
	/**
	 * 南京宏康报关有限公司 = 26
	 */
	int NANJING_HONGKANG = 26;
	
	/**
	 * 江苏宏康报关有限公司 = 27
	 */
	int JIANGSU_HONGKANG = 27;
	
	//  =============== 币制code  ===================
	String CNY_CODE = "142";
	String USD_CODE = "502";


	// Order_Fee_Type_Status
	/**
	 * 收入
	 */
	Byte fee_type_status_inconme = 1;

	/**
	 * 支出
	 */
	Byte fee_type_status_expenditure = 2;
	// ================ 优品订单发送状态 =================
	/**
	 * 待完善
	 */
	Byte PERFECTED = 0;
	
	/**
	 * 待发送
	 */
	Byte SEND = 1;
	
	/**
	 * 发送成功
	 */
	Byte SEND_SUCCESS = 2;
	
	/**
	 * 发送失败
	 */
	Byte SEND_FAIL = 3;
	
}
