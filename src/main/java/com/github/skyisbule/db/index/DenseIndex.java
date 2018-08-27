package com.github.skyisbule.db.index;

import com.github.skyisbule.db.page.Page;

import java.util.HashMap;

//稠密索引
public class DenseIndex implements Index{

    HashMap<Integer,Page> indexMap = new HashMap<>();

    @Override
    public Page getPageByPK(int keyId) {
        return indexMap.get(keyId);
    }

}
