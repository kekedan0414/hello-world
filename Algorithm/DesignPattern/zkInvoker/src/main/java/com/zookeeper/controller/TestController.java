package com.zookeeper.controller;

import com.zookeeper.utils.LoadBalance;
import com.zookeeper.utils.RandomLoadBalance;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author cocoegg
 * @date 2021/2/2 - 15:46
 */
@RestController
@RequestMapping("/order")
public class TestController {

    private RestTemplate restTemplate = new RestTemplate();

    private LoadBalance loadBalance = new RandomLoadBalance();

    @RequestMapping("/getOrder")
    public Object getProduct(@RequestBody Map entity){
        String host = loadBalance.choseServiceHost();
        Map res = restTemplate.postForObject("http://"+host+"/zk/getProduct",entity,Map.class);
        res.put("host",host);
        return res;
    }

    @RequestMapping("/test")
    public String test(){
        String host = loadBalance.choseServiceHost();
        String res = restTemplate.postForObject("http://"+host+"/zk/hello",null,String.class);
        //restTemplate.post
        return res;
    }
}
