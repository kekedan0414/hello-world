package com.cocoegg.principles.interfacesegregation.news;

public class Segregation2 {
    public static void main(String[] args) {
        A a = new A();
        a.depend1(new B());
        a.depend2(new B());
        a.depend3(new B());
        C c = new C();
        c.depend1(new D());
        c.depend4(new D());
        c.depend5(new D());

        //好处，1 ，23,45接口分离


    }
}

interface Interface1 {
    void operation1();
}

interface Interface23 {
    void operation2();
    void operation3();
}

interface Interface45 {
    void operation4();
    void operation5();
}

class B implements Interface1,Interface23 {

    @Override
    public void operation1() {
        System.out.println("B 实现了 operation1");
    }

    @Override
    public void operation2() {
        System.out.println("B 实现了 operation2");
    }

    @Override
    public void operation3() {
        System.out.println("B 实现了 operation3");
    }

}

class D implements Interface1,Interface45 {

    @Override
    public void operation1() {
        System.out.println("D 实现了 operation1");
    }


    @Override
    public void operation4() {
        System.out.println("D 实现了 operation4");
    }

    @Override
    public void operation5() {
        System.out.println("D 实现了 operation5");
    }
}

class A {  //A类通过Interface1 依赖（使用）B类，只会使用到1,2,3方法。
    public void depend1(Interface1 i){
        i.operation1();
    }
    public void depend2(Interface23 i){
        i.operation2();
    }
    public void depend3(Interface23 i){
        i.operation3();
    }
}

class C {  //C类通过Interface1 依赖（使用）D类，只会使用到1,4,5方法。
    public void depend1(Interface1 i){
        i.operation1();
    }
    public void depend4(Interface45 i){
        i.operation4();
    }
    public void depend5(Interface45 i){
        i.operation5();
    }
}