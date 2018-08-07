package com.k12.common.shiro.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.k12.common.util.common.NumberBytesUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k12.common.util.ehcache.RedisUtil;

/**
 * @author Dylan
 * @time 2014年1月6日
 * @param <K>
 * @param <V>
 */
public class RedisCache<K,V> implements Cache<K, V>  {
	
	private final static Logger LOG = LoggerFactory.getLogger(RedisCache.class);
	
	private String CACHE_PREFIX;

	public RedisCache(String cacheName) {
		CACHE_PREFIX = cacheName;
	}
	
	@Override
	public V get(K key) throws CacheException {
		V value=null;
		try{
			byte[] s=RedisUtil.getByteArray(keyToByte(key));
			value=byteToValue(s);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("get the entity json is " + key + " : " + value);
		}
		return value;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if(LOG.isDebugEnabled()){
			LOG.debug("begin save the "+ key + " : " + value+" to memcache");
		}
		RedisUtil.setByteArray(keyToByte(key), valueToByte(value));
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		if(LOG.isDebugEnabled()){
			LOG.debug("begin remove the "+key+" from memcache");
		}
		V value = get(key);
		RedisUtil.delByteArray(keyToByte(key));
		return value;
	}

	@Override
	public void clear() throws CacheException {
		for(Iterator<K> keys =keys().iterator();keys.hasNext();){
			K key = keys.next();
			remove(key);
		}
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Set<K> keys() {
		return (Set<K>)RedisUtil.getAllKeys(CACHE_PREFIX+"*") ;
	}

	@Override
	public Collection<V> values() {
		return (Collection<V>)RedisUtil.getAllValues(CACHE_PREFIX+"*");
	}
	
	
	private byte[] keyToByte(K key){
		String k = String.valueOf(key);
		if(StringUtils.startsWith(k, CACHE_PREFIX)){
			return k.getBytes();
		}
		return (CACHE_PREFIX+k).getBytes();
	}
	
	
	private byte[] valueToByte(V value) {
		byte[] bytes =null;// numberToByte(value);
		/*if(bytes != null){
			return bytes;
		}*/
		ByteArrayOutputStream bo =null;
		ObjectOutputStream oo=null;
		try {
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(value);
			bytes = bo.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(oo != null){
					oo.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bo != null){
					bo.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	private V byteToValue(byte[] bytes) {
		V value = null;//byteToNumber(bytes);
		if(value == null && bytes != null){
			ByteArrayInputStream bi =null;
			ObjectInputStream in =null;
			try {
				bi = new ByteArrayInputStream(bytes);
				in = new ObjectInputStream(bi);
				value = (V) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(in != null){
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(bi != null){
						bi.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public byte[] numberToByte(V value) {
		if(value instanceof Integer){
			return NumberBytesUtil.intToByte4((Integer)value);
		}else{
			return null;
		}
	}
	
	public V byteToNumber(byte[] value) {
		try {
			return (V) NumberBytesUtil.byte4ToInt(value, 0);
		} catch (Exception e) {
			return null;
		}
	}
}