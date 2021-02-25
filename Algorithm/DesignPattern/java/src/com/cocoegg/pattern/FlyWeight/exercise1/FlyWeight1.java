package com.cocoegg.pattern.FlyWeight.exercise1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cocoegg
 * @date 2021/1/7 - 13:51
 */
public class FlyWeight1 {
    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            Circle circle = (Circle) CircleFlyWeight11.getShape(getColor());
            circle.setRadius(100);
            circle.draw();
        }


    }


    public static String getColor() {
        return colors[(int)(Math.random() * colors.length)];
    }
    private static final String[] colors = {"RED","BLUE","YELLOW","GREEN","BLACK"};


}

class CircleFlyWeight11 {
    String color;
    private static Map<String,Shape> pool = new HashMap<>();
    public static Shape getShape(String color) {
        if (!pool.containsKey(color)) {
            System.out.println("new shap");
            pool.put(color,new Circle(color));
        }
        return pool.get(color);
    }
}


interface Shape {
    void draw();
}


class Circle implements Shape {
    private String color;
    private int x;
    private int y;
    private int radius;

    public Circle(String color){
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Circle: Draw() [Color : " + color
                +", x : " + x +", y :" + y +", radius :" + radius);
    }
}

