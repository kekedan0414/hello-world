package com.cocoegg.pattern.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/9 - 11:12
 */
public class MementoDemo {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setMemento(new Memento("#1"));
        originator.setMemento(new Memento("#2"));
        CareTaker careTaker = new CareTaker();
        careTaker.saveMemento(originator.getMemento());
        originator.setMemento(new Memento("#3"));
        careTaker.saveMemento(originator.getMemento());
        originator.setMemento(new Memento("#4"));

        System.out.println("lastest:" + originator.getMemento().getSequence());
        System.out.println("first:" + careTaker.getMemento(0).getSequence());
        System.out.println("second:" + careTaker.getMemento(1).getSequence());

        System.out.println("=====================================下面这个才是正确的，仔细体会getStateFromMemento()的this");

        Originator1 originator1 = new Originator1();
        CareTaker careTaker1 = new CareTaker();

        originator1.setSequence("#1");
        originator1.setSequence("#2");
        careTaker1.saveMemento(originator1.saveSequence2Memento());
        originator1.setSequence("#3");
        careTaker1.saveMemento(originator1.saveSequence2Memento());
        originator1.setSequence("#4");

        System.out.println("lastest:" + originator1.getSequence());
        originator1.getStateFromMemento(careTaker1.getMemento(0));
        System.out.println("second:" + originator1.getSequence());
        originator1.getStateFromMemento(careTaker1.getMemento(1));
        System.out.println("second:" + originator1.getSequence());




    }
}


class Memento {
    private String sequence;

    public Memento(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}

class CareTaker {

    private List<Memento> mementos = new ArrayList<>();

    public void saveMemento(Memento memento) {
        mementos.add(memento);
    }

    public Memento getMemento(int i) {
        return mementos.get(i);
    }

}

class Originator {
    private Memento memento;

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    public Memento getMemento() {
        return memento;
    }
}

class Originator1 {
    private String sequence;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Memento saveSequence2Memento() {
        return new Memento(this.sequence);
    }

    public void getStateFromMemento(Memento memento) {
        this.sequence = memento.getSequence();
    }
}
