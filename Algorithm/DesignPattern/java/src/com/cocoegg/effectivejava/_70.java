package com.cocoegg.effectivejava;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class _70 {
    public static void main(String[] args) throws InterruptedException {

        String fileName = getFileName();

        new Thread(() -> {
            //空指针，运行时异常，程序不应该去捕获它，因为它属于程序错误（程序员未检查传入的是否是空指针）
            String fileName1 = getFileName().toString();
        },"thread 1").start();


        new Thread(() -> {
            try {
                // 方法里面主动抛出异常。如果文件不存在，应该怎么处理，需要由应用程序来决定，属于受检异常。
                OutputStream outputStream = new FileOutputStream("path/fole");
                // 抛出IO异常
                outputStream.write(5);

            } catch (FileNotFoundException e) {
                // 主动处理
                System.out.println("文件不存在！");
            } catch (IOException e) {
                System.out.println("读写错误！");
            }
        },"thread 2").start();

        //尽管两个子线程都出现了异常，但主线程不会退出
        while (true) {
            Thread.sleep(1000);
            System.out.println("hello,world!");
        }

    }


    public static  String getFileName() {
        return null;
    }
}
