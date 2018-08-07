package com.k12.common.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 字符串工具类
 * @author xuhongjie
 * 
 */
public final class StringUtils {
	
	/**
	 * 判断字符串是否为null或者空
	 * @user LiuDZ
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * valueOf() 
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String valueOf(Object s){
		if(s == null){
			return null;
		}else{
			return String.valueOf(s);
		}
		
	}

	/**
	 * reAllSpace(去除所有空格) 
	 * @param str
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String reAllSpace(String str){
		if(str!=null){
			str=str.replace(" ", "");
		}
		return str;
	}
	
	/**
	 * dateXG2HG(字符串中的时间"/"转化为"-") 
	 * @param s
	 * @return
	 * @author LDz
	 */
	public static String dateXG2HG(String s){
		Pattern p = Pattern.compile("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
		Matcher m = p.matcher(s);
		while(m.find()){
			String re=m.group();
			String[] rs=re.split(" ");
			String[] b=rs[0].split("/");
			if(b[1].length()==1){
				b[1]="0"+b[1];
			}
			if(b[2].length() == 1){
				b[2]="0"+b[2];
			}
			String[] e=rs[1].split(":");
			if(e[0].length()==1){
				e[0]="0"+e[0];
			}
			if(e[1].length()==1){
				e[1]="0"+e[1];
			}
			if(e[2].length()==1){
				e[2]="0"+e[2];
			}
			s=s.replace(re, b[0]+"-"+b[1]+"-"+b[2]+" "+e[0]+":"+e[1]+":"+e[2]);
		}
		return s;
	}
	
	/**
	 * reAllSpace(去除所有空格 和 null) 
	 * @param str
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static List<String> reAllSpace(List<String> list){
		List<String> stlist=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if(!isEmpty(list.get(i))){
				stlist.add(list.get(i));
			}
		}
		return stlist;
	}
	
	/**
	 * 判断字符串是否不为null或者空
	 * @user LiuDZ
	 * @param values 可以为多个参数
	 * @return
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
	
	
	/**
	 * 判断第一个字符串是否 与后面的参数 有相等的
	 * @user LiuDZ
	 * @param values
	 * @return
	 */
	public static boolean hasSame(String... values) {
		if (values == null || values.length == 0) {
			return false;
		} else {
			String s="";
			int index=0;
			for (String value : values) {
				index++;
				if(index==1){
					s=value;
				}else{
					if(s.equals(value)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * unicode转中文
	 * @author LiuDZ
	 * @param unicode
	 * @return
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}
	
	/**
	 * 将list<String> 集合 用特殊符号 拼接成一条字符串
	 * @author LiuDZ
	 * @param list
	 * @param agrs 特殊符号
	 * @return
	 * @date 2014-11-7 上午10:58:54
	 */
	public static String strJoin(List<String> list,String agrs){
		if(list==null){
			return "";
		}else{
			StringBuffer str=new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				if(i>=list.size()-1){
					str.append(list.get(i));
				}else{
					str.append(list.get(i)).append(agrs);
				}
			}
			return str.toString();
		}
	}
	
	/**
	 * 将list<String> 集合 用特殊符号 拼接成一条字符串  返回：'A','B','C'
	 * @author LiuDZ
	 * @param list
	 * @return
	 * @date 2014-11-7 上午10:58:54
	 */
	@SuppressWarnings("rawtypes")
	public static String strJoinSql(List list){
		StringBuffer str=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if(!isEmpty(String.valueOf(list.get(i)))){
				str.append("'").append(list.get(i)).append("',");
			}
		}
		return str.substring(0,str.length()-1);
	}
	
	/**
	 * 将 字符串 用特殊符号隔开 形成list<String> 集合
	 * @author LiuDZ
	 * @param list
	 * @param agrs 特殊符号
	 * @return
	 * @date 2014-11-7 上午10:58:54
	 */
	public static List<String>  strSplit(String str,String agrs){
		List<String> list=new ArrayList<String>();
		if(!isEmpty(str)){
			if(str.matches("('[A-Za-z0-9]+')(,'[A-Za-z0-9]+')*")){
				str=str.replaceAll("'", "");
			}
			String[] arr=str.split(agrs);
			for (int i = 0; i < arr.length; i++) {
				if(!isEmpty(arr[i])){
					list.add(arr[i]);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 获取当前前几天的日期
	 * @author LiuDZ
	 * @param day 前几天(天数)
	 * @param con 格式化日期
	 * @return
	 * @date 2015-1-12
	 */
	public static List<String> getBeforeDay(int day,String con){
		List<String> list=new ArrayList<String>();
		SimpleDateFormat sf=new SimpleDateFormat(con);
		//当天日期
		Calendar ca=Calendar.getInstance();
		ca.setTime(new Date());
		if(day>0){
			for (int i = 0; i < day; i++) {
				ca.add(Calendar.DAY_OF_MONTH, -1);
				list.add(sf.format(ca.getTime()));
			}
		}else{
			list.add(sf.format(ca.getTime()));
		}
		return list;
	} 
	
	/**
	 * 获取本周的下几周或上几周的星期几日期
	 * @author LiuDZ
	 * @param n 几周(周数) 注:为负数则为上n周 , 0为本周
	 * @param xq 星期几(1-7)
	 * @return
	 * @date 2015-1-13
	 */
	public static String getWeekDay(int n,int xq){
		Calendar cal = Calendar.getInstance();
		//n为推迟的周数，0下周，-1向前推迟一周，1下周，依次类推
		String day;
		cal.add(Calendar.DATE, n*7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		xq++;
		if(xq>=8){
			xq=1;
			cal.add(Calendar.DATE, 7);
		}
		cal.set(Calendar.DAY_OF_WEEK,xq);
		day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return day;
	}
	
	/**
	 * toCaseTableColumn(将字符串转成数据库表字段) 
	 * @param s
	 * @return
	 * @author LDz
	 */
	public static String toCaseTableColumn(String s) {
		if(isEmpty(s)){
			return null;
		}
		String ss = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
				ss += "_" + String.valueOf(s.charAt(i)).toLowerCase();
			} else {
				ss += String.valueOf(s.charAt(i));
			}
		}
		return ss;
	}
	
    /**
     * 首字母转大写
     * @param src
     * @return
     */
    public static String upperFirstChar(String src) {
        char firstChar = src.charAt(0);
        return src.replaceFirst(String.valueOf(firstChar), String.valueOf(firstChar).toUpperCase());
    }

    /**
     * 拼接字符串","分割 "a,b,c"
     * @param @param array
     * @param @param separator
     * @param @return
     * @return String
     * @throws
     * @author xuhongjie
     * @date 2015-5-8 上午12:05:02
     */
    public static String join(String[] array, String separator) {
        String s = "";
        if (array == null || array.length == 0)
            return s;
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (str == null || str.length() == 0)
                continue;
            s += str;
            if (i < array.length - 1)
                s += separator;
        }
        return s;
    }

    /**
     * 拼接字符串","分割 "'a','b','c'"
     * @param @param array
     * @param @param separator
     * @param @return
     * @return String
     * @throws
     * @author xuhongjie
     * @date 2015-5-8 上午12:05:02
     */
    public static String joinStr(String[] array, String separator) {
        String s = "";
        if (array == null || array.length == 0)
            return s;
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (str == null || str.length() == 0)
                continue;
            s += "'" + str + "'";
            if (i < array.length - 1)
                s += separator;
        }
        return s;
    }

    /**
     * 替换
     * @param @param source
     * @param @param oldStr
     * @param @param newStr
     * @param @param matchCase
     * @param @return
     * @return String
     * @throws
     * @author xiehong
     * @date 2015-5-9 下午5:55:27
     */
    public static String replace(String source, String oldStr, String newStr, boolean matchCase) {
        if (source == null) {
            return null;
        }
        if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
            return source;
        }
        int findStartPos = 0;
        int a = 0;
        while (a > -1) {
            int b = 0;
            String str1, str2, str3, str4, strA, strB;
            str1 = source;
            str2 = str1.toLowerCase();
            str3 = oldStr;
            str4 = str3.toLowerCase();
            if (matchCase) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }
            a = strA.indexOf(strB, findStartPos);
            if (a > -1) {
                b = oldStr.length();
                findStartPos = a + b;
                StringBuffer bbuf = new StringBuffer(source);
                source = bbuf.replace(a, a + b, newStr) + "";
                findStartPos = findStartPos + newStr.length() - b;
            }
        }
        return source;
    }

    /**
     * cut off special string to fixed length string
     * @param source
     * @param len
     * @return
     * @throws Exception
     */
    public static String cutOff(String source, int len) throws Exception {
        Assert.notNull(source, "source must not be null");
        if (len < 0) {
            throw new Exception("指定的截取长度无法实现");
        }
        int slen = source.length();
        if (slen > len) {
            return source.substring(0, len + 1);
        }
        return source;
    }

    public static String replaceWithRE(String source, String target, String[] allReg) {
        Assert.notNull(source, "source must not be null");
        Assert.notNull(target, "target must not be null");
        Assert.notNull(allReg, "allReg must not be null");
        Assert.isTrue(allReg.length > 0, "allReg must not be empty");

        if (allReg == null || allReg.length == 0) {
            return "";
        }
        for (String reg : allReg) {
            Pattern pt = Pattern.compile(reg);
            Matcher mt = pt.matcher(source);
            while (mt.find()) {
                String s = mt.group();
                //System.out.println("s is : " + s);
                source = replace(source, s, target, true);
            }
        }
        return source;
    }
    /**
     * List转为String
     * @param singleEs
     * @return
     */
    public static String constr2StrUtil(List<?> singleEs) {

        if (!CollectionUtils.isEmpty(singleEs)) {
            StringBuffer sb = new StringBuffer("( ");
            for (int j = 0; j < singleEs.size(); j++) {
                sb.append("'" + singleEs.get(j) + "',");
            }
            String sbStr = sb.toString();
            return sbStr.substring(0, sbStr.lastIndexOf(",")) + " )";
        }
        return "";
    }
    
	/**
	 * getFieldValueByName(根据属性名获取属性值) 
	 * @param fieldName
	 * @param o
	 * @return
	 * @author LDz
	 */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * getFiledName(获取属性名数组) 
	 * @param o
	 * @return
	 * @author LDz
	 */
	public static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * getFiledsInfo(获取属性类型(type)，属性名(name)，属性值(value)的map组成的list) 
	 * @param o
	 * @return
	 * @author LDz
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static List getFiledsInfo(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		List list = new ArrayList();
		Map infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * getFiledValues(获取对象的所有属性值，返回一个对象数组) 
	 * @param o
	 * @return
	 * @author LDz
	 */
	public static Object[] getFiledValues(Object o) {
		String[] fieldNames = getFiledName(o);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			value[i] = getFieldValueByName(fieldNames[i], o);
		}
		return value;
	}
	
	/**
	 * GetRandomString(生成一个长度为len，范围为
										type为1时纯数字、
										type为2时数字+大写、
										type为3时纯小写、
										type为4时纯大写、
										type为5时数字+小写、
										type为6时小写+大写、
										type为其他时全有
											的随机字符串) 
	 * @author zhongfei
	 * @param Len 长度
	 * @param type 种类
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String getRandomString(int Len,int type) {
		  String str="";
		  String figure="0,1,2,3,4,5,6,7,8,9";
		  String letter="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
		  String upLetter="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		  if(type==1){
			  str=figure;
		  }else if(type==2){
			  str = figure + "," + upLetter;
		  }else if(type==3){
			  str = letter;
		  }else if(type==4) {
			  str = upLetter;
		  }else if(type==5){
			  str = figure + "," + letter;
		  }else if(type==6){
			  str = letter + "," + upLetter;
		  }else{
			  str = figure + "," + letter + "," +upLetter;
		  }
		   String[] baseString = str.split(",");
		   Random random = new Random();
		   int length=baseString.length;
		   String resultStr="";
		   for (int i = 0; i < Len; i++) {
		       resultStr += baseString[random.nextInt(length)];
		   }
		   return resultStr;
		}
	
	/**
	 * 	GetRandomListString(生成num个总长度为len、开头为str的随机字符串，随机范围为
	  												type为1时纯数字、
													type为2时数字+大写、
													type为3时纯小写、
													type为4时纯大写、
													type为5时数字+小写、
													type为6时小写+大写、
													type为其他时全有
													，且这些字符串不能有重复) 
	 * @author zhongfei
	 * @param str	开头字符串
	 * @param len	长度
	 * @param num	个数
	 * @param type	种类
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static List<String> getRandomListNEString(String str,int num,int len,int type){
	    List<String> list = new ArrayList<String>();
	    if(isEmpty(str)){
	    	str="";
	    }
		for(int i=0;i<num;i++){
			String rm=null;
			do{
				rm=str+getRandomString(len-str.length(),type);
			}while(list.contains(rm));
				list.add(rm);
        }
		return list;
	}
	
	/**
	 * 	GetRandomListString(生成num个总长度为len、开头为str的随机字符串，随机范围为
	  												type为1时纯数字、
													type为2时数字+大写、
													type为3时纯小写、
													type为4时纯大写、
													type为5时数字+小写、
													type为6时小写+大写、
													type为其他时全有
													，且这些字符串可以有重复) 
	 * @author zhongfei
	 * @param str	开头
	 * @param len	长度
	 * @param num	个数
	 * @param type	种类
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static List<String> getRandomListString(String str,int num,int len,int type){
		List<String> list = new ArrayList<String>();
	    if(isEmpty(str)){
	    	str="";
	    }
		for(int i=0;i<num;i++){
				String rm=null;
				rm=str+getRandomString(len-str.length(),type);
				list.add(rm);
        }
		return list;
	}
    
	/**
	 * toIpAddr(返回用户的IP地址) 
	 * @param request
	 * @return
	 * @author LDz
	 */
	public static String toIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/** 
     * 将url参数转换成map 
     * @param param 
     * @return 
     */  
    public static Map<String, String> getUrlParams(String param) {  
        Map<String, String> map = new HashMap<String, String>(0);  
        if (param ==null || param.trim().equals("")) {  
            return map;  
        }  
        String[] params = param.split("&");  
        for (int i = 0; i < params.length; i++) {  
            String[] p = params[i].split("=");  
            if (p.length == 2) {  
                map.put(p[0], p[1]);  
            }  
        }  
        return map;  
    }  
  
    /** 
     * 将map转换成url 
     * @param map 
     * @return 
     */  
    public static String getUrlParamsByMap(Map<String, String> map) {  
        if (map == null) {  
            return "";  
        }  
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : map.entrySet()) {  
            sb.append(entry.getKey() + "=" + entry.getValue());  
            sb.append("&");  
        }  
        String s = sb.toString();  
        if (s.endsWith("&")) {  
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");  
        }  
        return s;  
    }

    /**
     * 获取域名
     */
    public static String getHostUrl(HttpServletRequest req){
    	return req.getScheme()+"://"+req.getHeader("Host");
    }
    
    
    /**
     * 64位字符库
     * @author zhongfei
     */
    final static char[] digits = {  
        '0' , '1' , '2' , '3' , '4' , '5' ,  
        '6' , '7' , '8' , '9' , 'a' , 'b' ,  
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,  
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,  
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,  
        'u' , 'v' , 'w' , 'x' , 'y' , 'z' ,  
        'A' , 'B' , 'C' , 'D' , 'E' , 'F' ,  
        'G' , 'H' , 'I' , 'J' , 'K' , 'L' ,  
        'M' , 'N' , 'O' , 'P' , 'Q' , 'R' ,  
        'S' , 'T' , 'U' , 'V' , 'W' , 'X' ,  
        'Y' , 'Z' , '+' , '/'  ,  
        };  
  
    /** 
     * 把10进制的数字转换成64进制 
     * @param number 
     * @return 
     * @author zhongfei
     */  
    public static String CompressNumber(long number) {  
        char[] buf = new char[64];  
        int charPos = 64;  
        int radix = 1 << 6; //左移运算  1*2^6 
        long mask = radix - 1;  
        do {  
            buf[--charPos] = digits[(int)(number & mask)];  
            //number = number/2^6
            number >>>= 6;  
        } while (number != 0);  
      //从buf里index为charPos的下标开始取（64-charPos）个元素 组成一个新的字符串 
        return new String(buf, charPos, (64 - charPos)); 
       }  
    /** 
     * 把64进制的字符串转换成10进制 
     * @param decompStr 
     * @return 
     * @author zhongfei
     */  
    public static long UnCompressNumber(String decompStr)  
    {  
        long result=0;  
        for (int i =  decompStr.length()-1; i >=0; i--) {  
            if(i==decompStr.length()-1)  
            {  
                result+=getCharIndexNum(decompStr.charAt(i));  
                continue;  
            }  
            for (int j = 0; j < digits.length; j++) {  
                if(decompStr.charAt(i)==digits[j])  
                {  
                    result+=((long)j)<<6*(decompStr.length()-1-i);  
                }  
            }  
        }  
        return result;  
    }     
    /** 取出64位的字符对应的数字
     *  
     * @param ch 
     * @return 
     * @author zhongfei
     */  
    private static long getCharIndexNum(char ch)  
    {  
        int num=((int)ch);  
        // 0~10
        if(num>=48&&num<=57)  
        {  
            return num-48;  
        }  
        // a~z
        else if(num>=97&&num<=122)  
        {  
            return num-87;  
        }// A~Z
        else if(num>=65&&num<=90)  
        {  
            return num-29;  
        }// +
        else if(num==43)  
        {  
            return 62;  
        }
        // /
        else if (num == 47)  
        {  
            return 63;  
        }  
        return 0;  
    }  
    
    /** 
     * 检查日期格式 
     * @param date 
     * @return 
     */  
    public static boolean checkDate(String date) {  
        String eL = "^((//d{2}(([02468][048])|([13579][26]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|([1-2][0-9])))))|(//d{2}(([02468][1235679])|([13579][01345789]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(//s(((0?[0-9])|([1][0-9])|([2][0-3]))//:([0-5]?[0-9])((//s)|(//:([0-5]?[0-9])))))?$";  
        Pattern p = Pattern.compile(eL);  
        Matcher m = p.matcher(date);  
        boolean b = m.matches();  
        return b;  
    }  
    /** 
     * 检查整数 
     * @param num 
     * @param type "0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数 
     * @return 
     */  
    public static boolean checkNumber(String num,String type){  
        String eL = "";  
        if(type.equals("0+"))eL = "^//d+$";//非负整数  
        else if(type.equals("+"))eL = "^//d*[1-9]//d*$";//正整数  
        else if(type.equals("-0"))eL = "^((-//d+)|(0+))$";//非正整数  
        else if(type.equals("-"))eL = "^-//d*[1-9]//d*$";//负整数  
        else eL = "^-?//d+$";//整数  
        Pattern p = Pattern.compile(eL);  
        Matcher m = p.matcher(num);  
        boolean b = m.matches();  
        return b;  
    }  
    /** 
     * 检查浮点数 
     * @param num 
     * @param type "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数 
     * @return 
     */  
    public static boolean checkFloat(String num,String type){  
        String eL = "";  
        if(type.equals("0+"))eL = "^//d+(//.//d+)?$";//非负浮点数  
        else if(type.equals("+"))eL = "^((//d+//.//d*[1-9]//d*)|(//d*[1-9]//d*//.//d+)|(//d*[1-9]//d*))$";//正浮点数  
        else if(type.equals("-0"))eL = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";//非正浮点数  
        else if(type.equals("-"))eL = "^(-((//d+//.//d*[1-9]//d*)|(//d*[1-9]//d*//.//d+)|(//d*[1-9]//d*)))$";//负浮点数  
        else eL = "^(-?//d+)(//.//d+)?$";//浮点数  
        Pattern p = Pattern.compile(eL);  
        Matcher m = p.matcher(num);  
        boolean b = m.matches();  
        return b;  
    } 
    
    /**
     * checkHTMLSpecialCharacter(对一个要显示在页面的语句进行特殊字符的转译) 
     * @param html
     * @return 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static String checkHTMLSpecialCharacter(String html){
    	
    	if(html==null){ 
    		return "";
    	} 
    	
    	html = StringUtils.replace(html, "&", "&amp;",true);
    	html = StringUtils.replace(html, "\"", "&quot;",true);
    	html = StringUtils.replace(html, "<", "&lt;",true);
    	html = StringUtils.replace(html, ">", "&gt;",true);
    	html = StringUtils.replace(html, "'", "&apos;",true);
//    	html = StringUtils.replace(html, "\"", "&gt;",true);
//    	System.out.println(html);
    	return html;
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    

    /**  
     * 计算两个日期之间相差的天数  (带一位小数点)
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static BigDecimal daysBetweenByTimes(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        float between_days=(float)(time2-time1)/(1000*3600*24);  
        String day =new DecimalFormat("#.0").format(between_days);
        return new BigDecimal(day);           
    }    
    
    /**
	 * 格式化
	 * 将换行转换为<br/>
	 * 将空格转换为<br/>
	 * @param target
	 * @return
	 */
	public static String formatBr(String target){
		if(isEmpty(target)) return null;
		StringBuilder sb = new StringBuilder();
		// 将/r/n替换成换行
		Pattern CRLF = Pattern.compile("(\r\n|\r\n|\r|\n\r)");
        Matcher m = CRLF.matcher(target);
        // 如果包含了换行符，则不再替换空格
        if (m.find()) {
        	target = m.replaceAll("<br/>");
        	// 将剩下的/n替换掉
        	CRLF = Pattern.compile("(\n)");
            m = CRLF.matcher(target);
        	target = m.replaceAll("");
        	
        	return target;
        }
		String[] arr = target.split(" ");
		for(int i = 0;i < arr.length;i++){
			// 排除空格
			if(isEmpty(arr[i].trim()))
				continue;
			sb.append(arr[i]);
			// 最后不再添加<br/>
			if(i==arr.length-1)
				break;
			// 如果下一个元素是冒号，不换行
			if(i<arr.length-2 && arr[i+1].trim().startsWith(":")){
				continue;
			}
			// 考虑到有的序号后也会含有空格，如(1.2.)，如果不以.结尾，换行
			if(!arr[i].endsWith("."))
				sb.append("<br/>");
		}
		return sb.toString();
	}
	
	/**
	 * getTheLastDayOfLastMonth(获取上个月的最后一天的时间) 
	 * @return 
	 * @throws ParseException 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static Calendar getTheLastDayOfLastMonth() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH)+1;
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); 
		String data = format.format(calendar.getTime());
		calendar.setTime(format.parse(data));
		return calendar;
	}
	
	/**
	 * getToday(获取昨天日期时间) 
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static Calendar getYesterday(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.DATE,   -1);
		String data = format.format(calendar.getTime());
		try {
			calendar.setTime(format.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
		
	}
	

}
