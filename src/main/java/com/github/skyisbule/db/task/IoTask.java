package com.github.skyisbule.db.task;

import com.github.skyisbule.db.type.IoTaskType;

//定义io任务的结构
public class IoTask {

    public Integer    transactionId;
    public String     file;
    public IoTaskType type;
    public Integer    offset;
    public byte[]     data;
    public Integer    len;

}
