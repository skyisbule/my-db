package com.github.skyisbule.db.task;

import com.github.skyisbule.db.struct.SelectRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//插入任务的封装类
public class InsertTask implements Task{

    public String                 dbName;
    public String                 tableName;
    public HashMap<String,Object> values;

    @Override
    public List<SelectRange> getRanges(){
        return new ArrayList<SelectRange>();
    }

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
