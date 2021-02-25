package com.cocoegg.pattern.Composite.exercise1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/7 - 15:35
 */
public class HouG {
    public static void main(String[] args) {


        Person lester = new Person("lester");
        Person gtt = new Person("gtt");
        lester.join(gtt);
        Person fjy = new Person("fjy");
        lester.join(fjy);

        Person lxm = new Person("lxm");
        gtt.join(lxm);
        Person ocq = new Person("ocq");
        gtt.join(ocq);

        for (Person wife : lester.getWifes()) {
            System.out.println(wife.toString());
            for (Person wife2 : wife.getWifes()) {
                System.out.println(wife2.toString());
            }
        }

    }
}

class Person {

    private String name;

    List<Person> wifes;



    public List<Person> getWifes() {
        return wifes;
    }

    public void setWifes(List<Person> wifes) {
        this.wifes = wifes;
    }

    public Person(String name) {
        this.name = name;
        wifes = new ArrayList<>();
    }

    public void join(Person subs) {
        wifes.add(subs);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
