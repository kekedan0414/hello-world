package com.cocoegg.principles.openclose.olds;

public class ocp {
    public static void main(String[] args) {
        GraphicDraw graphicDraw = new GraphicDraw();
        graphicDraw.drawSharp(new Circle());
        graphicDraw.drawSharp(new Rectangle());
        //优点，当有一个新的形状时，需要修改代码较少
        graphicDraw.drawSharp(new OtherSharp());
    }
}

//绘图工具类
class GraphicDraw{
    public void drawSharp(Sharp sharp){
        sharp.draw();
    }
}

abstract class Sharp {
    abstract void  draw();
}

class Circle extends Sharp {
    public void draw(){System.out.println("draw circle!");}
}

class Rectangle extends Sharp {
    public void draw(){System.out.println("draw Rectangle!");}
}

class OtherSharp extends Sharp {
    public void draw(){System.out.println("draw OtherSharp!");}
}


