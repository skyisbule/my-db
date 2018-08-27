package com.github.skyisbule.db.callBack;

import com.github.skyisbule.db.thread.ServerSocketThread;

import java.util.HashMap;

/**
 * socketServerThread向mainThread提交CRUD TASK
 * mainThread来判断
 */
public class MainLoopObserver{

    private static HashMap<Integer, ServerSocketThread> socketMap = new HashMap<Integer, ServerSocketThread>();

    public static void registSocket(Integer transactionId, ServerSocketThread thread) {
        socketMap.put(transactionId,thread);
    }

    public static void callBack(int transcationId){
        socketMap.get(transcationId).getLockSuccess();
    }


}
