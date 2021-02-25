package com.yss.jump;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author cocoegg
 * @date 2021/2/8 - 15:51
 */
public class zkLock implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.2.106:2184",500,new zkLock());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        System.out.println("zk established!");

        //String s = zooKeeper.create("/client1","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        //System.out.println(s);
        zooKeeper.setData("/client1","cocoegg0914".getBytes(),-1);
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/client1", false, stat);
        System.out.println(new String(data, "utf-8"));
        System.out.println(stat.getVersion());


    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("receive the event:" + watchedEvent);
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }
}
