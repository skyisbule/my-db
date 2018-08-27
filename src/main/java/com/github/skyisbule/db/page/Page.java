package com.github.skyisbule.db.page;

//页表  每张页存16KB的数据
public class Page {

    int pageId;      //页号
    int recordNum;   //记录总数
    int minId;       //最小的id号
    int maxId;       //最大的记录号
    int usedSpace;   //已使用的空间   单位为byte
    int beginByte;   //文件中开始的byte位
    int endByte;     //文件中结束的byte位
    byte[] data;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getMinId() {
        return minId;
    }

    public void setMinId(int minId) {
        this.minId = minId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public int getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(int usedSpace) {
        this.usedSpace = usedSpace;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
