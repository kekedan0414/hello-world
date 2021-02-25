package com.cocoegg.pattern.AbstractFactory;


/**
 * @author cocoegg
 * @date 2021/1/6 - 12:54
 */
public class demo {

    public static void main(String[] args) {
        FactoryProducer factoryProducer = new FactoryProducer();
        AbstractFactory sharpFactory = factoryProducer.getFactory("sharp");
        Sharp sharp = sharpFactory.getSharp("triangle");
        sharp.draw();

        new FactoryProducer().getFactory("sharp").getSharp("circle").draw();
        new FactoryProducer().getFactory("color").getColor("red").fill();
    }


}


class FactoryProducer{
    public AbstractFactory getFactory(String factory) {
        if (factory == null)
            return null;
        if (factory.equalsIgnoreCase("sharp")) {
            return new SharpFactory();
        } else if (factory.equalsIgnoreCase("color")) {
            return new ColorFactory();
        } else return null;
    }

}

abstract class AbstractFactory {
    abstract public Sharp getSharp(String sharp);
    abstract public Color getColor(String color);
}

class ColorFactory extends AbstractFactory {

    public Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equalsIgnoreCase("red")) {
            return new Red();
        } else if (color.equalsIgnoreCase("blue")) {
            return new Blue();
        } else if (color.equalsIgnoreCase("yellow")) {
            return new Yellow();
        } else {
            return null;
        }
    }

    public Sharp getSharp(String sharp) {
        return null;
    }
}

class SharpFactory extends AbstractFactory {
    public Color getColor(String color) {
        return null;
    }

    public Sharp getSharp(String sharp) {
        if (sharp == null) {
            return null;
        }
        if (sharp.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (sharp.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        } else if (sharp.equalsIgnoreCase("triangle")) {
            return new Triangle();
        } else {
            return null;
        }
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

