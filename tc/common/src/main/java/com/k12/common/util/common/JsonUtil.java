/**
 * 项目名：greenpass
 * 包名：com.k12.greenpass.common.util
 * 文件名：JsonUtil.java *
 * 日期：2015-9-17 上午11:57:45
 * Copyright 宏坤供应链有限公司 2015
 * 版权所有
 */
package com.k12.common.util.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.k12.common.base.json.JsonFormatter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 此类描述的是：json 工具类
 * 
 * @author xujinfei
 */

public class JsonUtil extends JsonFormatter {

	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

	public static boolean checkFiledFlag = true;
	public static final String filterKey = "filter";

	public static final short ASC = 0;
	public static final short DESC = 1;

	/**
	 * 此方法描述的是：根据key取得相应的值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return String
	 */
	public static String getString(Map<String, Object> map, String key) {
		try {
			return (String) map.get(key);
		} catch (Exception e) {
			return JsonUtil.toString(map.get(key));
		}
	}
	
	
	/**
	 *  一个String字符串转换为json格式
	 *@descript
	 *@param s
	 *@return
	 *@author lijianning
	 *@date 2015年6月15日
	 *@version 1.0v
	 */
    public static String stringToJson(String s) { 
        if (s == null) { 
            return nullToJson(); 
        } 
        StringBuilder sb = new StringBuilder(); 
        for (int i = 0; i < s.length(); i++) { 
            char ch = s.charAt(i); 
            switch (ch) { 
            case '"': 
                sb.append("\\\""); 
                break; 
            case '\\': 
                sb.append("\\\\"); 
                break; 
            case '\b': 
                sb.append("\\b"); 
                break; 
            case '\f': 
                sb.append("\\f"); 
                break; 
            case '\n': 
                sb.append("\\n"); 
                break; 
            case '\r': 
                sb.append("\\r"); 
                break; 
            case '\t': 
                sb.append("\\t"); 
                break; 
            case '/': 
                sb.append("\\/"); 
                break; 
            default: 
                if (ch >= '\u0000' && ch <= '\u001F') { 
                    String ss = Integer.toHexString(ch); 
                    sb.append("\\u"); 
                    for (int k = 0; k < 4 - ss.length(); k++) { 
                        sb.append('0'); 
                    } 
                    sb.append(ss.toUpperCase()); 
                } else { 
                    sb.append(ch); 
                } 
            } 
        } 
        return sb.toString(); 
    } 
    
    
    public static String nullToJson() { 
        return ""; 
    } 
    
    
    /**
     * 一个obj对象转换为json格式
     *@descript
     *@param obj
     *@return
     *@author lijianning
     *@date 2015年6月15日
     *@version 1.0v
     */
    public static String objectToJson(Object obj) { 
        StringBuilder json = new StringBuilder(); 
        if (obj == null) { 
            json.append("\"\""); 
        } else if (obj instanceof Number) { 
            json.append(numberToJson((Number) obj)); 
        } else if (obj instanceof Boolean) { 
            json.append(booleanToJson((Boolean) obj)); 
        } else if (obj instanceof String) { 
            json.append("\"").append(stringToJson(obj.toString())).append("\""); 
        } else if (obj instanceof Object[]) { 
            json.append(arrayToJson((Object[]) obj)); 
        } else if (obj instanceof List) { 
            json.append(listToJson((List<?>) obj)); 
        } else if (obj instanceof Map) { 
            json.append(mapToJson((Map<?, ?>) obj)); 
        } else if (obj instanceof Set) { 
            json.append(setToJson((Set<?>) obj)); 
        } else { 
            json.append(beanToJson(obj)); 
        } 
        return json.toString(); 
    } 
   
    public static String numberToJson(Number number) { 
        return number.toString(); 
    } 
   
    public static String booleanToJson(Boolean bool) { 
        return bool.toString(); 
    } 
   
