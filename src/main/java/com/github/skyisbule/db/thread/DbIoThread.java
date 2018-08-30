package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.callBack.SocketToIOobserver;
import com.github.skyisbule.db.config.BaseConfig;
import com.github.skyisbule.db.io.DbRandomAccessIo;
import com.github.skyisbule.db.page.Page;
import com.github.skyisbule.db.result.DBResult;
import com.github.skyisbule.db.task.IoTask;
import com.github.skyisbule.db.type.IoTaskType;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class DbIoThread extends Thread{

    private LinkedBlockingQueue<List<IoTask>>  queue  = new LinkedBlockingQueue<List<IoTask>>(BaseConfig.IO_LINKED_BLOCK_QUEUE_SIZE);//io任务的阻塞队列
    //private boolean isDone = false;//run任务是否正在执行即队列是否被消费结束，true是在执行完毕，即run已退出，false是正在执行。
    private Map<String,DbRandomAccessIo> dbIoMap  = new HashMap<String, DbRandomAccessIo>(); //存储io的实例

    //初始化io实例
    public void init(){
        String dbRootPath = BaseConfig.DB_ROOT_PATH;
        File path = new File(dbRootPath);
        for (String files : path.list()){
            DbRandomAccessIo file = new DbRandomAccessIo(files);
            dbIoMap.put(files,file);
        }
    }

    /**
     * 这里的执行顺序
     * 调用阻塞io
     * 执行io任务
     * 调用回调
     * 检查是否还有任务
     * 设置任务已完成
     */
    public void run(){
        while (true){
            try {//阻塞读取io任务
                List<IoTask> tasks = queue.take();
                byte[] data;
                DBResult result = DBResult.buildEmpty();
                switch (tasks.get(0).getType()){
                    case INSERT:
                        IoTask insertTask = tasks.get(0);
                        DbRandomAccessIo insertIO = dbIoMap.get(insertTask.getFile());
                        insertIO.write(insertTask.offset,insertTask.data);
                        result = DBResult.buildUpdate(insertTask.getTransactionId());
                        break;
                    case SELECT:
                        DbRandomAccessIo selectIO = dbIoMap.get(tasks.get(0).getFile());
                        List<Page> pages = new LinkedList<>();
                        for (IoTask task : tasks) {
                            byte[] dataTemp = selectIO.read(task.getOffset(),task.getLen());
                            Page page = new Page();
                            page.setPageId(task.getPageId());
                            page.setData(dataTemp);
                            pages.add(page);
                        }
                        result = DBResult.buildSelect(tasks.get(0).getTransactionId(),pages);
                        break;
                    case UPDATE:
                        DbRandomAccessIo updateIO = dbIoMap.get(tasks.get(0).getFile());
                        for (IoTask task : tasks) {
                            updateIO.write(task.getOffset(),task.getData());
                        }
                        result = DBResult.buildUpdate(tasks.get(0).getTransactionId());
                }
                //开始返回result对象
                SocketToIOobserver.getInstances().commit(tasks.get(0).getTransactionId(),result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void commit(List<IoTask> tasks){
        queue.add(tasks);
    }
    public synchronized void commit(IoTask task){
        ArrayList<IoTask> tasks = new ArrayList<>();
        tasks.add(task);
        queue.add(tasks);
    }

}
