package com.github.skyisbule.db.index;

import com.github.skyisbule.db.page.Page;

import java.util.ArrayList;
import java.util.HashMap;

//稀疏索引实现  //todo  这个放到以后实现
public class SparseIndex implements Index {

    HashMap<Integer,Integer> indexMap = new HashMap<>();
    ArrayList<Page> pages = new ArrayList<>();
    int maxId;

    @Override
    public Page getPageByPK(int keyId) {
        Page page = new Page();
        if (keyId>maxId)
            return null;

        return null;
    }
}
