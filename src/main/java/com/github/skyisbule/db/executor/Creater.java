package com.github.skyisbule.db.executor;

import cn.hutool.core.io.FileUtil;
import com.github.skyisbule.db.config.BaseConfig;
import com.github.skyisbule.db.config.DbStorageConfig;
import com.github.skyisbule.db.struct.DbTableField;
import com.github.skyisbule.db.type.StoredType;
import com.github.skyisbule.db.type.TypeLen;
import com.github.skyisbule.db.util.TypeUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 工作流程
 * 检查库名，检查表名合法性。
 * check
 * 逐个生成表文件
 * 生成库文件
 */
//库表创建器
public class Creater {

    private String sql = "create db_test(uid int,name varchar 20,birth datetime)primary key uid;";

    private String dbName;
    private ArrayList<DbTableField> fields = new ArrayList<>();

    /**
     * 工作流程
     * 解析出db名，解析字段名生成字段列表
     * 生成物理存储计划
     * 调用存储
     */
    public void doCreate(String sql){
        sql = sql.toLowerCase();
        this.sql = sql;
        getDbName(sql);
        //todo  这里记得把库的信息存储到文件里。
        FileUtil.touch(new File(BaseConfig.DB_ROOT_PATH +this.dbName+".table"));
    }


    private void getDbFields(String sql){
        boolean hasPK = false;
        String  PK = "";
        //尝试获取主键
        if (sql.indexOf("primary key")>10){
            PK = sql.substring(sql.indexOf("primary key ")+12,sql.length()).replace(";","");
        }
        //拿到字段们
        String fieldStr = sql.substring(sql.indexOf('('),sql.indexOf(')'));
        for (String field :fieldStr.split(",")){
            String[] args = field.split(" ");//以‘，’和‘ ’分割后拿到字段
            String fieldName = args[0];
            String fieldType = args[1];
            String fieldLen  = "";
            if (args.length==3)//这里判断一下是否有长度设置，如果有那么获取它。
                fieldLen = args[2];
            //这里开始构造字段实体
            DbTableField fieldPO = new DbTableField(fieldName);
            //判断一下是否是主键
            if (fieldName.equals(PK))
                fieldPO.setPK(true);
            setTypeAndByteLenByStr(fieldType,fieldLen,fieldPO);
            fields.add(fieldPO);
        }
        //以上解析结束，接下来开始生成库信息文件 表信息文件
    }

    private void setTypeAndByteLenByStr(String type,String fieldLen,DbTableField fieldPO){
        Integer byteLen = 1;
        //判断一下是不是存储长度可变的类型  char 或者 varchar
        if (TypeUtil.isVarLenType(type)){
            byteLen = Integer.parseInt(fieldLen) * 8;//换算成底层存储的byte长度
        }
        switch (type){
            case "int":
                fieldPO.setType(StoredType.INT);
                fieldPO.setByteLen(TypeLen.INTEGER);
                break;
            case "BIGINT":
                fieldPO.setType(StoredType.BIGINT);
                fieldPO.setByteLen(TypeLen.BIGINT);
                break;
            case "CHAR":
                fieldPO.setType(StoredType.CHAR);
                fieldPO.setByteLen(byteLen);
                break;
            case "VARCHAR":
                fieldPO.setType(StoredType.VARCHAR);
                fieldPO.setByteLen(byteLen);
                break;
            case "DATA_TIME":
                fieldPO.setType(StoredType.DATA_TIME);
                fieldPO.setByteLen(TypeLen.DATA_TIME);
                break;
            case "TIME_STAMP":
                fieldPO.setType(StoredType.TIME_STAMP);
                fieldPO.setByteLen(TypeLen.TIME_STAMP);
                break;
        }
    }

    /**
     * 这里存储的顺序是：
     * 删除检测位、插入时间戳、事务id、读标记、写标记、{长度位、长度位、。。。}、{数据、数据、数据。。。}
     * 计算存储这么一条信息需要的长度
     * @return 长度
     */
    private Integer computeLen(){
        Integer total = DbStorageConfig.getTotalByteLen();
        total     +=  fields.size()*32; //计算长度位   ： 一个长度为用一个int存储  有多少列就存多少
        for (DbTableField field : this.fields)
            total += field.getByteLen();//这里计算一下数据位
        return total;
    }

    private String buildDBStorage(){
        return "";
    }

    private void getDbName(String sql){
        int begin   = sql.indexOf(' ');
        int end     = sql.indexOf('(');
        this.dbName = sql.substring(begin,end);
    }

}
