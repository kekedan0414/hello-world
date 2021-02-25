package com.cocoegg.pattern.Mediator;

/**
 * @author cocoegg
 * @date 2021/1/6 - 20:47
 */
public class MediatorDemo {
    public static void main(String[] args) {

        /**
         * 在现实生活中，有很多中介者模式的身影，例如QQ游戏平台，聊天室、QQ群、短信平台和房产中介。
         * 不论是QQ游戏还是QQ群，它们都是充当一个中间平台，QQ用户可以登录这个中间平台与其他QQ用户进行交流，
         * 如果没有这些中间平台，我们如果想与朋友进行聊天的话，可能就需要当面才可以了。
         * 电话、短信也同样是一个中间平台，有了这个中间平台，每个用户都不要直接依赖与其他用户，
         * 只需要依赖这个中间平台就可以了，一切操作都由中间平台去分发。
         * 中介者模式，定义了一个中介对象来封装一系列对象之间的交互关系。
         * 中介者使各个对象之间不需要显式地相互引用，从而使耦合性降低，而且可以独立地改变它们之间的交互行为。
         */
        User lester = new User("lester");
        User gtt = new User("gongtingting");
        User fjy = new User("feijingyi");
        lester.sendMessage("hello,gtt,let me suck your tilt!");
        gtt.sendMessage("en!!");


    }
}

class ChatRoom {
    //static 精髓
    public static void chatRoom(User user,String msg) {
        System.out.println(user.getName()  + ":" + msg);
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //精髓this
    public void sendMessage(String msg) {
        ChatRoom.chatRoom(this, msg);
    }
}
