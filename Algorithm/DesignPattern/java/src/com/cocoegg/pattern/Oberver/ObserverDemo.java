package com.cocoegg.pattern.Oberver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/9 - 12:07
 */
public class ObserverDemo {
    public static void main(String[] args) {
        Subject subject = new Subject();
        Observer observer1 = new Observer(subject);
        Observer observer2 = new Observer(subject);
        Observer observer3 = new Observer(subject);

        subject.setState(10);
//        System.out.println("o1:" + observer1.getState());
//        System.out.println("o2:" + observer2.getState());
//        System.out.println("o3:" + observer3.getState());

        subject.setState(5);
//        System.out.println("o1:" + observer1.getState());
//        System.out.println("o2:" + observer2.getState());
//        System.out.println("o3:" + observer3.getState());


        /** */

        subject.setState(4);
        //subject.notifyAllObservers();

    }
}

class Subject {
    private int state;
    private List<Observer> observers = new ArrayList<>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }

    }

}

class Observer {
    private Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        regist();
    }

    public void regist() {
        subject.add(this);
    }

    public int getState() {
        return subject.getState();
    }

    public void update() {
        System.out.println("o:" + getState());
    }

}
