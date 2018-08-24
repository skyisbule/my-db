package com.github.skyisbule.db.page;

//页表  每张页存16KB的数据
public class Page {

    int pageId;      //页号
    int recordNum;   //记录总数
    int minId;       //最小的id号
    int maxId;       //最大的记录号
    int usedSpace;   //已使用的空间   单位为bit

}
