package com.github.skyisbule.db.struct;

import java.util.ArrayList;

//存储表结构的基本信息
public class DbTableStruct {

    //表名 记录数 字段信息
    public String                  tableName;
    public Integer                 recordNum;
    public ArrayList<DbTableField> fields;



}
