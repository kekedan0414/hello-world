package com.cocoegg.principles.lisk;

public class old {
    public static void main(String[] args) {
        A a = new A();
        System.out.println("11 - 3 = " + a.func1(11, 3));
        A b = new B();
        //调用者有可能不知道func1被重写了，得到了错误的结果
        System.out.println("11 - 3 = " + b.func1(11, 3));

    }
}

class A {
    public int func1(int num1, int num2){
        return num1 - num2;
    }
}

class B extends A {
    @Override
    public int func1(int num1, int num2) {
        return num1 + num2;
    }

    public int func2(int num1, int num2) {
      return  func1(num1 , num2) + 9;
    }
}
