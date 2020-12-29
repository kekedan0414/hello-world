# JAVA

## 并发编程与JUC

### JMM--java线程内存模型

jmm数据原子操作

read 从主内存读取数据

load 将主内存的值读取到(线程的)工作内存

use 将(线程的)工作内存的值给cpu使用

assign 将计算好的值重新赋值给（线程的）工作内存

store 将工作内存的值存入主内存

write将store过来的值赋值给变量

volitale： MESI协议（缓存一致性协议），第一时间将工作线程的值同步到主内存中，另一个工作线程有一个CPU嗅探机制，嗅探到了这个值被修改了，那么立即让本工作线程的值失效（地址里存的值清空）并且调用read从主内存读取新值。

汇编层级：在指令前面加一个lock前缀指令（ADD 1 2--> lock ADD 1 2）,lock指令有两个作用：1、CPU第一时间将工作线程的值同步到主内存中

2、开启MESI协议

为什么是轻量级的，加锁粒度非常小，只在jmm store会写到主内存的时候加锁。

volitale不保证原子性，10个线程对一个变量++操作1000次，最终小于1000，CPU嗅探机制，嗅探到了这个值被修改了，那么立即让本工作线程的值失效（地址里存的值清空），然后在读主内存的值。

并发编程三大特性，可见性，原子性，有序性。

问题：不使用synchronize和lock，如何保证volitale的原子性？ 答案：因为num++实际上是两个操作，先+1在赋值，解决：变量使用atomic原子类（底层用了CAS）

单例里面加volitale防止指令重排。

> 扩展：反射可以破坏单例。那如何防止？
>
> 方法1、可以在私有构造函数里判断，但还是不能完全防止。原因：
>
> 

指令重排在并发编程中还有另一个问题，就是多个线程操作相互依赖的变量时，结果可能和顺序执行的预期不符。

### 可重入锁

一个线程可以多次获取锁。synchronized和ReentrantLock, ReentrantLock （底层使用AQS）和 synchronized 不一样，需要手动释放锁，所以使用 ReentrantLock的时候一定要**手动释放锁**，并且**加锁次数和释放次数要一样**。

什么是可重入锁，不可重入锁呢？"重入"字面意思已经很明显了，就是可以重新进入。可重入锁，就是说一个线程在获取某个锁后，还可以继续获取该锁，即允许一个线程多次获取同一个锁。比如synchronized内置锁就是可重入的，如果A类有2个synchornized方法method1和method2，那么method1调用method2是允许的。显然重入锁给编程带来了极大的方便。假如内置锁不是可重入的，那么导致的问题是：1个类的synchornized方法不能调用本类其他synchornized方法，也不能调用父类中的synchornized方法。

```java
// 可重入降低了编程复杂性
//synchronized
public class WhatReentrant {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					System.out.println("第1次获取锁，这个锁是：" + this);
					int index = 1;
					while (true) {
						synchronized (this) {
							System.out.println("第" + (++index) + "次获取锁，这个锁是：" + this);
						}
						if (index == 10) {
							break;
						}
					}
				}
			}
		}).start();
	}
}
//ReentrantLock 
public class WhatReentrant2 {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lock();
					System.out.println("第1次获取锁，这个锁是：" + lock);

					int index = 1;
					while (true) {
						try {
							lock.lock();
							System.out.println("第" + (++index) + "次获取锁，这个锁是：" + lock);
							
							try {
								Thread.sleep(new Random().nextInt(200));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							if (index == 10) {
								break;
							}
						} finally {
							lock.unlock();
						}

					}

				} finally {
					lock.unlock();
				}
			}
		}).start();
	}
}
```

### 为什么有synchronized还需要AQS

synchronize最终需要调用操作系统底层mutex，开销大。AQS在JVM层实现锁，不需要与OS交互。另外可以设置条件，更加灵活。

> 扩展：jdk1.6以后，synchronize改进了锁的等级以及升级策略，无锁-->偏向锁-->轻量锁（自旋锁）-->重量锁
>
> 扩展：synchronize锁是锁整个类。并不是某一个代码块。

### synchronized

**synchronized你到底锁住的是谁？**

synchronized从语法的维度一共有3个用法：

