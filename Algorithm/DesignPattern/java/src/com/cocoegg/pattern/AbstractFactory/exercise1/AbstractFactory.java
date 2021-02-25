package com.cocoegg.pattern.AbstractFactory.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 16:56
 */
public class AbstractFactory {
    public static void main(String[] args) {
        AbstractProducer abstractFactory1 = new AbstractProducer();
        abstractFactory1.getFactory("sharp").getSharp("circle").draw();
        abstractFactory1.getFactory("color").getColor("red").fill();

    }
}

class AbstractProducer {
    public AbstractFactory1 getFactory(String factory) {
        if (factory == null) return null;
        else if (factory.equalsIgnoreCase("sharp")) {return new ShapFactory();}
        else if (factory.equalsIgnoreCase("color")) {return new ColorFactory();}
        else return null;
    }

}

abstract class AbstractFactory1 {
    abstract Sharp getSharp(String sharp);
    abstract Color getColor(String color);

}

class ShapFactory extends AbstractFactory1 {

    public Sharp getSharp(String sharp) {
        if (sharp == null) return null;
        if (sharp.equalsIgnoreCase("circle")) {return new Circle();}
        else if (sharp.equalsIgnoreCase("rectangle")) {return new Rectangle();}
        else if (sharp.equalsIgnoreCase("triangle")) {return new Triangle();}
        else return null;
    }

    @Override
    Color getColor(String color) {
        return null;
    }
}

class ColorFactory extends AbstractFactory1 {
    @Override
    Sharp getSharp(String sharp) {
        return null;
    }

    public Color getColor(String color) {
        if (color == null) return  null;
        if (color.equalsIgnoreCase("red")) {return new Red();}
        else if (color.equalsIgnoreCase("blue")) {return new Blue();}
        else if (color.equalsIgnoreCase("yellow")) {return new Yellow();}
        else return null;
    }

}


interface Color{
    void fill();
}

class Red implements Color{
    public void fill() {
        System.out.println("fill Red.");
    }
}

class Blue implements Color{
    public void fill() {
        System.out.println("fill Blue.");
    }
}

class Yellow implements Color{
    public void fill() {
        System.out.println("fill Yellow.");
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

