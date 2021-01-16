package com.cocoegg.principles.openclose.news;

public class ocp {
    public static void main(String[] args) {

        GraphicDraw graphicDraw = new GraphicDraw();
        graphicDraw.drawSharp(new Circle());
        graphicDraw.drawSharp(new Rectangle());

        //缺点，当有一个新的形状时，需要修改代码较多
        graphicDraw.drawSharp(new OtherSharp());


    }

}

//绘图工具类
class GraphicDraw{

    public void drawSharp(Sharp sharp){
        if (sharp.m_type == 1){
            drawCircle();
        } else if (sharp.m_type == 2){
            drawRectangle();
        } else if (sharp.m_type == 3){
            drawOtherSharp();
        }

    }

    private void drawRectangle() {
        System.out.println("draw rectangle!");
    }

    private void drawCircle() {
        System.out.println("draw circle!");
    }

    private void drawOtherSharp() {
        System.out.println("draw otherSharp!");
    }

}


class Sharp {
    int m_type;
}

class Circle extends Sharp {
    Circle(){
        super.m_type = 1;
    }
}

class Rectangle extends Sharp {
    Rectangle(){
        super.m_type = 2;
    }
}

class OtherSharp extends Sharp {
    OtherSharp(){
        super.m_type = 3;
    }
}


