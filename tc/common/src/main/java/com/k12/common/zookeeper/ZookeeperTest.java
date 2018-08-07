package com.k12.common.zookeeper;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;



public class ZookeeperTest {
    static String path = "/dev/all/config/system_param_init_line.properties";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
    .connectString("192.168.226.145:2181,192.168.226.234:2182,192.168.226.207:2183")
    .sessionTimeoutMs(5000)
    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
    .build();
    public static void main(String[] args) throws Exception {
        client.start();
        //add---------
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "test".getBytes());
        
        //set&get-----------
//        Stat stat = new Stat();
//        System.out.println(stat.getAversion());
//        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));
//        client.setData().forPath(path, "test2".getBytes());
//        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));
        
        //listen---------nodecache
//        final NodeCache cache = new NodeCache(client, path, false);
//        cache.start();
//        cache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("new data:" + new String(cache.getCurrentData().getData()));
//            }
//        });
//        Thread.sleep(60000);
        
        //listen---------PathChildrenCache
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(StartMode.BUILD_INITIAL_CACHE);//创建cache后就从服务端拉入数据
        List<ChildData> childDatas = cache.getCurrentData();
        for (ChildData data : childDatas) {
        	
        	String path = data.getPath();
        	path=path.substring(path.lastIndexOf("/")+1, path.length());
        	
            System.out.println("create /dev/all/config/system_param_init_line.properties/"+path+ " " + new String(data.getData()));
        }
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("add child," + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("upd child," + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("del child," + event.getData().getPath());
                    break;
                default:
                    break;
                }
            }
        });
        
//        Thread.sleep(1000);
//        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/child3");
        Thread.sleep(60000);
    }
}
