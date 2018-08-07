package com.k12.common.init;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;


// 初始化文件
public class InitListener implements ServletContextListener {

	private final static Logger logger = LoggerFactory.getLogger(InitListener.class);
	
	public void contextInitialized(ServletContextEvent sce) {
	    //server程序启动后，需要显式调用心跳开关
	    MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}
}
