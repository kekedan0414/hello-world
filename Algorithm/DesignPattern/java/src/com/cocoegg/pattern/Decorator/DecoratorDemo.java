package com.cocoegg.pattern.Decorator;

/**
 * @author cocoegg
 * @date 2021/1/6 - 14:56
 */
public class DecoratorDemo {
    public static void main(String[] args) {
        //装饰模式和代理模式很像，
        //装饰模式强调的是自身的增强，调用时需要传入真实对象。
        //代理模式中，调用者不关心实现类的细节，实现类是在代理类中new出来的。
        //https://www.cnblogs.com/yanggb/p/10952843.html
        RedSharpDecorator redSharpDecorator = new RedSharpDecorator(new Rectangle());
        redSharpDecorator.draw();
        RedSharpDecorator redSharpDecorator1 = new RedSharpDecorator(new Circle());
        redSharpDecorator1.draw();


        Student s = new Student();
        System.out.println(s.a);

        Student s1 = new Student(1000);
        System.out.println(s1.a);



        //装饰模式应用：



    }
}


class RedSharpDecorator extends SharpDecorator{

    /**
     * 如果在父类中（这里就是你的抽象类）中显示的写了有参数的构造函数，在子类继承该构造函数时，就必须写一个构造函数来调用父类的构造函数
     * @param shapeDecorator
     */
    public RedSharpDecorator(Shape shapeDecorator) {
        super(shapeDecorator);
    }

    public void draw() {
        shapeDecorator.draw();
        //关键：进行你的装饰
        setColor();
    }

    private void setColor() {
        System.out.println("red border");
    }
}


abstract class SharpDecorator {
    Shape shapeDecorator;

    //抽象函数不能实例化为什么还要有构造函数呢？
    /**
     * 你的java代码中出现new关键字加上构造方法的调用，只会生成一个对象，其父类对象不会生成。
     * 抽象类中的构造方法其实是用来给继承的子类来用的，因为构造方法相当于初始化方法，
     * 当子类调用构造方法时必须调用父类构造方法，所以你可以在子类产生对象时抽象类中按需求初始化抽象类中的字段以及执行一些初始化代码。
     * @param shapeDecorator
     */
    public SharpDecorator(Shape shapeDecorator) {
        this.shapeDecorator = shapeDecorator;
    }

}


interface Shape {
    void draw();
}


class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Shape: Rectangle");
    }
}


class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Shape: Circle");
    }
}


/**********************abstract构造类的测试*****************/
abstract class Person {
    int a = 0;

    Person() { //如果不写Person()，java会默认加上一个不带参数的构造函数，即Person(){};
        a = 10;
        System.out.println("class is " + Person.class);
    }

    Person(int x) {
        a = x;
    }
}

class Student extends Person {
    int b = 0;

    Student() {
        //super();//可不写,默认调用Person();
        b = 100;
        System.out.println("class is " + Student.class);
    }

    Student(int x) {
        super(x);//此处若不显示调用父类的带参构造函数，则默认调用 无参 构造函数Person();
        b = 200;
    }
}


