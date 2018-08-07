package com.k12.common.init;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.baomidou.kisso.SSOConfig;



/**
 * 项目名称：oss-common 
 * 类名称：SystemParamInit 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年1月25日 下午2:36:10 
 * 修改人：徐洪杰 
 * 修改时间：2016年1月25日 下午2:36:10 
 * 修改备注：
 * @version 1.0*
 */
@Lazy(value=false)
@Component
public class SystemParamInit {
	
	
	/**
	 * redis缓存
	 */
	private static String redisHost;
	private static Integer redisPort;
	private static String redisPass;
	private static Integer redisTimeout;
	private static Integer redisPoolMaxIdle;
	private static Integer redisPoolMaxTotal;
	private static Integer redisPoolMaxWaitMillis;
	private static boolean redisPoolTestOnBorrow;
	private static boolean redisPoolTestOnReturn;
	//接口加密
    private static String encodingAesKey;
    private static String token;
    private static String appId;
    //sso加解密
    private static String secretkey;
    
    // FTP 地址信息
    // 主机地址
    private static String ftpHost;
    // 端口号
    private static int ftpPort;
    // 用户名
    private static String ftpUserName;
    // 密码
    private static String ftpPassword;
    // 服务器目录路径
    private static String dirPath;
    
    //邮件
    private static String serverHost;
    private static String serverPort;
    private static String username;
    private static String password;
    private static String toAddress;
    private static String fromAddress;
    
    
    public static String getServerHost() {
		return serverHost;
	}
    @Value("${mail.host}")
	public void setServerHost(String serverHost) {
		SystemParamInit.serverHost = serverHost;
	}
	public static String getServerPort() {
		return serverPort;
	}
	@Value("${mail.port}")
	public void setServerPort(String serverPort) {
		SystemParamInit.serverPort = serverPort;
	}
	public static String getUsername() {
		return username;
	}
	@Value("${mail.username}")
	public void setUsername(String username) {
		SystemParamInit.username = username;
	}
	public static String getPassword() {
		return password;
	}
	@Value("${mail.password}")
	public void setPassword(String password) {
		SystemParamInit.password = password;
	}
	public static String getToAddress() {
		return toAddress;
	}
	@Value("${mail.toAddress}")
	public void setToAddress(String toAddress) {
		SystemParamInit.toAddress = toAddress;
	}
	public static String getFromAddress() {
		return fromAddress;
	}
	@Value("${mail.fromAddress}")
	public void setFromAddress(String fromAddress) {
		SystemParamInit.fromAddress = fromAddress;
	}




	//新地址
    private static String baseHostDomain;
	private static String ucHostDomain;
    private static String ossHostDomain;
    private static String lineHostDomain;
    private static String productHostDomain;
    private static String gpHostDomain;
    private static String financeHostDomain;
    private static String spHostDomain;
    private static String orderHostDomain;
    //cms的图片目录
    private static String cmsFptPictureCatalog;
    
    //获取当前年份
    private static Calendar a=Calendar.getInstance();
    public static String getCmsFptPictureCatalog() {
    	if(cmsFptPictureCatalog.equals("cms")){
    		return cmsFptPictureCatalog + "/" + a.get(Calendar.YEAR);
    	}else{
    		return cmsFptPictureCatalog;
    	}
	}
    @Value("${cmsFptPictureCatalog}")
	public void setCmsFptPictureCatalog(String cmsFptPictureCatalog) {
		SystemParamInit.cmsFptPictureCatalog = cmsFptPictureCatalog;
	}
    
	public static String getSecretkey() {
		return secretkey;
	}
	@Value("${sso.secretkey}")
	public void setSecretkey(String secretkey) {
		SystemParamInit.secretkey = secretkey;
		SSOConfig.getInstance().setSecretkey(secretkey);
	}
	public static String getBaseHostDomain() {
		return baseHostDomain;
	}
    @Value("${base.host.domain}")
	public void setBaseHostDomain(String baseHostDomain) {
		SystemParamInit.baseHostDomain = baseHostDomain;
	}
	public static String getUcHostDomain() {
		return ucHostDomain;
	}
	@Value("${uc.host.domain}")
	public void setUcHostDomain(String ucHostDomain) {
		SystemParamInit.ucHostDomain = ucHostDomain;
	}
	public static String getOssHostDomain() {
		return ossHostDomain;
	}
	@Value("${oss.host.domain}")
	public void setOssHostDomain(String ossHostDomain) {
		SystemParamInit.ossHostDomain = ossHostDomain;
	}
	public static String getLineHostDomain() {
		return lineHostDomain;
	}
	@Value("${line.host.domain}")
	public void setLineHostDomain(String lineHostDomain) {
		SystemParamInit.lineHostDomain = lineHostDomain;
	}
	public static String getProductHostDomain() {
		return productHostDomain;
	}
	@Value("${product.host.domain}")
	public void setProductHostDomain(String productHostDomain) {
		SystemParamInit.productHostDomain = productHostDomain;
	}
	public static String getGpHostDomain() {
		return gpHostDomain;
	}
	@Value("${gp.host.domain}")
	public void setGpHostDomain(String gpHostDomain) {
		SystemParamInit.gpHostDomain = gpHostDomain;
	}
	public static String getFinanceHostDomain() {
		return financeHostDomain;
	}
	@Value("${finance.host.domain}")
	public void setFinanceHostDomain(String financeHostDomain) {
		SystemParamInit.financeHostDomain = financeHostDomain;
	}
	public static String getSpHostDomain() {
		return spHostDomain;
	}
	@Value("${sp.host.domain}")
	public void setSpHostDomain(String spHostDomain) {
		SystemParamInit.spHostDomain = spHostDomain;
	}
    public static String getOrderHostDomain() {
		return orderHostDomain;
	}
    @Value("${order.host.domain}")
	public void setOrderHostDomain(String orderHostDomain) {
		SystemParamInit.orderHostDomain = orderHostDomain;
	}




	// 绿道首页地址，退出登陆时需要用
    private static String gpHost;
    private static String lineHost;
    
    private static String ucHost;
    private static String baseHost;
    private static String ossHost;
    private static String spHost;
    
    private static String attachmentOssBk;

    private static String financeHost;
   
    //图片地址端口
    //访问文件地址
   private static String cmsDownloadPicUrl;
   
   //同步苏州SP系统用户账号接口地址
   private static String ocUserInfoImportUrl;
   
   //同步苏州SP企业信息接口地址
   private static String ocCompanyInfoImportUrl;
   
   private static String ocGetCompanyImageUrl;
   
   //同步苏州CMCA系统用户账号接口地址
   private static String cmcaUserInfoImportUrl;
   
