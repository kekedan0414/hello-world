package com.cocoegg.principles.compositereuse.hasa;

public class hasa {
    public static void main(String[] args) {
        // B has a A
    }
}


class A {
    void func1(){}
    void func2(){}
    void func3(){}
}

class B {
    //尽量不要使用继承,这里就是组合 ，组合比聚合更加耦合
    A a = new A();
}