package com.cocoegg.pattern.Template;

/**
 * @author cocoegg
 * @date 2021/1/6 - 19:56
 */
public class TemplateDemo {
    public static void main(String[] args) {
        Game football = new Football();
        //模板方法(这里为play)设置为 final，这样它就不会被重写。
        football.play();
        Game ml = new ML();
        ml.play();
        ml.neishe();

    }
}

abstract class Game {
    abstract void pregame();
    abstract void gaming();
    abstract void postgame();

    //关键：final，表示关键步骤不能改变
    final void play() {
        pregame();
        gaming();
        postgame();
    }

    void neishe(){
        System.out.println("ns");
    }
}


class Football extends Game {

    @Override
    void pregame() {
        System.out.println("系鞋带");
    }

    @Override
    void gaming() {
        System.out.println("踢球");
    }

    @Override
    void postgame() {
        System.out.println("喝水");
    }
}

class ML extends Game {

    @Override
    void pregame() {
        System.out.println("脱衣服");
    }

    @Override
    void gaming() {
        System.out.println("ML");
    }

    @Override
    void postgame() {
        System.out.println("穿衣服");
    }
}