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

