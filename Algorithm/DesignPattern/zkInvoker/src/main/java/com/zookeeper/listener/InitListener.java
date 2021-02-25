package com.zookeeper.listener;

import com.zookeeper.utils.LoadBalance;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/2/2 - 15:46
 */
@WebListener
public class InitListener implements ServletContextListener {

    private static final String BASE_SERVICE = "/zookeeper";

    private static final String SERVICE_NAME = "/server";

    private ZooKeeper zooKeeper;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){

    }

    public void init(){
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181",5000, (watchedEvent -> {
                if(watchedEvent.getType()== Watcher.Event.EventType.NodeChildrenChanged && watchedEvent.getPath().equals(BASE_SERVICE+SERVICE_NAME)){
                    updateServerList();
                }
            }));
            updateServerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateServerList(){
        List<String> newServiceList = new ArrayList<>();
        try {
            List <String> children = zooKeeper.getChildren(BASE_SERVICE+SERVICE_NAME,true);
            for(String subNode:children){
                byte [] data = zooKeeper.getData(BASE_SERVICE + SERVICE_NAME +"/" + subNode,false,null);
                String host = new String(data,"utf-8");
                System.out.println("host："+host);
                newServiceList.add(host);
            }
            LoadBalance.SERVICE_LIST = newServiceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}