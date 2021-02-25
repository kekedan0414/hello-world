package com.cocoegg.pattern.Prototype.exercise1;



/**
 * @author cocoegg
 * @date 2021/1/7 - 10:21
 */
public class PrototypePattern {
    public static void main(String[] args) {
        Shaper shape = new Rectangle();
        shape.setColor(new Red());
        Shaper shaper1 = (Shaper) shape.clone();

        System.out.println("shape : " +shape.hashCode());
        System.out.println("shape1: " +shaper1.hashCode());
        System.out.println("shape.color : " +shape.color.hashCode());
        System.out.println("shape1.color: " +shaper1.color.hashCode());
    }
}

abstract class Shaper implements Cloneable{
    protected Color color;
    abstract  void draw();

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Object clone(){
        Shaper object = null;
        try {
           object = (Shaper) super.clone();
           //深拷贝
           //object.setColor((Color) this.color.clone());

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }
}

class Rectangle extends Shaper {

    @Override
    void draw() {
        System.out.println("hello,Rectangle");
    }
}

abstract class Color implements Cloneable {
    abstract void fill();

    @Override
    public Object clone(){
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }

}

class Red extends Color {
    @Override
    void fill() {}

}

