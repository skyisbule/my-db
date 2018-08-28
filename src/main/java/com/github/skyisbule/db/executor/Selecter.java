package com.github.skyisbule.db.executor;

import com.github.skyisbule.db.struct.DbStruct;
import com.github.skyisbule.db.struct.DbTableStruct;

import java.util.HashMap;

public class Selecter {

    public HashMap<String,Object> doSelect(String dbName,String tableName,byte[] data){
        HashMap<String,Object> resulet = new HashMap<>();
        DbTableStruct struct = DbStruct.getTableStructByName(dbName,tableName);
        //接下来开始根据 字段名 ->字段类型 ->字段长度 解析data 生成最终的结果

        return resulet;
    }

}
