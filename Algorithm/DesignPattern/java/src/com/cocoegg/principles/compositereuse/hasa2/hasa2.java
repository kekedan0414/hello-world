package com.cocoegg.principles.compositereuse.hasa2;

public class hasa2 {
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
    A a;
    //尽量不要使用继承,这里就是聚合 ，组合比聚合更加耦合
    public void setA(A a) {
        this.a = a;
    }
}