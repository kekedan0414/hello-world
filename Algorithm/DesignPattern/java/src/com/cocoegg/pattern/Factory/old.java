package com.cocoegg.pattern.Factory;

/**
 * @author cocoegg
 * @date 2021/1/6 - 11:45
 */
public class old {
    public static void main(String[] args) {

        //调用者与提供者耦合。提供者一旦修改，则调用者每一处都得修改。
        Circle circle = new Circle();
        circle.draw();
        Rectangle rectangle = new Rectangle();
        rectangle.draw();
        Triangle triangle = new Triangle();
        triangle.draw();
    }
}

interface Sharp{
    void draw();
}

class Circle implements Sharp{
    @Override
    public void draw() {
        System.out.println("draw circle");
    }
}

class Rectangle implements Sharp{

    public void draw() {
        System.out.println("draw rectangle");
    }
}

class Triangle implements Sharp{

    public void draw() {
        System.out.println("draw triangle");
    }
}


