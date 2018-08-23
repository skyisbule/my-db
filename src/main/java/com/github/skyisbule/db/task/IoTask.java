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

    public IoTask(Integer transactionId, String file, IoTaskType type) {
        this.transactionId = transactionId;
        this.file = file;
        this.type = type;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public IoTaskType getType() {
        return type;
    }

    public void setType(IoTaskType type) {
        this.type = type;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }
}
