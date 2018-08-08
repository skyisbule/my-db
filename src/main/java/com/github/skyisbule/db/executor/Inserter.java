package com.github.skyisbule.db.executor;

import com.github.skyisbule.db.config.DbStorageConfig;
import com.github.skyisbule.db.task.InsertTask;
import com.github.skyisbule.db.util.ByteUtil;

import java.util.ArrayList;
import java.util.HashMap;

//插入执行器
public class Inserter {

    Integer size      = 3;
    Integer idLen     = 4;
    Integer userLen   = 20;
    Integer passwdLen = 25;

    ArrayList<String>  nameList = new ArrayList<String>();
    ArrayList<Object>  dataList = new ArrayList<Object>();
    ArrayList<Integer> lenList  = new ArrayList<Integer>();

    public boolean doInsert(InsertTask task){
        //todo test 这里暂时先用写死的库名和表名，最终整合时再通过调用中心接口的方式获取信息。
        //这里假设表的结构是  {#uid，user_name,passwd}
        final String dbName    = "test";
        final String tableName = "db_user";
        final HashMap<String,Object> dataMap = new HashMap<>();
        dataMap.put("uid",1);
        dataMap.put("user_name","skyisbule");
        dataMap.put("passwd","test");


        nameList.add("uid"); nameList.add("user_name"); nameList.add("passwd");
        dataList.add(1); dataList.add("skyisbule"); dataList.add("test");
        lenList.add(4);  lenList.add(20); lenList.add(25);

        //这里结束  下边开始处理业务
        Integer columnNum    = dataMap.size();
        Integer totalBytes   = computeTotalLen(dbName,tableName);
        byte[]  storageBytes = buildStorageBytes(totalBytes,dataMap);



        return true;
    }

    private byte[] buildStorageBytes(Integer totalBytes,HashMap<String,Object> data){

        byte[] storageBytes = new byte[totalBytes];
        //先拷贝信息头
        byte[] zerobyte = ByteUtil.intToByte4(0);
        byte[] timebyte = ByteUtil.getTimeStampByte4();
        System.arraycopy(timebyte,0,storageBytes,4,4);//拷贝时间戳
        for (int i =0 ;i < 5 + size ;i++){
            if (i==1) continue;
            System.arraycopy(zerobyte,0,storageBytes,i*4,4);//拷贝信息头
        }
        //接下来开始拷贝真正的信息
        int nowPos = DbStorageConfig.getTotalByteLen() + size * 4;
        for (int i = 0;i<nameList.size();i++){
            byte[] res;
            Object dataTemp = dataList.get(i);
            if (dataTemp instanceof Integer)
                dataTemp = ByteUtil.intToByte4((int)dataTemp);
            else
                dataTemp = dataTemp.toString().getBytes();
            System.arraycopy(dataTemp,0,storageBytes,nowPos,lenList.get(i));
            nowPos +=lenList.get(i);
        }
        return storageBytes;
    }


    private Integer computeTotalLen(String dbName,String tableName){
        //todo 这里的长度到时记得用接口获取
        Integer res = 0;

        res = DbStorageConfig.getTotalByteLen();
        res += size * 4;
        res += idLen + userLen + passwdLen;
        return res;
    }

}
