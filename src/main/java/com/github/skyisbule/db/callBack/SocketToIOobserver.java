package com.github.skyisbule.db.callBack;

import com.github.skyisbule.db.result.DBResult;
import com.github.skyisbule.db.task.IoTask;
import com.github.skyisbule.db.thread.ServerSocketThread;

import java.util.HashMap;

//从serverSocket 到 ioThread 的观察者
public class SocketToIOobserver {

    private static SocketToIOobserver socketToIOobserver = new SocketToIOobserver();

    private SocketToIOobserver(){

    }

    public static SocketToIOobserver getInstances(){
        return socketToIOobserver;
    }

    private HashMap<Integer,ServerSocketThread> socketMap = new HashMap<Integer, ServerSocketThread>();

    public void registSocket(Integer transactionId,ServerSocketThread thread){
        this.socketMap.put(transactionId,thread);
    }

    /**
     * IO任务结束，返回iotask。
     * @param transactionId
     * @param result
     */
    public void commit(Integer transactionId,DBResult result){
        ServerSocketThread thread = socketMap.get(transactionId);
        thread.doIoCallBack(result);
    }

    public void remove(Integer transactionId){
        this.socketMap.remove(transactionId);
    }
}
