package com.k12.tc.common.base.rest;


import java.util.Date;

import com.k12.tc.common.init.StaticProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k12.tc.common.aes.MsgCrypt;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * 项目名称：greenpass 
 * 类名称：RestSendUtil 
 * 类描述：rest请求辅助工具类
 * Copyright: Copyright (c) 2015
 * Company: k12 Supply Chain MGT
 * 创建人：孙海滨
 * 创建时间：2015年10月14日 下午6:29:10 
 * 修改人：孙海滨 
 * 修改时间：2015年10月14日 下午6:29:10 
 * 修改备注：
 * @version 1.0*
 */
public class RestSendUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(RestSendUtil.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	public static String getEncryptData(String data){
		String result = "";
		try
		{
			MsgCrypt pc = new MsgCrypt(StaticProperty.token, StaticProperty.encodingAesKey, StaticProperty.appId);
			Date currentTime = new Date();
			String dateStr = sdf.format(currentTime);
			String timestamp =dateStr.substring(0, 2)+dateStr.substring(3, 5)+dateStr.substring(6, 8)
					                   +dateStr.substring(9, 11)+dateStr.substring(12, 14)+dateStr.substring(15, 17);
			String nonce="xxxxxx";
			result = pc.encryptMsg(data,"15101010101", nonce);
//			result = Base64util.encode(result);
		} catch (Exception e)
		{
			LOG.error("加密报文失败{}",e);
		}
		return result;
	}

}
