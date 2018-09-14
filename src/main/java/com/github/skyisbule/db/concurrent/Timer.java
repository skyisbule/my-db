package com.github.skyisbule.db.concurrent;

public interface Timer {

    public boolean rollBack(int transactionId);

}
