package com.cocoegg.pattern.Visitor.exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author cocoegg
 * @date 2021/1/9 - 14:18
 */
public class VisitorDemo1 {
    public static void main(String[] args) {

        CEO ceo = new CEO();
        CTO cto = new CTO();

        List<Staff> staff = new ArrayList<>();
        Manager m1 = new Manager("M1");
        Engineer e1 = new Engineer("E1");
        Manager m2 = new Manager("M2");
        Engineer e2 = new Engineer("E2");
        Engineer e3 = new Engineer("E3");
        staff.add(m1);
        staff.add(e1);
        staff.add(m2);
        staff.add(e2);
        staff.add(e3);

        System.out.println("---------ceo---------");
        for (Staff staff1 : staff) {
            staff1.visit(ceo);
        }

        System.out.println("---------cto---------");
        for (Staff staff1 : staff) {
            staff1.visit(cto);
        }


    }
}

abstract class Staff {
    protected String name;
    protected int kpi;

    public String getName() {
        return name;
    }

    public int getKpi() {
        return kpi;
    }


    public Staff(String name) {
        this.name = name;
    }

    abstract void visit(Visitor visitor);
}

class Manager extends Staff {

    private int producerNum;

    public int getProducerNum() {
        return producerNum;
    }

    public Manager(String name) {
        super(name);
        kpi = new Random().nextInt(10);
        producerNum = new Random().nextInt(10);
    }

    @Override
    void visit(Visitor visitor) {
        visitor.visitManager(this);
    }
}

class Engineer extends Staff {

    private int codeLines;

    public int getCodeLines() {
        return codeLines;
    }

    public Engineer(String name) {
        super(name);
        kpi = new Random().nextInt(10 );
        codeLines = new Random().nextInt(10 ) * 1000;
    }

    @Override
    void visit(Visitor visitor) {
        visitor.visitEngineer(this);
    }
}

interface Visitor {
    void visitManager(Manager manager);
    void visitEngineer(Engineer engineer);

}

class CEO implements Visitor {

    @Override
    public void visitManager(Manager manager) {
        System.out.println(manager.getName() + ": kpi :" + manager.getKpi() + ": producer :" + manager.getProducerNum());
    }

    @Override
    public void visitEngineer(Engineer engineer) {
        System.out.println(engineer.getName() + ": kpi :" + engineer.getKpi());

    }
}

class CTO implements Visitor {

    @Override
    public void visitManager(Manager manager) {
        System.out.println(manager.getName() + ": producer :" + manager.getProducerNum());
    }

    @Override
    public void visitEngineer(Engineer engineer) {
        System.out.println(engineer.getName() + ": codes :" + engineer.getCodeLines());

    }
}
