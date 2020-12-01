**1、搜索使用ES的优势？**

**2、ES IK分词器两种方式？**

**3、ElasticSearch中的常用算法有哪些？**

ES中最常用的算法是TF/IDF相关度评分算法，如下定义：

TF：Term Frequency，即词频。它表示一个词在内容(如某文章)中出现的次数。通常，它的定义是：
TF ＝ 某个词在文档中出现的次数 ／ 文档的总词数
某个词出现越多，表示它越重要。

IDF（Inverse Document Frequency），即逆文档频率，它是一个表达词语重要性的指标。通常，它的计算方法是：
IDF＝log(语料库中的文档数／(包含该词的文档数+1))
如果所有文章都包涵某个词，那个词的IDF＝log(1)=0, 即重要性为零。停用词的IDF约等于0。如果某个词只在很少的文章中出现，则IDF很大，其重要性也越高。
ElasticSearch的相关性计算公式为：
TF-IDF = TF * IDF

**4、ES中是如何存储数据的？**

文档(Document)

es是面向文档的，文档是es中可搜索的最小单位，es的文档由一个或多个字段组成，类似于关系型数据库中的一行记录，但es的文档是以JSON进行序列化并保存的，每个JSON对象由一个或多个字段组成，字段类型可以是布尔，数值，字符串、二进制、日期等数据类型。

es每个文档都有唯一的id,这个id可以由我们自己指定，也可以由es自动生成。

**5、什么是倒排索引？**



**6、ElasticSearch集群如何选举Master节点？**

1、当集群 master 候选数量不小于 3 个时，可以通过设置最少投票通过数量

（discovery.zen.minimum_master_nodes）超过所有候选节点一半以上来解

决脑裂问题；


2、当候选数量为两个时，只能修改为唯一的一个 master 候选，其他作为 data

节点，避免脑裂问题。

**7、ElasticSearch更新文档操作**


ES中并不存在所谓的更新操作，而是用新文档替换旧文档；

在内部，Elasticsearch已经标记旧文档为删除并添加了一个完整的新文档并建立索引。旧版本文档不会立即消失

，但你也不能去访问它。

**8 ElasticSearch如何创建索引？**

新建 Index，可以直接向 Elastic 服务器发出 PUT 请求。下面的例子是新建一个名叫weather的 Index。


    $ curl -X PUT 'localhost:9200/weather'

服务器返回一个 JSON 对象，里面的acknowledged字段表示操作成功。


    {
      "acknowledged":true,
      "shards_acknowledged":true
    }



**9、ElasticSearch中match和term查询的区别？**

match在匹配时会对所查找的关键词进行分词，然后按分词匹配查找，
term代表完全匹配，不进行分词器分析，会直接对关键词进行查找。
一般模糊查找的时候，多用match，而精确查找时可以使用term。

**10、ElasticSearch中搜索使用的数据类型有哪些？**
? ? ElasticSearch5.4版本以后，string类型被废弃，取而代之的是text与keyword类型。

? ? 
1、文本类型text： 当一个字段需要用于全文搜索(会被分词), 比如产品名称、产品描述信息, 就应该使用text类型.

? ? 
2、关键字类型keyword：当一个字段需要按照精确值进行过滤、排序、聚合等操作时, 就应该使用keyword类型，keyword的内容不会被分词。

**11、ElasticSearch参数retry_on_conflict意义**

当执行索引和更新的时候，有可能另一个进程正在执行更新。这个时候就会造成冲突，这个参数就是用于定义当遇到冲突时，再过多长时间执行操作。？

**12、logstash的数据处理过程？**
logstash包含Inputs,Filters,Outputs 三部分
输入插件Inputs从数据源那里消费数据，例如指定Mysql的连接以及导入规则；
过滤器插件Filters根据你的期望修改数据，例如过滤掉数据中的html标签；
输出插件Outputs将数据写入目的地，例如指定ElasticSearch的Index与document。

**13、Logstash和Flume对比**

    1、Logstash比较偏重于字段的预处理，在异常情况下可能会出现数据丢失，只是在运维日志场景下，一般认为这个可能不重要；
而Flume偏重数据的传输，几乎没有数据的预处理，仅仅是数据的产生，封装成event然后传输；传输的时候flume比logstash多考虑了一些可靠性。因为数据会持久化在channel中，数据只有存储在下一个存储位置（可能是最终的存储位置，如HDFS；
也可能是下一个Flume节点的channel），数据才会从当前的channel中删除。这个过程是通过事务来控制的，这样就保证了数据的可靠性。
    2、Logstash有几十个插件，配置比较灵活，flume强调用户自定义开发；
    3、Logstash的input和filter还有output之间都存在buffer，进行缓冲；Flume直接使用channel做持久化
    4、Logstash性能以及资源消耗比较严重，且不支持缓存；

**14、ElasticSearch常用参数有哪些？**
#设置索引的分片数,默认为5? "number_of_shards" 是索引创建后一次生成的,后续不可更改设置
index.number_of_shards: 5??

#设置索引的副本数,默认为1
index.number_of_replicas: 1??

#索引的刷新频率，默认1秒，太小会造成索引频繁刷新，新的数据写入就慢了。
（此参数的设置需要在写入性能和实时搜索中取平衡）通常在ELK场景中需要将值调大一些比如60s，在有_template的情况下，需要设置在应用的_template中才生效。?

index.refresh_interval: 120s

#当事务日志累积到多少条数据后flush一次。
index.translog.flush_threshold_ops: 50000

#这个参数决定了要选举一个Master至少需要多少个节点，默认值是1，推荐设置为 N/2 + 1，N是集群中节点的数量，这样可以有效避免脑裂

discovery.zen.minimum_master_nodes: 1

#节点用于 fielddata 的最大内存，如果 fielddata达到该阈值，就会把旧数据交换出去。该参数可以设置百分比或者绝对值。默认设置是不限制，所以强烈建议设置该值，比如 10%。

indices.fielddata.cache.size: 50mb

**15、ElasticSearch性能调优**
对于不同的字段禁用 norms 和 doc_values
如果以上建议适用，还需要检查字段是否启用了 norms 和 doc_values。通常只用于过滤而不需要进行打分（匹配度打分）的字段，可以直接禁用 norms 。不用于排序或者聚合的字段可以禁用 doc_values 。注意，如果在已有的 index 做这些变更，是需要对 index 做 reindex的动作。


增加 refresh_interval 刷新的间隔时间
index.refresh_interval的默认值是 1s，这迫使Elasticsearch集群每秒创建一个新的 segment （可以理解为Lucene 的索引文件）。增加这个值，例如30s，可以允许更大的segment写入，减后以后的segment合并压力。

禁用 swapping
把操作系统的虚拟内存交换区关闭。sysctl 里面添加 vm.swappiness = 1

**16、ElasticSearch API分类：**
Elasticsearch有四类API：


第一：检查集群，节点，索引等健康与否，以及获取其相应状态；


第二：管理集群，节点，索引及元数据；


第三：执行CRUD（增删查改）操作；


第四：执行高级操作，例如：paging，filtering等

ES访问接口：9200/TCP，它是HTTP请求，我们可以通过curl命令访问，例如查看集状态：
    curl -X GET 'http://node101.yinzhengjie.org.cn:9200/_cat/health?v'

**17、ElasticSearch中9200端口与9300端口的区别？**
9200作为Http协议Restful接口，主要用于外部通讯。

9300作为Tcp协议，jar之间就是通过tcp协议通讯

ES集群之间是通过9300进行通讯，集群间和 TCPClient 都走的该端口。
