package com.k12.tc.common.listener;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class OnlineUserListener implements HttpSessionListener{
    @Override
    public void sessionCreated(HttpSessionEvent paramHttpSessionEvent) {
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent paramHttpSessionEvent) {
//        HttpSession session = paramHttpSessionEvent.getSession();
//        ServletContext servletContext = session.getServletContext();
//        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//        ILoginLogDao loginLogDaoImpl = (ILoginLogDao) context.getBean("loginLogDaoImpl");
//        if(session.getAttribute("loginLogId") != null){
//            //记录登录日志
//            LoginLog loginLog = new LoginLog();
//            loginLog.setLogoutTime(new Date());
//            loginLog.setId(Integer.valueOf(session.getAttribute("loginLogId").toString()));
//            loginLogDaoImpl.updateLoginLog(loginLog);
//        }
    }

}
