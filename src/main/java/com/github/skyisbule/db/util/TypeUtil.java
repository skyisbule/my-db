package com.github.skyisbule.db.util;

import com.github.skyisbule.db.type.StoredType;

//类型相关的全局工具类
public class TypeUtil {

    public static boolean isVarLenType(String type){
        boolean res = false;
        switch (type){
            case "char" : res = true;
            case "varchar": res = true;
        }
        return res;
    }

}
