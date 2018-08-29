package com.github.skyisbule.db.task;

import com.github.skyisbule.db.struct.SelectRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//插入任务的封装类
public class InsertTask implements Task{

    public Integer                transcationId;
    public String                 dbName;
    public String                 tableName;
    public Integer                PKID;
    public CRUDTaskType           taskType = CRUDTaskType.INSERT;

    public InsertTask(){}

    public InsertTask(Integer transcationId,String dbName,String tableName){
        this.dbName = dbName;
        this.tableName = tableName;
        this.transcationId = transcationId;
    }

    @Override
    public List<SelectRange> getRanges(){
        return new ArrayList<SelectRange>();
    }

    @Override
    public CRUDTaskType getCRUDtype() {
        return this.taskType;
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

    public Integer getTranscationId() {
        return transcationId;
    }

    public void setTranscationId(Integer transcationId) {
        this.transcationId = transcationId;
    }

    public Integer getPKID() {
        return PKID;
    }

    public void setPKID(Integer PKID) {
        this.PKID = PKID;
    }
}
