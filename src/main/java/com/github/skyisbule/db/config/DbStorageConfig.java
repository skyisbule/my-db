package com.github.skyisbule.db.config;

//进行实例存储的设置
//这里按顺序写上存储的定义
public class DbStorageConfig {

    public static final Integer DELETE_FLAGE_BIT  = 4;//记录是否删除标志为
    public static final Integer INSERT_TIME_STAMP = 4;//插入这条记录的时间戳
    public static final Integer TRANSCATION_ID    = 4;//操作过这条记录的事务id
    public static final Integer READED_TIME_STAMP = 4;//事务读标记的时间戳
    public static final Integer UPDATE_TIME_STAMP = 4;//事务写标记的时间戳

    public static Integer getTotalByteLen(){
        return DELETE_FLAGE_BIT+
                INSERT_TIME_STAMP+
                TRANSCATION_ID+
                READED_TIME_STAMP+
                UPDATE_TIME_STAMP;
    }

}
