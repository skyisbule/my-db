package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.result.DBResult;
import com.github.skyisbule.db.task.IoTask;
import com.github.skyisbule.db.task.SelectTask;
import com.github.skyisbule.db.type.IoTaskType;

import java.net.Socket;

//定义连接客户端的监听线程，用于解析用于请求，解析sql，构造ioTask并递交给mainThread

/**
 * 处理流程为
 * 1.获取事务id，将自己注册到两个回调中心 用于接收mainThread以及IOThread的任务回调。
 * 2.解析用户的网络请求，分析sql
 * 3.根据sql生成对应的 CRUD TASK 以及IO TASK
 * 4.将CRUD TASK递交给mainThread尝试获取锁 并进入等待状态//todo  这里未来再改成等待的过程中继续视情况解析sql
 * 5.获取锁成功后mainThread会调用getLockSuccess方法 进入第二个状态
 * 6.递交ioTask给IOThread
 * 7.等待回调 将生成的结果返回给客户端
 */
public class ServerSocketThreadImp extends Thread implements ServerSocketThread {

    private Socket     socket;
    private int        trascathionId;
    private IoTask     ioTaskTemp;
    private DBResult   result;
    private DbIoThread ioThread;

    public void init(Socket socket,int trascathionId){
        this.socket = socket;
        this.trascathionId = trascathionId;
    }

    public void run(){


        //这里先模拟解析到了用户的sql
        String sql = "select * from test";
        //这里开始构造Task
        SelectTask task = new SelectTask(trascathionId,"test",true);
        ioTaskTemp = new IoTask(trascathionId,"test",IoTaskType.READ);
        try {
            DbMainLoopThread.commit(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    //拿到了IO返回的东西
    public void doIoCallBack(DBResult result) {
        //根据ioTask生成result

    }

    //代表拿到了锁，可以向IOThread提交io任务了
    public void getLockSuccess(){
        ioThread.commit(ioTaskTemp);
    }

}
