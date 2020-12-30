Hbase有哪些应用场景？

Hbase：very Large Table，它的优势是可以存储十亿级别的行和百亿级别的列。可以支持千万的QPS、PB级别的存储，

1、由于是列数稀疏的，所以对于非结构化和半结构化数据的存储比较合适。

2、Region可由Hbase自动切分，而传统数据数据库数据量达到瓶颈时需要分库分表，分表需要横切纵切。而Hbase本身可支持动态扩展自动切分，非常方便。

3、然后写比读快，适用于需要大量写的场景。

你们公司Hbase的作用？

实时估值，需要将各个金融市场上的数据拿到，写入到数据库中，并且计算出每一只基金实时的单位净值。有大量的时序数据，

Hbase是面向列的分布式数据库。

什么是面向列：

我们先看mysql，它是面向行存储的，并且是结构化的，就是列事先是定义好的。如果某个列的值不存在，你填null实际上也占用空间。

而列式存储，来存储非结构化或半结构化的数据，比如一个列里面可以存储任意个K-V，并且对于同一列，不同行的K-V是可以不同的。

物理存储上也和mysql不同，每一个列族都是存放在同一个文件的。

Hbase（实时读写）和Hive（数据仓库，写SQL底层调用MapReduce）的底层都是HDFS，处理数据都是用的Hadoop的MapReduce。Hbase使用Zookeeper来实现分布式协调。

到底遇到了什么问题，用Mysql解决不了了，才开始使用Hbase。

Mysql扛不住的时候，不考虑使用外部组件，一般会分库分表，读写分离。而Hbase类似于Mysql的横向分表，分到多个分布式节点上。

Hbase实时读写的两个原因：利用内存，IO顺序读写（memsotre满了后flush写到磁盘，到达堆内存的40%进行刷写）。

Kafka没有利用大内存，为什么块，主要利用IO顺序读写。



Hbase的数据模型：

RowKey：决定一行数据，按字典序排序（与数值序不同），最大允许64K字节，但建议不超过100字节

字典序的好处：范围查询时，合理设置RowKey，可以将某些经常查询的数据的rowkey设置为相似的，这样范围查询时是顺序读取。

TimeStamp：时间戳版本号，在数据写入时自动赋值，不同版本的数据按时间倒序排序，，也可以

列族CF Column Family：列族必须预先给出，是Hbase的最小控制单元。例如create 'class'，‘course’

列名：以列族为前缀，例如course : English，新的列可以按需动态加入。

Cell单元格：从Hbase里找到一个唯一的值需要4个属性：Rowkey + column family + qualifier + version。cell里没有数据类型（类似Oracle的数值型，字符型），全部都是字节数组形式存储。

Rowkey找到某一行，的哪一个列族的哪一个列的哪个时间戳版本号

（Hbase默认取出版本号最新的值，老的值不会删除，只会设置一个失效标记）

什么是数据埋点：分析师提前设定需要哪些用户数据，比如用户点击了哪些页面，停留了多久，而这些行为将会触发数据采集。



Hbase可以增删改查，但是改的话是没有实际命令的，因为有版本号的概念，所以其实也是put + 最新版本号，并不覆盖，也不立即把之前旧版本的数据删掉，那么对于客户端而言，也就相当于改操作了。



（memsotre满了后flush写到磁盘，flush条件：

到达堆内存的40%进行刷写，

或者Hlog（WAL，write ahead log 预写日志）到达一定大小，

或者最后一个put操作过了一个小时）

