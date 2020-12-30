## HDFS

### HDFS适合场景

 1、存储大文件场景

 2、一次写入，多次读出分析的场景

 3、数据增长非常快的，HDFS可以横向扩展

 4、HDFS有副本机制，可以提高数据的安全性。

### HDFS不适合场景

 1、存储小文件，因为小文件太多，HDFS的namenode中的元数据也会越多。而元数据存在内存中，占用过多内存。

 2、对延迟要求高的，HDFS不适合毫秒级的数据访问。

 3、对文件进行多次写入修改的。HDFS只支持文件的追加，不支持修改。

### HDFS中几个角色

#### 1、Client 客户端

+ 文件切分，文件写入HDFS时，Client将文件切分成一个一个的Block，然后进行存储。
+ 与NameNode交互，获取文件的位置信息
+ 与DataNode交互，读取写入数据	

#### 2、NameNode 管理节点

+ 存储元数据信息，一个文件被切成了几个block，有几个副本，每个block大小以及存放DataNode的位置信息。维护一个block块列表
+ 处理客户端读写请求
+ 定期把内存中的元数据写入到fsimage镜像中，fsimage在启动时会被加载到内存与editlog操作日志合并。

#### 3、DataNode 数据节点

+ 具体的读写操作
+ 汇报心跳、定期汇报自己的数据块信息、以及数据库缓存信息（某个block块经常被访问，就将块缓存到内存中）。

#### 4、Secondary NameNode

+ 辅助NameNode，分担工作量

> 扩展: 机架感知：默认block块128M，3个副本。机架rack，将副本存储到不同机架上。



### HDFS读写流程

#### 写流程

1. Client请求上传文件
2. nameNode检查上传权限（权限以及文件相关检查，文件是否已经存在）
3. nameNode返回Client可以上传。
4. Client将文件进行切分blk块，向nameNode请求上传blk1，（假设有blk1，blk2，blk3）
5. nameNode根据集群中的dn上的blk信息和机架信息，选出可以上传的3个主机（datanode1，2，3），来存储blk1。
6. namenode把datanode列表返回给Client。
7. Client和DataNode建立pipeline。（Client连接dn1，dn1连接dn2，dn2连接dn3.）
8. Client将blk1划分为packet（64k）通过socket流传给dn1，dn1通过pipeline传给dn2，（划分为packet的好处是，可以像水管流水一样不停的传数据，提高效率）
9. 每个节点都会将packet信息缓存起来，然后写入文件。
10. 写入文件后，发送应答ACK。后一个节点往前一个节点发应答，最后告诉Client，这个packet传送完成。
11. 第一个blk的所有packet传完后，给Client回应，Client继续传第二个blk。



#### 读流程

1. Client向nameNode请求，读取文件。
2. nameNode对客户端以及文件进行权限检查，然后根据策略选出合适的blk所在的datanode（blk1：dn1，dn3，dn4）（blk2：dn2，dn3，dn4）。
3. 返回给客户端该文件的block列表。客户端就知道去哪个datanode去找相应的blk。
4. Client和对应的datanode分别建立pipeline。
5. 读取的时候，并行执行，按照pipeline的packet读到客户端。
6. 客户端将三个blk合并为完整文件。

### 元数据管理

#### fsimage 元数据镜像

fsimage是一段时间内元数据的快照。

#### edits 操作日志

edits是最近一段时间的操作日志。

fsimage + edits 才是完整的内存中的元数据信息。  （重启的时候会加载fsimage+edits）

考虑一个问题：如果namenode一直不重启，edits会越来越多越来越大。

需要引入secondaryNameNode，隔一段时间将fsimage + edits拷贝过来（触发条件：隔一个小时，或者editlog达到64M的时候）。

在secondaryNameNode合并后生成新的fsimage，然后拷贝给namenode。



## MR

MapReduce就是分布式分治的思想。Map分，Reduce来合。MR需要运行在Yarn集群上。

Yarn的角色：

+ ResourceManager
+ NodeManager

经典案例，WordCount单词统计。

MR工作三个阶段：

+ Map

  + 每个blk块的数据经过MapTask任务后得到一个KV数据集合。

+ Shuffle 

  + 分区：Partitioner 按单词长度分区

    > ```java
    > package pation;
    > 
    > import org.apache.hadoop.io.NullWritable;
    > import org.apache.hadoop.io.Text;
    > import org.apache.hadoop.mapreduce.Partitioner;
    > 
    > public class PationOwn extends Partitioner<Text, NullWritable> {
    >     @Override
    >     public int getPartition(Text text, NullWritable nullWritable, int i) {
    >         String[] split = text.toString().split("\t");
    >         if (Integer.parseInt(split[5]) > 15) {
    >             return 0;
    >         } else {
    >             return 1;
    >         }
    >     }
    > }
    > ```

  + 排序：按单词长度排序

  + 规约：将相同的数据规约在一起

    + 比如hello 1 和 hello 1 规约成 hello 2

  + 分组:

    经过shuffle后，得到了<K2，V2>.

+ Reduce



## YARN



