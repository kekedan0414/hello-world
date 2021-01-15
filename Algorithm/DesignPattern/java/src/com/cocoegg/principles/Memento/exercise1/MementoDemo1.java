package com.cocoegg.principles.Memento.exercise1;


import java.util.ArrayList;
import java.util.List;

public class MementoDemo1 {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Memento memento1 = new Memento("#1");
        Memento memento2 = new Memento("#2");
        CareTaker careTaker = new CareTaker();
        careTaker.add(memento2);
        Memento memento3 = new Memento("#3");
        careTaker.add(memento3);
        Memento memento4 = new Memento("#4");

        System.out.println("Current State: " + memento4.getState());
        //originator.getMemontoState(careTaker.get(0));
        System.out.println("First saved State: " + careTaker.get(0).getState());
        //originator.getMemontoState(careTaker.get(1));
        System.out.println("Second saved State: " + careTaker.get(1).getState());

        Originator originator1 = new Originator();
        CareTaker careTaker1 = new CareTaker();
        originator1.setState("#1");
        originator1.setState("#2");
        careTaker1.add(originator1.saveMemontoState());
        originator1.setState("#3");
        careTaker1.add(originator1.saveMemontoState());
        originator1.setState("#4");


        System.out.println("latest:" + originator1.getState());
        System.out.println("first:" + originator1.getMemontoState(careTaker1.get(0)));
        System.out.println("second:" + originator1.getMemontoState(careTaker1.get(1)));

    }
}

class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

class CareTaker {
    private List<Memento> mementos = new ArrayList<>();

    public void add(Memento memento) {
        mementos.add(memento);
    }

    public Memento get(int i) {
        return mementos.get(i);
    }
}

class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMemontoState(Memento memento) {
        return memento.getState();
    }

    public  Memento saveMemontoState() {
        return new Memento(state);
    }
}