package com.cocoegg.principles.DynamicProxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.*;

/**
 * https://www.cnblogs.com/gonjan-blog/p/6685611.html
 * https://www.zhihu.com/question/20794107
 * 动态代理只能对接口进行代理：
 * 生成的代理类：$Proxy0 extends Proxy implements Person，
 * 我们看到代理类继承了Proxy类，所以也就决定了java动态代理只能对接口进行代理，
 * Java的继承机制注定了这些动态代理类们无法实现对class的动态代理。
 */
public class Demo1 {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 创建一个实例对象，这个对象是被代理的对象
        Person zhangsan = new Student("张三");

        // 创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler<>(zhangsan);

        // 动态代理方法1：newProxyInstance
        // 创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        Person stuProxy = (Person) Proxy.newProxyInstance(
                Person.class.getClassLoader(),
                new Class<?>[]{Person.class},
                stuHandler
        );

        // 动态代理方法2
        // 1. 获取类
        Class<?> proxyClass = Proxy.getProxyClass(Person.class.getClassLoader(),new Class<?>[]{Person.class});

        // 2. 构造器
        Constructor<?> cons = proxyClass.getConstructor(new Class<?>[]{InvocationHandler.class});

        // 3. 创建实例
        Person stuPorxy2 = (Person) cons.newInstance(stuHandler);

        //代理执行上交班费的方法
        stuProxy.giveMoney();

        //代理执行支出方法
        stuPorxy2.PayMent();

        //将动态代理类写入class文件
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", Student.class.getInterfaces());
        String path = "out/production/DesignPattern/com/cocoegg/principles/DynamicProxy/StuProxy.class";
        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }
}

/**
 * 生成的真正的动态代理类
 *
 * stuProxy.giveMoney() 实际调用的是$Proxy0的giveMoney()方法。
 * $Proxy0继承Proxy，下面代码实际调用InvocationHandler的invoke方法。
 * InvocationHandler 在Proxy.newProxyInstance创建代理实例时作为参数传入（就是InvocationHandler h）。
 */
//public final class $Proxy0 extends Proxy implements Person {
//    m3 = Class.forName("com.cocoegg.principles.DynamicProxy.Person").getMethod("giveMoney", new Class[0]);
//    public final void giveMoney() throws {
//        try {
                //this.h 是Proxy里的成员属性InvocationHandler
                // 调用InvocationHandler的invoke方法。
//            this.h.invoke(this, m3, null);
//            return;
//        }
//    }
//}


interface Person {
    //上交班费
    void giveMoney();

    //支付
    void PayMent();
}

class Student implements Person {
    private String name;
    public Student(String name) {
        this.name = name;
    }

    @Override
    public void giveMoney() {
        try {
            //假设数钱花了一秒时间
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "上交班费50元");
    }

    @Override
    public void PayMent() {
        try {
            //假设数钱花了一秒时间
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "花费了150元");
    }
}

class MonitorUtil {

    private static ThreadLocal<Long> tl = new ThreadLocal<>();

    public static void start() {
        tl.set(System.currentTimeMillis());
    }

    //结束时打印耗时
    public static void finish(String methodName) {
        long finishTime = System.currentTimeMillis();
        System.out.println(methodName + "方法耗时" + (finishTime - tl.get()) + "ms");
    }
}

class StuInvocationHandler<T> implements InvocationHandler {
    //invocationHandler持有的被代理对象
    T target;

    public StuInvocationHandler(T target) {
        this.target = target;
    }

    /**
     * proxy:代表动态代理对象
     * method：代表正在执行的方法
     * args：代表调用目标方法时传入的实参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行" +method.getName() + "方法");

        //代理过程中插入监测方法,计算该方法耗时
        MonitorUtil.start();
        Object result = method.invoke(target, args);
        MonitorUtil.finish(method.getName());
        return result;
    }
}
