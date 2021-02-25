package com.cocoegg.pattern.Builder;

/**
 * @author cocoegg
 * @date 2021/1/6 - 13:48
 */
public class demo {
    public static void main(String[] args) {

        // https://zhuanlan.zhihu.com/p/58093669
        //当一个类的构造函数参数个数超过4个，而且这些参数有些是可选的参数，考虑使用构造者模式。

        Computer computer = new Computer.
                Builder("hisilicon","ft").
                setDisplay("sansum").
                setKeyboard("logitcal").
                setUsbCount(3).
                build();
        System.out.println(computer.toString());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("hello").append(",").append("world!");
        System.out.println(stringBuilder);
    }
}


class Computer {
    /** final
1.创建的时候立即初始化
2.创建之后由构造方法初始化
3.创建之后由代码块初始化
    **/
    private final String cpu;//必须
    private final String ram;//必须
    private final int usbCount;//可选
    private final String keyboard;//可选
    private final String display;//可选

    private Computer(Builder builder) {
        this.cpu=builder.cpu;
        this.ram=builder.ram;
        this.usbCount=builder.usbCount;
        this.keyboard=builder.keyboard;
        this.display=builder.display;
    }

    public static class Builder {
        private String cpu;//必须
        private String ram;//必须
        private int usbCount;//可选
        private String keyboard;//可选
        private String display;//可选

        public Builder(String cup,String ram){
            this.cpu=cup;
            this.ram=ram;
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

        public Computer build(){
            return new Computer(this);
        }
    }
    //省略getter方法

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