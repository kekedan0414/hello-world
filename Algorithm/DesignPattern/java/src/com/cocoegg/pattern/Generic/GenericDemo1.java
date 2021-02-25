package com.cocoegg.pattern.Generic;

/**
 * @author cocoegg
 * @date 2021/1/9 - 16:42
 */
public class GenericDemo1 {
    public static void main(String[] args) {

        new G1().test1("gtt,wocaoni");
        System.out.println(new G1().test2());

        new G2().test1("fjy,wocaoni");
        System.out.println(new G2().test2());
    }
}

interface GenericInterface<T,S> {
    void test1(T t);
    S test2();
}

//继承的时候指定具体类型
class G1 implements GenericInterface<String,Integer> {

    @Override
    public void test1(String s) {
        System.out.println(s);
    }

    @Override
    public Integer test2() {
        return 5;
    }
}

//继承的时候不指定具体的类型
class G2<M> implements GenericInterface<M , String> {

    @Override
    public void test1(M m) {
        System.out.println(m);
    }

    @Override
    public String test2() {
        return "neishe zhm";
    }
}