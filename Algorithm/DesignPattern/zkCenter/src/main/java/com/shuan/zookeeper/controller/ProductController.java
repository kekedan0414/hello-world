package com.shuan.zookeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cocoegg
 * @date 2021/2/2 - 13:20
 */
@RestController
@RequestMapping("/zk")
public class ProductController {

    @RequestMapping("/getProduct")
    public Map getProduct(@RequestBody Map entity){
        Map map = new HashMap();
        map.put("id",entity.get("id"));
        map.put("name","你好");
        return map;
    }

    @RequestMapping("/getProduct1")
    String home1(){
        return "Hello world1";
    }

    @RequestMapping("/hello")
    @ResponseBody
    String home(){
        return "Hello world";
    }
}