[读写流程,Hbase是一个读比写慢的框架，读要读好几个地方memStore，BlockCache，Store](https://blog.csdn.net/iteye_3893/article/details/82646439)

[顺序写入](https://www.imooc.com/wenda/detail/488238)

[读写流程不需要Master](https://www.cnblogs.com/yxym2016/p/13441007.html)

一张表最开始只有一个region，只存在于一个regionServer中（region就是某一张表的切片，只有到达一定数据量才会切片，并将这个切片分到另一个regionServer中，比如rowkey从1到10000，切成1-5000，5001到10000手动切，数据量大的表到达10G一切）。

一张表里的每一个CF列族都对应一个Store（对应于HDFS中的一个文件夹info1、info2）

每个Store又由一个memStore和0至多个StoreFile组成。（StoreFile以HFile格式保存在HDFS上，对应于HDFS中的一个文件夹AAAAAf489as7f4as56f2ff6a54f）。



HDFS浏览器：

 						/Hbase/data/default/stu/000001321456sdf1231465a6s(region ID)/info1(列族)/AAAAAf489as7f4as56f2ff6a54f(Hfile)

​                         /Hbase/data/default/stu/000001321456sdf1231465a6s/info1/BBBBBf489as7f4as56f2ff6a456(Hfile)

​                         /Hbase/data/default/stu/000001321456sdf1231465a6s/info2/CCCCCf489as7f4as56f2ff6a456(Hfile)

​                         /Hbase/data/default/stu/000001321456sdf1231465a6s/info2/DDDDDf489as7f4as56f2ff6a456(Hfile)

Hbase并非先读内存再读磁盘，而是将memstore和hfile里的数据都读出来，比较时间戳，然后把最新的数据放到blockcache中。下次再读该数据的时候，直接从blockcache中读取。

如果此时对于同一个数据，又put了一个新版本号，（此时上一个版本的数据是存在blockcache中的），也会将最新的数据在磁盘中找一遍，再刷到blockcache中。如果对于同一个数据没有put新版本号，直接从blockcache里面取。

注意：blockcache缓存的是整个Hfile文件，比如将AAAAAf489as7f4as56f2ff6a54f缓存到blockcache。在AAAAAf489as7f4as56f2ff6a54f中找不到，再去磁盘上和memstore上找（memstore上找到的数据不会放到blockcache中）。因此，无论如何都会去找磁盘！

> 扩展：LRU是什么？一种缓存淘汰机制，按照英文的直接原义就是Least Recently Used,最老没有被使用过的数据淘汰，它是按照一个非常著名的计算机操作系统基础理论得来的：**最近使用的页面数据会在未来一段时期内仍然被使用,已经很久没有使用的页面很有可能在未来较长的一段时间内仍然不会被使用**
>
> 实现：主要是基于hashmap+双向链表，就是linkedhashmap（为什么不用数组和链表，因为都是时间复杂度都是Big O(N),数组插入删除麻烦）,我只是手写了一个简单的，原理就是相当于一个堆，先进后出的原理，最后插入的元素或者最近访问过的元素都会在最上面，当存储不够的时候就会淘汰最下面的元素。
>
> LRU-K的主要目的是为了解决LRU算法“缓存污染”的问题，其核心思想是将“最近使用过1次”的判断标准扩展为“最近使用过K次”。
>
> 实现：第一个队列存访问次数，档到达K次后，将数据存到第二个队列中缓存起来。第二个队列满了就淘汰最久未被使用的K次数据。
>
> K越大，命中率会越高，但适应性比较差，一般K=2

读写流程不需要Mater，zk承担了Master的部分工作。

Master主要工作为：哪个HregionServer挂了，负责将该RegionServer负责的region转移到其他节点上。

注意：regionServer不负责存储数据，存储数据由HDFS的DataNode负责。

>  扩展:
>
> **DDL**(Data Definition Language 数据定义语言)
>
> Create语句：可以创建数据库和数据库的一些对象。
>
> Drop语句：可以删除数据表、索引、触发程序、条件约束以及数据表的权限等。
>
> Alter语句：修改数据表定义及属性。
>
> DML(Data Manipulation Language 数据操控语言) CRUD

操作：

```bash
#      创建     表    列族
hbase> create 'stu','info'

#      插入  表    rowkey  列族  ：列名     值 
hbase> put 'stu','1001','info1':'name','zhangsan'
hbase> put 'stu','1001','info1':'sex','male'
hbase> put 'stu','1001','info2':'addr','shanghai'
hbase> put 'stu','1002','info1':'name','lisi'
hbase> put 'stu','1002','info1':'phone','17308488555'
hbase> put 'stu','1003','info2':'addr','beijing'
hbase> put 'stu','10010','info1':'name','wangwu'
#新版本号
hbase> put 'stu','1001','info1':'name','zhangsansan'

#	   扫描 可以查询指定某张表，也可以指定一些过滤条件，范围查询等等
hbase> scan 
ROW							COLUMN+CELL(虽然有7行，但实际上是4条数据，按rowkey)
1001						column=info1:name, timestamp=1563259047832,value=zhangsansan
1001						column=info1:sex,  timestamp=1563259047627,value=male
1001						column=info2:addr, timestamp=1563259047657,value=shanghai
10010						column=info1:name, timestamp=1563259047855,value=wangwu
1002						column=info1:name, timestamp=1563259047744,value=lisi
1002						column=info1:phone,timestamp=1563259047798,value=17308488555
1003						column=info2:addr, timestamp=1563259047821,value=beijing

#范围查询，左闭右开
hbase> scan 'stu',{STATROW=>'1001',STOP=>'1003'}
ROW							COLUMN+CELL(虽然有6行，但实际上是三条数据，按rowkey)
1001						column=info1:name, timestamp=1563259047832,value=zhangsansan
1001						column=info1:sex,  timestamp=1563259047627,value=male
1001						column=info2:addr, timestamp=1563259047657,value=shanghai
10010						column=info1:name, timestamp=1563259047855,value=wangwu
1002						column=info1:name, timestamp=1563259047744,value=lisi

#查询多个版本号 			可以使用 alter 'stu', {COLUNM=>'info1:name', VERSIONS=>10}修改为10个版本号
hbase> scan 'stu',{RAW=>true, VERSION=>'10'}  #HBase默认情况下，只存储3个版本的历史数据。  
1001						column=info1:name, timestamp=1563259047832,value=zhangsansan
1001						column=info1:name, timestamp=1563259047559,value=zhangsan
1001						column=info1:sex,  timestamp=1563259047627,value=male
1001						column=info2:addr, timestamp=1563259047657,value=shanghai
10010						column=info1:name, timestamp=1563259047855,value=wangwu
1002						column=info1:name, timestamp=1563259047744,value=lisi

#      查询  表   rowkey 
hbase> get 'stu' '1001'
COLUNM						CELL
info1:name					timestamp=1563259047832,value=zhangsansan
info1:sex					timestamp=1563259047627,value=male
info1:addr					timestamp=1563259047657,value=shanghai

#      查询  表   rowkey   列族
hbase> get 'stu' '1001' 'info1'
COLUNM						CELL
info1:name					timestamp=1563259047832,value=zhangsansan  #返回最大的时间戳
info1:sex					timestamp=1563259047627,value=male

#		删除	     表    rowkey   列族：列
hbase> delete    'stu', '1001', 'info1:name'    #时间轴上最后一个数据如果delete，你再指定前面的时间戳去put也无效。
#		删除	  	 表    rowkey   
hbase> deleteall 'stu', '1001'   #把info1和info2都删了

hbase> flush 'stu'   #强制将stu 从memstore flush到磁盘
```



【数据合并major compaction和minor compaction】

为什么要做整合？

一个列簇对应一个文件的情况并不保证这样，尤其是在HBase频繁写的时候，因此HBase需要一种机制把HFiles合并以减少最大磁盘寻址开销以提高读性能。这个过程称为*compaction*。

当hfile的文件数默认超过3个的时候，会触发minor compaction，它只做磁盘文件的整合成为1个文件（那3个文件不会马上消失，过一段时间才会删除掉），并不会触发数据的删除操作。

而major compaction则会删除无用的数据（比如超过版本号的老数据，被删除的数据等），Major Compaction时间会持续比较长，整个过程会消耗大量系统资源，对上层业务有比较大的影响。因此线上业务都会将关闭自动触发Major Compaction功能，改为手动在业务低峰期触发。

【region的split切分】

region的自动切分会有存在一个问题，出现数据倾斜。region的大小越来越大。按照目前的规则，小于10G的region，第一次切分为128M，切成64+64，然后后面的64增长到512（2的2次方*128），切分成256+256，成指数增长。有的region大，有的region小。

【数据删除标记何时真正删除掉】

在memStore中是不会删除掉删除标记的。否则会出现数据不一致问题。比如一个数据有3个版本，1,2版本都已经进入磁盘了，这个时候put第三个版本到memstore，然后再delete，这个时候查询，正常结果应该是查询不到任何数据的。因为数据设置了删除标记。但是如果这个删除标记在memStore中被删除了，那么查询出的数据就是磁盘的2号版本数据了！

【如何防止Hbase数据倾斜】

做数据预分区。

为了防止数据倾斜，我们需要对region进行预分区。

预分区的一些规则：安机器数量：一个HregionServer一般2-3个region比较合理。例如10台机器，那就20-30个region。

按数据量大小：一般一个region大于10G肯定会进行自动分区，为了防止自动分区，我们一般需要小于10G作为1个region。

【Hbase Rowkey设计原则】

合理的rowkey设计，将每一个预分区的数据做到竟可能的均匀。

设计rowkey具有散列性，因为rowkey是字典序的，比如按日期的rowkey，某一段时间的rowkey全放到一个region中，其他的regionserver没数据，这个时候可以倒序。

但在一些具体的场景下，我们希望范围查询，startKey到StopKey之间做范围查询，尽可能的把这些数据集中的放入某一个rowkey。

rowkey的长度：一般64Kb大小。太大占用空间。太小的话可容纳的数据量不多，因此权衡考虑10-100吧。