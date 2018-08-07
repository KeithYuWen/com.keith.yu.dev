/**
 * 项目名: oss-common
 * 文件名：HMACSHA1.java 
 * 版本信息： V1.0
 * 日期：2016年4月13日 
 * Copyright: Corporation 2016 版权所有
 *
 */
package com.k12.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 项目名称：oss-common <br>
 * 类名称：HMACSHA1 <br>
 * 类描述：<br>
 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2016年4月13日 下午7:23:27 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年4月13日 下午7:23:27 <br>
 * 修改备注：<br>
 * @version 1.0
 * @author arlen
 */
public class HMACSHA1 {
	
	private final static Logger logger = LoggerFactory.getLogger(HMACSHA1.class);
	private static final String HMAC_SHA1 = "HmacSHA1";  
	
	// 获取原始加密byte数组
    private static byte[] getRawHashArr(byte[] dataArr, byte[] keyArr) {
    	if (dataArr == null || keyArr == null || dataArr.length == 0 || keyArr.length == 0) {
    		return null;
    	}
        SecretKeySpec signingKey = new SecretKeySpec(keyArr, HMAC_SHA1);
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(signingKey);
			byte[] raw= mac.doFinal(dataArr);
			return raw;
		} catch (InvalidKeyException e) {
			logger.error("HMAC SHA1加密，key非法", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("HMAC SHA1加密，初始化Mac示例失败", e);
		}
		return null;
    }
	
    /** 
     * 获取原始SHA1加密hash
     *  
     * @param dataArr 待加密的数据 
     * @param keyArr  加密使用的key 
     * @return raw hash string
     * @throws InvalidKeyException 
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     * @author arlen
     */
    public static String getRawHashString(byte[] dataArr, byte[] keyArr) {  
    	if (dataArr == null || keyArr == null || dataArr.length == 0 || keyArr.length == 0) {
    		return null;
    	}
        byte[] rawHash = getRawHashArr(dataArr, keyArr);
        try {
			return new String(rawHash, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("HMAC SHA1加密，加密后byte转为String失败", e);
		}
        return null;
    }
    
    /**
     * 获取原始SHA1加密hash
     * @param data
     * @param key
     * @return raw hash string
     * @throws IllegalStateException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException 
     * @author arlen
     */
    public static String getRawHashString(String data, String key) {
    	if (data == null || key == null) {
    		return null;
    	}
    	try {
			byte[] dataArr = data.getBytes("utf-8");
			byte[] keyArr = key.getBytes("utf-8");
			return getRawHashString(dataArr, keyArr);
		} catch (UnsupportedEncodingException e) {
			logger.error("HMAC SHA1加密，data/key转byte数组失败", e);
		}
    	return null;
    }
    
    public static String getSignature(byte[] dataArr, byte[] keyArr) {
    	if (dataArr == null || keyArr == null || dataArr.length == 0 || keyArr.length == 0) {
    		return null;
    	}
    	byte[] rawHash = getRawHashArr(dataArr, keyArr);
    	return rawHash == null? null: MD5.encode(rawHash);
    }
    
    public static String getSignature(String data, String key) {
    	if (data == null || key == null) {
    		return null;
    	}
    	try {
			byte[] dataArr = data.getBytes("utf-8");
			byte[] keyArr = key.getBytes("utf-8");
			return getSignature(dataArr, keyArr);
		} catch (UnsupportedEncodingException e) {
			logger.error("HMAC SHA1加密，data/key转byte数组失败", e);
		}
    	return null;
    }

    public static String getBase64RawHash(byte[] dataArr, byte[] keyArr) {
    	if (dataArr == null || keyArr == null || dataArr.length == 0 || keyArr.length == 0) {
    		return null;
    	}
    	byte[] rawHash = getRawHashArr(dataArr, keyArr);
		if (null != rawHash) {
			String raw64 = Base64.encodeBase64String(rawHash);
			return raw64;
		}
    	return null;
    }
    
    public static String getBase64RawHash(String data, String key) {
    	if (data == null || key == null) {
    		return null;
    	}
    	try {
			byte[] dataArr = data.getBytes("utf-8");
			byte[] keyArr = key.getBytes("utf-8");
			return getBase64RawHash(dataArr, keyArr);
		} catch (UnsupportedEncodingException e) {
			logger.error("HMAC SHA1加密，data/key转byte数组失败", e);
		}
    	return null;
    }
    
    public static void main(String[] args) {
		String sig = HMACSHA1.getBase64RawHash("hahahahddd", "11112222");
		System.out.println(sig);
	}
}
