package com.github.skyisbule.db.concurrent;

/**
 * rollBack 实现类 通过读取undo日志实现回滚 使数据库恢复一致性
 */
public class Backer implements Timer {

    //todo 这里要先获取最新的检查点
    public void getLeastCheckPoint(){

    }

    @Override
    public boolean rollBack(int transactionId) {
        return false;
    }

}
