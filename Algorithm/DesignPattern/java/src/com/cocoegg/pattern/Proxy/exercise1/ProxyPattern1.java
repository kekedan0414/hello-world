package com.cocoegg.pattern.Proxy.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 10:00
 */
public class ProxyPattern1 {
    public static void main(String[] args) {

        ProxyPlayer proxyPlayer = new ProxyPlayer();
        proxyPlayer.play("gtt's ml video!");
        proxyPlayer.play("fjy's ml video!");
    }
}

interface ImagePlayer {
    void play(String jpg);
}

class RealPlayer implements ImagePlayer {
    public RealPlayer() {
        initPlayer();
    }

    @Override
    public void play(String jpg) {
        loadImage(jpg);
        System.out.println(jpg);
    }

    private void loadImage(String jpg) {
        System.out.println(jpg + " loading...");
    }

    private void initPlayer() {
        System.out.println(" init...");
    }
}

class ProxyPlayer implements ImagePlayer {

    ImagePlayer realPlayer;

    @Override
    public void play(String jpg) {
        if (this.realPlayer == null) {
            this.realPlayer = new RealPlayer();
        }
        this.realPlayer.play(jpg);
    }
}