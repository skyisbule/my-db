package com.github.skyisbule.db.executor;

import com.github.skyisbule.db.config.DbStorageConfig;
import com.github.skyisbule.db.filter.Filter;
import com.github.skyisbule.db.struct.DbStruct;
import com.github.skyisbule.db.struct.DbTableStruct;
import com.github.skyisbule.db.util.ByteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Selecter {

    LinkedList<HashMap<String,Object>> results = new LinkedList<>();
    HashMap<String,Object> rowData = new HashMap<>();

    private ArrayList<Filter> filterChian;

    public LinkedList<HashMap<String,Object>> doSelect(String dbName,String tableName,byte[] data){

        DbTableStruct struct = DbStruct.getTableStructByName(dbName,tableName);
        //接下来开始根据 字段名 ->字段类型 ->字段长度 解析data 生成最终的结果
        Integer headerLen   = DbStorageConfig.getTotalByteLen();//信息头的长度
        Integer storageLen  = struct.getFieldNum() * 4;//记录已存储信息的长度
        Integer realDataLen = struct.getRecordLen();//插入的的信息的长度
        Integer rowTotalLen = headerLen + storageLen + realDataLen;
        for (int loop = 0 ;loop<data.length/rowTotalLen;loop++){
            doOneRow((loop+1)*rowTotalLen,headerLen,storageLen,realDataLen,data,struct);
        }

        return results;
    }

    private void doOneRow(int beginPos,int headerLen,int storageLen,int realDataLen,byte[] data,DbTableStruct struct){
        //header
        int isDelete         = byte2Int4(0,data);
        int insertTimeStamp  = byte2Int4(4,data);
        int transcationId    = byte2Int4(8,data);
        int readTimeStamp    = byte2Int4(12,data);
        int updateTimeStamp  = byte2Int4(16,data);
        //body
        int basePos = beginPos + DbStorageConfig.getTotalByteLen();
        int lenPos  = beginPos + DbStorageConfig.getTotalByteLen();
        int dataPos = basePos  + struct.getFieldNum() * 4 ;
        for (int i = 0; i < struct.getFieldNameList().size(); i++) {//遍历那三个同步的List 处理一行数据
            switch (struct.getFieldTypeList().get(i)){
                case INT:
                    Integer intTemp = byte2Int4(dataPos,data);
                    rowData.put(struct.getFieldNameList().get(i),intTemp);
                    lenPos  += 4;
                    dataPos += 4;
                    break;
                case VARCHAR: break;
                case CHAR:
                    int    strLen      = byte2Int4(lenPos,data);
                    byte[] strByteTemp = new byte[strLen];
                    ByteUtil.arraycopy(data,dataPos,strByteTemp);
                    String strTemp = new String(strByteTemp);
                    rowData.put(struct.getFieldNameList().get(i),strTemp);
                    lenPos  += 4;
                    dataPos += struct.getFieldLensList().get(i);
                    break;
                case TIME_STAMP:
                    Integer timeTemp = byte2Int4(dataPos,data);
                    rowData.put(struct.getFieldNameList().get(i),timeTemp);
                    lenPos  += 4;
                    dataPos += 4;
                    break;
            }
        }
    }


    private int byte2Int4(int pos,byte[] data){
        return   data[pos+3] & 0xFF |
                (data[pos+2] & 0xFF) << 8 |
                (data[pos+1] & 0xFF) << 16 |
                (data[pos] & 0xFF) << 24;
    }

}
