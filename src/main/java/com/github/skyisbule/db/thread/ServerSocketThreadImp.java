package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.task.IoTask;

//定义连接客户端的监听线程，用于解析用于请求，解析sql，构造ioTask并递交给mainThread
public class ServerSocketThreadImp extends Thread implements ServerSocketThread {

    public void run(){

    }

    public void doIoCallBack(IoTask ioTask) {

    }

}
