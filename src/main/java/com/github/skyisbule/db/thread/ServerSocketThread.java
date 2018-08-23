package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.result.DBResult;
import com.github.skyisbule.db.task.IoTask;

//定义serverSocket的接口
public interface ServerSocketThread {

    /**
     * 定义io响应后应该执行的回调方法
     */
    public void doIoCallBack(DBResult result);

    public void getLockSuccess();

}
