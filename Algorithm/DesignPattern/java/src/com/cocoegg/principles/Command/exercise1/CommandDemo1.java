package com.cocoegg.principles.Command.exercise1;

import java.util.ArrayList;
import java.util.List;

public class CommandDemo1 {
    public static void main(String[] args) {
        //顺序：调用者→命令→接受者。
        // 券商是调用者，收取买卖股票的命令
        // BuyStock买股票和SellStock卖股票是具体的Order命令，具体命令里需要聚合一个接受者，Order命令里指定接收者去真正执行。
        // Stock是接受者

        Stock stock = new Stock("zhongkeshuguang",5);
        Order buyOrder = new BuyStock(stock);
        Stock stock1 = new Stock("zhongkeshuguang",10);
        Order buyOrder1 = new BuyStock(stock1);
        Stock stock2 = new Stock("zhongkeshuguang",8);
        Order sellOrder = new SellStock(stock2);
        Stock stock3 = new Stock("zhongkeshuguang",7);
        Order sellOrder1 = new SellStock(stock3);
        Broket broket = new Broket();
        broket.takeOrder(buyOrder);
        broket.takeOrder(buyOrder1);
        broket.takeOrder(sellOrder);
        broket.takeOrder(sellOrder1);
        broket.placeOrders();

    }
}

interface Order {
    void execute();
}


class Stock {
    protected String name;
    protected int quantity;

    public Stock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public void buy() {
        System.out.println("buy " + this.toString());
    }

    public void sell() {
        System.out.println("sell " + this.toString());
    }
}

class BuyStock implements Order {
    private Stock stock;

    public BuyStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }
}

class SellStock implements Order {
    private Stock stock;

    public SellStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}


class Broket {
    private List<Order> orders = new ArrayList<>();

    public void takeOrder(Order order) {
        this.orders.add(order);
    }

    public void placeOrders() {
        for (Order order : orders)
            order.execute();
    }

}