package com.cocoegg.effectivejava;

/**
 * @author cocoegg
 * @date 2021/1/15 - 10:26
 */
public class _34enum {
    public static void main(String[] args) {
        int code = HTTPCODE.ERROR.getCode();
        String msg = HTTPCODE.WARNINGS.getMsg();
        HTTPCODE httpcode = HTTPCODE.SUCCESS;
        httpcode.setCode(5);
        System.out.println(code);
        System.out.println(httpcode.getCode());
        System.out.println(HTTPCODE.SUCCESS);

        System.out.println("====");
        System.out.println(HTTPCODE.valueOf("ERROR"));

    }
}


enum HTTPCODE {
    SUCCESS(0,"success"),
    WARNINGS(1,"warnings"),
    ERROR(2,"sb");

    private int code;
    private String msg;

    HTTPCODE(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HTTPCODE{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}

enum ENUM1 {
    I1,I2;
}