package skyisbule.github.com.executor;

import skyisbule.github.com.struct.DbTableField;
import skyisbule.github.com.type.StoredType;
import skyisbule.github.com.type.TypeLen;

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

    }

    private void getDbFields(String sql){
        boolean hasPK = false;
        String  PK;
        //尝试获取主键
        if (sql.indexOf("primary key")>10){

        }
        //拿到字段们
        String fieldStr = sql.substring(sql.indexOf('('),sql.indexOf(')'));
        for (String field :fieldStr.split(",")){
            String[] args = field.split(" ");
            String fieldName = args[0];
            String fieldType = args[1];
            //这里开始构造字段实体
            DbTableField fieldPO = new DbTableField(fieldName);
            setTypeAndByteLenByStr(fieldType,fieldPO);

        }
    }

    private void setTypeAndByteLenByStr(String type,DbTableField fieldPO){
        Integer byteLen = 1;
        //判断一下是不是char或者varchar
        if (type.indexOf(' ')>2){
            String strs[] = type.split(" ");
            type    = strs[0];
            byteLen = Integer.parseInt(strs[1]);
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

    private void getDbName(String sql){
        int begin   = sql.indexOf(' ');
        int end     = sql.indexOf('(');
        this.dbName = sql.substring(begin,end);
    }

}
