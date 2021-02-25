package com.cocoegg.pattern.FlyWeight;

import java.util.HashMap;

/**
 * @author cocoegg
 * @date 2021/1/6 - 16:03
 */
public class FlyWeight {
    public static void main(String[] args) {
        //享元模式，把相同的部分提取出来共享，减少系统开销。
        //在有大量对象时，有可能会造成内存溢出，我们把其中共同的部分抽象出来，
        // 如果有相同的业务请求，直接返回在内存中已有的对象，避免重新创建。
        /**
         * 说到享元模式，第一个想到的应该就是池技术了，String常量池、数据库连接池、缓冲池等等都是享元模式的应用，
         * 所以说享元模式是池技术的重要实现方式。
         * 比如我们每次创建字符串对象时，都需要创建一个新的字符串对象的话，内存开销会很大，所以如果第一次创建了字符串对象“adam“，
         * 下次再创建相同的字符串”adam“时，只是把它的引用指向”adam“，这样就实现了”adam“字符串再内存中的共享。
         */
        for (int i = 0; i < 10; i++) {
            Circle circle = (Circle)CircleFlyWeight.getShape(getColor());
            circle.setX(getX());
            circle.setY(getY());
            circle.setRadius(100);
            circle.draw();
        }


    }

    private static final String[] colors = {"RED","BLUE","YELLOW","GREEN","BLACK"};

    private static String getColor() {
        return colors[(int) (colors.length * Math.random())];
    }
    private static int getX(){
        return (int) (100 * Math.random());
    }
    private static int getY(){
        return (int) (100 * Math.random());
    }

}


class CircleFlyWeight {

    //static关键
    private static final HashMap<String,Shape> pool = new HashMap<>();

    //static关键
    public static Shape getShape(String color) {

        if (!pool.containsKey(color)) {
            Shape shape = new Circle(color);
            pool.put(color, shape);
            System.out.println("池中不存在，创建一个Shape");
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


