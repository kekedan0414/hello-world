package com.cocoegg.pattern.Bridge;

/**
 * @author cocoegg
 * @date 2021/1/6 - 17:53
 */
public class BridgeDemo {
    public static void main(String[] args) {
        Shape circle = new Circle(new Red());
        circle.draw();
        Shape rectangle = new Rectangle(new Red());
        rectangle.draw();

        new Circle(new Blue()).draw();
        new Rectangle(new Blue()).draw();

    }
}


interface Color {
    void fill();
}

class Red implements Color {

    @Override
    public void fill() {
        System.out.println("fill Red");
    }
}

class Blue implements Color {

    @Override
    public void fill() {
        System.out.println("fill Blue");
    }
}

abstract class Shape {
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    abstract void draw();
}


class Circle extends Shape {

    public Circle(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.println("draw Circle");
        color.fill();
    }
}

class Rectangle extends Shape {

    public Rectangle(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.println("draw Rectangle");
        color.fill();
    }
}



