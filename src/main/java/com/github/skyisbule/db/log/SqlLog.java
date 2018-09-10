package com.github.skyisbule.db.log;

import cn.hutool.core.io.file.FileWriter;
import com.github.skyisbule.db.config.BaseConfig;
import com.github.skyisbule.db.config.LogConfig;

/**
 * 这个类用于记录执行过的sql
 */
public class SqlLog {

    //todo 完成check opint
    public void doCheckPoint(){

    }

    public synchronized void doWriteSql(String dbName,int transactionId,String sql){
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(transactionId);
        sb.append(",");
        sb.append(System.currentTimeMillis());
        sb.append(",");
        sb.append(sql);
        sb.append(">\n");
        this.write(dbName,sb.toString());
    }

    private void write(String dbName,String content){
        String finalFineName = dbName+"_"+ LogConfig.DB_SQL_LOG_FILE;
        FileWriter writer = new FileWriter(BaseConfig.DB_ROOT_PATH+finalFineName);
        writer.write(content);
    }

}
