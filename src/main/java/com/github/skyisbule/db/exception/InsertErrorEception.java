package com.github.skyisbule.db.exception;

public class InsertErrorEception extends Exception{

    public InsertErrorEception(int code ,String message){
        super("插入错误："+code+" message:"+message);
    }

}
