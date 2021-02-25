package com.cocoegg.pattern.Adapter;


/**
 * @author cocoegg
 * @date 2021/1/6 - 19:17
 */
public class AdapterDemo {
    public static void main(String[] args) {
        //适配器不是在详细设计时添加的，而是解决正在服役的项目的问题。
        Computer computer = new ThinkpadComputer();
        SDCard sdCard = new SDCardImpl();
        System.out.println(computer.readSD(sdCard));

        //新增TF卡，没办法插入电脑，所以需要加一个TFAdapterSD适配器，实现SD接口，但是里面调用TF。
        System.out.println("====================================");
        TFCard tfCard = new TFCardImpl();
        SDCard tfCardAdapterSD = new TFAdapterSD(tfCard);
        System.out.println(computer.readSD(tfCardAdapterSD));

    }
}

/********************原有系统start******************/
interface SDCard {
    //读取SD卡方法
    String readSD();
    //写入SD卡功能
    int writeSD(String msg);
}

interface Computer {
    String readSD(SDCard sdCard);
}

class SDCardImpl implements SDCard {
    @Override
    public String readSD() {
        String msg = "sdcard read a msg :hello word SD";
        return msg;
    }
    @Override
    public int writeSD(String msg) {
        System.out.println("sd card write msg : " + msg);
        return 1;
    }
}


class ThinkpadComputer implements Computer {
    @Override
    public String readSD(SDCard sdCard) {
        if(sdCard == null)throw new NullPointerException("sd card null");
        return sdCard.readSD();
    }
}

/********************原有系统end******************/


interface TFCard {
    String readTF();
    int writeTF(String msg);
}

class TFCardImpl implements TFCard {
    @Override
    public String readTF() {
        String msg ="tf card reade msg : hello word tf card";
        return msg;
    }
    @Override
    public int writeTF(String msg) {
        System.out.println("tf card write a msg : " + msg);
        return 1;
    }
}

class TFAdapterSD implements SDCard {

    private TFCard tfCard;

    public TFAdapterSD(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    @Override
    public String readSD() {
        return tfCard.readTF();
    }

    @Override
    public int writeSD(String msg) {
        tfCard.writeTF(msg);
        return 0;
    }
}