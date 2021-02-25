package com.cocoegg.pattern.Generic;

/**
 * @author cocoegg
 * @date 2021/1/9 - 17:20
 */
public class GenericDemo3 {
    public static void main(String[] args) {
        new G4().printMsg(1,"hello",new Integer(5),40L);

        G4.showMsg("execute Service Error!");
    }
}

//类上的T 与 printMsg方法的T 不是一个T
class G4 <T>{

    //可变参数
    public <T> void printMsg(T... arg) {
        for (T t : arg) {
            System.out.println(t);
        }
    }

    //如果静态方法要使用泛型的话，必须将静态方法也定义成泛型方法。
    // 错误示例，编译不通过
    //public static void showMsg(T t) {}

    public static <T>  void showMsg(T t) {
        System.out.println(t);
    }

}
