package com.cocoegg.pattern.Builder.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 17:58
 */
public class Builder1 {
    public static void main(String[] args) {
        //传统模式
        Computer computer = new Computer("ft","sasum",2);
        System.out.println(computer.toString());


        //建造者模式
        Computer.Builder builder = new Computer.Builder("ft","sasum").setDisplay("gouzi").setUsbCount(2);

        Computer computer1  = builder.build();
        System.out.println(computer1.toString());

        Computer computer2 = new Computer.Builder("ft","sasum").setDisplay("tilt").setUsbCount(2).build();
        System.out.println(computer2.toString());

    }
}


class Computer {
    private  String cpu;//必须
    private  String ram;//必须
    private  int usbCount;//可选
    private  String keyboard;//可选
    private  String display;//可选

    public Computer(String cpu, String ram) {
        this.cpu = cpu;
        this.ram = ram;

    }

    public Computer(String cpu, String ram, int usbCount) {
        this(cpu,ram);
        this.usbCount = usbCount;
    }

    public Computer(String cpu, String ram, int usbCount, String keyboard) {
        this(cpu,ram,usbCount);
        this.keyboard = keyboard;
    }

    public Computer(String cpu, String ram, int usbCount, String keyboard, String display) {
        this(cpu,ram,usbCount,keyboard);
        this.display = display;
    }

    public Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usbCount = builder.usbCount;
        this.keyboard = builder.keyboard;
        this.display = builder.display;
    }

    public static class Builder {
        private String cpu;//必须
        private String ram;//必须
        private int usbCount;//可选
        private String keyboard;//可选
        private String display;//可选

        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Builder setUsbCount(int usbCount) {
            this.usbCount = usbCount;
            return this;
        }

        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }

    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", usbCount=" + usbCount +
                ", keyboard='" + keyboard + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