1. 静态方法加上关键字
2. 普通方法加上关键字
3. 方法中使用同步代码块

synchronized从锁的是谁的维度一共有两种情况：

+ 对象锁：对于对象锁，如果两个线程里对应的是同一个对象，那么其中一个线程必然不可能获取这个锁；如果两个线程针对的是两个对象实例，那么这两个线程不相关均能获取这个锁。

+ 类锁：而对于类锁，上述两种情况，都有一个线程无法获得锁。

代码块中，synchronized(this){...}是对象锁，如果this里的A方法和B方法都加了锁，假设线程1调用对象A方法，并拿到这这个对象的锁，线程2调用B方法，是需要等待锁的。因为锁的是整个对象。

```java
/**
* Description:
* synchronized同步代码块对本实例加锁（this）
* 假设demo1与demo2方法不相关，此时两个线程对同一个对象实例分别调用demo1与demo2，只要其中一个线程获取到了锁即执行了demo1或者demo2，此时另一个线程会永远处于阻塞状态
* 2019-06-13
* Created with OKevin.
*/
public class Demo {

   public void demo1() {
       synchronized (this) {
           while (true) {  //死循环目的是为了让线程一直持有该锁
               System.out.println(Thread.currentThread());
           }
       }
   }

   public void demo2() {
       synchronized (this) {
           while (true) {
               System.out.println(Thread.currentThread());
           }
       }
   }
}
```

那么这样效率就变低了，优化上述方式：private Object obj = new Object();   synchronized(obj){...}

```java
/**
* Description:
* synchronized同步代码块对对象内部的实例加锁
* 假设demo1与demo2方法不相关，此时两个线程对同一个对象实例分别调用demo1与demo2，均能获取各自的锁
* 2019-06-13
* Created with OKevin.
*/
public class Demo {
   private Object lock1 = new Object();
   private Object lock2 = new Object();

   public void demo1() {
       synchronized (lock1) {
           while (true) {  //死循环目的是为了让线程一直持有该锁
               System.out.println(Thread.currentThread());
           }
       }
   }

   public void demo2() {
       synchronized (lock2) {
           while (true) {
               System.out.println(Thread.currentThread());
           }
       }
   }
}
```

synchronized(Demo.class){...} 类锁。





