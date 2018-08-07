package com.k12.common.base.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.k12.common.exception.SystemException;
/**
 * 项目名称：greenpass-api 
 * 类名称：JedisClient 
 * 类描述：Jsk12，Jedis。
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年8月1日 下午3:06:31 
 * 修改人：徐洪杰 
 * 修改时间：2016年8月1日 下午3:06:31 
 * 修改备注：
 * @version 1.0*
 */
public class JedisClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(JedisClient.class);
    
//    @Value("${redis.host}")
//    private static String host;
//    @Value("${redis.port}")
//    private static String port;
//    @Value("${redis.pass}")
//    private static String pass;
//    
//    @Value("${redis.pool.maxTotal}")
//    private static String maxTotal;
//    @Value("${redis.pool.maxIdle}")
//    private static String maxIdle;
//    @Value("${redis.pool.maxWaitMillis}")
//    private static String maxWaitMillis;
//    @Value("${redis.pool.testOnBorrow}")
//    private static String testOnBorrow;
//    @Value("${redis.pool.testOnReturn}")
//    private static String testOnReturn;
    
    private JedisPool pool;
    
    static {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(Integer.valueOf(maxTotal));
//        config.setMaxIdle(Integer.valueOf(maxIdle));
//        config.setMaxWaitMillis(Long.valueOf(maxWaitMillis));
//        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
//        config.setTestOnReturn(Boolean.valueOf(testOnReturn));
//        pool = new JedisPool(config, host, Integer.valueOf(port), 2000, pass);
    }
    
    public static void main(String[] args) {
        Jedis jedis = new Jedis("183.129.244.135",6379);  
        jedis.auth("redis@2016");
        String keys = "name";  
          
        // 删数据  
        jedis.del(keys);  
        // 存数据  
        jedis.set(keys, "snowolf");  
        // 取数据  
        String value = jedis.get(keys);  
          
        System.out.println(value); 
    }

    public JedisPool getPool() {
        return pool;
    }

    /** 
     * 获取Redis实例. 
     * @return Redis工具类实例 
     */
    public Jedis getJedis(){
        Jedis jedis  = null;  
        try {
            jedis = pool.getResource();
            // log.info("get redis master1!");
        } catch (Exception e) {
            LOG.error("get redis failed!", e);
            // 销毁对象
            pool.returnBrokenResource(jedis);
            throw new SystemException("get redis failed!");
        }
        return jedis;  
    }
    
    /** 
     * 释放redis实例到连接池. 
     * @param jedis redis实例 
     */  
    public void closeJedis(Jedis jedis) {  
        if(jedis != null) {  
            pool.returnResource(jedis);  
        }  
    }  
    /**
     * exists(健值是否存在) 
     * @param key
     * @return 
     * @since xuhongjie
     */
    public boolean exists(String key){
        Jedis jedis = null;
        try {  
            jedis = getJedis();
            return jedis.exists(key); 
        } catch (Exception e) {  
            //释放redis对象  
            e.printStackTrace();
            throw new SystemException("redis operate exists failed!");
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
    }
    /**
     * zrevrange(返回有序集中指定区间内的成员，通过索引，分数从高到底) 
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @return 
     * @since xuhongjie
     */
    public <T> List<T> zrevrange(String key, long start, long end, Class<T> clazz){
        Jedis jedis = null;
        try {  
            jedis = getJedis();
            Set<String> set = jedis.zrevrange(key, start, end);
            return parseJsonList(setToList(set), clazz); 
        } catch (Exception e) {  
            //释放redis对象  
            e.printStackTrace();
            throw new SystemException("redis operate zrevrange failed!");
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
    }
    /**
     * zaddAll(向有序集合添加多个成员，或者更新已存在成员的分数) 
     * @param key
     * @param scoreMembers
     * @param timeout 
     * @since xuhongjie
     */
    public void zaddAll(String key, Map<String, Double> scoreMembers, int timeout) {
        Jedis jedis = null;
        try {  
            if(CollectionUtils.isEmpty(scoreMembers)){
                return;
            }
            jedis = getJedis();
            jedis.zadd(key, scoreMembers);
            if (timeout != 0) {
                jedis.expire(key, timeout);
            }
        } catch (Exception e) {  
            //释放redis对象  
            e.printStackTrace();
            throw new SystemException("redis operate zaddAll failed!");
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
    }
    
    public int zcard(String key) {
        Jedis jedis = null;
        try {  
            jedis = getJedis();
            return jedis.zcard(key).intValue();
        } catch (Exception e) {  
            //释放redis对象  
            e.printStackTrace();
            throw new SystemException("redis operate zcard failed!");
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
    }
    /** 
     * 返还到连接池 
     *  
     * @param pool  
     * @param redis 
     */  
    public static void returnResource(JedisPool pool, Jedis redis) {  
        if (redis != null) {  
            pool.returnResource(redis);  
        }  
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
    
    private static List<String> setToList(Set<String> set) {
        if (set == null) {
            return null;
        }
        return new ArrayList<String>(set);
    }
    public static <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }
    
}