   //同步苏州CMCA系统企业信息接口地址
   private static String cmcaCompanyInfoImportUrl;
   
   //同步CMI用户账号接口地址
   private static String ocUserInfoImportCMIUrl;
   
   private static String getOrderListUrl;
   
   private static String getOrderDetailUrl;
   
   private static String getOrderCancelUrl;
   
   private static String dataStatisticsUrl;
   
   private static String logisticsStatusTrackUrl;
   
   private static String getOrderList9610Url;//9610订单列表接口
   
   private static String getOrderReportUrl;//报表接口
   
	
	public static String getFinanceHost() {
		   return financeHost;
	}
	   public static String getSpHost() {
		return spHost;
	}
	   @Value("${sp.host}")
	public void setSpHost(String spHost) {
		SystemParamInit.spHost = spHost;
	}
	@Value("${finance.host}")
   public void setFinanceHost(String financeHost) {
	   SystemParamInit.financeHost = financeHost;
   }

   public static String getCmsDownloadPicUrl() {
		return cmsDownloadPicUrl;
	}
   @Value("${cmsDownloadPicUrl}")
	public void setCmsDownloadPicUrl(String downloadPicUrl) {
	   SystemParamInit.cmsDownloadPicUrl = downloadPicUrl;
	}

    
    
    public static String getOssHost() {
		return ossHost;
	}

    @Value("${oss.host}")
	public void setOssHost(String ossHost) {
		SystemParamInit.ossHost = ossHost;
	}

	public static String getBaseHost() {
		return baseHost;
	}

    @Value("${base.host}")
	public void setBaseHost(String baseHost) {
		SystemParamInit.baseHost = baseHost;
	}
    
    public static String getLineHost() {
		return lineHost;
	}
    @Value("${line.host}")
	public void setLineHost(String lineHost) {
		SystemParamInit.lineHost = lineHost;
	}

	public static String getUcHost() {
		return ucHost;
	}
    
    @Value("${uc.host}")
	public void setUcHost(String ucHost) {
		SystemParamInit.ucHost = ucHost;
	}

    private static String localFileTempPath;
    
    private static String downloadPicUrl;
    
    private static String downloadPicHttpUrl;
    
	public static String getDownloadPicUrl() {
		return downloadPicUrl;
	}
	
    @Value("${downloadPicUrl}")
	public void setDownloadPicUrl(String downloadPicUrl) {
		SystemParamInit.downloadPicUrl = downloadPicUrl;
	}

    public static String getLocalFileTempPath() {
        return localFileTempPath;
    }
    @Value("${localFileTempPath}")
    public void setLocalFileTempPath(String localFileTempPath) {
        SystemParamInit.localFileTempPath = localFileTempPath;
    }
    
    public static String getDownloadPicHttpUrl() {
		return downloadPicHttpUrl;
	}
    @Value("${downloadPicHttpUrl}")
	public void setDownloadPicHttpUrl(String downloadPicHttpUrl) {
		SystemParamInit.downloadPicHttpUrl = downloadPicHttpUrl;
	}
    
	public static String getGpHost() {
		return gpHost;
	}
    @Value("${gp.host}")
	public void setGpHost(String gpHost) {
		SystemParamInit.gpHost = gpHost;
	}
    public static String getFtpHost() {
		return ftpHost;
	}
	@Value("${uploadFileHost}")
	public void setFtpHost(String ftpHost) {
		SystemParamInit.ftpHost = ftpHost;
	}
	public static int getFtpPort() {
		return ftpPort;
	}
	@Value("${uploadFileFtpPort}")
	public void setFtpPort(int ftpPort) {
		SystemParamInit.ftpPort = ftpPort;
	}
	public static String getFtpUserName() {
		return ftpUserName;
	}
	@Value("${uploadFileUserName}")
	public void setFtpUserName(String ftpUserName) {
		SystemParamInit.ftpUserName = ftpUserName;
	}
	public static String getFtpPassword() {
		return ftpPassword;
	}
	@Value("${uploadFilePassword}")
	public void setFtpPassword(String ftpPassword) {
		SystemParamInit.ftpPassword = ftpPassword;
	}
	public static String getDirPath() {
		return dirPath;
	}
	@Value("${dirPath}")
	public void setDirPath(String dirPath) {
		SystemParamInit.dirPath = dirPath;
	}

	public static String getRedisHost() {
		return redisHost;
	}
	@Value("${redis.host}")
	public void setRedisHost(String redisHost) {
		SystemParamInit.redisHost = redisHost;
	}
	public static Integer getRedisPort() {
		return redisPort;
	}
	@Value("${redis.port}")
	public void setRedisPort(Integer redisPort) {
		SystemParamInit.redisPort = redisPort;
	}
	public static String getRedisPass() {
		return redisPass;
	}
	@Value("${redis.pass}")
	public void setRedisPass(String redisPass) {
		SystemParamInit.redisPass = redisPass;
	}
	public static Integer getRedisTimeout() {
		return redisTimeout;
	}
	@Value("${redis.timeout}")
	public void setRedisTimeout(Integer redisTimeout) {
		SystemParamInit.redisTimeout = redisTimeout;
	}
	public static Integer getRedisPoolMaxIdle() {
		return redisPoolMaxIdle;
	}
	@Value("${redis.pool.maxIdle}")
	public void setRedisPoolMaxIdle(Integer redisPoolMaxIdle) {
		SystemParamInit.redisPoolMaxIdle = redisPoolMaxIdle;
	}
	public static Integer getRedisPoolMaxTotal() {
		return redisPoolMaxTotal;
	}
	@Value("${redis.pool.maxTotal}")
	public void setRedisPoolMaxTotal(Integer redisPoolMaxTotal) {
		SystemParamInit.redisPoolMaxTotal = redisPoolMaxTotal;
	}
	public static Integer getRedisPoolMaxWaitMillis() {
		return redisPoolMaxWaitMillis;
	}
	@Value("${redis.pool.maxWaitMillis}")
	public void setRedisPoolMaxWaitMillis(Integer redisPoolMaxWaitMillis) {
		SystemParamInit.redisPoolMaxWaitMillis = redisPoolMaxWaitMillis;
	}
	public static boolean getRedisPoolTestOnBorrow() {
		return redisPoolTestOnBorrow;
	}
	@Value("${redis.pool.testOnBorrow}")
	public void setRedisPoolTestOnBorrow(boolean redisPoolTestOnBorrow) {
		SystemParamInit.redisPoolTestOnBorrow = redisPoolTestOnBorrow;
	}
	public static boolean getRedisPoolTestOnReturn() {
		return redisPoolTestOnReturn;
	}
	@Value("${redis.pool.testOnReturn}")
	public void setRedisPoolTestOnReturn(boolean redisPoolTestOnReturn) {
		SystemParamInit.redisPoolTestOnReturn = redisPoolTestOnReturn;
	}
	@Value("${encodingAesKey}")
	public  void setEncodingAesKey(String encodingAesKey) {
		SystemParamInit.encodingAesKey = encodingAesKey;
	}
	@Value("${token}")
	public  void setToken(String token) {
		SystemParamInit.token = token;
	}
	@Value("${appId}")
	public  void setAppId(String appId) {
		SystemParamInit.appId = appId;
	}
	@Value("${enterpriseInformationSyncUrl}")
	public static String getEncodingAesKey() {
		return encodingAesKey;
	}
	public static String getToken() {
		return token;
	}
	public static String getAppId() {
		return appId;
	}
	
