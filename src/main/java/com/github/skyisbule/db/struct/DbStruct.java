package com.github.skyisbule.db.struct;

import java.util.HashMap;

/**
 * 该类用于管理整个db的信息，是整个存储信息的核心，主要由serverSocketThread中的sql解析器进行调用，生成ioTask。
 * 初始化流程
 * 逐个读取表文件，todo：这里需要先验证 check一下有效性
 * 1.生成数据库表信息，key是表名、po存储：表的所有字段以及字段的长度。
 * 2.生成数据库元信息，插入map。
 * 3.将12生成的东西插入dbs。
 */
//存储数据库的基本信息
public class DbStruct {

    //key：数据库名  v：库内表结构
    public static HashMap<String,HashMap<String,DbTableStruct>> dbs = new HashMap<>();

    public static void add(String dbName,HashMap<String,DbTableStruct> tableInfo){
        dbs.put(dbName,tableInfo);
    }

    public static DbTableStruct getTableStructByName(String dbName,String tableName){
        return dbs.get(dbName).get(tableName);
    }

}
