package com.cocoegg.pattern.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/9 - 10:19
 */
public class OrderDemo {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        invoker.takeOrder(new StartOrder(new ReceiverA()));
        invoker.takeOrder(new StopOrder(new ReceiverB()));
        invoker.placeOrder();
    }
}

interface Order {
    void execute();

}

class StartOrder implements Order {
    private Receiver receiver;

    public StartOrder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.execute();
    }
}

class StopOrder implements Order {
    private Receiver receiver;

    public StopOrder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.execute();
    }
}

interface Receiver {
    void execute();
}

class ReceiverA implements Receiver {

    @Override
    public void execute() {
        System.out.println("ReceiverA: doing something!");
    }
}

class ReceiverB implements Receiver {

    @Override
    public void execute() {
        System.out.println("ReceiverB: doing something!");
    }
}

class Invoker {
    private List<Order> orders;

    public Invoker() {
        this.orders = new ArrayList<>();
    }

    public void takeOrder(Order order) {
        orders.add(order);
    }

    public void placeOrder() {
        for (Order order : orders) {
            order.execute();
        }
    }
}
