# redis

远程字典服务Remote Dictionary Server 基于k-v的内存数据库，一般在业务系统中数据量较大的交互时，做缓存使用，加快响应速度，提升用户体验。（Redis 一秒写8万次，读取11万），公司使用版本3.2.8，目前6.0版本

**扩展 :** 数据操作的速度

> ![1607081345017](C:\Users\coco\AppData\Roaming\Typora\typora-user-images\1607081345017.png)

### redis具体的应用场景

**redis的特点**：

​	work工作线程是单线程的，处理数据是串行的，底层调用epoll，因此不存在线程不安全的问题。因此可以做秒杀，抢红包等实时性，数据安全性要求高的场景。

+ 数据库缓存。（String）

+ Session缓存，单点登录。（String）

+ 电商秒杀活动，计数器。（String incr，decr，incrby，decreby 原子递增（atomic increment））

+ 消息队列，抢红包（List的左右都能push和pop，先进先出就可以做队列）

+ 好友共同关注，共同爱好，推荐好友（set集合取交集）

+ 微博热搜榜，排行榜，抽奖，评论分页。（sorted set有序集合）

+ 用户信息保存（hash，快速，否则存在String中取出整个对象在改其中一个属性，慢）

+ 查找附近的人（geo地理位置信息）

+ uv用户访问量（hyperloglog基数计算一个集合中不重复的元素数量，计算user view用户访问，有错误率）

+ 统计活跃用户，打卡签到（bitmap，占用空间小，位运算很快，凡事两个状态的数据，都可以使用bitmap）

+ 微信公众号，群聊。（pub/sub发布订阅频道，哨兵sentinel就使用了这个功能）

+ 分布式锁（setnx，想要获取锁，先set if not exist成不成功，成功就获取资源。否则就自旋一下再次尝试获取）

+ Redis Stream（5.0提供的功能，消息队列消息持久化，pub/sub发布订阅宕机后消息丢失）

  

### Redis事务

A原子性，C顺序性、I隔离性，D持久性（一旦被提交就是持久化的）

redis事务不保证原子性。即某一条命令执行失败也可能事务执行成功。

**redis事务：**

+ 开启事务（multi）

+ 命令入队（set ...）

+ 执行事务（exec）

**Watch监控：**

watch可以当做redis的乐观锁（CAS  比较与交换，Compare And Swap去执行的）

```bash
127.0.0.1:6379> set money 100
OK
127.0.0.1:6379> set out 0
OK
127.0.0.1:6379> watch money   # 监视  money
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379> DECRBY money 10
QUEUED
127.0.0.1:6379> INCRBY out 10
QUEUED
127.0.0.1:6379> exec  # 执行之前，另外一个线程，修改了我们的值，这个时候，就会导致事务执行失败！
(nil)
```

**如果修改失败，获取最新的值就好**

```bash
127.0.0.1:6379> unwatch   # 如果发现事务失败，先解锁
OK
127.0.0.1:6379> watch money  #获取最新的值，再次监视
OK
127.0.0.1:6379> DECRBY money 10 
QUEUED
127.0.0.1:6379> INCRBY out 10 #
QUEUED
127.0.0.1:6379> exec  
1) (integer 90)
2) (integer 10)
```

![image-20201202174957981](C:\Users\chenmeilan\AppData\Roaming\Typora\typora-user-images\image-20201202174957981.png)

+ 扩展 

  线程要把v = A修改为v = B，先记录A（old Value），然后更新的时候再取出v看是否与A相等，如果相等，则设置v = B。如果不相等，则自旋一会再尝试修改。

