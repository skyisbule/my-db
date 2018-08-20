package com.github.skyisbule.db.concurrent;

import com.github.skyisbule.db.task.InsertTask;
import com.github.skyisbule.db.task.SelectTask;
import com.github.skyisbule.db.task.Task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//全局的锁分配器

/**
 *  插入 、 删除 、 修改
 *  行级锁 范围锁
 */
public class CentLocker {

    HashMap<String,Rowlock> centLockMap = new HashMap<>();

    private class Rowlock{
        Set<Integer> rowIds = new HashSet<>();
    }

    public static boolean getLock(Task task){
        if (task instanceof InsertTask){
            //这里拿到maxid 加锁
        }else if(task instanceof SelectTask){
            //todo  这里要过滤掉ids
            return true;
        }

        return false;
    }

}
