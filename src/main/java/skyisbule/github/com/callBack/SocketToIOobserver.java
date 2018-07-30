package skyisbule.github.com.callBack;

import skyisbule.github.com.task.IoTask;
import skyisbule.github.com.thread.ServerSocketThread;

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
     * @param ioTask
     */
    public void commit(Integer transactionId,IoTask ioTask){
        ServerSocketThread thread = socketMap.get(transactionId);
        thread.doIoCallBack(ioTask);
    }

    public void remove(Integer transactionId){
        this.socketMap.remove(transactionId);
    }
}