> [什么是CAS？乐观地认为并发不那么严重，让线程不断去重试更新](https://blog.csdn.net/qq_32998153/article/details/79529704)



### redis持久化

+ rdb （Redis DataBase）

  + 触发规则：

    + 配置文件中的save规则

      > ![image-20201202191857120](C:\Users\chenmeilan\AppData\Roaming\Typora\typora-user-images\image-20201202191857120.png)

    + flushall

    + 退出redis

  + 定期保存为dump.rdb文件，主进程fork一个子进程来写rdb文件，大规模数据恢复上有优势，但如果宕机，会丢失最近的一部分数据。

+ aof （Append Only File）

  + 触发规则

    > ![image-20201202194048567](C:\Users\chenmeilan\AppData\Roaming\Typora\typora-user-images\image-20201202194048567.png)

  + 记录每一条数据写的日志，解决了数据持久化的实时性。

    扩展：

    > [redis主从复制全量同步与增量同步](http://blog.itpub.net/15498/viewspace-2151660/)
    >
    > [AOF详解](https://blog.csdn.net/weixin_39040059/article/details/79120444)

+ aof与rdb对比

  + 数据文件aof远大于rdb，修复速度也比rdb慢
  + aof运行速率比rdb要慢

+ aof与rdb都存在的情况下的数据恢复

  > ![image-20201202193748892](C:\Users\chenmeilan\AppData\Roaming\Typora\typora-user-images\image-20201202193748892.png)

+ 建议：
  
  + 因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够了，只保留 save 900 1 这条规则。

### redis主从复制

一般3个节点，主负责写请求，从负责读请求。

主从复制，主宕机后会出现单点故障问题，主节点不会迁移，因此需要借助哨兵sentinel来实现高可用。

### redis哨兵模式

一般做哨兵集群，监控redis集群中主节点挂了的话，哨兵集群就会进行投票选出新的master

### redis单线程模型

redis在6.0是多线程的多线程指io threads多线性，work工作线程还是单线程的，在6.0以前是单线程的。

连接池：存放多个socket的list或者数组集合。 
redis是的工作线程是单线程的，主要使用的是epoll的多路复用，一个线程处理多个socket。
服务器接收客户端数据，通过socket连接。
服务端先创建一个socket，bind绑定端口，listen监听socket，然后accept等待客户端连接，accept会阻塞（线程挂起，放弃CPU资源），等待客户端连接。（客户端创建socket，connect连接）
（服务器有两类socket，一个accept监听socket，一个通讯socket）

### redis缓存穿透

透，相当于redis是透明的。其实就是访问了大量数据库中没有的数据。自然redis中也不会有，所以大量的请求直接穿过redis，打到数据库把数据库打崩。

**解决方案：**

+ **布隆过滤器**，把所有的数据都加载到布隆过滤器的bit二进制数组中，它可以百分比确定某一个元素不在数组中，直接告诉客户端数据不存在。但有误判，但99.9的准确率足以保证只有微乎其微的访问（不存在的数据）能进入到数据库层面。
+ **缓存空对象**，给值为空的key缓存起来，并设置一个过期时间。

+ **数据库限流**，降级，排队，上锁。

### redis缓存击穿

缓存击穿，这个击，表示击打的意思。好比一块钢板，子弹不停的射击一个点，就很容易把钢板击穿。redis中如果大量客户访问了同一个数据，而这个数据刚好过期了，那么大量请求直接打到数据库，数据库压力攀升。

**解决方案：**

+ 设置热点数据永不过期。
+ 加锁排队限流。

### redis雪崩

**是指在某一个时间段，大量缓存集中过期失效，或者宕机。**接着来的一大波请求瞬间都落在了数据库中导致连接异常。
解决方案：

事前：数据预热，另外给缓存设置随机的过期时间，防止集中失效。

方案1、访问数据库限流、降级，加锁排队;
方案2、做redis集群，高可用，可以做异地多活;

+ 扩展

> 顾名思义，异地多活架构的关键点就是异地、多活，其中异地就是指地理位置上不同的地方**，类似于“不要把鸡蛋都放在同一篮子里”；**多活就是指不同地理位置上的系统都能够提供业务服务**，这里的“活”是活动、活跃的意思。判断一个系统是否符合异地多活，需要满足两个标准：
>
> - 正常情况下，用户无论访问哪一个地点的业务系统，都能够得到正确的业务服务。
> - 某个地方业务异常的时候，用户访问其他地方正常的业务系统，能够得到正确的业务服务。
>
> 与“活”对应的是字是“备”，备是备份，正常情况下对外是不提供服务的，如果需要提供服务，则需要大量的人工干预和操作，花费大量的时间才能让“备”变成“活”。



### redis string hash底层实现区别

