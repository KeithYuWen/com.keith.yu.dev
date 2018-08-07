package com.k12.common.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;


public class ZookeeperPropertyPlaceHolderConfigurer extends PreferencesPlaceholderConfigurer{
    
    @Override
    protected Properties mergeProperties() throws IOException {
        Properties result = new Properties();

        if (this.localOverride) {
            super.loadProperties(result);
        }

        if (this.localProperties != null) {
            for (Properties localProp : this.localProperties) {
                CollectionUtils.mergePropertiesIntoMap(localProp, result);
            }
        }

        if (!(this.localOverride)) {
            loadProperties(result);
        }
        
        //从zookeeper读取数据
        CuratorFramework client = ZookeeperFactory
                .getClient(result.getProperty("connectString"),Integer.valueOf(result.getProperty("sessionTimeoutMs")),Integer.valueOf(result.getProperty("maxRetries")),Integer.valueOf(result.getProperty("baseSleepTimems")));
        if (!CuratorFrameworkState.STARTED.equals(client.getState())) {
            client.start();
        }
        
        String[] configResult = result.getProperty("configAndSysPropPath").split(";");
        for (String config : configResult) {
        	splitProperty(result,client,config);
		}
        return result;
    }
    
    
    public void splitProperty(Properties result,CuratorFramework client,String address) throws IOException{
    	
        PathChildrenCache cache = new PathChildrenCache(client, address, true);
    	try {
    		cache.start(StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            throw new IOException(e);
        }
    	
        List<ChildData> configChildDatas = cache.getCurrentData();
        for (ChildData data : configChildDatas) {
            //遍历设置全局变量
            String[] pathArr = data.getPath().split("/");
            String key = pathArr[pathArr.length-1];
            String value = new String(data.getData());
            System.out.println(key + " = " + value);
            if (!result.containsKey(key) || StringUtils.isEmpty(result.get(key))) {
            	Properties property = new Properties();
            	property.put(key, value);
            	CollectionUtils.mergePropertiesIntoMap(property, result);
            }
        }
        
        cache.getListenable().addListener(new PathChildrenCacheListener() {
    		
    		@Override
    		public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
    			switch (event.getType()) {
    			case CHILD_ADDED:
    				System.out.println("add child," + event.getData().getPath());
    				break;
    			case CHILD_UPDATED:
    				System.out.println("upd child," + event.getData().getPath() + "," + new String(event.getData().getData()));
    				XmlWebApplicationContext webApplicationContext = (XmlWebApplicationContext)ContextLoader.getCurrentWebApplicationContext();
    				webApplicationContext.refresh();
    				break;
    			case CHILD_REMOVED:
    				System.out.println("del child," + event.getData().getPath());
    				break;
    			default:
    				break;
    			}
    		}
    	});
        
    }
    
}
