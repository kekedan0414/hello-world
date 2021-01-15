package com.cocoegg.principles.dependencyinversion.old;

public class DependencyInversion {
    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
        person.receive(new Weixin());
    }
}

class Email{
    public String getInfo(){
        return "receive Emals: Hello,world!";
    }
}

class Weixin{
    public String getInfo(){
        return "receive Weixin: Hello,world!";
    }
}

//存在问题：如果新增了微信，短信，怎么办？新增微信类的同时Person也要新增receive
class Person{
    public void receive(Email email){
        System.out.println(email.getInfo());
    }

    public void receive(Weixin weixin){
        System.out.println(weixin.getInfo());
    }
}