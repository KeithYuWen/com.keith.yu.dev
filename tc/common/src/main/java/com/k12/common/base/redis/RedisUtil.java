package com.k12.common.base.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set; 
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

  
/**
 * 项目名称：greenpass-api 
 * 类名称：RedisUtil 
 * 类描述：redis cache 工具类 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年6月24日 上午9:56:35 
 * 修改人：徐洪杰 
 * 修改时间：2016年6月24日 上午9:56:35 
 * 修改备注：
 * @version 1.0*
 */
public class RedisUtil { 
  private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);
  
  private RedisTemplate<String, String> redisTemplate;
  
  public void setRedisTemplate(RedisTemplate<String, String> redisTemp) {
      redisTemplate = redisTemp;
  }
   
  /* ----------- common --------- */
  public void multi(){
      redisTemplate.multi();
  }
  public void discard(){
      redisTemplate.discard();
  }
  public Collection<String> keys(String pattern) {
      return redisTemplate.keys(pattern);
  }
   
  public void delete(String key) {
      redisTemplate.delete(key);
  }

  public void delete(Collection<String> key) {
      redisTemplate.delete(key);
  }
  /**
   * exists(key是否存在) 
   * @param key
   * @return 
   */
  public boolean exists(String key){
      return redisTemplate.hasKey(key);
  }
  /**
   * persist(移除 key 的过期时间，key 将持久保持。) 
   * @param key
   * @return 
   */
  public boolean persist(String key){
      return redisTemplate.persist(key);
  }
  /**
   * type(返回 key 所储存的值的类型。) 
   * @param key
   * @return 
   */
  public String type(String key){
      return redisTemplate.type(key).name();
  }
  /* ----------- string --------- */
  public <T> T get(String key, Class<T> clazz) {
      String value = redisTemplate.opsForValue().get(key);
      return parseJson(value, clazz);
  }
   
  public <T> List<T> mget(Collection<String> keys, Class<T> clazz) {
      List<String> values = redisTemplate.opsForValue().multiGet(keys);
      return parseJsonList(values, clazz);
  }
  /**
   * set(将给定 key 的值设为 obj的json字符串) 
   * @param key
   * @param obj
   * @param timeout
   * @param unit 
   */
  public <T> void set(String key, T obj, Long timeout, TimeUnit unit) {
      if (obj == null) {
          return;
      }
      String value = toJson(obj);
      if (timeout != null) {
          redisTemplate.opsForValue().set(key, value, timeout, unit);
      } else {
          redisTemplate.opsForValue().set(key, value);
      }
  }
  /**
   * setIfAbsent(只有在 key 不存在时设置 key 的值。) 
   * @param key
   * @param obj
   * @return 
   */
  public <T> boolean setIfAbsent(String key, T obj){
      if(obj == null){
          return false;
      }
      String value = toJson(obj);
      return redisTemplate.opsForValue().setIfAbsent(key, value);
  }
  /**
   * getAndSet(将给定 key 的值设为 value ，并返回 key 的旧值(old value)。) 
   * @param key
   * @param obj
   * @param clazz
   * @return 
   */
  public <T> T getAndSet(String key, T obj, Class<T> clazz) {
      if (obj == null) {
          return get(key, clazz);
      }
      String value = redisTemplate.opsForValue().getAndSet(key, toJson(obj));
      return parseJson(value, clazz);
  }
   
  public int decrement(String key, int delta) {
      Long value = redisTemplate.opsForValue().increment(key, -delta);
      return value.intValue();
  }
   
  public int increment(String key, int delta) {
      Long value = redisTemplate.opsForValue().increment(key, delta);
      return value.intValue();
  }
  
  /* ----------- list --------- */
  public int size(String key) {
      return redisTemplate.opsForList().size(key).intValue();
  }

  public <T> List<T> range(String key, long start, long end, Class<T> clazz) {
      List<String> list = redisTemplate.opsForList().range(key, start, end);
      return parseJsonList(list, clazz);
  }

  public void rightPushAll(String key, Collection<?> values, Long timeout,
          TimeUnit unit) {
      if (values == null || values.isEmpty()) {
          return;
      }
       
      redisTemplate.opsForList().rightPushAll(key, toJsonList(values));
      if (timeout != null) {
          redisTemplate.expire(key, timeout, unit);
      }
  }
   
  public <T> void leftPush(String key, T obj) {
      if (obj == null) {
          return;
      }
       
      redisTemplate.opsForList().leftPush(key, toJson(obj));
  }

  public <T> T leftPop(String key, Class<T> clazz) {
      String value = redisTemplate.opsForList().leftPop(key);
      return parseJson(value, clazz);
  }
  /**
   * remove(
   * COUNT 的值可以是以下几种：
    count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
    count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
    count = 0 : 移除表中所有与 VALUE 相等的值。) 
   * @param key
   * @param count
   * @param obj 
   * @since CodingExample　Ver(编码范例查看) 1.1
   */
  public void remove(String key, int count, Object obj) {
      if (obj == null) {
          return;
      }
       
      redisTemplate.opsForList().remove(key, count, toJson(obj));
  }
   
  /* ----------- zset --------- */
  /**
   * zcard(获取有序集合的成员数) 
   * @param key
   * @return 
   */
  public int zcard(String key) {
      return redisTemplate.opsForZSet().zCard(key).intValue();
  }
  /**
   * zrange(通过索引区间返回有序集合成指定区间内的成员) 递增
   * @param key
   * @param start
   * @param end
   * @param clazz
   * @return 
   */
  public <T> List<T> zrange(String key, long start, long end, Class<T> clazz) {
      Set<String> set = redisTemplate.opsForZSet().range(key, start, end);
      return parseJsonList(setToList(set), clazz);
  }
  /**
   * zrevrange(通过索引区间返回有序集合成指定区间内的成员) 递减
   * @param key
   * @param start
   * @param end
   * @param clazz
   * @return 
   */
  public <T> List<T> zrevrange(String key, long start, long end, Class<T> clazz){
      Set<String> set = redisTemplate.opsForZSet().reverseRange(key, start, end);
      return parseJsonList(setToList(set), clazz);
  }
  private List<String> setToList(Set<String> set) {
      if (set == null) {
          return null;
      }
      return new ArrayList<String>(set);
  }
   
  public void zadd(String key, Object obj, double score) {
      if (obj == null) {
          return;
      }
      redisTemplate.opsForZSet().add(key, toJson(obj), score);
  }
   
  public void zaddAll(String key, Set<TypedTuple<String>> tupleSet, Long timeout, TimeUnit unit) {
//      if (tupleList == null || tupleList.isEmpty()) {
//          return;
//      }
//       
//      Set<TypedTuple<String>> tupleSet = toTupleSet(tupleList);
      if(CollectionUtils.isEmpty(tupleSet)){
          return;
      }
      redisTemplate.opsForZSet().add(key, tupleSet);
      if (timeout != null) {
          redisTemplate.expire(key, timeout, unit);
      }
  }
   
