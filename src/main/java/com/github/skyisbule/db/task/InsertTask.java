package com.github.skyisbule.db.task;

import java.util.HashMap;

//插入任务的封装类
public class InsertTask implements Task{

    public String                 dbName;
    public String                 tableName;
    public HashMap<String,Object> values;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public HashMap<String, Object> getValues() {
        return values;
    }

    public void setValues(HashMap<String, Object> values) {
        this.values = values;
    }
}
