package com.cocoegg.effectivejava;
/**
 * @author cocoegg
 * @date 2021/1/15 - 11:35
 */
public class _35enumordinal {
    public static void main(String[] args) {
        System.out.println(EnumOrdinal.I1.getOrdinal());
        System.out.println(EnumOrdinalWithInt.I2.getOrdinal());


    }
}

enum EnumOrdinal {
    I1,I2,I3,I4;

    //bad idea
    public int  getOrdinal() {
        return ordinal() + 1;
    }
}

enum EnumOrdinalWithInt {

    I1(11),I2(21),I(31),I4(41);

    int ii;
    EnumOrdinalWithInt(int i) {
        this.ii = i;
    }

    public int getOrdinal() {
        return ii;
    }
}