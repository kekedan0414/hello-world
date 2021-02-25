package com.cocoegg.pattern.State;

/**
 * @author cocoegg
 * @date 2021/1/9 - 10:56
 */
public class StateDemo {
    public static void main(String[] args) {
        Context context = new Context();
        context.handle();
        context.handle();
        context.handle();
    }
}

interface State {
    void operation(Context context);
}

class StateA implements State {
    public void operation(Context context) {
        System.out.println("this is stateA...");
        context.setState(new StateB());
    }
}

class StateB implements State {
    public void operation(Context context) {
        System.out.println("this is stateB...");
        context.setState(new StateA());
    }
}

class Context {
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public Context() {
        this.state = new StateA();
    }

    public void handle() {
        state.operation(this);
    }
}
