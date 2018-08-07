package com.k12.common.base.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
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
public class JedisClusterClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(JedisClusterClient.class);
    
    private JedisCluster jedisCluster;
    
    public static void main(String[] args) {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();  
        jedisClusterNodes.add(new HostAndPort("192.168.227.185", 7000));  
        jedisClusterNodes.add(new HostAndPort("192.168.227.185", 7001));  
        jedisClusterNodes.add(new HostAndPort("192.168.227.185", 7002));  
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
//        jc.auth("redis");
        String keys = "name";  
          
        // 删数据  
//        jedis.del(keys);  
        // 存数据  
//        jc.set(keys, "snowolf");  
        // 取数据  
        String value = jc.get(keys);  
          
        System.out.println(value); 
    }

    /**
     * exists(健值是否存在) 
     * @param key
     * @return 
     * @since xuhongjie
     */
    public boolean exists(String key){
        return jedisCluster.exists(key);
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
        Set<String> set = jedisCluster.zrevrange(key, start, end);
        return parseJsonList(setToList(set), clazz);
    }
    /**
     * zaddAll(向有序集合添加多个成员，或者更新已存在成员的分数) 
     * @param key
     * @param scoreMembers
     * @param timeout 
     * @since xuhongjie
     */
    public void zaddAll(String key, Map<String, Double> scoreMembers, int timeout) {
        if (CollectionUtils.isEmpty(scoreMembers)) {
            return;
        }
        jedisCluster.zadd(key, scoreMembers);
        if (timeout != 0) {
            jedisCluster.expire(key, timeout);
        }
    }
    
    public int zcard(String key) {
        return jedisCluster.zcard(key).intValue();
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

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

}
