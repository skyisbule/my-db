package com.github.skyisbule.db.cache;

import com.github.skyisbule.db.page.Page;

//缓存管理容器
public class PageCacheContainer {

    private PageCache pageCache;

    public void setPageCache(PageCache cache){
        this.pageCache = cache;
    }

    public Page getPageByPK(String dbName,String tableName,Integer PK){
        return pageCache.getPageByPK(dbName,tableName,PK);
    }

}
