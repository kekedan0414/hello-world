package com.cocoegg.pattern.Factory;

/**
 * @author cocoegg
 * @date 2021/1/6 - 11:45
 */
public class news {
    public static void main(String[] args) {

        //调用者与提供者耦合。提供者一旦修改，则调用者每一处都得修改。
        Sharp circle = new SharpFactory().getSharp("circle");
        circle.draw();
    }
}


class SharpFactory {
    public Sharp getSharp(String sharp){

        if (sharp == null){
            return null;
        }

        if (sharp.equalsIgnoreCase("circle")){
                return new Circle();
        } else if (sharp.equalsIgnoreCase("rectangle")){
                return new Rectangle();
        } else if (sharp.equalsIgnoreCase("triangle")){
                return new Triangle();
        }  else {
            return  null;
        }
    }
}


