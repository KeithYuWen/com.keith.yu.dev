/**
 * 项目名: oss-common
 * 文件名：MD5.java 
 * 版本信息： V1.0
 * 日期：2016年4月13日 
 * Copyright: Corporation 2016 版权所有
 *
 */
package com.k12.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 项目名称：oss-common <br>
 * 类名称：MD5 <br>
 * 类描述：<br>
 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2016年4月13日 下午7:09:02 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年4月13日 下午7:09:02 <br>
 * 修改备注：<br>
 * 
 * @version 1.0
 * @author arlen
 */
public class MD5 {

	/**
	 * 获取MD5 结果字符串
	 * 
	 * @param source
	 * @return
	 */
	public static String encode(byte[] source) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(source);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public final static String encode(String s) {
        if (s == null || "".equals(s)) {
        	return null;
        }
        try {
			byte[] source = s.getBytes("UTF-8");
			return encode(source);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }

}
