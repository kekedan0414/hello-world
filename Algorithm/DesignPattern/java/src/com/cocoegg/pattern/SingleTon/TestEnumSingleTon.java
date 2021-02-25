package com.cocoegg.pattern.SingleTon;

/**
 * @author cocoegg
 * @date 2021/1/6 - 11:19
 */

public class TestEnumSingleTon{
    public static void main(String[] args) {

        EnumSingleTon instance = EnumSingleTon.INSTANCE;
        instance.method();
    }

}

/*public*/ enum EnumSingleTon {
    INSTANCE;
    public void method(){
        System.out.println("hello,EnumSingleTon!");
    }

}
