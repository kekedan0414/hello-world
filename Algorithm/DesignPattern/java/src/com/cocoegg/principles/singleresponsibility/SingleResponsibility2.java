package com.cocoegg.principles.singleresponsibility;

public class SingleResponsibility2 {
    public static void main(String[] args) {
        RoldVehicle roldVehicle = new RoldVehicle();
        roldVehicle.run("摩托车");
        WaterVehicle waterVehicle = new WaterVehicle();
        waterVehicle.run("船");
        AirVehicle airVehicle = new AirVehicle();
        airVehicle.run("飞机");
    }
}

//在类级别遵守单一职责，新增需求则需要类
class RoldVehicle {
    public void run(String vehicle){
        System.out.println(vehicle + "在公路上运行");
    }
}

class WaterVehicle {
    public void run(String vehicle){
        System.out.println(vehicle + "在水上运行");
    }
}

class AirVehicle {
    public void run(String vehicle){
        System.out.println(vehicle + "在天上运行");
    }
}
