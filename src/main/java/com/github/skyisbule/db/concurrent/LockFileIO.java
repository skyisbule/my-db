package com.github.skyisbule.db.concurrent;

import cn.hutool.core.io.file.FileWriter;
import com.github.skyisbule.db.config.BaseConfig;

import java.util.HashMap;
import java.util.HashSet;

public class LockFileIO {

    private static LockFileIO lockFileIO   = new LockFileIO();
    private static HashSet<String> lockSet = new HashSet<>();

    public static LockFileIO getInstance(){
        return lockFileIO;
    }

    public static synchronized boolean getLock(String file){
        if (file.contains(file))
            return false;
        lockSet.add(file);
        return true;
    }

    public static synchronized boolean unLock(String file){
        lockSet.remove(file);
        return true;
    }

    public static boolean doAppend(String fileName,String data){
        FileWriter writer = new FileWriter(BaseConfig.DB_ROOT_PATH+fileName);
        writer.append(data);
        return true;
    }

}
