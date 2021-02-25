package com.cocoegg.pattern.Facade;

/**
 * @author cocoegg
 * @date 2021/1/6 - 15:39
 */
public class FacadePatternDemo {
    public static void main(String[] args) {
        //外观模式（Facade Pattern）隐藏系统的复杂性,并向客户端提供了一个客户端可以访问系统的接口。
        ShapeMaker shapeMaker = new ShapeMaker();
        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSqure();

        System.out.println("否则需要 new 多个不同类型的对象,调用方提高了耦合度");
        new Circle().draw();
        new Rectangle().draw();
        new Square().draw();
    }
}

class ShapeMaker {
    private Shape circle;
    private Shape rectangle;
    private Shape square;

    public ShapeMaker() {
        circle = new Circle();
        rectangle = new Rectangle();
        square = new Square();
    }

    public void drawCircle(){
        circle.draw();
    }
    public void drawRectangle(){
        rectangle.draw();
    }
    public void drawSqure(){
        square.draw();
    }
}


interface Shape {
    void draw();
}

class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Rectangle::draw()");
    }
}

class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Square::draw()");
    }
}

class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}