   /**
    *  一个bean对象转换为json格式
    *@descript
    *@param bean
    *@return
    *@author lijianning
    *@date 2015年6月15日
    *@version 1.0v
    */
    public static String beanToJson(Object bean) { 
        StringBuilder json = new StringBuilder(); 
        json.append("{"); 
        PropertyDescriptor[] props = null; 
        try { 
            props = Introspector.getBeanInfo(bean.getClass(), Object.class) 
                    .getPropertyDescriptors(); 
        } catch (IntrospectionException e) { 
        } 
        if (props != null) { 
            for (int i = 0; i < props.length; i++) { 
                try { 
                    String name = objectToJson(props[i].getName()); 
                    String value = objectToJson(props[i].getReadMethod() 
                            .invoke(bean)); 
                    json.append(name); 
                    json.append(":"); 
                    json.append(value); 
                    json.append(","); 
                } catch (Exception e) { 
                } 
            } 
            json.setCharAt(json.length() - 1, '}'); 
        } else { 
            json.append("}"); 
        } 
        return json.toString(); 
    } 
   
    /**

     *@descript
     *@param list
     *@return
     *@author lijianning
     *@date 2015年6月15日
     *@version 1.0v
     */
    public static String listToJson(List<?> list) { 
        StringBuilder json = new StringBuilder(); 
        json.append("["); 
        if (list != null && list.size() > 0) { 
            for (Object obj : list) { 
                json.append(objectToJson(obj)); 
                json.append(","); 
            } 
            json.setCharAt(json.length() - 1, ']'); 
        } else { 
            json.append("]"); 
        } 
        return json.toString(); 
    } 
   
    /**
     *  一个数组集合转换为json格式
     *@descript
     *@param array
     *@return
     *@author lijianning
     *@date 2015年6月15日
     *@version 1.0v
     */
    public static String arrayToJson(Object[] array) { 
        StringBuilder json = new StringBuilder(); 
        json.append("["); 
        if (array != null && array.length > 0) { 
            for (Object obj : array) { 
                json.append(objectToJson(obj)); 
                json.append(","); 
            } 
            json.setCharAt(json.length() - 1, ']'); 
        } else { 
            json.append("]"); 
        } 
        return json.toString(); 
    } 
   
   /**
    * 一个Map集合转换为json格式
    *@descript
    *@param map
    *@return
    *@author lijianning
    *@date 2015年6月15日
    *@version 1.0v
    */
    public static String mapToJson(Map<?, ?> map) { 
        StringBuilder json = new StringBuilder(); 
        json.append("{"); 
        if (map != null && map.size() > 0) { 
            for (Object key : map.keySet()) { 
                json.append(objectToJson(key)); 
                json.append(":"); 
                json.append(objectToJson(map.get(key))); 
                json.append(","); 
            } 
            json.setCharAt(json.length() - 1, '}'); 
        } else { 
            json.append("}"); 
        } 
        return json.toString(); 
    } 
   
