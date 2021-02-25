package com.zookeeper.utils;

import org.springframework.util.CollectionUtils;

import java.util.Random;

/**
 * @author cocoegg
 * @date 2021/2/2 - 15:47
 */
public class RandomLoadBalance extends LoadBalance {

    @Override
    public String choseServiceHost() {
        String result = "";

        if(!CollectionUtils.isEmpty(SERVICE_LIST)){
            int index = new Random().nextInt(SERVICE_LIST.size());
            result = SERVICE_LIST.get(index);
        }
        return result;
    }
}