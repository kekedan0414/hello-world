package com.cocoegg.principles.dependencyinversion.news;

public class DependencyInversion {
    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
        person.receive(new Weixin());
    }
}

interface Ireceiver {
   public String getInfo();
}

class Email implements Ireceiver{
    public String getInfo(){
        return "receive Emals: Hello,world!";
    }
}

class Weixin implements Ireceiver{
    public String getInfo(){
        return "receive Weixin: Hello,world!";
    }
}

//好处，无需在person中添加代码
class Person{
    public void receive(Ireceiver email){
        System.out.println(email.getInfo());
    }
}