package com.cocoegg.pattern.Template.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 9:31
 */
public class TemplateDemo1 {
    public static void main(String[] args) {
        Game ml = new ML();
        ml.play();
    }
}

class ML extends Game {
    @Override
    public void pregame() {
        System.out.println("tuo yifu!");
    }

    @Override
    public void gaming() {
        System.out.println("ml ,neishe");
    }

    @Override
    public void postgame() {
        System.out.println("sleep");
    }
}

abstract class Game {
    abstract public void pregame();
    abstract public void gaming();
    abstract public void postgame();

    final public void play() {
        pregame();
        gaming();
        postgame();
    }
}
