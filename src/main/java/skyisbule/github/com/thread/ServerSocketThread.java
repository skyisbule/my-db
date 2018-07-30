package skyisbule.github.com.thread;

import skyisbule.github.com.task.IoTask;

//定义serverSocket的接口
public interface ServerSocketThread {

    /**
     * 定义io响应后应该执行的回调方法
     */
    public void doIoCallBack(IoTask ioTask);

}
