package com.github.skyisbule.db.task;

import com.github.skyisbule.db.struct.SelectRange;

import java.util.List;

//定义main loop 里crud任务的接口
public interface Task {

    public List<SelectRange> getRanges();

}
