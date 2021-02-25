package com.cocoegg.pattern.Facade.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 14:44
 */
public class Facade {
    public static void main(String[] args) {

        ShapeMaker shapeMaker = new ShapeMaker();
        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();




    }
}


class ShapeMaker {
    Shape rectangle = new Rectangle();
    Shape circle = new Circle();
    Shape square = new Square();



    void drawCircle() {
        circle.draw();
    }

    void drawRectangle() {
        rectangle.draw();
    }

    void drawSquare() {
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
