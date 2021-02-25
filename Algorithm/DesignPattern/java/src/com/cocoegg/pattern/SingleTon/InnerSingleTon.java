package com.cocoegg.pattern.SingleTon;

/**
 * @author cocoegg
 * @date 2021/1/6 - 11:18
 */
public class InnerSingleTon {

    public static void main(String[] args) {
        //缺点：可以反射或者反序列化攻击！
        InnerSingleTon.getInstance();
    }

    private InnerSingleTon() {
    }

    /**
    静态内部类的优点是：外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。
    即当SingleTon第一次被加载时，并不需要去加载SingleTonHoler，只有当getInstance()方法第一次被调用时，
    才会去初始化INSTANCE,第一次调用getInstance()方法会导致虚拟机加载SingleTonHoler类，
    这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
     **/
    private static class SingleTonHoler{
        private static InnerSingleTon INSTANCE = new InnerSingleTon();
    }

    public static InnerSingleTon getInstance(){
        return SingleTonHoler.INSTANCE;
    }

}
