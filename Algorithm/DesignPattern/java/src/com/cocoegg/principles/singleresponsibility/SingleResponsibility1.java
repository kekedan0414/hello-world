package com.cocoegg.principles.singleresponsibility;

public class SingleResponsibility1 {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.run("摩托车");
        vehicle.run("汽车");
        //飞机、船不能在路上运行
        vehicle.run("船");
    }
}

class Vehicle {
    public void run(String vehicle){
        System.out.println(vehicle + "在公路上运行");
    }
}
