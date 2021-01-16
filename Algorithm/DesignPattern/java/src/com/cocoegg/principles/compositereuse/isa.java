package com.cocoegg.principles.compositereuse;

public class isa {
    public static void main(String[] args) {
        // B is a A
        //尽量不要使用继承
    }
}


class A {
    void func1(){}
    void func2(){}
    void func3(){}
}

class B extends A{

}