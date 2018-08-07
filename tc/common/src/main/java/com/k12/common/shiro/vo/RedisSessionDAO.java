package com.k12.common.shiro.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k12.common.util.ehcache.RedisUtil;

public class RedisSessionDAO extends AbstractSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

	/**
	 * The Redis key prefix for the sessions
	 */
	private String keyPrefix = "shiro_sid:";

	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}

	/**
	 * save session
	 * 
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		byte[] key = getByteKey(session.getId());
		byte[] value = sessionToByte(session);
		RedisUtil.setByteArray(key, value);
	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		RedisUtil.delByteArray(getByteKey(session.getId()));

	}

	// 用来统计当前活动的session
	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		List<byte[]> values = RedisUtil.getAllByteValues((this.keyPrefix + "*").getBytes());
		if (values != null && values.size() > 0) {
			for (byte[] value : values) {
				Session s = byteToSession(value);
				sessions.add(s);
			}
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			logger.error("session id is null");
			return null;
		}
		Session s =byteToSession(RedisUtil.getByteArray(this.getByteKey(sessionId)));
		return s;
	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(Serializable sessionId) {
		String preKey = this.keyPrefix + sessionId;
		return preKey.getBytes();
	}

	/**
	 * Returns the Redis session keys prefix.
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key prefix.
	 * 
	 * @param keyPrefix
	 *            The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	// 把session对象转化为byte保存到redis中
	public byte[] sessionToByte(Session session) {
		byte[] bytes=null;
		ByteArrayOutputStream bo =null;
		ObjectOutputStream oo=null;
		try {
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(session);
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

	// 把byte还原为session
	public Session byteToSession(byte[] bytes) {
		SimpleSession session = null;
		if(bytes != null){
			ByteArrayInputStream bi =null;
			ObjectInputStream in =null;
			try {
				bi = new ByteArrayInputStream(bytes);
				in = new ObjectInputStream(bi);
				session = (SimpleSession) in.readObject();
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
		return session;
	}
}
