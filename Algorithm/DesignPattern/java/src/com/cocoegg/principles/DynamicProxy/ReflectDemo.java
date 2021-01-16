package com.cocoegg.principles.DynamicProxy;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        //new
        Person1 lester = new Boy("lester",30);
        Person1 gtt = new Girl("gtt",22);
        Girl fjy = new Girl("fjy",22);
        Girl zhm = new Girl("zhm",23);

        ((Boy) lester).addGfs((Girl) gtt);
        ((Boy) lester).addGfs(fjy);
        ((Boy) lester).addGfs(zhm);
        ((Boy) lester).dafeiji("jizemingbu");
        lester.sleep();
        lester.doingML();

        ((Girl) gtt).setBf((Boy) lester);
        fjy.setBf((Boy)lester);
        fjy.ziwei("lester",50);
        fjy.doingML();

        System.out.println("===================");

        //reflect
        Class clazzPerson2 = Class.forName("com.cocoegg.principles.DynamicProxy.Person2");
        Class clazzPerson1 = Class.forName("com.cocoegg.principles.DynamicProxy.Person1");
        Field[] fields = clazzPerson1.getDeclaredFields();

        Object person2 = clazzPerson2.newInstance();

        System.out.println(person2);


        ClassLoader classLoaderPerson2 = person2.getClass().getClassLoader();
        System.out.println(classLoaderPerson2);

        ClassLoader classLoader = Class.forName("com.cocoegg.principles.DynamicProxy.ReflectDemo").getClassLoader();
        System.out.println(classLoader);
        System.out.println(lester.getClass().getClassLoader());

        System.out.println(((Boy) lester).getWf().getClass().getClassLoader());

        System.out.println(ClassLoader.getSystemClassLoader());

        System.out.println(classLoader.getParent());

        System.out.println(classLoader.getParent().getParent());

        //Object person1 = clazzPerson1.newInstance();
        //Constructor constructor= new Constructor<Person1>(clazz);

        Class clazz1 = Person1.class;

        Class clazz2 = lester.getClass();


        Method[] methods = clazzPerson1.getMethods();
        for (Method method : methods) {
            System.out.println("methods: " + method.getName());
        }

        Method[] declaredMethods = clazzPerson1.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println("declaredMethods: " + method.getName());
        }

        Method method = clazzPerson2.getMethod("setName",String.class);
        Method method1 = clazzPerson2.getMethod("getName");
        method.setAccessible(true);
        Object object = clazzPerson2.newInstance();
        method.invoke(object,"hello");
        System.out.println(method1.invoke(object));


        Utils.invokeMethodbyObject(object,"setName","gtt");
        Utils.invokeMethodbyClazzName("com.cocoegg.principles.DynamicProxy.Person2", "setName","fjy");
        System.out.println(Utils.invokeMethodbyClazzName("com.cocoegg.principles.DynamicProxy.Person2", "getName"));

        Utils.invokeAnyMethodbyClazzName("com.cocoegg.principles.DynamicProxy.Person2", "move", 50);
        //Utils.invokeMethodbyClazzName("com.cocoegg.principles.DynamicProxy.Person2", "setName","fjy");

        Field[] fields1 = clazzPerson2.getFields();
        for (Field field : fields) {
            System.out.println("Person2 filed: " + field.getName());
        }
        Field[] declaredFields = clazzPerson2.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("Person2 decfiled: " + field.getName());
        }

        Field field = clazzPerson2.getDeclaredField("age");
        System.out.println(field.getName());
        Person2 person21 = new Person2();
        field.set(person21,99);
        System.out.println(field.get(person21));
        System.out.println(field.get(person2));

        Constructor<Person2> constructor = clazzPerson2.getConstructor(String.class,int.class);
        Object obj = constructor.newInstance("lele",31);
        System.out.println(obj);


        ((Person2) obj).setAge(19);
        System.out.println(((Person2) obj).getAge());


        int age = 11;
        Method method2 = clazzPerson2.getMethod("setAge",int.class);
        Annotation annotation = method2.getAnnotation(Validator.class);
         if (annotation != null) {
             if (((Validator) annotation).max() < age ||  ((Validator) annotation).min() > age) {
                 System.out.println("invalid!!!");
             }

         }



        System.out.println();
    }


}


@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@interface Validator {
    int max();
    int min();
}

class Utils {
    public static Object invokeMethodbyObject(Object object, String methodName, Object... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Class[] argsClass = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
        }

        Method method = object.getClass().getDeclaredMethod(methodName,argsClass);
        Object ret =  method.invoke(object,args);
        return ret;
    }

    public static Object invokeMethodbyClazzName(String clazzName, String methodName, Object... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {

        Class[] argsClass = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
        }
        Class clazz = Class.forName(clazzName);
        Object object = clazz.newInstance();
        Method method = object.getClass().getDeclaredMethod(methodName,argsClass);
        Object ret =  method.invoke(object,args);
        return ret;
    }

    public static Object invokeAnyMethodbyClazzName(String clazzName, String methodName, Object... args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class[] argsClass = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
        }
        Class clazz = Class.forName(clazzName);
        Object object = clazz.newInstance();
        Object ret = null;
        try {
            Method method = getAnyClazz(clazz,methodName,argsClass);
            method.setAccessible(true);
            ret = method.invoke(object,args);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Method getAnyClazz(Class clazz,String methodName, Class... argsClass)  {
        for (; clazz != Object.class;clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, argsClass);
                return method;
            } catch (Exception e) {}
        }
        return null;
    }

}

interface ML {
    void doingML();
}

class Creator {
    private int cellNum;
    private void  move(Integer integer){
        System.out.println("step : " + integer);
    }
}

class Person2 extends Creator {

    protected String name;
    protected int age;

    public Person2() {
    }

    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Validator(max = 18,min = 12)
    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


abstract class Person1 implements ML {
    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person1() {
        super();
    }

    protected String name;
    protected int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    abstract public void doingML();

    abstract void sleep();

    private void privateMethod() {}
}

class Boy extends Person1 {
    private List<Girl> gfs = new ArrayList<>();
    private Girl wf = new Girl("qh",29);
    public Boy(String name, int age) {
        super(name, age);
    }

    public Girl getWf() {
        return wf;
    }

    public List<Girl> getGfs() {
        return gfs;
    }

    public void addGfs(Girl gf) {
        this.gfs.add(gf);
    }

    @Override
    public void doingML() {

        System.out.println("nei she " + gfs.get((int) (Math.random()*gfs.size())).getName());
    }

    @Override
    public void sleep() {
        System.out.println("missing girls!");
    }

    public void dafeiji(String nvyou){
        System.out.println("watch " + nvyou + " da fei ji ,she jing!");
    }
}

class Girl extends Person1 {
    private Boy bf;
    public Girl(String name, int age) {
        super(name, age);
    }

    public Girl(String name, int age, Boy bf) {
        super(name, age);
        this.bf = bf;
    }

    public Boy getBf() {
        return bf;
    }

    public void setBf(Boy bf) {
        this.bf = bf;
    }

    @Override
    public void doingML() {
        System.out.println("accept " + bf.getName() + " jingye!");
    }

    @Override
    public void sleep() {
        System.out.println("missing boy!");

    }

    public void ziwei(String  boyStar, int times){
        System.out.println("watch " + boyStar + " charu " + times  +" times!");
    }
}