	public static String getAttachmentOssBk() {
		return attachmentOssBk;
	}
	@Value("${entrust.attachment.oss.bk}")
	public void setAttachmentOssBk(String attachmentOssBk) {
		SystemParamInit.attachmentOssBk = attachmentOssBk;
	}
	
	public static String getOcUserInfoImportUrl() {
		return ocUserInfoImportUrl;
	}
	@Value("${ocUserInfoImportUrl}")
	public void setOcUserInfoImportUrl(String ocUserInfoImportUrl) {
		SystemParamInit.ocUserInfoImportUrl = ocUserInfoImportUrl;
	}
	public static String getOcCompanyInfoImportUrl() {
		return ocCompanyInfoImportUrl;
	}
	@Value("${ocCompanyInfoImportUrl}")
	public void setOcCompanyInfoImportUrl(String ocCompanyInfoImportUrl) {
		SystemParamInit.ocCompanyInfoImportUrl = ocCompanyInfoImportUrl;
	}
	public static String getOcGetCompanyImageUrl() {
		return ocGetCompanyImageUrl;
	}
	@Value("${ocGetCompanyImageUrl}")
	public void setOcGetCompanyImageUrl(String ocGetCompanyImageUrl) {
		SystemParamInit.ocGetCompanyImageUrl = ocGetCompanyImageUrl;
	}
	public static String getGetOrderList9610Url() {
		return getOrderList9610Url;
	}
	@Value("${getOrderList9610Url}")
	public void setReturnTelegramUrl(String getOrderList9610Url) {
		SystemParamInit.getOrderList9610Url = getOrderList9610Url;
	}
	
	public static String getGetOrderListUrl() {
		return getOrderListUrl;
	}
	@Value("${getOrderListUrl}")
	public void setGetOrderListUrl(String getOrderListUrl) {
		SystemParamInit.getOrderListUrl = getOrderListUrl;
	}
	
	public static String getGetOrderReportUrl(){
		return getOrderReportUrl;
	}
	@Value("${getOrderReportUrl}")
	public void setGetOrderReportUrl(String getOrderReportUrl) {
		SystemParamInit.getOrderReportUrl = getOrderReportUrl;
	}
	
	 public static String getGetOrderCancelUrl() {
			return getOrderCancelUrl;
	}
	 @Value("${getOrderCancelUrl}")
	public void setGetOrderCancelUrl(String getOrderCancelUrl) {
			SystemParamInit.getOrderCancelUrl = getOrderCancelUrl;
	}
	
	public static String getGetOrderDetailUrl() {
		return getOrderDetailUrl;
	}
	@Value("${getOrderDetailUrl}")
	public void setGetOrderDetailUrl(String getOrderDetailUrl) {
		SystemParamInit.getOrderDetailUrl = getOrderDetailUrl;
	}
	
	public static String getDataStatisticsUrl() {
		return dataStatisticsUrl;
	}
	@Value("${dataStatisticsUrl}")
	public void setDataStatisticsUrl(String dataStatisticsUrl) {
		SystemParamInit.dataStatisticsUrl = dataStatisticsUrl;
	}
	
	public static String getLogisticsStatusTrackUrl() {
		return logisticsStatusTrackUrl;
	}
	@Value("${logisticsStatusTrackUrl}")
	public void setLogisticsStatusTrackUrl(String logisticsStatusTrackUrl) {
		SystemParamInit.logisticsStatusTrackUrl = logisticsStatusTrackUrl;
	}
	
	public static String getOcUserInfoImportCMIUrl() {
		return ocUserInfoImportCMIUrl;
	}
	@Value("${ocUserInfoImportCMIUrl}")
	public void setOcUserInfoImportCMIUrl(String ocUserInfoImportCMIUrl) {
		SystemParamInit.ocUserInfoImportCMIUrl = ocUserInfoImportCMIUrl;
	}
	
	public static String getCmcaUserInfoImportUrl() {
		return cmcaUserInfoImportUrl;
	}
	@Value("${cmcaUserInfoImportUrl}")
	public void setCmcaUserInfoImportUrl(String cmcaUserInfoImportUrl) {
		SystemParamInit.cmcaUserInfoImportUrl = cmcaUserInfoImportUrl;
	}
	
