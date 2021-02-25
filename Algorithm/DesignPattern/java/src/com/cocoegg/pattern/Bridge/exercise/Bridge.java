package com.cocoegg.pattern.Bridge.exercise;

/**
 * @author cocoegg
 * @date 2021/1/7 - 16:11
 */
public class Bridge {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                threadLocal.set(Thread.currentThread().getName());
                threadLocal.get();
        },String.valueOf(i)).start();
        }

        Sharp circle = new Circle(new Pink());
        circle.suck();
        Sharp pussy = new Pussy(new Black());
        pussy.suck();
    }
}


abstract class Sharp {
    Color color;

    public Sharp(Color color) {
        this.color = color;
    }

    abstract void draw();
    final void suck() {
        draw();
        color.fill();
    }

}

interface Color {
    void fill();
}

class Circle extends Sharp {

    public Circle(Color color) {
        super(color);
    }

    @Override
    void draw() {
        System.out.println("draw tilt!");
    }
}

class Pussy extends Sharp {

    public Pussy(Color color) {
        super(color);
    }

    @Override
    void draw() {
        System.out.println("draw pussy!");
    }
}

class Pink implements Color {

    @Override
    public void fill() {
        System.out.println("is pink");
    }
}

class Black implements Color {

    @Override
    public void fill() {
        System.out.println("is black");
    }
}




