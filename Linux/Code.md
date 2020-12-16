





# select-server.c

```c
#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>

int main()
{
    int fd[1024] = {0};
    printf("hello,world!");
    int sockfd = socket(AF_INET, SOCK_STREAM,0);//ipv4
    if (-1 == sockfd)
    {
        perror("socket");
        exit(1);
    }

    int opt = 1;
    setsockopt(sockfd,SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));

    struct sockaddr_in server_addr;

    memset(&server_addr, 0 ,sizeof(server_addr));

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = 8000;
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

    //绑定信息
    int ret = bind(sockfd,(struct sockaddr *)&server_addr,sizeof(server_addr));
    if(-1 == ret)
    {
        perror("bind");
        exit(1);
    }

    //设置监听队列
    ret = listen(sockfd, 10);
    if(-1 == ret)
    {
        perror("listen");
        exit(1);
    }

    fd_set readfd,tmpfd;
    FD_ZERO(&readfd);
    FD_SET(sockfd, &readfd);	//添加到集合

    printf("等待客户端的连接..\n");

    int maxfd = sockfd, i = 0;    		//maxfd 最大的文件描述符
    struct sockaddr_in client_addr;		//用于保存客户端的信息
    int length = sizeof(client_addr);
    char buf[32] = {0};

    while(1)
    {
        tmpfd = readfd;
        ret = select(maxfd+1, &tmpfd/*位图bitmap 最大1024*/, NULL, NULL, NULL);//监听集合是否可读，最后一个null表示阻塞
        if(-1 == ret)
        {
            perror("select");
            exit(1);
        }
        //有文件描述符可读
        if (FD_ISSET(sockfd, &tmpfd)) //判断sockfd是否留在集合里面 判断是否有客户端发起连接
        {
            for (i = 0; i < 1024; i++) //选择合适的i
            {
                if (fd[i] == 0)
                {
                    break;
                }
            }
            fd[i] = accept(sockfd, (struct sockaddr*)&client_addr, &length);
            if (-1 == fd[i])
            {
                perror("accept");
                exit(1);
            }

            printf("接收来自%s的客户端的连接,fd = %d\n",inet_ntoa(client_addr.sin_addr), fd[i]);

            FD_SET(fd[i], &readfd);	//新的文件描述符加入到集合中
            if (maxfd < fd[i])
            {
                maxfd = fd[i];
            }

        }
        else	//有客户端发消息
        {
            for(i = 0; i <1024; i++)
            {
                if (FD_ISSET(fd[i],&tmpfd))	//判断是哪个fd可读
                {
                    ret = recv(fd[i], buf, sizeof(buf),0);
                    if (-1 == ret)
                    {
                        perror("recv");
                    }
                    else if (0 == ret)
                    {
                        close(fd[i]);	//关闭tcp连接
                        FD_CLR(fd[i],&readfd);	//从集合中清除
                        printf("收到来自%d的客户端的关闭\n",fd[i]);
                        fd[i] = 0;
                    }
                    else
                    {
                        printf("收到来自%d的客户端的消息,%s\n",fd[i],buf);
                    }
                    memset(buf, 0, sizeof(buf));
                    break;
                }
            }
        }
    }


    return 0;
}


```



# epoll-server.c

```c
#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <string.h>
#include <sys/epoll.h>

#define MAXSIZE 256

int main()
{
    printf("hello,world!");
    int sockfd = socket(AF_INET, SOCK_STREAM,0);//ipv4
    if (-1 == sockfd)
    {
        perror("socket");
        exit(1);
    }

    int opt = 1, num = 0, i =0;
    setsockopt(sockfd,SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));

    struct sockaddr_in server_addr;

    memset(&server_addr, 0 ,sizeof(server_addr));

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = 8000;
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

    //绑定信息
    int ret = bind(sockfd,(struct sockaddr *)&server_addr,sizeof(server_addr));
    if(-1 == ret)
    {
        perror("bind");
        exit(1);
    }

    //设置监听队列
    ret = listen(sockfd, 10);
    if(-1 == ret)
    {
        perror("listen");
        exit(1);
    }

    int epfd = epoll_create(MAXSIZE); //创建epoll红黑树
    if(-1 == epfd)
    {
        perror("epoll_create");
        exit(1);
    }

    struct epoll_event ev,events[MAXSIZE];
    ev.events = EPOLLIN;	//监听sockfd 可读
    ev.data.fd = sockfd;
    ret = epoll_ctl(epfd, EPOLL_CTL_ADD, sockfd, &ev);
    if (-1 == ret)
    {
        perror("epoll_ctl");
        exit(1);
    }

    struct sockaddr_in client_addr;
    int length = sizeof(client_addr);
    char buf[32] = {0};

    while(1)
    {
        num = epoll_wait(epfd,events,MAXSIZE,-1);// -1表示阻塞
        if (-1 == num)
        {
            perror("epoll_wait");
            exit(1);
        }

        for(i = 0;i < num;i++)
        {
            if(events[i].data.fd == sockfd)	//有客户端发起连接
            {
                int fd = accept(sockfd,(struct sockaddr*)&client_addr,&length);
                if (-1 == num)
                {
                    perror("accept");
                    exit(1);
                }

                printf("收到%d客户端的连接\n",fd);
                //为新的文件描述符注册事件
                ev.data.fd = fd;
                ev.events = EPOLLIN;
                ret = epoll_ctl(epfd,EPOLL_CTL_ADD,fd,&ev);
                if (-1 == ret)
                {
                    perror("accept");
                }
            }
            else
            {
                if(events[i].events & EPOLLIN) //如果事件是可读的
                {
                    ret = recv(events[i].data.fd,buf,sizeof(buf),0);
                    if(-1 == ret)
                    {
                        perror("recv");
                    }
                    else if (0 == ret)
                    {
                        ev.data.fd = events[i].data.fd;
                        ev.events = EPOLLIN;
                        epoll_ctl(epfd,EPOLL_CTL_DEL,events[i].data.fd,&ev);//注销事件
                        printf("收到%d客户端的关闭\n",events[i].data.fd);
                        close(events[i].data.fd);
                    }
                    else
                    {
                        printf("收到%d客户端的消息%s\n",events[i].data.fd,buf);
                        memset(buf, 0 ,sizeof(buf));
                    }

                }
            }
        }
    }

    return 0;
}


```

