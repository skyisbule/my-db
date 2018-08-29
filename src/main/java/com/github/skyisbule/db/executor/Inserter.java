package com.github.skyisbule.db.executor;

import com.github.skyisbule.db.config.DbStorageConfig;
import com.github.skyisbule.db.page.Page;
import com.github.skyisbule.db.struct.DbStruct;
import com.github.skyisbule.db.struct.DbTableStruct;
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

    DbTableStruct      struct;

    public byte[] doInsert(Integer transcationId,String dbName,String tableName,HashMap<String,Object> dataMap){
        //这里假设表的结构是  {#uid，user_name,passwd}
        //final String dbName    = "test";
        //final String tableName = "db_user";
        //final HashMap<String,Object> dataMap = new HashMap<>();
        //dataMap.put("uid",1);
        //dataMap.put("user_name","skyisbule");
        //dataMap.put("passwd","test");
        //todo 记得清这里的注释
        DbTableStruct struct = DbStruct.getTableStructByName(dbName, tableName);
        this.struct          = struct;
        nameList             = struct.fieldNameList;
        lenList              = struct.fieldLensList;
        //开始赋值
        for (Integer i = 0; i < struct.getFieldNum(); i++) {
            if (dataMap.containsKey(nameList.get(i))){
                dataList.add(i,dataMap.get(nameList.get(i)));
            }else {//说明没传这个值 那么插入空
                dataList.add(i,null);
            }
        }
       //dataList.add(1); dataList.add("skyisbule"); dataList.add("test");
        lenList.add(4);  lenList.add(20); lenList.add(25);

        //这里结束  下边开始处理业务
        Integer totalBytes   = struct.getRecordLen() + DbStorageConfig.getTotalByteLen() + struct.getFieldNum()*4;
        byte[]  storageBytes = buildStorageBytes(transcationId,totalBytes,dataMap);

        return storageBytes;
    }

    private byte[] buildStorageBytes(Integer transcationId,Integer totalBytes,HashMap<String,Object> data){

        byte[] storageBytes = new byte[totalBytes];
        //先拷贝信息头
        byte[] timebyte = ByteUtil.getTimeStampByte4();
        System.arraycopy(timebyte,0,storageBytes,4,4);//拷贝时间戳
        byte[] transcationIdByte = ByteUtil.intToByte4(transcationId);
        System.arraycopy(transcationIdByte,0,storageBytes,8,4);//拷贝事务id
        /*
        for (int i =0 ;i < 5 + size ;i++){
            if (i==1) continue;
            System.arraycopy(zerobyte,0,storageBytes,i*4,4);//拷贝信息头
        }
        */
        //接下来开始拷贝真正的信息
        int dataLenPos  = DbStorageConfig.getTotalByteLen();
        int realDataPos = dataLenPos + nameList.size()*4;
        Object dataTemp;
        for (int i = 0; i < nameList.size(); i++) {
            dataTemp = dataList.get(i);
            if (dataTemp == null){//说明这个字段没有数据
                dataLenPos  += 4;
                realDataPos += lenList.get(i);
            }else {
                switch (struct.getFieldTypeList().get(i)){
                    case INT:
                        ByteUtil.arraycopy(storageBytes,realDataPos,ByteUtil.intToByte4((int)dataTemp));
                        realDataPos += 4;
                        break;
                    case CHAR:
                        String str = dataTemp.toString();//拷贝长度
                        ByteUtil.arraycopy(storageBytes,dataLenPos,ByteUtil.intToByte4(str.length()));
                        ByteUtil.arraycopy(storageBytes,realDataPos,str.getBytes());//拷贝真实信息
                        realDataPos += lenList.get(i);
                        break;
                    case TIME_STAMP:
                        ByteUtil.arraycopy(storageBytes,realDataPos,ByteUtil.getTimeStampByte4());
                        realDataPos += 4;
                }
            }
            dataLenPos +=4 ;
        }

        /*
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
        */
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
