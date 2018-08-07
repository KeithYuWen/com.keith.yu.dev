package com.k12.common.util.ehcache;

import com.alibaba.fastjson.JSON;
import com.k12.common.base.rest.RestCodec;
import com.k12.common.base.rest.RestException;

public class RedisGetObjUtil {
	
	/**
	 * 获取redis中缓存数据
	 * @param key
	 * @param clasz
	 * @return
	 */
	public static <T> T getObj(String prefix,String key, Class<T> clasz) {
		String str = null;
		try {
			str = RedisUtil.getString(RestCodec.encodeData(prefix+"-"+key));
			if(null != str){
				str = RestCodec.decodeData(str);
			}else{
				return null;
			}
		} catch (RestException e) {
			e.printStackTrace();
		}
		return clasz.cast(JSON.parseObject(str, clasz));
	}
	
	/**
	 * 获取redis中缓存数据
	 * @param key
	 * @param clasz
	 * @return
	 */
	public static String getObjList(String key) {
		String str = null;
		try {
			str = RedisUtil.getString(RestCodec.encodeData(key));
			if(null != str){
				str = RestCodec.decodeData(str);
			}else{
				return null;
			}
		} catch (RestException e) {
			e.printStackTrace();
		}
		return str;
	}
}
