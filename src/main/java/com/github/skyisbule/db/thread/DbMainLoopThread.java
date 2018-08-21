package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.callBack.MainLoopObserver;
import com.github.skyisbule.db.concurrent.CentLocker;
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
                if (task instanceof SelectTask){
                    if (CentLocker.getLock(task)){//如果拿到锁，则处理后递交给ioThread 然后由ioThread掉用locker释放锁以及将结果回调给观察者
                        Integer transcationId = ((SelectTask) task).getTranscationId();
                        MainLoopObserver.callBack(transcationId);//回调 告知用户线程可以递交ioTask
                    }else {//如果没能拿到锁，则直接将此任务丢掉队列末尾（todo 这里在以后要优化，加入权值保证公平性）

                        tasksQueue.put(task);
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

}
