package com.cocoegg.pattern.Decorator.exercise;

/**
 * @author cocoegg
 * @date 2021/1/7 - 15:01
 */
public class Decorator {
    public static void main(String[] args) {
        ShapeDecorator shapeDecorator = new ShapeDecorator(new Rectangle());
        shapeDecorator.draw();

    }
}


class ShapeDecorator {
    private Shape shape;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    public void draw() {
        decorate();
        shape.draw();
    }

    private void decorate() {
        System.out.println("heloo!,decorate");
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

