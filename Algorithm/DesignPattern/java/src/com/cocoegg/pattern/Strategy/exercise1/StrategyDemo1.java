package com.cocoegg.pattern.Strategy.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 9:39
 */
public class StrategyDemo1 {
    public static void main(String[] args) {

        //没有统一入口
        Operation add = new Add();
        System.out.println(add.operation(5 , 10));
        Operation sub = new Sub();
        System.out.println(sub.operation(5, 10));
        Operation multi = new Multi();
        System.out.println(multi.operation(5, 10));

        System.out.println("---------");
        //提供统一入口execute,不需要关心调用不同类的不同方法
        Context context = new Context(new Add());
        System.out.println(context.execute(5, 10));
        context.setOperation(new Sub());
        System.out.println(context.execute(5, 10));
        context.setOperation(new Multi());
        System.out.println(context.execute(5, 10));


    }
}


interface Operation {
    int operation(int n1, int n2);
}


class Add implements Operation {
    @Override
    public int operation(int n1, int n2) {
        return n1 + n2;
    }
}

class Sub implements Operation {
    @Override
    public int operation(int n1, int n2) {
        return n1 - n2;
    }
}

class Multi implements Operation {
    @Override
    public int operation(int n1, int n2) {
        return n1 * n2;
    }
}

class Context {
    Operation operation;

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Context(Operation operation) {
        this.operation = operation;
    }


    public int execute(int n1, int n2){
        return operation.operation(n1, n2);
    }
}


