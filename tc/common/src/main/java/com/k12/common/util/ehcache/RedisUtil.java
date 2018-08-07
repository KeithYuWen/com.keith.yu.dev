package com.k12.common.util.ehcache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.k12.common.init.SystemParamInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPool jedisPool;
    //计数器的过期时间默认2天
    private static int countExpireTime = 2*24*3600; 
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    static {
        initPool();        
    }
    // 初始化连接池
    public static void initPool(){
    	//初始化数据
    	countExpireTime= SystemParamInit.getRedisTimeout();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(SystemParamInit.getRedisPoolMaxTotal());
        config.setMaxIdle(SystemParamInit.getRedisPoolMaxIdle());
        config.setMaxWaitMillis(SystemParamInit.getRedisPoolMaxWaitMillis());
        config.setTestOnBorrow(SystemParamInit.getRedisPoolTestOnBorrow());
        config.setTestOnReturn(SystemParamInit.getRedisPoolTestOnReturn());
        jedisPool = new JedisPool(config, SystemParamInit.getRedisHost(), SystemParamInit.getRedisPort(), 10000, SystemParamInit.getRedisPass().equals("123456") ? null : SystemParamInit.getRedisPass());
    }
    
    /**
     * 从连接池获取redis连接
     * @return
     * @author LDz
     */
    public static Jedis getJedis(){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
        } catch(Exception e){
        	logger.error("获取redis连接异常",e);
        }
        return jedis;
    }
    
    /**
     * 回收redis连接
     * @param jedis
     * @author LDz
     */
    public static boolean recycleJedis(Jedis jedis){
        if(jedis != null){
            try{
                jedis.close();
                return true;
            } catch(Exception e){
            	logger.error("回收redis连接异常",e);
            }
        }
        return false;
    }
    
    
    /**
	 * 保存字符串数据
	 * @param key
	 * @param value
	 * @author LDz
	 */
	public static boolean setString(String key, String value) {
		return setString(key, value,null);
	}
    
	/**
	 * 保存字符串数据
	 * @param key
	 * @param value
	 * @param time 过期时间
	 * @author LDz
	 */
	public static boolean setString(String key, String value, Integer time) {
		Jedis jedis = getJedis();
		if (jedis != null) {
			try {
				jedis.set(key, value);
				if (time == null) {
					jedis.expire(key, countExpireTime);
				} else {
					jedis.expire(key, time);
				}
				return true;
			} catch (Exception e) {
				logger.error("保存字符串数据异常", e);
			} finally {
				recycleJedis(jedis);
			}
		}
		return false;
	}

    /**
     * 获取字符串类型的数据
     * @param key
     * @return
     * @author LDz
     */
    public static String getString(String key){
        Jedis jedis = getJedis();
        String result = "";
        if(jedis != null){
            try{
                result = jedis.get(key);
            }catch(Exception e){
            	logger.error("获取字符串数据异常", e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return result;
    }
    
    /**
     * 删除字符串数据
     * @param key
     * @author LDz
     */
    public static boolean delString(String key){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.del(key);
                return true;
            }catch(Exception e){
            	logger.error("删除字符串数据异常", e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return false;
    }
    
    /**
     * 保存byte类型数据
     * @param key
     * @param value
     * @author LDz
     */
    public static boolean setByteArray(byte[] key, byte[] value){
    	return setByteArray(key, value, null);
    }
    
    /**
     * 保存byte类型数据
     * @param key
     * @param value
     * @param time 过期时间
     * @author LDz
     */
    public static boolean setByteArray(byte[] key, byte[] value , Integer time){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.set(key, value);
                if(time == null){
                	jedis.expire(key, countExpireTime);
                }else{
                	jedis.expire(key, time);
                }
                return true;
            } catch(Exception e){
            	logger.error("保存byte类型数据", e);
            } finally{
                recycleJedis(jedis);
            }
        } 
        return false;
    }
    
    /**
     * 获取byte类型数据
     * @param key
     * @return
     * @author LDz
     */
    public static byte[] getByteArray(byte[] key){
        Jedis jedis = getJedis();
        byte[] bytes = null;
        if(jedis != null){
            try{
                bytes = jedis.get(key);
            }catch(Exception e){
            	logger.error("获取byte类型数据异常", e);
            } finally{
                recycleJedis(jedis);
            }
        }    
        return bytes;
        
    }
    
    /**
     * 删除byte数组数据
     * @param key
     * @return
     * @author LDz
     */
    public static boolean delByteArray(byte[] key){
    	Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.del(key);
                return true;
            }catch(Exception e){
            	logger.error("删除byte数组数据异常", e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return false;
    }
    
    
    /**
     * key对应的整数value加1,不存在则添加（默认超时时间）
     * @param key
     * @author LDz
     */
    public static byte[] updateOrSetByteArray(byte[] key, byte[] value){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                if(!jedis.exists(key)){
                    jedis.set(key, value);
                    jedis.expire(key, countExpireTime);
                } else {
                    return jedis.get(key);
                }
            }catch(Exception e){
                logger.error("key对应的整数value加1,不存在则添加（默认超时时间）出现异常",e);
            } finally{
                recycleJedis(jedis);
            }
        } 
        return value;
    }
    
    
    /**
     * 更新字符串过期时间
     * @param key
     * @return
     * @author LDz
     */
    public static boolean afreshString(String key){
    	return afreshString(key, null);
    }
    
    /**
     * 更新字符串过期时间
     * @param key
     * @param time 过期时间
     * @return
     * @author LDz
     */
    public static boolean afreshString(String key,Integer time){
    	Jedis jedis = getJedis();
        if(jedis != null){
            try{
            	if(time == null){
                	jedis.expire(key, countExpireTime);
                }else{
                	jedis.expire(key, time);
                }
            	return true;
            }catch(Exception e){
                logger.error("更新字符串过期时间",e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return false;
    }
    
    
    /**
     * 更新byte类型的数据过期时间
     * @param key
     * @author LDz
     */
    public static boolean afreshByteArray(byte[] key){
    	return afreshByteArray(key, null);
    }
    
    /**
     * 更新byte类型的数据过期时间
     * @param key
     * @param time 过期时间
     * @return
     * @author LDz
     */
    public static boolean afreshByteArray(byte[] key,Integer time){
    	logger.info("更新byte类型的数据过期时间");
    	Jedis jedis = getJedis();
        if(jedis != null){
            try{
            	if(time == null){
                	jedis.expire(key, countExpireTime);
                }else{
                	jedis.expire(key, time);
                }
            	return true;
            }catch(Exception e){
                logger.error("更新byte类型的数据过期时间",e);
            } finally{
                recycleJedis(jedis);
            }
        }    
        return false;
    }
    
    
    /**
     * key对应的整数value加1,不存在则添加（默认超时时间）
     * @param key
     * @author LDz
     */
    public static boolean inc(String key){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                if(!jedis.exists(key)){
                    jedis.set(key, "1");
                    jedis.expire(key, countExpireTime);
                } else {
                    // 加1
                    jedis.incr(key);
                }
                return true;
            }catch(Exception e){
                logger.error("key对应的整数value加1,不存在则添加（默认超时时间）出现异常",e);
            } finally{
                recycleJedis(jedis);
            }
        } 
        return false;
    }
    
    
    /**
     * key对应的整数value加1,不存在则添加
     * @param key
     * @param time 超时时间
     * @return
     * @author LDz
     */
    public static boolean inc(String key,Integer time){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                if(!jedis.exists(key)){
                    jedis.set(key, "1");
                } else {
                    // 加1
                    jedis.incr(key);
                }
                if(time == null){
                	jedis.expire(key, countExpireTime);
                }else{
                	jedis.expire(key, time);
                }
                return true;
            }catch(Exception e){
            	logger.error("key对应的整数value加1,不存在则添加 出现异常",e);
            } finally{
                recycleJedis(jedis);
            }
        } 
        return false;
    }
    
    /**
     * 获取所有keys
     * @param pattern
     * @return
     * @author LDz
     */
    public static Set<String> getAllKeys(String pattern){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                return jedis.keys(pattern);
            }catch(Exception e){
                logger.error("获取keys异常",e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return null;
    }
    
    /**
     * 获取所有keys
     * @param pattern
     * @return
     * @author LDz
     */
    public static Set<byte[]> getAllByteKeys(byte[] pattern){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                return jedis.keys(pattern);
            }catch(Exception e){
                logger.error("获取byte keys异常",e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return null;
    }
    
    /**
     * 获取所有Values
     * @param pattern key的pattern
     * @return
     * @author LDz
     */
    public static List<String> getAllValues(String pattern){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
            	List<String> values = new ArrayList<String>();
            	Set<String> keysSet=jedis.keys(pattern);
            	for(Iterator<String> keys =keysSet.iterator();keys.hasNext();){
        			String key = keys.next();
        			String value = jedis.get(key);
        			values.add(value);
        		}
                return values;
            }catch(Exception e){
                logger.error("获取values异常",e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return null;
    }
    
    /**
     * 获取所有Values
     * @param pattern key的pattern
     * @return
     * @author LDz
     */
    public static List<byte[]> getAllByteValues(byte[] pattern){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
            	List<byte[]> values = new ArrayList<byte[]>();
            	Set<byte[]> keysSet=jedis.keys(pattern);
            	for(Iterator<byte[]> keys =keysSet.iterator();keys.hasNext();){
            		byte[] key = keys.next();
            		byte[] value = jedis.get(key);
        			values.add(value);
        		}
                return values;
            }catch(Exception e){
                logger.error("获取byte values异常",e);
            } finally{
                recycleJedis(jedis);
            }
        }
        return null;
    }
}
