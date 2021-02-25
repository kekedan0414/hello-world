package com.cocoegg.pattern.Strategy;

/**
 * @author cocoegg
 * @date 2021/1/6 - 20:06
 */
public class StrategyDemo {
    public static void main(String[] args) {

        //主要解决：在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
        //何时使用：一个系统有许多许多类，而区分它们的只是他们直接的行为。
        Context context = new Context(new Add());
        System.out.println(context.excute(5, 10));
        context = new Context(new Sub());
        System.out.println(context.excute(5, 10));
        context = new Context(new Multi());
        System.out.println(context.excute(5, 10));
    }
}


interface Strategy {
    public int doOperation(int num1, int num2);
}

class Add implements Strategy {

    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}

class Sub implements Strategy {

    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}

class Multi implements Strategy {

    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}

class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int excute(int num1, int num2){
        return strategy.doOperation(num1,num2);
    }
}