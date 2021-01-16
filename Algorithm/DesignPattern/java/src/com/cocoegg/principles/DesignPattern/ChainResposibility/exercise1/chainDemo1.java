package com.cocoegg.principles.DesignPattern.ChainResposibility.exercise1;

import java.util.logging.LogManager;

public class chainDemo1 {
    public static LogManager log = LogManager.getLogManager();
    public static void main(String[] args) {
        AbstractLogger logger = logManager();
        logger.printLog(1,"this is info...");
        logger.printLog(2,"this is debug...");
        logger.printLog(3,"this is error...");



    }

    public static AbstractLogger logManager() {
        AbstractLogger error = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger debug = new DebugLogger(AbstractLogger.DEBUG);
        AbstractLogger info  = new InfoLogger(AbstractLogger.INFO);

        info.setNextLogger(debug);
        debug.setNextLogger(error);
        return info;
    }
}


abstract class AbstractLogger {

    public static int INFO  = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    int level;
    AbstractLogger nextLogger;

    public AbstractLogger(int level) {
        this.level = level;
    }

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void printLog(int level,String msg) {
        if (level <= this.level) {
            write(msg);
        } else {
            if (nextLogger != null) {
                nextLogger.printLog(level, msg);
            }
        }

    }

    abstract void write(String msg);
}


class InfoLogger extends AbstractLogger {

    public InfoLogger(int level) {
        super(level);
    }

    @Override
    void write(String msg) {
        System.out.println("[Info]:" + msg);
    }
}

class DebugLogger extends AbstractLogger {

    public DebugLogger(int level) {
        super(level);
    }

    @Override
    void write(String msg) {
        System.out.println("[Debug]:" + msg);
    }
}

class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level) {
        super(level);
    }

    @Override
    void write(String msg) {
        System.out.println("[Error]:" + msg);
    }
}