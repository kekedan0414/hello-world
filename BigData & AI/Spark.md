# 大数据

**【RDD】**
弹性分布式数据集。逻辑上数据是各个节点分片存储的，但是在操作上可以看成一个整体，针对RDD进行编程操作。

**【RDD算子】**
Transform类（生成新的RDD，transform并不会执行计算）：map，flatmap，groupbykey，reducebykey，join，union，filter等。
Action类（生成结果，只有在遇到action的时候才开始计算）：count，collect，reduce，lookup，save。

**【RDD宽窄依赖】**
所谓窄依赖是指，RDD的各个节点上的分片数据在各自节点上操作就可以了。父子RDD的分区是一一对应的。
而宽依赖是指，父子RDD是多对多的关系，也就是说要得到下游RDD中的某个分区数据，依赖于上游多个RDD分区数据。

**【pipline】**
在管道中，数据一直在运转，管道没有空闲。
在窄依赖中，数据是按照pipline模式进行的，数据不落地。
比如一个RDD分为2个partition，在窄依赖中进行filter，map操作。那么数据就有两个Task，进行pipline处理。任务数根据窄依赖中
最后一个RDD的分区数来确定。

**【DAG有向无环图】**
图论中的一个概念，有向图中的一个节点按方向无法回答本节点。
DAG有向无环图主要是在一个任务流过程中切分Stage的，RDD窄依赖的操作可以看做一个整体一起执行，而遇到窄依赖则划分为另一个
stage。中间结果不落盘。

**【SparkSchedule】**
Spark的任务调度就是如何组织任务去处理RDD中每个分区的数据，根据RDD的依赖关系构建DAG，基于DAG划分Stage，将每个Stage中的
任务发到指定节点运行。

**【Driver】**
Driver作为Spark应用程序的总控，负责分发任务以及监控任务运行状态



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