package com.cocoegg.pattern.Order.exercise1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/9 - 10:42
 */
public class OrderDemo1 {
    public static void main(String[] args) {

        Broket broket = new Broket();
        broket.takeOrder(new BuyOrder(new Stock("zhongke",5)));
        broket.takeOrder(new BuyOrder(new Stock("zhongke",5)));
        broket.takeOrder(new SellOrder(new Stock("zhongke",3)));
        broket.takeOrder(new SellOrder(new Stock("zhongke",7)));
        broket.paleceOrder();
    }
}

class Stock {
    private String name;
    private int quantity;

    public Stock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    void buy() {
        System.out.println("buy:" + this.toString());
    }

    void sell() {
        System.out.println("sell:" + this.toString());
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

interface Order {
    void execute();
}

class BuyOrder implements Order {

    private Stock stock;

    public BuyOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }
}

class SellOrder implements Order {

    private Stock stock;

    public SellOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}


class Broket {
    private List<Order> orders;

    public Broket() {
        this.orders = new ArrayList<>();
    }

    public void takeOrder(Order order) {
        orders.add(order);
    }

    public void paleceOrder() {
        for (Order order : orders) {
            order.execute();
        }
    }
}