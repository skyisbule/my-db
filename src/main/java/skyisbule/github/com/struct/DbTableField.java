package skyisbule.github.com.struct;

import skyisbule.github.com.type.StoredType;

//定义字段的实体
public class DbTableField {

    public DbTableField(String filedName){
        this.filedName=filedName;
    }

    public String     filedName; //字段名
    public Integer    byteLen;   //byte长度
    public StoredType type;      //存储类型
    public boolean    isPK;      //是否为主键

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public Integer getByteLen() {
        return byteLen;
    }

    public void setByteLen(Integer byteLen) {
        this.byteLen = byteLen;
    }

    public StoredType getType() {
        return type;
    }

    public void setType(StoredType type) {
        this.type = type;
    }

    public boolean isPK() {
        return isPK;
    }

    public void setPK(boolean PK) {
        isPK = PK;
    }

}
