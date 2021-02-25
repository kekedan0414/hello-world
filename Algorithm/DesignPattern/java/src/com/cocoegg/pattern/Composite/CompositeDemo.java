package com.cocoegg.pattern.Composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/6 - 17:29
 */
public class CompositeDemo {
    public static void main(String[] args) {

        Employee CEO = new Employee("John","CEO", 30000);

        Employee headSales = new Employee("Robert","Head Sales", 20000);

        Employee headMarketing = new Employee("Michel","Head Marketing", 20000);

        Employee clerk1 = new Employee("Laura","Marketing", 10000);
        Employee clerk2 = new Employee("Bob","Marketing", 10000);

        Employee salesExecutive1 = new Employee("Richard","Sales", 10000);
        Employee salesExecutive2 = new Employee("Rob","Sales", 10000);

        CEO.add(headSales);
        CEO.add(headMarketing);

        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        //打印该组织的所有员工
        System.out.println(CEO);
        for (Employee headEmployee : CEO.getSubbordinates()) {
            System.out.println(headEmployee);
            for (Employee employee : headEmployee.getSubbordinates()) {
                System.out.println(employee);
            }
        }

    }
}

class Employee {
    private String name;
    private String dept;
    private int salarys;
    List<Employee> subbordinates;

    public Employee(String name, String dept, int salarys) {
        this.name = name;
        this.dept = dept;
        this.salarys = salarys;
        this.subbordinates = new ArrayList<>();
    }

    public void add(Employee employee) {
        subbordinates.add(employee);
    }

    public void remove(Employee employee) {
        subbordinates.remove(employee);
    }

    public List<Employee> getSubbordinates() {
        return subbordinates;
    }

    @Override
    public String toString() {
        return "Employee{  " +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", salarys=" + salarys +
                '}';
    }
}
