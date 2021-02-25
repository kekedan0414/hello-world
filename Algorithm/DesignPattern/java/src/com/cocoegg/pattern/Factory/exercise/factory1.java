package com.cocoegg.pattern.Factory.exercise;

/**
 * @author cocoegg
 * @date 2021/1/7 - 14:30
 */
public class factory1 {
    public static void main(String[] args) {
        Sharp circle =new SharpFactory().getSharp("circle");
        circle.draw();
        Sharp tri = new SharpFactory().getSharp("triangle");
        tri.draw();

        SharpFactory sharpFactory = new SharpFactory();
        sharpFactory.getSharp("rectangle");
        sharpFactory.getSharp("circle");
    }
}

class SharpFactory {
    public Sharp getSharp(String shape) {
        if (shape == null) {
            return null;
        }
        if (shape.equalsIgnoreCase("Circle")) { return  new Circle();}
        else if (shape.equalsIgnoreCase("Rectangle")) {return  new Rectangle();}
        else if (shape.equalsIgnoreCase("Triangle")) {return new Triangle();}
        else return null;
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
