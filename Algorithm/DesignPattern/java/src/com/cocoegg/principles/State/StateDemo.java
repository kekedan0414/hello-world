package com.cocoegg.principles.State;

public class StateDemo {
    public static void main(String[] args) {
        Context context = new Context();
        context.handle();
        context.handle();
        context.handle();

    }
}

interface State {
    void handle(Context context);
}

class StateA implements State {
    @Override
    public void handle(Context context) {
        System.out.println("当前状态是A");
        context.setState(new StateB());
    }
}


class StateB implements State {
    @Override
    public void handle(Context context) {
        System.out.println("当前状态是B");
        context.setState(new StateA());
    }
}

class Context {
    private State state;

    public Context() {
        this.state = new StateA();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void handle() {
        state.handle(this);
    }

}
