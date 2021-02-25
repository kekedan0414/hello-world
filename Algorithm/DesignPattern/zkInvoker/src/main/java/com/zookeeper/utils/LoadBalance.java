package com.zookeeper.utils;

import java.util.List;

/**
 * @author cocoegg
 * @date 2021/2/2 - 15:47
 */
public abstract class LoadBalance {
    public volatile static List<String> SERVICE_LIST;
    public abstract String choseServiceHost();
}