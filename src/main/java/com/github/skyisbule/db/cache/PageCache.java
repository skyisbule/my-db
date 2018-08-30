package com.github.skyisbule.db.cache;

import com.github.skyisbule.db.page.Page;

//页缓存组件
public interface PageCache {

    public Page getPageByPK(String dbName,String tableName,Integer PK);

}
