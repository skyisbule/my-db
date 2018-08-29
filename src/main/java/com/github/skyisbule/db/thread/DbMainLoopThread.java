package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.callBack.MainLoopObserver;
import com.github.skyisbule.db.concurrent.CentLocker;
import com.github.skyisbule.db.struct.DbStruct;
import com.github.skyisbule.db.task.InsertTask;
import com.github.skyisbule.db.task.IoTask;
import com.github.skyisbule.db.task.SelectTask;
import com.github.skyisbule.db.task.Task;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

//db的主循环线程，管理整个db
public class DbMainLoopThread extends Thread{

    private static LinkedBlockingQueue<Task> tasksQueue = new LinkedBlockingQueue<>();
    private AtomicInteger     maxIOnum   = new AtomicInteger();//最大的io线程数
    /**
     * 主循环的逻辑
     * 检测事务队列是否为空
     * 解析事务enty
     * 检测锁的状态
     * 尝试获取锁  若失败直接把当前任务丢到队列末尾
     * 递交Io任务
     * 其它线程：文件删除标记的彻底删除、日志检查点、脏页刷新
     */
    public void run(){
        for (;;){
            try {
                Task task = tasksQueue.take();
                switch (task.getCRUDtype()){
                    case SELECT:
                        if (task instanceof SelectTask){
                            if (CentLocker.getLock(task)){//如果拿到锁，则处理后递交给ioThread 然后由ioThread掉用locker释放锁以及将结果回调给观察者
                                Integer transactionId = ((SelectTask) task).getTranscationId();
                                MainLoopObserver.callBack(transactionId);//回调 告知用户线程可以递交ioTask
                            }else {//如果没能拿到锁，则直接将此任务丢掉队列末尾（todo 这里在以后要优化，加入权值保证公平性）
                                this.getLockfail(task);
                            }
                        }
                        break;
                    case INSERT:
                        if (CentLocker.getLock(task)){
                            InsertTask task1 = (InsertTask)task;
                            //代表用户没有输入id，所以id应该自增
                            if (task1.getPKID() == null){
                                Integer pk = DbStruct.getTableMaxId(task1.getDbName(),task1.getTableName());
                                task1.setPKID(pk);
                            }
                            MainLoopObserver.callBack(task1.transcationId);
                        }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void commit(Task task) throws InterruptedException {
        tasksQueue.put(task);
    }

    private void getLockfail(Task task) throws InterruptedException {
        tasksQueue.put(task);
    }
}
