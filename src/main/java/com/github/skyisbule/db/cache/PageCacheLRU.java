package com.github.skyisbule.db.cache;

import com.github.skyisbule.db.page.Page;

import java.util.LinkedHashMap;
import java.util.Map;

//lru的页面置换算法
public class PageCacheLRU<K,V> extends LinkedHashMap<K,V> implements PageCache{

    private int capacity;                     //初始内存容量

    PageCacheLRU(int capacity){          //构造方法，传入一个参数
        super(16,0.75f,true);//调用LinkedHashMap，传入参数
        this.capacity=capacity;             //传递指定的最大内存容量
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest){
        return size()>capacity;
    }

    @Override
    public Page getPageByPK(String dbName, String tableName, Integer PK) {
        return null;
    }
}
