package com.shuan.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
/**
 * @author cocoegg
 * @date 2021/2/2 - 11:39
 */

@Controller
@EnableAutoConfiguration
@ServletComponentScan
@SpringBootApplication
//@ComponentScan(basePackages = {"com.shaun.zookeeper","com.shaun.zookeeper.controller"})
public class ApplicationDemo {


    public static void main(String[] args){
        SpringApplication.run(ApplicationDemo.class,args);
    }
}


