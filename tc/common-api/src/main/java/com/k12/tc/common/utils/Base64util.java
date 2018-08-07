/**
 * 项目名：greenpass
 * 包名：com.k12.greenpass.common.util
 * 文件名：Base64util.java *
 * 日期：2015-9-28 下午1:37:44
 * Copyright 宏坤供应链有限公司 2015
 * 版权所有
 */
package com.k12.tc.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


    /**
 * 此类描述的是：
 * @author xuhongjie
 */

public class Base64util {
    
    private static final Logger LOG = LoggerFactory.getLogger(Base64util.class);
    
    /**
     * 对给定的字符串进行base64解码操作
     * 
     * @param str : 待解码的字符串
     * @return 解码后的字符串
     */    
    public static String decode(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes("utf-8")),"utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error(input, e);
        }
        
        return null;
    }
    
    /**
     * 对给定的字符串进行base64压码操作
     * 
     * @param str 待压码的字符串
     * @return 压码后的字符串
     */    
    public static String encode(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes("utf-8")),"utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error(input, e);
        }
        
        return null;
    }
    
    
    /**
     * 将二进制数据编码为BASE64字符串
     * @param binaryData
     * @return
     */
    public static String encodeByteToStr(byte[] binaryData) {
        try {
            return new String(Base64.encodeBase64(binaryData), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
     
    /**
     * 将BASE64字符串恢复为二进制数据
     * @param base64String
     * @return
     */
    public static byte[] decodeStrToByte(String base64String) {
        try {
            return Base64.decodeBase64(base64String.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
