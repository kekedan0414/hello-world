package com.cocoegg.principles.Generic;

import java.util.ArrayList;
import java.util.List;

public class G6 {
    public static void main(String[] args) {



        Apple[] apples = new Apple[1];
        Fruit[] fruits = apples;
        //fruits[0] = new Strawberry();


        List<Apple> apples1 = new ArrayList<>();
        apples1.add(new Apple());
        List<? extends Apple> fruits1 = apples1;
        //fruits1.add(new Apple());
        Fruit fruits10 = fruits1.get(0);

        System.out.println("---------");


        List<Fruit> fruits2 = new ArrayList<>();
        List<? super Apple> apple2 = fruits2;

        fruits2.add(new Apple());
        fruits2.add(new GreenApple());





    }
}

class Apple extends Fruit { }
class GreenApple extends Apple{}
class Fruit {}
class Strawberry extends Fruit {}