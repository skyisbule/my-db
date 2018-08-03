package com.github.skyisbule.db.thread;

//db的主循环线程，管理整个db
public class DbMainLoopThread extends Thread{

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

    }

}
