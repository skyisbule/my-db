package skyisbule.github.com.task;

import skyisbule.github.com.type.IoTaskType;

//定义io任务的结构
public class IoTask {

    public long       transactionId;
    public String     file;
    public IoTaskType type;
    public Integer    offset;
    public byte[]     data;
    public Integer    len;

}
