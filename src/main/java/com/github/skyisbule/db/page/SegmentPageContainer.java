package com.github.skyisbule.db.page;

import cn.hutool.core.io.FileUtil;
import com.github.skyisbule.db.config.BaseConfig;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//段页式管理容器
/**
 * 查找的时候在这里拿到跟id相关的page 处理后再根据page的offset写回文件
 */
public class SegmentPageContainer {

    HashMap<String,Map<String,List<Segment>>> dbMap = new HashMap<>();//key：数据库名 value：table->segment->page
    HashMap<String,List<Segment>>          tableMap = new HashMap<>();//key: 表名 value：页名

    public void add(String dbName,HashMap<String,List<Segment>> tableMap){
        dbMap.put(dbName,tableMap);
    }

    public List<Page> getAllPageByTableName(String dbName,String tableName){
        LinkedList<Page> pages = new LinkedList<>();
        for (Segment segment : dbMap.get(dbName).get(tableName)) {
            pages.addAll(segment.getPages());
        }
        return pages;
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

    public Page getMaxPage(String dbName,String tableName){
        List<Segment> segmentList = dbMap.get(dbName).get(tableName);
        Segment segment  = segmentList.get(segmentList.size()-1);
        List<Page> pages = segment.getPages();
        return pages.get(pages.size()-1);
    }

    public boolean createBlankPage(String dbName,String tableName){
        Page page = new Page();
        byte[] data = new byte[16 * 1024];
        page.setData(data);
        String realFile = BaseConfig.DB_ROOT_PATH + dbName + "_" +tableName + ".db";
        File file  = new File(realFile);
        boolean returnFlag = false;
        try {
            if (!file.exists()){
                FileUtil.writeBytes(data,realFile);
            }
            int maxPos = (int)file.getTotalSpace();
            FileUtil.writeBytes(data,file,maxPos,16*1024,true);
            returnFlag = true;
        }catch (Exception e){
            System.out.println("create blank page error!");
        }
       return returnFlag;
    }

}
