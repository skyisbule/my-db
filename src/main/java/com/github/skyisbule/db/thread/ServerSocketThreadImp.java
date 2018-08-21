package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.task.IoTask;

import java.net.Socket;

//定义连接客户端的监听线程，用于解析用于请求，解析sql，构造ioTask并递交给mainThread
public class ServerSocketThreadImp extends Thread implements ServerSocketThread {

    private Socket socket;

    public void init(Socket socket){
        this.socket = socket;
    }

    public void run(){

    }

    //拿到了IO返回的东西
    public void doIoCallBack(IoTask ioTask) {

    }

    //代表拿到了锁，可以向IOThread提交io任务了
    public void getLock(){

    }

}
