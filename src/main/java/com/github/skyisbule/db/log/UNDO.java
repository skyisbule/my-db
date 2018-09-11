package com.github.skyisbule.db.log;

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
}
