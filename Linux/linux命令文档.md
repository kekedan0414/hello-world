### 内存不足，丢弃buf/cache

```bash
[root@localhost bin]# free -h
              total        used        free      shared  buff/cache   available
Mem:            15G        347M        7.5G         25M        7.6G         14G
Swap:          7.8G          0B        7.8G
[root@localhost bin]# echo 1 > /proc/sys/vm/drop_caches  #丢弃cache
[root@localhost bin]# free -h
              total        used        free      shared  buff/cache   available
Mem:            15G        361M         14G         25M        221M         14G

```

buff是程序准备写入到磁盘，但还未正式写入内容，cache是从磁盘读到内存中的缓存。

### sync

 将存于 buff 中的资料强制写入硬盘中



### trace

追踪系统调用

