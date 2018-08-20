package com.github.skyisbule.db.struct;

import java.util.ArrayList;
import java.util.List;

//定义查询范围
public class SelectRange {

    private class range{
        int begin;
        int end;
    }

    List<range> ranges = new ArrayList<>();
    int size;


}
