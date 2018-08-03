package com.github.skyisbule.db.struct;

import java.util.HashMap;
import java.util.List;

//记录数据库的基本信息
public class DbInfo {

    private class TableInfo{
        int recordNum;   //这张表有多少条记录
        int tableKBlen; //这张表占多少kb
    }

    private HashMap<String,TableInfo> infoMap = new HashMap<String, TableInfo>();

    public void add(String tableName,int recordNum,int tableKBlen){
        TableInfo info = new TableInfo();
        info.recordNum = recordNum;
        info.tableKBlen = tableKBlen;
        infoMap.put(tableName,info);
    }

}
