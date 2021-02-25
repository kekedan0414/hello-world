package com.cocoegg.pattern.Prototype;

/**
 * @author cocoegg
 * @date 2021/1/6 - 14:12
 */
public class PrototypePattern {
    public static void main(String[] args) {
        //clone操作的成本比new的成本低。如果对象的new非常复杂且耗时。在反复需要创建该对象时，可以考虑clone提高效率
        Sharp sharp = new Rectangle();
        Sharp sharp1 = (Sharp) sharp.clone();
        System.out.println("sharp :" + sharp.hashCode());
        System.out.println("sharp1:" + sharp1.hashCode());
        //浅拷贝，hashcode相等
        System.out.println(sharp.color.hashCode());
        System.out.println(sharp1.color.hashCode());
    }
}

abstract class Sharp implements Cloneable {
    private int id;
    String type;
    Color color = new Red();

    abstract void draw();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Object clone(){
        try {
            //浅拷贝
            //return super.clone();

            //深拷贝
            Color color = (Color) this.color.clone();
            Sharp sharp = (Sharp)super.clone();
            sharp.setColor(color);
            return sharp;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Rectangle extends Sharp {


    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

abstract class Color implements Cloneable {
    abstract void fill();

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Red extends Color{

    @Override
    public void fill() {
        System.out.println("fill Red.");
    }
}