   /**
    * 一个Set集合转换为json格式 
    *@descript
    *@param set
    *@return
    *@author lijianning
    *@date 2015年6月15日
    *@version 1.0v
    */
    public static String setToJson(Set<?> set) { 
        StringBuilder json = new StringBuilder(); 
        json.append("["); 
        if (set != null && set.size() > 0) { 
            for (Object obj : set) { 
                json.append(objectToJson(obj)); 
                json.append(","); 
            } 
            json.setCharAt(json.length() - 1, ']'); 
        } else { 
            json.append("]"); 
        } 
        return json.toString(); 
    } 
    /**
     * json字符串转换为List
     *@descript
     *@param jsonStr
     *@return
     *@author lijianning
     *@date 2015年6月15日
     *@version 1.0v
     */
    public static List<Map<String, Object>> parseJSONList(String jsonStr){  
        JSONArray jsonArr = JSONArray.parseArray(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
        for (int i = 0; i < jsonArr.size(); i++) {
        	list.add(parseJSONMap(jsonArr.getString(i)));  
		}
        return list;  
    }  
      
     /**
      * json字符串转换为map
      *@descript
      *@param jsonStr
      *@return
      *@author lijianning
      *@date 2015年6月15日
      *@version 1.0v
      */
    public static Map<String, Object> parseJSONMap(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        try {
			//最外层解析  
			JSONObject json = JSONObject.parseObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				//如果内层还是数组的话，继续解析  
				if (v instanceof JSONArray) {
					JSONArray jsonArr= (JSONArray) v;
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < jsonArr.size(); i++) {
			        	list.add(parseJSONMap(jsonArr.getString(i)));  
					}
					map.put(k.toString(), list);
				} else {
					map.put(k.toString(), v);
				}
			} 
		} catch (Exception e) {
			map.put("exception", jsonStr);
		}
		return map;  
    }  
      
     /**
      * 根据一个url地址.获取json数据.转换为List
      *@descript
      *@param url
      *@return
      *@author lijianning
      *@date 2015年6月15日
      *@version 1.0v
      */
    public static List<Map<String, Object>> getListByUrl(String url){  
        try {  
            //通过HTTP获取JSON数据  
            InputStream in = new URL(url).openStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
            StringBuilder sb = new StringBuilder();  
            String line;  
            while((line=reader.readLine())!=null){  
                sb.append(line);  
            }  
            return parseJSONList(sb.toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
     /**
      * 根据一个url地址.获取json数据.转换为MAP
      *@descript
      *@param url
      *@return
      *@author lijianning
      *@date 2015年6月15日
      *@version 1.0v
      */
    public static Map<String, Object> getMapByUrl(String url){  
        try {  
            //通过HTTP获取JSON数据  
            InputStream in = new URL(url).openStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
            StringBuilder sb = new StringBuilder();  
            String line;  
            while((line=reader.readLine())!=null){  
                sb.append(line);  
            }  
            return parseJSONMap(sb.toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  

	/**
	 * 此方法描述的是：取得list
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getList(Map<String, Object> map,
			String key) {
		try {
			return (List<Map<String, Object>>) map.get(key);
		} catch (Exception e) {
			try {
				return JsonUtil.toList(map.get(key));
			} catch (Exception e2) {
				return null;
			}
		}
	}

	/**
	 * 此方法描述的是：取得list
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(Map<String, Object> map, String key) {
		try {
			return (Map<String, Object>) map.get(key);
		} catch (Exception e) {
			return JsonUtil.toBean(map.get(key), Map.class);
		}

	}

	/**
	 * 此方法描述的是：根据key取值int
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return int
	 */
	public static int getInt(Map<String, Object> map, String key,
			int defaultValue) {
		try {
			return (Integer) map.get(key);
		} catch (Exception e) {
			try {
				return Integer.parseInt(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}
	}

	/**
	 * 此方法描述的是：根据key取BigDecial
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(Map<String, Object> map, String key,
			BigDecimal defaultValue) {
		return BigDecimal.valueOf(getDouble(map, key,
				defaultValue.doubleValue()));
	}

	/**
	 * 此方法描述的是：根据key取BigDecial
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
		return BigDecimal.valueOf(getDouble(map, key));
	}

	/**
	 * 此方法描述的是：根据key取值int
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return int
	 */
	public static int getInt(Map<String, Object> map, String key) {
		return JsonUtil.getInt(map, key, 0);
	}

	/**
	 * 此方法描述的是：根据key取得boolean值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return boolean
	 */
	public static boolean getBoolean(Map<String, Object> map, String key,
			boolean defaultValue) {
		try {
			return (Boolean) map.get(key);
		} catch (Exception e) {
			try {
				return Boolean.parseBoolean(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}
	}

	/**
	 * 此方法描述的是：根据key取得boolean值,默认为false
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return boolean
	 */
	public static boolean getBoolean(Map<String, Object> map, String key) {
		return getBoolean(map, key, false);
	}

	/**
	 * 此方法描述的是：向obj数组中加新元素,
	 * 
	 * @param list
	 *            list
	 * @param obj
	 *            增加的元素
	 */
	@SuppressWarnings("unchecked")
	public static void add(List<HashMap<String, Object>> list, Object obj) {
		list.add(JsonUtil.toBean(list, HashMap.class));
	}

	/**
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return double
	 */
	public static double getDouble(Map<String, Object> map, String key,
			double defaultValue) {
		try {
			return (Double) map.get(key);
		} catch (Exception e) {
			try {
				return Double.parseDouble(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}

	}

	/**
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return double
	 */
	public static double getDouble(Map<String, Object> map, String key) {
		return getDouble(map, key, 0D);
	}

	/**
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return long
	 */
	public static long getLong(Map<String, Object> map, String key,
			long defaultValue) {
		try {
			return (Long) map.get(key);
		} catch (Exception e) {
			try {
				return Long.parseLong(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}
	}

	/**
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return long
	 */
	public static long getLong(Map<String, Object> map, String key) {
		return getLong(map, key, 0L);
	}

	/**
	 * 此方法描述的是：将Object转化为Json格式字符串
	 * 
	 * @param obj
	 *            欲转换的对象
	 * @return String
	 */
	public static String toString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		} else if (null == obj) {
			return null;
		}
		try {
			return JsonFormatter.toJsonString(obj);
		} catch (JsonGenerationException e) {
			LOG.error("JsonGenerationException error", e);
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException error", e);
		} catch (IOException e) {
			LOG.error("IOException error", e);
		}
		return null;
	}

	/**
	 * 此方法描述的是：将Object转化为Json格式字符串
	 * 
	 * @param obj
	 *            欲转换的对象
	 * @param dateFormat
	 *            日期format
	 * @return String
	 */
	public static String toString(Object obj, DateFormat dateFormat) {
		if (obj instanceof String) {
			return (String) obj;
		} else if (null == obj) {
			return null;
		}
		try {
			JsonFormatter.setDateFormat(dateFormat);
			return JsonFormatter.toJsonString(obj);
		} catch (JsonGenerationException e) {
			LOG.error("JsonGenerationException error", e);
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException error", e);
		} catch (IOException e) {
			LOG.error("IOException error", e);
		}
		return null;
	}

	/**
	 * 此方法描述的是：将传入的对象转换成指定的对象
	 * 
	 * @param <T>
	 *            模板类
	 * @param cls
	 *            与转化的类
	 * @param obj
	 *            被转换的对象
	 * @return T
	 */
	public static <T> T toBean(Object obj, Class<T> cls) {
		if (null == obj) {
			return null;
		}
		try {
			return JsonFormatter.toObject(JsonUtil.toString(obj), cls);
		} catch (JsonParseException e) {
			LOG.error("JsonParseException error", e);
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException error", e);
		} catch (JsonGenerationException e) {
			LOG.error("JsonGenerationException error", e);
		} catch (IOException e) {
			LOG.error("IOException error", e);
		}
		return null;
	}

	/**
	 * 此方法描述的是：字符串转换为List<map>
	 * 
	 * @param obj
	 *            与转换的对象
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toList(Object obj) {
		List<Map<String, Object>> lists = new LinkedList<Map<String, Object>>();
		List<Object> list = JsonUtil.toBean(obj, List.class);
		if (null != list) {
			for (Object object : list) {
				Map<String, Object> map = JsonUtil
						.toBean(object, HashMap.class);
				if (null != map) {
					lists.add(map);
				}
			}
		}
		return lists;
	}

	/**
	 * 此方法描述的是：字符串转换为List
	 * 
	 * @param <T>
	 *            模板类
	 * @param cls
	 *            与转化的类
	 * @param obj
	 *            被转换的对象
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(Object obj, Class<T> cls) {
		List<T> lists = new LinkedList<T>();
		List<Object> list = JsonUtil.toBean(obj, List.class);
		if (null != list) {
			for (Object object : list) {
				T t = JsonUtil.toBean(object, cls);
				if (null != t) {
					lists.add(t);
				}
			}
		}
		return lists;
	}

	/**
	 * 根据字符串获取指定key对应的值
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int getIntFromStringData(String data, String key) {
		int result = 0;
		Map<String, Object> paramMap = JsonUtil.toBean(data, Map.class);
		result = JsonUtil.getInt(paramMap, key);
		return result;
	}

	/**
	 * Description: json字符串转map<br>
	 * 
	 * @author shiming<br>
	 * @taskId <br>
	 * @param json
	 *            json字符串
	 * @return map<br>
	 */
	public static <K, V> Map<K, V> toMap(String json) {
		if (json == null) {
			return Collections.emptyMap();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper = setMapperConfig(mapper);
		try {
			return mapper.readValue(json, new TypeReference<Map<K, V>>() {
			});
		} catch (JsonParseException e) {
			LOG.error("JsonParseException error", e);
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException error", e);
		} catch (IOException e) {
			LOG.error("IOException error", e);
		}
		return Collections.emptyMap();
	}

	/**
	 * Description: setMapperConfig<br>
	 * 
	 * @author shiming<br>
	 * @taskId <br>
	 * @param mapper
	 *            ObjectMapper
	 * @return ObjectMapper<br>
	 */
	private static ObjectMapper setMapperConfig(ObjectMapper mapper) {
		Set<String> fliterSet = new HashSet<String>(0);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		if (checkFiledFlag) {
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
		}

		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(
				filterKey,
				SimpleBeanPropertyFilter.serializeAllExcept(fliterSet));
		mapper.setFilters(filterProvider);

		return mapper;
	}

	/**
	 * Description:获取jsonArray <br>
	 * 
	 * @author shiming<br>
	 * @taskId <br>
	 * @param obj
	 *            JSONObject
	 * @param key
	 *            key
	 * @return <br>
	 */
	public static JSONArray getJSONArray(JSONObject obj, String key) {
		JSONArray realArray = new JSONArray();
		if (null != obj) {
			if (StringUtils.isNotBlank(key)) {
				try {
					realArray = obj.getJSONArray(key);
				} catch (Exception e) {
					LOG.error("方法名getJSONArray,在json获取key：的时候发生异常。key=" + key,
							e);
				}
			}
		}
		return realArray;
	}

	/**
	 * Description:获取jsonObject <br>
	 * 
	 * @author shiming<br>
	 * @taskId <br>
	 * @param obj
	 *            JSONObject
	 * @param key
	 *            key
	 * @return <br>
	 */
	public static JSONObject getJSONObject(JSONObject obj, String key) {
		JSONObject realObj = new JSONObject();
		if (null != obj) {
			if (StringUtils.isNotBlank(key)) {
				try {
					realObj = obj.getJSONObject(key);
				} catch (Exception e) {
					LOG.error("方法名getJSONObject,在json获取key：的时候发生异常。key=" + key,
							e);
				}
			}
		}
		return realObj;
	}

	/**
	 * Description: 获取json string<br>
	 * 
	 * @author shiming<br>
	 * @taskId <br>
	 * @param obj
	 *            JSONObject
	 * @param key
	 *            key
	 * @return <br>
	 */
	public static String getString(JSONObject obj, String key) {
		String realString = "";
		if (null != obj) {
			if (StringUtils.isNotBlank(key)) {
				try {
					realString = obj.getString(key);
				} catch (Exception e) {
					LOG.error("方法名getString,在json获取key：的时候发生异常。key=" + key);
				}
			}
		}
		return realString;
	}

	/**
	 * 此方法描述的是：根据key取值int
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return int
	 */
	public static int getInt(JSONObject obj, String key) {
		int val = 0;
		if (obj.containsKey(key)) {
			try {
				val = obj.getIntValue(key);
			} catch (Exception e) {
				LOG.error("方法名getInt,在json获取key：的时候发生异常。key=" + key);
			}
		}
		return val;
	}

	/**
	 * sort(以key为索引对json array进行排序)
	 * 
	 * @param json
	 * @param key
	 * @return
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings("finally")
	public static JSONArray sort(JSONArray json, String key, boolean isDate,
			short sort) {
		try {
			if (isDate) {

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				for (int j = 1, jl = json.size(); j < jl; j++) {
					JSONObject temp = json.getJSONObject(j);
					Date val = sdf.parse((String) temp.get(key));

					int i = j - 1;
					if (sort == JsonUtil.ASC) {
						while (i >= 0
								&& (sdf.parse((String) json.getJSONObject(i)
										.get(key))).compareTo(val) > 0) {
							json.set(i + 1, json.getJSONObject(i));
							i = i - 1;
						}
					}
					if (sort == JsonUtil.DESC) {
						while (i >= 0
								&& (sdf.parse((String) json.getJSONObject(i)
										.get(key))).compareTo(val) < 0) {
							json.set(i + 1, json.getJSONObject(i));
							i = i - 1;
						}
					}
					json.set(i + 1, temp);
				}
			} else {
				for (int j = 1, jl = json.size(); j < jl; j++) {
					JSONObject temp = json.getJSONObject(j);
					String val = (String) temp.get(key);

					int i = j - 1;
					if (sort == JsonUtil.ASC) {
						while (i >= 0
								&& ((String) json.getJSONObject(i).get(key))
										.compareTo(val) > 0) {
							json.set(i + 1, json.getJSONObject(i));
							i = i - 1;
						}
					}
					if (sort == JsonUtil.DESC) {
						while (i >= 0
								&& ((String) json.getJSONObject(i).get(key))
										.compareTo(val) < 0) {
							json.set(i + 1, json.getJSONObject(i));
							i = i - 1;
						}
					}
					json.set(i + 1, temp);
				}
			}
		} catch (ParseException e) {
			LOG.error("日期格式错误，请检查！");
			e.printStackTrace();
		} finally {
			return json;
		}
	}

	/**
	 * filterEnabled(过滤物流信息的返回，只取IsEnabled=1的记录)
	 * 
	 * @param json
	 * @return
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static JSONArray filterEnabled(JSONArray json) {
		JSONObject temp;
		JSONArray result = new JSONArray();
		for (int i = 0; i < json.size(); i++) {
			temp = json.getJSONObject(i);
			if ("1".equals(temp.get("IsEnabled").toString().trim())) {
				result.add(temp);
			}
		}
		return result;
	}

	public static JSONArray filterRepetition(JSONArray json, String key) {
		json = sort(json, "CreateDate",true, DESC);
		JSONArray result = new JSONArray();
		boolean isExisted = false;
		for (int i = 0; i < json.size(); i++) {
			isExisted = false;
			for (int j = 0; j < result.size(); j++) {
				if (json.getJSONObject(i).get(key).toString()
						.equals(result.getJSONObject(j).get(key).toString())) {
					isExisted = true;
				}
			}
			if (!isExisted) {
				result.add(json.getJSONObject(i));
			}
		}
		return result;
	}
	public static <T> List<T> parseJsonList(List<String> list, Class<T> clazz) {
	      if (list == null) {
	          return null;
	      }

	      List<T> result = new ArrayList<T>();
	      for (String s : list) {
	          result.add(parseJson(s, clazz));
	      }
	      return result;
	  }
	public static <T> T parseJson(String json, Class<T> clazz) {
	      return JSON.parseObject(json, clazz);
	  }
}