> 扩展：[synchronize锁的是类还是对象，失之毫厘谬之千里](https://www.cnblogs.com/yulinfeng/p/11020576.html)

### AQS

### CAS

比较交换，CPU底层就有CAS指令，原子操作。乐观锁，先比较，如果相等再交换。如果不相等，就自旋。

CAS有ABA问题：如果变量初始值是1，线程A拿到1后改成2，再写回变量，这是不会有问题的。但是如果你拿到1后，被线程B改成3又被改回1，这个时候需要看你的业务系统在不在乎这个ABA（1--3--1）中间过程的改变。如果在乎的话，解决方案是加一个AtomicStampleReference版本号，每个线程拿到值后都给版本号+1。如果不在乎被改了多少次，可以使用AtomicMarkableReference，如果是false表示没被其他线程改动过，如果变为true，表示有被改变过。

### Semphore 信号量

与synchronize完全串行不同，1000个线程，可以运行10个线程一起跑

> 扩展：[Java信号量](https://blog.csdn.net/qq_25956141/article/details/102959976)

### object.wait()

让当前线程进入该锁的等待队列，并释放object锁，[wait、notify，notifyall](https://www.cnblogs.com/xumaomao/p/12843683.html)

notify随机唤醒，notifyall全部唤醒。唤醒策略基于操作系统，比如CFS（completely-fair-scheduler）

### LockSupport

LockSupport.park()       t线程停车。

LockSupport.unpark(t)     可以指定唤醒某个线程。

### ReentrantLock

lock(), unlock() ,await()，signalall()

默认为非公平的（可以插队），可以传参true改为公平锁（先来后到）

```java
static class ProducerAndConsumer{

        boolean flag = false;
        Lock lock = new ReentrantLock();
        Condition producerCon = lock.newCondition();
        Condition consumerCon = lock.newCondition();

        public void producer(){
            lock.lock();
            if(flag){
                System.out.println("还有食物，暂时不需要生产");
                    try {
                        producerCon.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            flag = true;
            System.out.println("生产者生产了食物");
            consumerCon.signal();
            lock.unlock();
        }

        public void consumer(){
            lock.lock();
            if(!flag){
                System.out.println("没有食物了,通知生产者生产");
                try {
                    consumerCon.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("消费者进食中");
            flag = false;
            producerCon.signal();
            lock.unlock();
        }
    }

    public static void main(String []args) throws Exception{
        producerAndConsumer pac = new producerAndConsumer();
            new Thread(()->{
                for(int i=0;i<3;i++)
                pac.producer();
            }).start();

            new Thread(()->{
                for(int i=0;i<3;i++)
                pac.consumer();
            }).start();
    }
```

condition可以做到精准的通知控制。

### CountDownLatch门栓计数器

1、等待所有线程结束，类似join

```java

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线程等待子线程执行完成再执行
 */
public class CountdownLatchTest1 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("子线程"+Thread.currentThread().getName()+"执行完成");
                        latch.countDown();//当前线程调用此方法，则计数减一
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        try {
            System.out.println("主线程"+Thread.currentThread().getName()+"等待子线程执行完成...");
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("主线程"+Thread.currentThread().getName()+"开始执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```



百米赛跑，4名运动员选手到达场地等待裁判口令，裁判一声口令，选手听到后同时起跑，当所有选手到达终点，裁判进行汇总排名

```java
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchTest2 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        cdOrder.await();
                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("裁判"+Thread.currentThread().getName()+"即将发布口令");
            cdOrder.countDown();
            System.out.println("裁判"+Thread.currentThread().getName()+"已发送口令，正在等待所有选手到达终点");
            cdAnswer.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判"+Thread.currentThread().getName()+"汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
```

CountDownLatch典型用法：1、某一线程在开始运行前等待n个线程执行完毕。

CountDownLatch典型用法：2、实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发,类似semphore。

### CyclicBarrier栅栏

```java
public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() ->{
            System.out.println("****召唤神龙");
        });
        for(int i = 1;i <= 7; i++){
            int finalI = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第"+ finalI +"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
```

从javadoc的描述可以得出区别：

- CountDownLatch：一个或者多个线程，等待其他多个线程完成某件事情之后才能执行；
- CyclicBarrier：多个线程互相等待，直到到达同一个同步点，再继续**一起执行**。

对于CountDownLatch来说，重点是“一个线程（多个线程）等待”，而其他的N个线程在完成“某件事情”之后，可以终止，也可以等待。而对于CyclicBarrier，重点是多个线程，在任意一个线程没有完成，所有的线程都必须互相等待，然后继续一起执行。

CountDownLatch是计数器，线程完成一个记录一个，只不过计数不是递增而是递减，而CyclicBarrier更像是一个阀门，需要所有线程都到达，阀门才能打开，然后继续执行。

> [可重复使用的栅栏](https://www.jianshu.com/p/beed2c00ff6d)

### ReadwriteLock读写锁

ReadWriteLock管理一组锁，一个是只读的锁（共享锁），一个是写锁（独占锁），如果不管是读写都锁住的话，效率低。而读写锁可以

 1.Java并发库中ReetrantReadWriteLock实现了ReadWriteLock接口并添加了可重入的特性。
 2.ReetrantReadWriteLock读写锁的效率明显高于synchronized关键字。
 3.ReetrantReadWriteLock读写锁的实现中，读锁使用共享模式；写锁使用独占模式，换句话说，读锁可以在没有写锁的时候被多个线程同时持有，写锁是独占的。
 4.ReetrantReadWriteLock读写锁的实现中，需要注意的，当有读锁时，写锁就不能获得；而当有写锁时，除了获得写锁的这个线程可以获得读锁外，其他线程不能获得读锁。

锁升级 读锁 ----> 写锁

锁降级 写锁 ----> 读锁

```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test1 {
    public static void main(String[] args) {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.readLock().lock();
        System.out.println("get readLock.");
        rtLock.writeLock().lock();
        System.out.println("blocking");
    }
}
//打印：get readLock.产生死锁

public class Test2 {
    public static void main(String[] args) {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();  
        rtLock.writeLock().lock();  
        System.out.println("writeLock");  
          
        rtLock.readLock().lock();  
        System.out.println("get read lock");  
        //lock.writeLock().unlock();
        //lock.readLock().unlock();
    }
}
//打印：writeLock
//  get readLock.
       
```

结论：**ReentrantReadWriteLock支持锁降级**，上面代码不会产生死锁。这段代码虽然不会导致死锁，但没有正确的释放锁。从写锁降级成读锁，并不会自动释放当前线程获取的写锁，仍然需要显示的释放，否则别的线程永远也获取不到写锁。

### Thread、Callable、Runnable

```java
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("This is a runnable!");
        }
    };
    new Thread(myRunnable).start();// 为什么用start不用run，start会新起一个线程，而myThread.run()只会在当前线程执行Runnable的run方法方法。


    Callable callable = new Callable<Integer>() {    //返回值
        @Override
        public Integer call() throws Exception {    //异常
            System.out.println("This is a callable!");
            return 5;
        }
    };
    FutureTask<Integer> futureTask = new FutureTask<>(callable); 
    new Thread(futureTask).start(); 	//Thread不能直接接收callable，必须用FutureTask包一层。（futureTask里面有run方法，run里面调用call）
    System.out.println(futureTask.get());
```



### Thread.sleep()与wait()区别

sleep谁调用谁睡觉，让出CPU给其他线程。如果sleep在同步代码块中，那么当前线会阻塞，但不会释放锁。也就是其他的竞争该锁的线程会等待锁的释放。

wait()挂起当前线程，并释放锁，在同步代码块中执行的话，其他的竞争该锁的线程会得到锁并执行。而wait挂起的线程得等待有notify通知并且其他线程锁释放后，即可以继续执行。



### 函数式编程

**函数式接口**：只有1个方法的接口。

**四大函数式接口**：

+ **Function Interface**   函数型接口，接收一个参数，加工后返回一个值。

  ```java
      /*  @FunctionalInterface
          public interface Function<T, R> {
              R apply(T t);
          }
      */
  
  	Function<String,String> function = new Function<String, String>() {
          @Override
          public String apply(String str) {
              return str;
          }
      };
      System.out.println(function.apply("hello,function"));
  	//简化   ()->{}
      Function<String,String> function1 = (str) -> {return str;};
      System.out.println(function1.apply("hello,function1"));
  	//再简化，把return，() {}都可以去掉
      Function function2 = str -> str;
      System.out.println(function2.apply("hello,function2"));
  ```

  

+ **Predicate Interface**   断定型接口，输入一个参数，返回true false。

  ```java
  /*  @FunctionalInterface
      public interface Predicate<T> {
          * Evaluates this predicate on the given argument.
              *
              * @param t the input argument
              * @return {@code true} if the input argument matches the predicate,
          * otherwise {@code false}
          boolean test(T t);
      }
  */
  
      Predicate<String> predicate = new Predicate<String>() {
          @Override
          public boolean test(String str) {
              return str.contains("dog");
          }
      };
      System.out.println(predicate.test("dog"));
  
      Predicate<String> predicate1 = str -> str.contains("dog");
      System.out.println(predicate1.test("cat"));
  ```

+ **Consumer Interface**   消费型接口，接收一个参数，内部消化

  ```java
  /*  @FunctionalInterface
      public interface Consumer<T> {
           * Performs this operation on the given argument.
           *
           * @param t the input argument     
          void accept(T t);
      }
  */
  
      Consumer<String> consumer = new Consumer<String>() {
          @Override
          public void accept(String s) {
              System.out.println(s);
          }
      };
      consumer.accept("hello,concumer");
  
      Consumer consumer1 = str -> System.out.println(str);
      consumer1.accept("hello,consumer1");
  ```

  

+ **Supplier Interface**   供给型接口，不接受参数，返回一个值

  ```java
  /*	@FunctionalInterface
      public interface Supplier<T> {
           * Gets a result.
           *
           * @return a result     
          T get();
      }
  */
      Supplier supplier = new Supplier() {
          @Override
          public Object get() {
              return "hello,I'm Supplier";
          }
      };
      System.out.println(supplier.get());
  
      Supplier supplier1 = () -> "hello,I'm Supplier1";
      System.out.println(supplier1.get());
  ```

### Lambda表达式

Runnable和Callable接口都只有一个方法

```java
   //1、定义变量方法
   Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("This is a runnable!");
        }
    };
    Thread myThread = new Thread(myRunnable);
    myThread.start();

   //2、不定义变量直接执行
	new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("This is a runnable!");
        }
    }).start();

   //2、lambda   ()->{}
	new Thread(()->{
        System.out.println("This is a runnable!");
    }).start();

	//普通方式
    Callable callable1 = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            System.out.println("This is a callable!");
            return 5;
        }
    };
    FutureTask<Integer> futureTask1  = new FutureTask<>(callable1);
    new Thread(futureTask1).start();
	System.out.println(futureTask.get());

	//lambda
    Callable callable2 = () -> {
        System.out.println("This is a callable!");
        return 5;
    };
    FutureTask<Integer> futureTask2  = new FutureTask<>(callable2);
    new Thread(futureTask2).start();
	System.out.println(futureTask.get());
```

虽然使用 Lambda 表达式可以对某些接口进行简单的实现，但并不是所有的接口都可以使用 Lambda 表达式来实现。**Lambda 规定接口中只能有一个需要被实现的方法，不是规定接口中只能有一个方法**

### Stream流式编程

```java
		/**一、
         * 1、只要id为偶数的用户
         * 2、只要>23岁的
         * 3、用户名转为大写
         * 4、按用户名倒着排序
         * 5、只输出1个用户
         */
        User u1 = new User(1, "a", 21);
        User u2 = new User(2, "b", 22);
        User u3 = new User(3, "c", 23);
        User u4 = new User(4, "d", 24);
        User u5 = new User(5, "e", 25);

        List<User> list1 = Arrays.asList(u1, u2, u3, u4, u5);
        list1.stream()
            .filter(u -> u.getId()%2==0)
            .filter(u -> u.getAge()>23)
            .map(u -> u.getName().toUpperCase())
            .sorted(Comparator.reverseOrder())
            .limit(1)
            .forEach(System.out::println);
		
		//二、并行计算 0到100000000求和，底层使用的forkJoin分治的思想
		System.out.println(LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum));
```



### ForkJoin工作窃取

AB线程同时运行，如果A执行完了，B还没有执行完，A可以窃取B的工作提高效率（work-stealing）。在大数据量的情况下使用，小数据量下效果不明显。



### 异步回调

```java
     	//
        CompletableFuture<String> completableFuture= new CompletableFuture<>();
        new Thread( () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" 执行.....");
                completableFuture.complete("success");//
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        String result=completableFuture.get();//主线程阻塞，等待完成
        System.out.println(Thread.currentThread().getName()+" 1x:  "+result);

        //输出结果：
        //Thread-0 执行.....
        //main 1x:  success
```



```java
        //运行一个有返回值的异步任务
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>(){
            @Override
            public String get() {
                try {
                    System.out.println(Thread.currentThread().getName()+"正在执行一个有返回值的异步任务。");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "OK";
            }
        });
        String result1=future.get(); //阻塞10秒，等待子线程完成
        System.out.println(Thread.currentThread().getName()+"  结果："+result1);

        //输出结果：
        //ForkJoinPool.commonPool-worker-1正在执行一个有返回值的异步任务。
        //main  结果：OK
```



```java

```





> [什么是回调？异步回调](https://www.cnblogs.com/liujiarui/p/13395424.html)
>
> 参考MQ的远程调用，消费者要消费MQ上的消息，消费者写一个回调方法，这个方法给MQ来调用，而回调方法是在消费者上执行的，执行完并把返回值（消费状态）返回给MQ（MQ收到消费成功的状态后再删除消息）。



### 线程池参数

核心线程数
最大并发线程数
普通线程的空闲超时时间。
缓存队列，超过常驻的线程数的线程会进入到缓存队列，队列满了就会创建新的线程直到max最大线程数。
拒绝策略，队列满了之后，线程池到了max，那么新的任务是否丢弃，等待，丢弃老的，或者抛出异常。

最大并发线程数如何定义？

CPU密集型：CPU比较忙，最佳性能就是开当前最大核数（如果是超线程可以开当前最大的超线程数）。

IO密集型：CPU比较空闲，可以适当比当前IO任务线程数多一点。

[CPU密集型，IO密集型](https://www.cnblogs.com/aspirant/p/11441353.html)



### CopyOnWriteArrayList/Set

底层使用的是写时复制技术以及可重入锁：

```Java
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();		//先加锁
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);		//复制到新数组中
            newElements[len] = e;		//在新数组中添加元素
            setArray(newElements);		//将元素设置为新数组
            return true;
        } finally {
            lock.unlock();
        }
    }
```

HashSet底层就是使用的HashMap，只是使用了它的Key。

JUC并发包下的线程安全的HashMap为ConCurrentHashMap。

### HashMap

hashmap相当于数组和链表的一个结合方案。
数组插入和删除数据涉及移动数据，但它空间占用少。
链表新增删除数据很方便，但空间占用多。
数组和链表的平均复杂度都为o（n）
hashmap主干使用数组，通过key和映射关系直接找到数组的位置，时间复杂度为0(1)
但是映射关系可能会导致不同的key找到同一个位置，这就是hash冲突，为了解决这个问题，引入了链表，如果遇到冲突，
就以链表形式加在它位置的后面。

### HashMap的容量，refactor加载因子

HashMap容量（capacity）就是数组的大小，默认是16，rehash的时候也会设置为2的幂次方。
而作为默认容量，太大和太小都不合适，所以16就作为一个比较合适的经验值被采用了。
refactor加载因子0.75 ，取0.5到1的折中值。
如果太大的话，所有数组都可以被填满，空间利用率提高了，但hash冲突几率变大，查询会变慢。  
太低的话，用不了多久就rehash，rehash太频繁。

### hashmap死循环

jdk1.6、1.7存在这个问题，1.8不存在
主要原因：hashmap是线程不安全的，在多线程的情况下，假如两个线程同时触发了resize。
并且对同一个链表进行迁移的时候，可能导致部分链表首尾相连。当hash.get操作这个链表时如果查找的数据不存在，那么就进入了死循环。
根本原因就是transfer函数中是对链表的头部插入数据导致的。所以1.8对链表尾部进行了数据插入。（插入头效率高）
另外一个问题是链表首位相连，而另一部分数据没有被引用，
建议用ConcurrentHashMap

https://www.cnblogs.com/jing99/p/11319175.html

### hashcode 与 equal
覆盖equals（Object obj）但不覆盖hashCode（）,导致数据不唯一性
覆盖hashCode方法，但不覆盖equals方法，仍然会导致数据的不唯一性
https://blog.csdn.net/lijiecao0226/article/details/24609559

### JAVA-CPU占比高线程堆栈问题定位

1、top  -p <pid> 查看某个进程的CPU使用情况

2、top -H -p <pid>查看进程中每个线程的CPU使用情况

3、找到最高线程的thread id，并将tid转换为16进制。

4、 执行jstack <pid> | grep -A 10  -i <thread id>   查看该线程的10行堆栈信息，-i 忽略16进制的大小写问题

同样适用于死锁的问题，dead lock

### Exception与RuntimeException区别

+ **Exception**：在程序中必须使用try...catch进行处理。 不用trycatch或者throws抛出的话，编译不通过。
+ **RuntimeException**：可以不使用try...catch进行处理，但是如果有异常产生，则异常将由JVM进行处理。
      不用trycatch的话，出错直接终止程序。一般建议用trycatch捕获。

继承Exception还是继承RuntimeException是由异常本身的特点决定的，而不是由是否是自定义的异常决定的。
例如我要写一个java api，这个api中会调用一个极其操蛋的远端服务，这个远端服务经常超时和不可用。
所以我决定以抛出自定义异常的形式向所有调用这个api的开发人员周知这一操蛋的现实，让他们在调用这个api时务必考虑到远端服务不可用时应该执行的补偿逻辑（比如尝试调用另一个api）。
此时自定义的异常类就应继承Exception，这样其他开发人员在调用这个api时就会收到编译器大大的红色报错：【你没处理这个异常！】，强迫他们处理。又如，我要写另一个api，
这个api会访问一个非常非常稳定的远端服务，除非有人把远端服务的机房炸了，否则这个服务不会出现不可用的情况。
而且即便万一这种情况发生了，api的调用者除了记录和提示错误之外也没有别的事情好做。但出于某种不可描述的蛋疼原因，我还是决定要定义一个异常对象描述“机房被炸”这一情况，

那么此时定义的异常类就应继承RuntimeException，因为我的api的调用者们没必要了解这一细微的细节，把这一异常交给统一的异常处理层去处理就好了。

###  java类加载

1）Bootstrap ClassLoader

负责加载$JAVA_HOME中jre/lib/rt.jar里所有的class，由C++实现，不是ClassLoader子类

2）Extension ClassLoader

负责加载java平台中扩展功能的一些jar包，包括$JAVA_HOME中jre/lib/*.jar或-Djava.ext.dirs指定目录下的jar包

3）App ClassLoader

负责记载classpath中指定的jar包及目录中class

4）Custom ClassLoader

属于应用程序根据自身需要自定义的ClassLoader，如tomcat、jboss都会根据j2ee规范自行实现ClassLoader

加载过程中会先检查类是否被已加载，检查顺序是自底向上，从Custom ClassLoader到BootStrap ClassLoader逐层检查，只要某个classloader已加载就视为已加载此类，保证此类只所有ClassLoader加载一次。而加载的顺序是自顶向下，也就是由上层来逐层尝试加载此类。


由此可见，会首先把保证程序运行的基础类一次性加载到jvm中。而根据资料java最早就是为嵌入式系统而设计的，内存宝贵。所有如果开始就把所有，用的到、用不到的类都加载到jvm中，势必会占用很多宝贵的内存，而且有些class可能压根在整个运行过程中都不会使用。


所有得出结论：

一个应用程序总是由n多个类组成，Java程序启动时，并不是一次把所有的类全部加载后再运行，它总是先把保证程序运行的基础类一次性加载到jvm中，其它类等到jvm用到的时候再加载

重点：final是在准备阶段时就赋值了，static准备阶段时数据是零值，在初始化阶段才会赋值。

参考：https://blog.csdn.net/noaman_wgs/article/details/74489549?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control



## JVM专题

### JVM内存模型

> ![1608456701648](C:\Users\coco\AppData\Roaming\Typora\typora-user-images\1608456701648.png)

#### JVM内存划分

一个JAVA进程拿到自己可支配的内存时，会将内存分为5个部分：

+ **栈区**：存对象的引用、局部变量。

+ **堆区**：存对象。

+ **本地方法栈**：C++ native方法运行的栈区。

+ **程序计数器**：指向程序当前运行的位置。

+ **方法区**：JDK7之前称之为永久代（Permanet Space），JDK8之后改为元数据空间（Meta Space）：存储static静态方法或变量，类信息，ClassLoader，class文件，final常量

> 扩展：栈区、本地方法栈、程序计数器是线程私有的，有多少个线程就有多少份栈区。堆区和方法区是全局共享的。
>
> 扩展：方法区与永久代与元数据空间MetaSpace
>
> 方法区和永久代的关系很像Java中接口和类的关系，类实现了接口，而永久代就是HotSpot虚拟机对虚拟机规范中方法区的一种实现方式。
> 在java1.7开始移除老年代，1.8彻底移除了老年代，取而代之的是MetaSpace。主要区别是MetaSpace放在本地内存，就不会出现永久代OOM内存溢出问题。
>
> 扩展：[JVM内存模型](https://blog.csdn.net/qzqanzc/article/details/81008598)

#### 堆区详解

> ![1608462170499](C:\Users\coco\AppData\Roaming\Typora\typora-user-images\1608462170499.png)

+ **堆划分比例**

  **年轻代**：1/3的堆空间 eden区：8/10 的年轻代 **From**: 1/10 的年轻代 **To**:1/10的年轻代   

  **老年代**： 2/3的堆空间

+ **年轻代**：主要是用来存放新生的对象。一般占据堆的1/3空间。由于频繁创建对象，所以新生代会频繁触发MinorGC进行垃圾回收。

  + Eden区：Java新对象的出生地（如果新创建的对象占用内存很大，则直接分配到老年代）。当Eden区内存不够的时候就会触发MinorGC，对新生代区进行一次垃圾回收。

  + ServivorTo：保留了一次MinorGC过程中的幸存者。

  + ServivorFrom：上一次GC的幸存者，作为这一次GC的被扫描者。

  + MinorGC的过程：MinorGC采用复制算法。首先，把Eden和ServivorFrom区域中存活的对象复制到ServicorTo区域（如果有对象的年龄以及达到了老年的标准，则赋值到老年代区），同时把这些对象的年龄+1（如果ServicorTo不够位置了就放到老年区）；然后，清空Eden和ServicorFrom中的对象；最后，ServicorTo和ServicorFrom互换，原ServicorTo成为下一次GC时的ServicorFrom区。

  > ![1608462917705](C:\Users\coco\AppData\Roaming\Typora\typora-user-images\1608462917705.png)

  垃圾判断条件：根可达算法（Root Search）如果根没有直接引用或间接引用，就判断为垃圾。

+ **老年代**：主要存放应用程序中生命周期长的内存对象。

​    老年代的对象比较稳定，所以MajorGC不会频繁执行。在进行MajorGC前一般都先进行了一次MinorGC，使得有新生代的对象晋身入老年代，导致空间不够用时才触发。当无法找到足够大的连续空间分配给新创建的较大对象时也会提前触发一次MajorGC进行垃圾回收腾出空间。

​    MajorGC采用标记—清除算法：首先扫描一次所有老年代，标记出存活的对象，然后回收没有标记的对象。MajorGC的耗时比较长，因为要扫描再回收。MajorGC会产生内存碎片，为了减少内存损耗，我们一般需要进行合并或者标记出来方便下次直接分配。

​     当老年代也满了装不下的时候，就会抛出OOM（Out of Memory）异常。

+ **永久代**（1.8废弃）

​    指内存的永久保存区域，主要存放Class和Meta（元数据）的信息,Class在被加载的时候被放入永久区域. 它和和存放实例的区域不同,GC不会在主程序运行期对永久区域进行清理。所以这也导致了永久代的区域会随着加载的Class的增多而胀满，最终抛出OOM异常。

​    在Java8中，永久代已经被移除，被一个称为“元数据区”（元空间）的区域所取代。

​    元空间的本质和永久代类似，都是对JVM规范中方法区的实现。不过元空间与永久代之间最大的区别在于：元空间并不在虚拟机中，而是使用本地内存。因此，默认情况下，元空间的大小仅受本地内存限制。类的元数据放入 native memory, 字符串池和类的静态变量放入java堆中. 这样可以加载多少类的元数据就不再由MaxPermSize控制, 而由系统的实际可用空间来控制.

​    采用元空间而不用永久代的几点原因：（参考：http://www.cnblogs.com/paddix/p/5309550.html）

　　1、为了解决永久代的OOM问题，元数据和class对象存在永久代中，容易出现性能问题和内存溢出。

​		2、类及方法的信息等比较难确定其大小，因此对于永久代的大小指定比较困难，太小容易出现永久代溢出，太大则容易导致老年代溢出（因为堆空间有限，此消彼长）。

#### 三种GC及触发

+ **minor GC**：频繁，eden区满了或new出来的新对象大于剩余eden区空间了，触发minorGC。

+ **major GC**：次数少，老年代满了，或者new出来的大对象大于剩余的老年代空间了，触发majorGC。

+ **Full GC**：minor GC + major GC，引起整个程序停顿，专门来进行垃圾回收。Full GC定义是相对明确的，就是针对整个新生代、老生代、元空间（metaspace，java8以上版本取代perm gen）的全局范围的GC；
  + 如果创建一个大对象，eden区中空间不足，直接保存到老年代中，当老年代空间不足时候，直接触发full gc
  + 
  + 调用System.gc()方法

> 扩展：Java中Stop-The-World机制简称STW，是在执行垃圾收集算法时，Java应用程序的其他所有线程都被挂起（除了垃圾收集帮助器之外）。Java中一种全局暂停现象，全局停顿，所有Java代码停止，native代码可以执行，但不能与JVM交互。
>
> ![1608465228835](C:\Users\coco\AppData\Roaming\Typora\typora-user-images\1608465228835.png)
>
> 链接：[minorGC也会触发SWT](https://www.zhihu.com/question/29114369)

#### 对象生死判定

#### 对象晋升

垃圾回收器

JVM调优指令

OOM