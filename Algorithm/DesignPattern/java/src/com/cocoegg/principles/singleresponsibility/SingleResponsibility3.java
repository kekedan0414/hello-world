package com.cocoegg.principles.singleresponsibility;

public class SingleResponsibility3 {
    public static void main(String[] args) {
        Vehicle2 vehicle2 = new Vehicle2();
        vehicle2.run("摩托车");
        vehicle2.WaterRun("船");
        vehicle2.AirRun("飞机");
    }
}

//在方法级别遵守单一职责，新增需求则需要方法
class Vehicle2 {
    public void run(String vehicle){
        System.out.println(vehicle + "在公路上运行");
    }
    public void WaterRun(String vehicle){
        System.out.println(vehicle + "在水上运行");
    }
    public void AirRun(String vehicle){
        System.out.println(vehicle + "在天上运行");
    }

}
