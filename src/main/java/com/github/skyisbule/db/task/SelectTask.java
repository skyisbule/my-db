package com.github.skyisbule.db.task;

import com.github.skyisbule.db.struct.SelectRange;

import java.util.List;

/**
 * 处理流程
 * serverSocket解析sql生成selectTask，生成查询范围
 * 传给mainThread，根据查询范围读取，视情况回滚
 */
public class SelectTask implements Task{

    Integer           transcationId;//事务id
    boolean           hasFilter;//是否有过滤条件  目前不用
    String            fileName; //表名
    boolean           selectAll;//是否查询全部，即查询条件中是否有“主键”的过滤条件
    boolean           isRange;  //是否是范围，若不是则代表查询指定主键的值
    List<Integer>     PK;       //主键  如（id =1 or id = 2....)
    List<SelectRange> ranges;   //查找范围

}
