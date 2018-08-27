package com.github.skyisbule.db.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//段页式管理容器
public class SegmentPageContainer {

    HashMap<String,Map<String,List<Segment>>> dbMap = new HashMap<>();//key：数据库名 value：table->segment->page
    HashMap<String,List<Segment>>          tableMap = new HashMap<>();//key: 表名 value：页名

    public void add(String dbName,HashMap<String,List<Segment>> tableMap){
        dbMap.put(dbName,tableMap);
    }

    //todo 这里后期要进行查询优化
    public Page getPageById(String dbName,String table,int keyId){
        Page page = new Page();
        List<Segment> segments = dbMap.get(dbName).get(table);
        for (Segment segment : segments) {
            if (segment.getMaxId()>keyId){//说明id所在的page在这个段表里
                for (Page pageTemp : segment.getPages()) {
                    if (pageTemp.getMinId()<keyId && pageTemp.getMaxId()>keyId){
                        page = pageTemp;
                    }
                }
            }
        }
        return page;
    }

}
