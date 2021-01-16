package com.cocoegg.principles.lisk.news;

public class news {
    public static void main(String[] args) {
        A a = new A();
        System.out.println("11 - 3 = " + a.func1(11, 3));
        B b = new B();
        //由于B没有相减的方法，所以调用者也会知道。不会误用
        System.out.println("11 + 3 = " + b.func1(11, 3));
    }
}

class Base {
}

class A extends Base{
    public int func1(int num1, int num2){
        return num1 - num2;
    }
}

class B extends Base {
    public int func1(int num1, int num2) {
        return num1 + num2;
    }

    public int func2(int num1, int num2) {
        return  func1(num1 , num2) + 9;
    }
}



