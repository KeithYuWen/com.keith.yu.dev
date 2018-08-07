package com.k12.common.util.uid;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 生成唯一标识
 * @author liudazhou
 * @email  skycool@vip.qq.com
 */
public class UID {
	private static synchronized String next() {
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMddHHmmssms");
		int rand=new SecureRandom().nextInt(8999) + 1000;
		String n=simp.format(new UniqTimer().getCurrentTime());
		int count=18-n.length();
		if(count>0){
			for (int i = 0; i < count; i++) {
				n+="0";
			}
		}
		return  n+ rand;
	}
	
	/**
	 * 获取唯一表示
	 * @author liuDZ
	 * @return
	 */
	public static String getKey() {
		return next();
	}
	
	/**
	 * 加开头标识
	 * @author liuDZ
	 * @param bstr
	 * @return
	 */
	public static String getKey(String bstr) {
		return bstr+next();
	}
	
	/**
	 * 加开头、结尾标识
	 * @author liuDZ
	 * @param bstr
	 * @return
	 */
	public static String getKey(String bstr,String estr) {
		return bstr+next()+estr;
	}
	
	/**
	 * 实现不重复的时间
	 * 
	 * @author dogun
	 */
	private static class UniqTimer {
		private AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());

		public long getCurrentTime() {
			return this.lastTime.incrementAndGet();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(next());
	}
}
