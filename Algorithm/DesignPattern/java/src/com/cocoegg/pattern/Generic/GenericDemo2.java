package com.cocoegg.pattern.Generic;

/**
 * @author cocoegg
 * @date 2021/1/9 - 16:58
 */
public class GenericDemo2 {
    public static void main(String[] args) {

        //// showKeyValue这个方法编译器会为我们报错：Generic<java.lang.Integer>
        //// cannot be applied to Generic<java.lang.Number> showKeyValue(gInteger);
        // Number虽然是Integer的父类，但是G3<Number> 不是G3<Integer>的父类
        // new GenericDemo2().showValue(new G3<Integer>(10));


        //解决方案1： ？通配符
        new GenericDemo2().showValue1(new G3<>(10));

        //解决方案2： 泛型方法
        new GenericDemo2().showValue2(new G3<>(10));

    }

    public void showValue(G3<Number> num) {
        System.out.println("key value is :" + num.getT());
    }

    //此处’？’是类型实参，而不是类型形参
    // ？和Number、String、Integer一样都是一种实际的类型
    // 可以把？看成所有类型的父类。是一种真实的类型。
    public void showValue1(G3<?> num) {
        System.out.println("key value is :" + num.getT());
    }

    public <F> void showValue2(G3<F> num) {
        System.out.println("key value is :" + num.getT());
    }



}

class G3<T> {
    private T t;

    public G3(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
