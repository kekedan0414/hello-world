package com.cocoegg.pattern.Proxy;

/**
 * @author cocoegg
 * @date 2021/1/6 - 16:56
 */
public class ProxyPattern {
    public static void main(String[] args) {
        ProxyImage proxyImage = new ProxyImage("Mr Chen.jpg");
        proxyImage.display();
        //第二次访问代理类
        proxyImage.display();
    }

}

interface Image {
    void display();
}


class RealImage implements Image {

    private String fileName;

    public RealImage(String fileName){
        this.fileName = fileName;
        loadFromDisk(fileName);
    }

    @Override
    public void display() {
        System.out.println("Displaying " + fileName);
    }

    private void loadFromDisk(String fileName){
        System.out.println("Loading " + fileName);
    }
}

class ProxyImage implements Image {
    private Image realProxy;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realProxy == null) {
            realProxy = new RealImage(fileName);
        }
        realProxy.display();
    }

}

