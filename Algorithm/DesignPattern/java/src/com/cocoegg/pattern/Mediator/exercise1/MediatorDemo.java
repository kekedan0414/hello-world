package com.cocoegg.pattern.Mediator.exercise1;

/**
 * @author cocoegg
 * @date 2021/1/7 - 10:13
 */
public class MediatorDemo {

    public static void main(String[] args) {
        User lester = new User("lester");
        User gtt = new User("gtt");
        User fjy = new User("fjy");
        lester.sendMsg("hi,gtt,fjy!");
        gtt.sendMsg("en!,master!");
        fjy.sendMsg("en!,master!");
        lester.sendMsg("let me double fly!");
    }
}


class ChatGroup {
    public static void Chat(User user, String msg) {
        System.out.println(user.getUser() + ":" + msg);
    }
}

class User {
    String user;

    public User(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void sendMsg(String msg) {
        ChatGroup.Chat(this, msg);
    }
}
