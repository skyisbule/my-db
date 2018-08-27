package com.github.skyisbule.db.index.bptree;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
// testing purpose
    private BPTree<String> dictionay = new BPTree<String>(200, 200, 3);

    public void loadDic(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader r = new BufferedReader(fr);
        String s = r.readLine();
        ArrayList<String> dic = new ArrayList<String>();
        ArrayList<Object> record = new ArrayList<Object>();
        int count=0;
        while (s != null) {
            dic.add((String) s.toLowerCase());
            record.add((String) s.toLowerCase());
            s = r.readLine();
            count++;
        }
        System.out.println(count);
        dictionay.insertBulk(dic, record);
    }

    public boolean search(String s) {
        Node<String> n = dictionay.search(s.toLowerCase());
        if (n == null) {
            return false;
        } else {
            return true;
        }
    }
}
