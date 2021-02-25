package com.cocoegg.pattern.ChainResponsibility;

/**
 * @author cocoegg
 * @date 2021/1/7 - 19:43
 */
public class ChainDemo1 {
    public static void main(String[] args) {
        //定义：责任链模式(Chain of Responsibility)使多个对象都有机会处理请求，从而避免请求的发送者和接受者之间的耦合关系。
        // 将这些对象连成一条链，并沿着这条链传递该请求，直到有对象能够处理它。

        //你要去给某公司借款 1 万元，当你来到柜台的时候向柜员发起 "借款 1 万元" 的请求时，柜员认为金额太多，处理不了这样的请求，
        // 他转交这个请求给他的组长，组长也处理不了这样的请求，那么他接着向经理转交这样的请求。
        /**
         public void test(Request request) {
         int money = request.getRequestMoney();
         if(money <= 1000) {
         Clerk.response(request);
         } else if(money <= 5000) {
         Leader.response(request);
         } else if(money <= 10000) {
         Manager.response(request);
         }
         }
         **/

        //缺点：if else 耦合太强！
//        Handler ch1 = new ConcreteHandler1();
//        Handler ch2 = new ConcreteHandler2();
//        Handler ch3 = new ConcreteHandler3();
//
//        ch1.setNextHandler(ch2);
//        ch2.setNextHandler(ch3);
//
//        Response res1 = ch1.handlerRequest(new Request(new Level(2)));
//        if (res1 != null) {
//            System.out.println(res1.getMessage());
//        }
//        Response res2 = ch1.handlerRequest(new Request(new Level(4)));
//        if (res2 != null) {
//            System.out.println(res2.getMessage());
//        }
    }

}