	public static String getCmcaCompanyInfoImportUrl() {
		return cmcaCompanyInfoImportUrl;
	}
	@Value("${cmcaCompanyInfoImportUrl}")
	public void setCmcaCompanyInfoImportUrl(String cmcaCompanyInfoImportUrl) {
		SystemParamInit.cmcaCompanyInfoImportUrl = cmcaCompanyInfoImportUrl;
	}
	
	
	
/*
    //审核结果同步会员中心
    private static String checkResultSyncToUcUrl;
    //客户信息修改同步kyd地址
    private static String userUpdateToUcUrl;
    
    //商品合规同步给跨易达
    private static String classifyPrdGpUrl;
    
    //商品合规同步到CBCA
    private static String classifyPrdCbcaUrl;
    
    //商品备案数据发送给海关
    private static String sendPrdToCustomsUrl;
    
    //商品备案数据发送给跨易达
    private static String sendPrdToGpUrl;
    
    //外运商品备案数据发送给跨易达
    private static String sendWyPrdToGpUrl;
    
    //商品取消审核发送给跨易达
    private static String cancelCheckToGpUrl;
    
    // 图片服务器外网地址
    private static String localPicUrl;
    private static String downloadPicUrl;
    private static String cmsDownloadPicUrl;
    private static String downloadPicUrlToOffline;
    // 注册同步
    private static String registerSyncUrl;
    //查询已注册用户数
    private static String getRegiserUserCountUrl;
    
    //虚拟账户充值
    private static String walletRechangeToPay;
    private static String orderPayCallback;
    
    //发送风控确认给greenpass
    private static String sendControlUrl;
    // 修改后委托附件上传地址
    private static String attachmentOss;
    // 修改后委托附件上传地址
    private static String attachmentOssBk;
    //目的国风控信息pdf上传地址
    private static String attachmentOssFk;
    // 委托附件下载地址
    private static String attachmentSp;
    //外运海关报关地址
    private static String waiyunRecordUrl;
    
    //友链
    private static String cmsSaveFriendLinkUrl;
    private static String cmsFindFriendLinkUrl;
    private static String cmsGetFriendLinkUrl;
    private static String cmsUpdateFriendLinkUrl;
    
    //贴士
    private static String cmsSaveTipsUrl;
    private static String cmsFindTipsUrl;
    private static String cmsGetTipsUrl;
    private static String cmsUpdateTipsUrl;
    
    //供应商代码同步给UC  logisticsCompany 
    private static String supplierCodeUcUrl;
    
    //供应商代码同步给跨易达  logisticsCompany 
    private static String supplierCodeKydUrl;
    
	public static String getWaiyunRecordUrl()
	{
		return waiyunRecordUrl;
	}
	@Value("${waiyunRecordUrl}")
	public  void setWaiyunRecordUrl(String waiyunRecordUrl)
	{
		SystemParamInit.waiyunRecordUrl = waiyunRecordUrl;
	}

	public static String getSendControlUrl()
	{
		return sendControlUrl;
	}
	
	@Value("${sendControlUrl}")
	public  void setSendControlUrl(String sendControlUrl)
	{
		SystemParamInit.sendControlUrl = sendControlUrl;
	}
	
    // FTP 地址信息
    // 主机地址
    private static String ftpHost;
    // 端口号
    private static int ftpPort;
    // 用户名
    private static String ftpUserName;
    // 密码
    private static String ftpPassword;
    // 服务器目录路径
    private static String dirPath;
    //对接osm
    //sftp服务器地址
    private static String osmFileHost;
    //sftp服务器用户名
    private static String osmFileUserName;
    //sftp服务器密码
    private static String osmFilePassword;
    //图片服务器
    private static String osmUploadDirPath;
    // sftp下载跟踪信息文件路径
    private static String osmDownloadTrackingFilePath;
    // sftp下载后存放路径
    private static String osmDownloadedSavePath;
    
    // solr的地址
//    private static String solrURL;
    
    //新闻保存地址
    private static String cmsSaveNewsUrl;
    //新闻列表查询地址
    private static String cmsFindNewsUrl;
    //新闻查询地址
    private static String cmsGetNewsUrl;
    //编辑新闻
    private static String cmsUpdateNewsUrl;
    //广告保存地址
    private static String cmsSaveAdvertisementUrl;
    //广告列表查询地址
    private static String cmsFindAdvertisementUrl;
    //广告查询地址
    private static String cmsGetAdvertisementUrl;
    //编辑广告
    private static String cmsUpdateAdvertisementUrl;
    
    private static String cmsFindAdvertisementTypeUrl;
    
    private static String cmsupdateAdvertisementTypeUrl;
    
    private static String cmsUpdateAdvertisementSpaceUrl;
    
    private static String cmsupdateRecommendTypeUrl;
    
    //订单修改传苏州
    private static String ossOrderModify;
    
    public static String getOssOrderModify() {
		return ossOrderModify;
	}
    @Value("${ossOrderModify}")
	public void setOssOrderModify(String ossOrderModify) {
		SystemParamInit.ossOrderModify = ossOrderModify;
	}
	public static String getCmsUpdateAdvertisementSpaceUrl() {
		return cmsUpdateAdvertisementSpaceUrl;
	}
    @Value("${cmsUpdateAdvertisementSpaceUrl}")
	public void setCmsUpdateAdvertisementSpaceUrl(
			String cmsUpdateAdvertisementSpaceUrl) {
		SystemParamInit.cmsUpdateAdvertisementSpaceUrl = cmsUpdateAdvertisementSpaceUrl;
	}

	//图片地址端口
    private static String downloadPicHttpUrl;
    
    public static String getDownloadPicHttpUrl() {
		return downloadPicHttpUrl;
	}
    
    @Value("${downloadPicHttpUrl}")
	public void setDownloadPicHttpUrl(String downloadPicHttpUrl) {
		SystemParamInit.downloadPicHttpUrl = downloadPicHttpUrl;
	}

	public static String getCmsFindAdvertisementTypeUrl() {
		return cmsFindAdvertisementTypeUrl;
	}
    
    @Value("${cmsFindAdvertisementTypeUrl}")
	public void setCmsFindAdvertisementTypeUrl(
			String cmsFindAdvertisementTypeUrl) {
		SystemParamInit.cmsFindAdvertisementTypeUrl = cmsFindAdvertisementTypeUrl;
	}

	public static String getCmsupdateAdvertisementTypeUrl() {
		return cmsupdateAdvertisementTypeUrl;
	}
	
	@Value("${cmsupdateAdvertisementTypeUrl}")
	public void setCmsupdateAdvertisementTypeUrl(
			String cmsupdateAdvertisementTypeUrl) {
		SystemParamInit.cmsupdateAdvertisementTypeUrl = cmsupdateAdvertisementTypeUrl;
	}

	public static String getCmsSaveAdvertisementUrl() {
		return cmsSaveAdvertisementUrl;
	}
    
    @Value("${cmsSaveAdvertisementUrl}")
	public void setCmsSaveAdvertisementUrl(String cmsSaveAdvertisementUrl) {
		SystemParamInit.cmsSaveAdvertisementUrl = cmsSaveAdvertisementUrl;
	}

	public static String getCmsFindAdvertisementUrl() {
		return cmsFindAdvertisementUrl;
	}
	
	@Value("${cmsFindAdvertisementUrl}")
	public void setCmsFindAdvertisementUrl(String cmsFindAdvertisementUrl) {
		SystemParamInit.cmsFindAdvertisementUrl = cmsFindAdvertisementUrl;
	}

	public static String getCmsGetAdvertisementUrl() {
		return cmsGetAdvertisementUrl;
	}
	
	@Value("${cmsGetAdvertisementUrl}")
	public void setCmsGetAdvertisementUrl(String cmsGetAdvertisementUrl) {
		SystemParamInit.cmsGetAdvertisementUrl = cmsGetAdvertisementUrl;
	}

	public static String getCmsUpdateAdvertisementUrl() {
		return cmsUpdateAdvertisementUrl;
	}
	
	@Value("${cmsUpdateAdvertisementUrl}")
	public void setCmsUpdateAdvertisementUrl(String cmsUpdateAdvertisementUrl) {
		SystemParamInit.cmsUpdateAdvertisementUrl = cmsUpdateAdvertisementUrl;
	}

	public static String getCmsUpdateNewsUrl() {
		return cmsUpdateNewsUrl;
	}
    
    @Value("${cmsUpdateNewsUrl}")
	public void setCmsUpdateNewsUrl(String cmsUpdateNewsUrl) {
		SystemParamInit.cmsUpdateNewsUrl = cmsUpdateNewsUrl;
	}

	public static String getCmsGetNewsUrl() {
		return cmsGetNewsUrl;
	}
    
    @Value("${cmsGetNewsUrl}")
	public void setCmsGetNewsUrl(String cmsGetNewsUrl) {
		SystemParamInit.cmsGetNewsUrl = cmsGetNewsUrl;
	}

	public static String getCmsFindNewsUrl() {
		return cmsFindNewsUrl;
	}
	
	@Value("${cmsFindNewsUrl}")
	public void setCmsFindNewsUrl(String cmsFindNewsUrl) {
		SystemParamInit.cmsFindNewsUrl = cmsFindNewsUrl;
	}

	public static String getCmsSaveNewsUrl() {
		return cmsSaveNewsUrl;
	}
	
	@Value("${cmsSaveNewsUrl}")
	public void setCmsSaveNewsUrl(String cmsSaveNewsUrl) {
		SystemParamInit.cmsSaveNewsUrl = cmsSaveNewsUrl;
	}

	public static String getDownloadPicUrlToOffline() {
		return downloadPicUrlToOffline;
	}
	@Value("${downloadPicUrlToOffline}")
	public void setDownloadPicUrlToOffline(String downloadPicUrlToOffline) {
		SystemParamInit.downloadPicUrlToOffline = downloadPicUrlToOffline;
	}

	public static String getOrderPayCallback() {
		return orderPayCallback;
	}
	@Value("${orderPayCallback}")
	public void setOrderPayCallback(String orderPayCallback) {
		SystemParamInit.orderPayCallback = orderPayCallback;
	}

	
	
	public static String getAttachmentOss() {
		return attachmentOss;
	}
	@Value("${entrust.attachment.oss}")
	public void setAttachmentOss(String attachmentOss) {
		SystemParamInit.attachmentOss = attachmentOss;
	}
	
	
	
	public static String getAttachmentOssFk() {
		return attachmentOssFk;
	}
	@Value("${entrust.attachment.oss.fk}")
	public void setAttachmentOssFk(String attachmentOssFk) {
		SystemParamInit.attachmentOssFk = attachmentOssFk;
	}

	public static String getAttachmentSp() {
		return attachmentSp;
	}
	@Value("${entrust.attachment.sp}")
	public void setAttachmentSp(String attachmentSp) {
		SystemParamInit.attachmentSp = attachmentSp;
	}
	public static String getWalletRechangeToPay() {
		return walletRechangeToPay;
	}
	@Value("${walletRechangeToPay}")
	public  void setWalletRechangeToPay(String walletRechangeToPay) {
		SystemParamInit.walletRechangeToPay = walletRechangeToPay;
	}
	public static String getClassifyPrdGpUrl(){
		return classifyPrdGpUrl;
	}
	@Value("${classifyPrdGpUrl}")
	public  void setClassifyPrdGpUrl(String classifyPrdGpUrl)
	{
		SystemParamInit.classifyPrdGpUrl = classifyPrdGpUrl;
	}
	public static String getClassifyPrdCbcaUrl()
	{
		return classifyPrdCbcaUrl;
	}
	@Value("${classifyPrdCbcaUrl}")
	public  void setClassifyPrdCbcaUrl(String classifyPrdCbcaUrl)
	{
		SystemParamInit.classifyPrdCbcaUrl = classifyPrdCbcaUrl;
	}
	public static String getSendPrdToCustomsUrl()
	{
		return sendPrdToCustomsUrl;
	}
	@Value("${sendPrdToCustomsUrl}")
	public  void setSendPrdToCustomsUrl(String sendPrdToCustomsUrl)
	{
		SystemParamInit.sendPrdToCustomsUrl = sendPrdToCustomsUrl;
	}
	public static String getSendPrdToGpUrl()
	{
		return sendPrdToGpUrl;
	}
	
	public static String getSendWyPrdToGpUrl()
	{
		return sendWyPrdToGpUrl;
	}
	@Value("${sendWyPrdToGpUrl}")
	public  void setSendWyPrdToGpUrl(String sendWyPrdToGpUrl)
	{
		SystemParamInit.sendWyPrdToGpUrl = sendWyPrdToGpUrl;
	}
	@Value("${sendPrdToGpUrl}")
	public  void setSendPrdToGpUrl(String sendPrdToGpUrl)
	{
		SystemParamInit.sendPrdToGpUrl = sendPrdToGpUrl;
	}
    public static String getCancelCheckToGpUrl()
	{
		return cancelCheckToGpUrl;
	}
    @Value("${cancelCheckToGpUrl}")
	public  void setCancelCheckToGpUrl(String cancelCheckToGpUrl)
	{
		SystemParamInit.cancelCheckToGpUrl = cancelCheckToGpUrl;
	}




	//订单箱车信息同步跨易达地址
    private static String orderCaseSyncKydUrl;
    //订单箱车信息同步跨易达地址
    private static String orderSpaceSyncKydUrl;
    //订单毛重体积保费同步跨易达地址
    private static String orderGoodsReceivedSyncKydUrl;
    //订单费用项同步跨易达地址
    private static String orderExpenseItemSyncKydUrl;
    //主单信息传sp
    private static String orderMainTransToSpUrl;
    
    
    *//**
     * b2c预先提货信息发送跨易达
     *//*
    private static String b2cOrderPickAdvanceToKydUrl;
    *//**
     * b2c已提货信息发送跨易达
     *//*
    private static String b2cOrderPickAlreadyToKydUrl;
    
    
    
    
	public static String getB2cOrderPickAdvanceToKydUrl() {
		return b2cOrderPickAdvanceToKydUrl;
	}
	@Value("${b2cOrderPickAdvanceToKydUrl}")
	public void setB2cOrderPickAdvanceToKydUrl(
			String b2cOrderPickAdvanceToKydUrl) {
		SystemParamInit.b2cOrderPickAdvanceToKydUrl = b2cOrderPickAdvanceToKydUrl;
	}
	public static String getB2cOrderPickAlreadyToKydUrl() {
		return b2cOrderPickAlreadyToKydUrl;
	}
	@Value("${b2cOrderPickAlreadyToKydUrl}")
	public void setB2cOrderPickAlreadyToKydUrl(
			String b2cOrderPickAlreadyToKydUrl) {
		SystemParamInit.b2cOrderPickAlreadyToKydUrl = b2cOrderPickAlreadyToKydUrl;
	}
	@Value("${registerSyncUrl}")
    public  void setRegisterSyncUrl(String registerSyncUrl) {
        SystemParamInit.registerSyncUrl = registerSyncUrl;
    }
	public static String getRegisterSyncUrl() {
        return registerSyncUrl;
    }
    
    public static String getOrderSpaceSyncKydUrl() {
		return orderSpaceSyncKydUrl;
	}
    @Value("${orderSpaceSyncKydUrl}")
	public void setOrderSpaceSyncKydUrl(String orderSpaceSyncKydUrl) {
		SystemParamInit.orderSpaceSyncKydUrl = orderSpaceSyncKydUrl;
	}
	public static String getOrderCaseSyncKydUrl() {
        return orderCaseSyncKydUrl;
    }
    @Value("${orderCaseSyncKydUrl}")
    public void setOrderCaseSyncKydUrl(String orderCaseSyncKydUrl) {
        SystemParamInit.orderCaseSyncKydUrl = orderCaseSyncKydUrl;
    }
    public static String getOrderGoodsReceivedSyncKydUrl() {
        return orderGoodsReceivedSyncKydUrl;
    }
    @Value("${orderGoodsReceivedSyncKydUrl}")
    public void setOrderGoodsReceivedSyncKydUrl(String orderGoodsReceivedSyncKydUrl) {
        SystemParamInit.orderGoodsReceivedSyncKydUrl = orderGoodsReceivedSyncKydUrl;
    }
    public static String getOrderExpenseItemSyncKydUrl() {
        return orderExpenseItemSyncKydUrl;
    }
    @Value("${orderExpenseItemSyncKydUrl}")
    public void setOrderExpenseItemSyncKydUrl(String orderExpenseItemSyncKydUrl) {
        SystemParamInit.orderExpenseItemSyncKydUrl = orderExpenseItemSyncKydUrl;
    }
   
    
    public static String getDownloadPicUrl() {
		return downloadPicUrl;
	}
    @Value("${downloadPicUrl}")
	public void setDownloadPicUrl(String downloadPicUrl) {
		SystemParamInit.downloadPicUrl = downloadPicUrl;
	}

    public static String getCmsDownloadPicUrl() {
		return cmsDownloadPicUrl;
	}
    @Value("${cmsDownloadPicUrl}")
	public void setCmsDownloadPicUrl(String downloadPicUrl) {
		SystemParamInit.cmsDownloadPicUrl = downloadPicUrl;
	}

    
	public static String getOrderMainTransToSpUrl() {
        return orderMainTransToSpUrl;
    }
    @Value("${orderMainTransToSpUrl}")
    public void setOrderMainTransToSpUrl(String orderMainTransToSpUrl) {
        SystemParamInit.orderMainTransToSpUrl = orderMainTransToSpUrl;
    }
    public static String getUserUpdateToUcUrl() {
        return userUpdateToUcUrl;
    }
    @Value("${userUpdateToUcUrl}")
    public void setUserUpdateToUcUrl(String userUpdateToUcUrl) {
        SystemParamInit.userUpdateToUcUrl = userUpdateToUcUrl;
    }

    private static String etowerKey;
    private static String etowerToken;
    private static String etowerCreateOrder;
    private static String etowerGetLabels;
    private static String etowerSendOrder;
    private static String etowerTracking;
    private static String etowerDeleteOrder;
    private static String callSpToEtower;
    
    
	public static String getCallSpToEtower() {
		return callSpToEtower;
	}
	@Value("${callSpToEtower}")
	public void setCallSpToEtower(String callSpToEtower) {
		SystemParamInit.callSpToEtower = callSpToEtower;
	}
	public static String getEtowerKey() {
		return etowerKey;
	}
	@Value("${etowerKey}")
	public void setEtowerKey(String etowerKey) {
		SystemParamInit.etowerKey = etowerKey;
	}
	public static String getEtowerToken() {
		return etowerToken;
	}
	@Value("${etowerToken}")
	public void setEtowerToken(String etowerToken) {
		SystemParamInit.etowerToken = etowerToken;
	}
	public static String getEtowerCreateOrder() {
		return etowerCreateOrder;
	}
	@Value("${etowerCreateOrder}")
	public void setEtowerCreateOrder(String etowerCreateOrder) {
		SystemParamInit.etowerCreateOrder = etowerCreateOrder;
	}
	public static String getEtowerGetLabels() {
		return etowerGetLabels;
	}
	@Value("${etowerGetLabels}")
	public void setEtowerGetLabels(String etowerGetLabels) {
		SystemParamInit.etowerGetLabels = etowerGetLabels;
	}
	public static String getEtowerSendOrder() {
		return etowerSendOrder;
	}
	@Value("${etowerSendOrder}")
	public void setEtowerSendOrder(String etowerSendOrder) {
		SystemParamInit.etowerSendOrder = etowerSendOrder;
	}
	public static String getEtowerTracking() {
		return etowerTracking;
	}
	@Value("${etowerTracking}")
	public void setEtowerTracking(String etowerTracking) {
		SystemParamInit.etowerTracking = etowerTracking;
	}
	public static String getEtowerDeleteOrder() {
		return etowerDeleteOrder;
	}
	@Value("${etowerDeleteOrder}")
	public void setEtowerDeleteOrder(String etowerDeleteOrder) {
		SystemParamInit.etowerDeleteOrder = etowerDeleteOrder;
	}
	
//	public static String getSolrURL() {
//		return solrURL;
//	}
//
//	@Value("${solrURL}")
//	public void setSolrURL(String solrURL) {
//		SystemParamInit.solrURL = solrURL;
//	}

    public static String getOsmFileHost() {
        return osmFileHost;
    }
    @Value("${osmFileHost}")
    public  void setOsmFileHost(String osmFileHost) {
        SystemParamInit.osmFileHost = osmFileHost;
    }

    public static String getOsmFileUserName() {
        return osmFileUserName;
    }
    @Value("${osmFileUserName}")
    public void setOsmFileUserName(String osmFileUserName) {
        SystemParamInit.osmFileUserName = osmFileUserName;
    }

    public static String getOsmFilePassword() {
        return osmFilePassword;
    }
    @Value("${osmFilePassword}")
    public void setOsmFilePassword(String osmFilePassword) {
        SystemParamInit.osmFilePassword = osmFilePassword;
    }

    public static String getOsmUploadDirPath() {
        return osmUploadDirPath;
    }
    @Value("${osmUploadDirPath}")
    public void setOsmUploadDirPath(String osmUploadDirPath) {
        SystemParamInit.osmUploadDirPath = osmUploadDirPath;
    }

    public static String getOsmDownloadTrackingFilePath() {
        return osmDownloadTrackingFilePath;
    }
    @Value("${osmDownloadTrackingFilePath}")
    public void setOsmDownloadTrackingFilePath(String osmDownloadTrackingFilePath) {
        SystemParamInit.osmDownloadTrackingFilePath = osmDownloadTrackingFilePath;
    }

    public static String getOsmDownloadedSavePath() {
        return osmDownloadedSavePath;
    }
    @Value("${osmDownloadedSavePath}")
    public void setOsmDownloadedSavePath(String osmDownloadedSavePath) {
        SystemParamInit.osmDownloadedSavePath = osmDownloadedSavePath;
    }
    
    *//**cms 底部栏标题 接口*//*
    private static String cmsTitleFirstAdd;
    private static String cmsTitleFirstUpdate;
	private static String cmsTitleFirstList;
	private static String cmsTitleSecondAdd;
	private static String cmsTitleSecondUpdate;
	private static String cmsTitleSecondList;

	public static String getCmsTitleFirstAdd() {
		return cmsTitleFirstAdd;
	}
	@Value("${cmsTitleFirstAdd}")
	public void setCmsTitleFirstAdd(String cmsTitleFirstAdd) {
		SystemParamInit.cmsTitleFirstAdd = cmsTitleFirstAdd;
	}

	public static String getCmsTitleFirstUpdate() {
		return cmsTitleFirstUpdate;
	}
	@Value("${cmsTitleFirstUpdate}")
	public void setCmsTitleFirstUpdate(String cmsTitleFirstUpdate) {
		SystemParamInit.cmsTitleFirstUpdate = cmsTitleFirstUpdate;
	}

	public static String getCmsTitleFirstList() {
		return cmsTitleFirstList;
	}
	@Value("${cmsTitleFirstList}")
	public void setCmsTitleFirstList(String cmsTitleFirstList) {
		SystemParamInit.cmsTitleFirstList = cmsTitleFirstList;
	}

	public static String getCmsTitleSecondAdd() {
		return cmsTitleSecondAdd;
	}
	@Value("${cmsTitleSecondAdd}")
	public void setCmsTitleSecondAdd(String cmsTitleSecondAdd) {
		SystemParamInit.cmsTitleSecondAdd = cmsTitleSecondAdd;
	}

	public static String getCmsTitleSecondUpdate() {
		return cmsTitleSecondUpdate;
	}
	@Value("${cmsTitleSecondUpdate}")
	public void setCmsTitleSecondUpdate(String cmsTitleSecondUpdate) {
		SystemParamInit.cmsTitleSecondUpdate = cmsTitleSecondUpdate;
	}

	public static String getCmsTitleSecondList() {
		return cmsTitleSecondList;
	}
	@Value("${cmsTitleSecondList}")
	public void setCmsTitleSecondList(String cmsTitleSecondList) {
		SystemParamInit.cmsTitleSecondList = cmsTitleSecondList;
	}

    public static String getCheckResultSyncToUcUrl() {
        return checkResultSyncToUcUrl;
    }
    @Value("${checkResultSyncToUcUrl}")
    public void setCheckResultSyncToUcUrl(String checkResultSyncToUcUrl) {
        SystemParamInit.checkResultSyncToUcUrl = checkResultSyncToUcUrl;
    }
	public static String getCmsupdateRecommendTypeUrl() {
		return cmsupdateRecommendTypeUrl;
	}
	
	 @Value("${cmsupdateRecommendTypeUrl}")
	public  void setCmsupdateRecommendTypeUrl(String cmsupdateRecommendTypeUrl) {
		SystemParamInit.cmsupdateRecommendTypeUrl = cmsupdateRecommendTypeUrl;
	}
	 
	public static String getCmsSaveFriendLinkUrl() {
		return cmsSaveFriendLinkUrl;
	}
	
	 @Value("${cmsSaveFriendLinkUrl}")
	public  void setCmsSaveFriendLinkUrl(String cmsSaveFriendLinkUrl) {
		SystemParamInit.cmsSaveFriendLinkUrl = cmsSaveFriendLinkUrl;
	}
	public static String getCmsFindFriendLinkUrl() {
		return cmsFindFriendLinkUrl;
	}
	@Value("${cmsFindFriendLinkUrl}")
	public  void setCmsFindFriendLinkUrl(String cmsFindFriendLinkUrl) {
		SystemParamInit.cmsFindFriendLinkUrl = cmsFindFriendLinkUrl;
	}
	public static String getCmsGetFriendLinkUrl() {
		return cmsGetFriendLinkUrl;
	}
	@Value("${cmsGetFriendLinkUrl}")
	public  void setCmsGetFriendLinkUrl(String cmsGetFriendLinkUrl) {
		SystemParamInit.cmsGetFriendLinkUrl = cmsGetFriendLinkUrl;
	}
	public static String getCmsUpdateFriendLinkUrl() {
		return cmsUpdateFriendLinkUrl;
	}
	
	@Value("${cmsUpdateFriendLinkUrl}")
	public  void setCmsUpdateFriendLinkUrl(String cmsUpdateFriendLinkUrl) {
		SystemParamInit.cmsUpdateFriendLinkUrl = cmsUpdateFriendLinkUrl;
	}
    public static String getGetRegiserUserCountUrl() {
        return getRegiserUserCountUrl;
    }
    @Value("${getRegiserUserCountUrl}")
    public void setGetRegiserUserCountUrl(String getRegiserUserCountUrl) {
        SystemParamInit.getRegiserUserCountUrl = getRegiserUserCountUrl;
    }
	public static String getCmsSaveTipsUrl() {
		return cmsSaveTipsUrl;
	}
	@Value("${cmsSaveTipsUrl}")
	public  void setCmsSaveTipsUrl(String cmsSaveTipsUrl) {
		SystemParamInit.cmsSaveTipsUrl = cmsSaveTipsUrl;
	}
	public static String getCmsFindTipsUrl() {
		return cmsFindTipsUrl;
	}
	@Value("${cmsFindTipsUrl}")
	public  void setCmsFindTipsUrl(String cmsFindTipsUrl) {
		SystemParamInit.cmsFindTipsUrl = cmsFindTipsUrl;
	}
	public static String getCmsGetTipsUrl() {
		return cmsGetTipsUrl;
	}
	@Value("${cmsGetTipsUrl}")
	public  void setCmsGetTipsUrl(String cmsGetTipsUrl) {
		SystemParamInit.cmsGetTipsUrl = cmsGetTipsUrl;
	}
	public static String getCmsUpdateTipsUrl() {
		return cmsUpdateTipsUrl;
	}
	@Value("${cmsUpdateTipsUrl}")
	public  void setCmsUpdateTipsUrl(String cmsUpdateTipsUrl) {
		SystemParamInit.cmsUpdateTipsUrl = cmsUpdateTipsUrl;
	}
	public static String getSupplierCodeUcUrl() {
		return supplierCodeUcUrl;
	}
	@Value("${supplierCodeUcUrl}")
	public void setSupplierCodeUcUrl(String supplierCodeUcUrl) {
		SystemParamInit.supplierCodeUcUrl = supplierCodeUcUrl;
	}
	public static String getSupplierCodeKydUrl() {
		return supplierCodeKydUrl;
	}
	@Value("${supplierCodeKydUrl}")
	public void setSupplierCodeKydUrl(String supplierCodeKydUrl) {
		SystemParamInit.supplierCodeKydUrl = supplierCodeKydUrl;
	}
	

	
	    //consultInfo
    private static String cmsFindConsultInfoUrl;
    private static String cmsUpdateConsultInfoUrl;
    private static String cmsGetConsultInfoUrl;

	public static String getCmsFindConsultInfoUrl() {
		return cmsFindConsultInfoUrl;
	}
	
	@Value("${cmsFindConsultInfoUrl}")
	public void setCmsFindConsultInfoUrl(String cmsFindConsultInfoUrl) {
		SystemParamInit.cmsFindConsultInfoUrl = cmsFindConsultInfoUrl;
	}
	public static String getCmsUpdateConsultInfoUrl() {
		return cmsUpdateConsultInfoUrl;
	}
	
	@Value("${cmsUpdateConsultInfoUrl}")
	public void setCmsUpdateConsultInfoUrl(String cmsUpdateConsultInfoUrl) {
		SystemParamInit.cmsUpdateConsultInfoUrl = cmsUpdateConsultInfoUrl;
	}
	public static String getCmsGetConsultInfoUrl() {
		return cmsGetConsultInfoUrl;
	}
	
	@Value("${cmsGetConsultInfoUrl}")
	public void setCmsGetConsultInfoUrl(String cmsGetConsultInfoUrl) {
		SystemParamInit.cmsGetConsultInfoUrl = cmsGetConsultInfoUrl;
	}
	
	//notice
    //private static String cmsSaveNoticeUrl;
    //private static String cmsFindNoticeUrl;
    //private static String cmsGetNoticeUrl;
    //private static String cmsUpdateNoticeUrl;
    //private static String cmsDelNoticeUrl;
    //
    //	public static String getCmsSaveNoticeUrl() {
//		return cmsSaveNoticeUrl;
//	}
//	//	@Value("${cmsSaveNoticeUrl}")
//	public  void setCmsSaveNoticeUrl(String cmsSaveNoticeUrl) {
//		SystemParamInit.cmsSaveNoticeUrl = cmsSaveNoticeUrl;
//	}
//	public static String getCmsFindNoticeUrl() {
//		return cmsFindNoticeUrl;
//	}
//	@Value("${cmsFindNoticeUrl}")
//	public  void setCmsFindNoticeUrl(String cmsFindNoticeUrl) {
//		SystemParamInit.cmsFindNoticeUrl = cmsFindNoticeUrl;
//	}
//	public static String getCmsGetNoticeUrl() {
//		return cmsGetNoticeUrl;
//	}
//	@Value("${cmsGetNoticeUrl}")
//	public  void setCmsGetNoticeUrl(String cmsGetNoticeUrl) {
//		SystemParamInit.cmsGetNoticeUrl = cmsGetNoticeUrl;
//	}
//	public static String getCmsUpdateNoticeUrl() {
//		return cmsUpdateNoticeUrl;
//	}
//	@Value("${cmsUpdateNoticeUrl}")
//	public  void setCmsUpdateNoticeUrl(String cmsUpdateNoticeUrl) {
//		SystemParamInit.cmsUpdateNoticeUrl = cmsUpdateNoticeUrl;
//	}
//	public static String getCmsDelNoticeUrl() {
//		return cmsDelNoticeUrl;
//	}
//	@Value("${cmsDelNoticeUrl}")
//	public  void setCmsDelNoticeUrl(String cmsDelNoticeUrl) {
//		SystemParamInit.cmsDelNoticeUrl = cmsDelNoticeUrl;
//	}
//	
//	

    //banner
    //private static String cmsSaveBannerUrl ;
    //private static String cmsFindBannerUrl;
    //private static String cmsGetBannerUrl ;
    //private static String cmsUpdateBannerUrl;
    //private static String cmsDelBannerUrl;
    //
    //
  // public static String getCmsSaveBannerUrl() {
//		return cmsSaveBannerUrl;
//	}
//	@Value("${cmsSaveBannerUrl}")
//	public  void setCmsSaveBannerUrl(String cmsSaveBannerUrl) {
//		SystemParamInit.cmsSaveBannerUrl = cmsSaveBannerUrl;
//	}
//	public static String getCmsFindBannerUrl() {
//		return cmsFindBannerUrl;
//	}
//	@Value("${cmsFindBannerUrl}")
//	public  void setCmsFindBannerUrl(String cmsFindBannerUrl) {
//		SystemParamInit.cmsFindBannerUrl = cmsFindBannerUrl;
//	}
//	public static String getCmsGetBannerUrl() {
//		return cmsGetBannerUrl;
//	}
//	@Value("${cmsGetBannerUrl}")
//	public  void setCmsGetBannerUrl(String cmsGetBannerUrl) {
//		SystemParamInit.cmsGetBannerUrl = cmsGetBannerUrl;
//	}
//	public static String getCmsUpdateBannerUrl() {
//		return cmsUpdateBannerUrl;
//	}
//	@Value("${cmsUpdateBannerUrl}")
//	public  void setCmsUpdateBannerUrl(String cmsUpdateBannerUrl) {
//		SystemParamInit.cmsUpdateBannerUrl = cmsUpdateBannerUrl;
//	}
//	public static String getCmsDelBannerUrl() {
//		return cmsDelBannerUrl;
//	}
//	@Value("${cmsDelBannerUrl}")
//	public  void setCmsDelBannerUrl(String cmsDelBannerUrl) {
//		SystemParamInit.cmsDelBannerUrl = cmsDelBannerUrl;
//	}
  */
}
