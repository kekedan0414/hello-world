package com.cocoegg.effectivejava;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cocoegg
 * @date 2021/1/15 - 9:54
 *
 * 异构容器
 */
public class _33yigoucontainer {
    public static void main(String[] args) {

        Map<Class<?>,Object> map = new HashMap<>();
        map.put(String.class,"abc");
        map.put(Integer.class,11);
        System.out.println(map.get(String.class));
        System.out.println(map.get(Integer.class));
        Object o1 = map.get(String.class);
        //Object o2 = (String)map.get(Integer.class); //报错
        System.out.println(o1);
        //System.out.println(o2);



        //类型检测
        String checkStatus = "MonitorCheckStatus";
        Integer integer  = 10;
        String.class.cast(integer);
        System.out.println("===");
        System.out.println(integer);


    }
}



