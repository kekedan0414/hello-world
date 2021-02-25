package com.cocoegg.pattern.Adapter.exercise;


/**
 * @author cocoegg
 * @date 2021/1/7 - 16:40
 */
public class Adapter1 {
    public static void main(String[] args) {
        SDCard sdCard = new SDCardImpl();
        ThinkpadComputer thinkpadComputer = new ThinkpadComputer();
        System.out.println(thinkpadComputer.readSD(sdCard));

        System.out.println("===========");
        TFCard tfCard = new TFCardImpl();
        TFCardAdapter tfCardAdapter = new TFCardAdapter(tfCard);
        System.out.println(thinkpadComputer.readSD(tfCardAdapter));


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
        return sdCard.readSD();
    }
}

/********************原有系统end******************/

interface TFCard {
    //读取TF卡方法
    String readTF();
    //写入TF卡功能
    int writeTF(String msg);
}

class TFCardImpl implements TFCard {

    @Override
    public String readTF() {
        return "this is vdeio with gtt!";
    }

    @Override
    public int writeTF(String msg) {
        return 1;
    }
}

class TFCardAdapter implements SDCard {

    private TFCard tfCard;

    public TFCardAdapter(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    @Override
    public String readSD() {
        return tfCard.readTF();
    }

    @Override
    public int writeSD(String msg) {
        return tfCard.writeTF(msg);
    }
}
