package com.github.skyisbule.db.log;

import com.github.skyisbule.db.concurrent.LockFileIO;
import com.github.skyisbule.db.config.LogConfig;

import java.util.Arrays;

/**
 * undo日志
 * 为了防止在事务执行的过程中出现崩溃
 * 如：我们读到了A 与 B 两个记录的某字段值并尝试将它们的值乘二并写回
 * 那么在写的过程中如果发生崩溃则会使数据库处于非一致性的状态
 * 此时我们就需要从尾到头读取undo日志
 * 将修改的值重新写回数据库
 * <start transactionId>
 *     < transactionId , recordId , type(insert,write) , value >
 * <commit transactionId>
 */
public class UNDO {

    private String dbName;
    private int    transactionId;
    private int    recordId;
    private int    type;
    private byte[] data;
    private String finalFineName;

    public UNDO(String dbName, int transactionId, int recordId, int type, byte[] data) {
        this.dbName = dbName;
        this.transactionId = transactionId;
        this.recordId = recordId;
        this.type = type;
        this.data = data;
        this.finalFineName = dbName+"_"+ LogConfig.DB_SQL_LOG_FILE;
    }

    public boolean doStart(String dbName, int transactionId){
        String content = "<start "+String.valueOf(transactionId)+">\n";
        while (!LockFileIO.getLock(finalFineName)){
            LockFileIO.doAppend(finalFineName,content);
            LockFileIO.unLock(finalFineName);
        }
        return true;
    }

    public boolean doOne(){
        String content = "<"+transactionId+","+recordId+","+String.valueOf(type)+",";
        if (type == 0)//0代表插入
            content = content + "insert>\n";
        else
            content = content + Arrays.toString(data) + ">\n";
        while (!LockFileIO.getLock(finalFineName)){
            LockFileIO.doAppend(finalFineName,content);
            LockFileIO.unLock(finalFineName);
        }
        return true;
    }

    public boolean doEnd(){
        String conent = "<commit "+transactionId+">\n";
        while (!LockFileIO.getLock(finalFineName)){
            LockFileIO.doAppend(finalFineName,conent);
            LockFileIO.unLock(finalFineName);
        }
        return true;
    }
    //todo 这里记得完成检查点
    public boolean doCheckPoint(){
        return true;
    }

    public void setData(byte[] data){
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }
}