//  private Set<TypedTuple<String>> toTupleSet(List<TypedTuple<?>> tupleList) {
//      Set<TypedTuple<String>> tupleSet = new LinkedHashSet<TypedTuple<String>>();
//      for (TypedTuple<?> t : tupleList) {
//          tupleSet.add(new DefaultTypedTuple<String>(toJson(t.getValue()), t.getScore()));
//      }
//      return tupleSet;
//  }
   
  public void zrem(String key, Object obj) {
      if (obj == null) {
          return;
      }
      redisTemplate.opsForZSet().remove(key, toJson(obj));
  }
   
  public void unionStore(String destKey, Collection<String> keys, Long timeout, TimeUnit unit) {
      if (keys == null || keys.isEmpty()) {
          return;
      }
       
      Object[] keyArr = keys.toArray();
      String key = (String) keyArr[0];
       
      Collection<String> otherKeys = new ArrayList<String>(keys.size() - 1);
      for (int i = 1; i < keyArr.length; i++) {
          otherKeys.add((String) keyArr[i]);
      }
       
      redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
      if (timeout != null) {
          redisTemplate.expire(destKey, timeout, unit);
      }
  }
   
  /* ----------- tool methods --------- */
  public String toJson(Object obj) {
      return JSON.toJSONString(obj, SerializerFeature.SortField);
  }

  public <T> T parseJson(String json, Class<T> clazz) {
      return JSON.parseObject(json, clazz);
  }

  public List<String> toJsonList(Collection<?> values) {
      if (values == null) {
          return null;
      }

      List<String> result = new ArrayList<String>();
      for (Object obj : values) {
          result.add(toJson(obj));
      }
      return result;
  }
   
  public <T> List<T> parseJsonList(List<String> list, Class<T> clazz) {
      if (list == null) {
          return null;
      }

      List<T> result = new ArrayList<T>();
      for (String s : list) {
          result.add(parseJson(s, clazz));
      }
      return result;
  }
}
