package com.github.skyisbule.db.concurrent;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

//事务的id生成器
public class IdBuilder {

    AtomicInteger id;
    Date          startDate;

    public void setId(AtomicInteger id){
        this.id = id;
    }

    public Integer getId(){
        return id.incrementAndGet();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
