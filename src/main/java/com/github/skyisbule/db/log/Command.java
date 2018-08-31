package com.github.skyisbule.db.log;

//定义重做和撤销的接口
public interface Command {

    public void execute();
    public void undo();

}
