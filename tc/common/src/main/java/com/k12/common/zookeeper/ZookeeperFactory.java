package com.k12.common.zookeeper;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperFactory {
    
    private static CuratorFramework client = null;
    
    private ZookeeperFactory(String connectString, int sessionTimeoutMs, int maxRetries, int baseSleepTimems){
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(new ExponentialBackoffRetry(baseSleepTimems, maxRetries))
                .build();
    }
    
    public static synchronized CuratorFramework getClient(String connectString, int sessionTimeoutMs, int maxRetries, int baseSleepTimems){
        if(client == null){
            new ZookeeperFactory(connectString,sessionTimeoutMs,maxRetries,baseSleepTimems);
        }
        return client;
    }
    
}
