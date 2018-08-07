package com.k12.tc.common.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConfigListener implements ServletContextListener{
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public void contextInitialized(ServletContextEvent sce) { 
         Properties props = new Properties(); 
         InputStream inputStream = null; 
         try { 
             inputStream = getClass().getResourceAsStream("/config/resource/dev/baseconfig.properties");
             props.load(inputStream); 
             //存入全局变量
             Map<String, String> map = new HashMap<String, String>(((Map) props));
             for (String s : map.keySet()) {
				sce.getServletContext().setAttribute(s, map.get(s));
			 }
         } catch (IOException ex) { 
             ex.printStackTrace(); 
         }finally{
        	 try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
         }
     }

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
