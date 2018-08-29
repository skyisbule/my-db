package com.github.skyisbule.db.thread;

import com.github.skyisbule.db.exception.InsertErrorEception;
import com.github.skyisbule.db.executor.Inserter;
import com.github.skyisbule.db.executor.Selecter;
import com.github.skyisbule.db.page.SegmentPageContainer;
import com.github.skyisbule.db.result.DBResult;
import com.github.skyisbule.db.struct.DbStruct;
import com.github.skyisbule.db.struct.DbTableStruct;
import com.github.skyisbule.db.task.InsertTask;
import com.github.skyisbule.db.task.IoTask;
import com.github.skyisbule.db.task.SelectTask;
import com.github.skyisbule.db.type.IoTaskType;
import com.github.skyisbule.db.util.ByteUtil;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

//定义连接客户端的监听线程，用于解析用于请求，解析sql，构造ioTask并递交给mainThread

/**
 * 处理流程为
 * 1.获取事务id，将自己注册到两个回调中心 用于接收mainThread以及IOThread的任务回调。
 * 2.解析用户的网络请求，分析sql
 * 3.根据sql生成对应的 CRUD TASK 以及IO TASK
 * 4.将CRUD TASK递交给mainThread尝试获取锁 并进入等待状态//todo  这里未来再改成等待的过程中继续视情况解析sql
 * 5.获取锁成功后mainThread会调用getLockSuccess方法 进入第二个状态
 * 6.递交ioTask给IOThread
 * 7.等待回调 将生成的结果返回给客户端
 */
public class ServerSocketThreadImp extends Thread implements ServerSocketThread{

    private Socket     socket;
    private int        transactionId;
    private IoTask     ioTaskTemp;
    private DBResult   result;
    private DbIoThread ioThread;
    private volatile   boolean    getLock;
    private volatile   boolean    getResult;
    private boolean    isTranscation;
    private String     dbName;
    private SegmentPageContainer pageContainer;

    public void init(Socket socket,int trascathionId){
        this.socket = socket;
        this.transactionId = trascathionId;
        this.getLock   = false;
        this.getResult = false;
    }

    /**
     * 这里需要区分是普通sql还是事务
     * 获取sql   构造对应的 crud task
     */
    //todo   未来再把这里的流程拆一拆 拆成单个函数
    public void run(){
        //先判断是否是事务

        if (isTranscation){


        }else{//不是事务  则可以简化流程 可以不用再接收socket的信息了


        }


    }

    private boolean doInsert(String sql) throws InterruptedException {
        //先模拟一下sql
        sql = "insert into test values(1,\'sky\',21)";
        String tableName = "test";
        HashMap<String,Object> insertDataMap = new HashMap<>();
        insertDataMap.put("id",1);
        insertDataMap.put("user_name","sky");
        insertDataMap.put("age",21);
        boolean isAutoIncreasePK = true;
        //接下来开始构造任务
        Inserter inserter = new Inserter();
        byte[] insertByteData = inserter.doInsert(transactionId,dbName,tableName,insertDataMap);
        //开始尝试获取锁
        InsertTask task = new InsertTask(transactionId,dbName,tableName);
        DbMainLoopThread.commit(task);
        while (!getLock){
            Thread.sleep(1);
        }
        //已拿到锁  开始递交IOTask
        getLock = false;
        DbTableStruct struct = DbStruct.getTableStructByName(dbName,tableName);
        if (isAutoIncreasePK){
            int PKpos = 20 + struct.getFieldNum()*4;
            ByteUtil.arraycopy(insertByteData,PKpos,ByteUtil.intToByte4(task.PKID));
        }
        IoTask ioTask = new IoTask(transactionId,struct.getRealFileName(),IoTaskType.INSERT);
        ioThread.commit(ioTask);
        while (!getResult){
            Thread.sleep(1);
        }
        getResult = false;
        //拿到了结果
        if (result.getCode()==200) {
            return true;
        }
        else {
            try {
                throw new InsertErrorEception(result.getCode(),result.getQueryResult());
            } catch (InsertErrorEception insertErrorEception) {
                insertErrorEception.printStackTrace();
            }
        }
        return false;
    }

    private void doSelect(String sql) throws InterruptedException{
        //这里先模拟解析到了用户的sql
        sql = "select * from test";
        String tableName = "test";
        //这里先构造 crud Task   获取最重要的两个信息：表  加锁范围  递交给mainThread
        SelectTask task = new SelectTask(transactionId,"test",true);
        //构造完获取锁的任务后，需要通过段页表获取你需要读取的块的位置，即byte位  并基于此构造IO TASK 递交给io线程去读
        ioTaskTemp = new IoTask(transactionId,"test",IoTaskType.READ);
        DbMainLoopThread.commit(task);
        //轮训状态 一旦获取了锁就递交给io线程
        while (!getLock){
            Thread.sleep(1);
        }
        getLock = false;
        //走到这里代表已经获取了锁并递交了io任务  开始轮训结果状态  看看结果返回没
        while(!getResult){
            Thread.sleep(1);
        }
        getResult = false;
        //走到这里就代表结果反回了  这里将result递交给selecter 获取最终响应结果
        Selecter selecter = new Selecter();
        //拿到响应结果就可以生成串写回给用户了
        LinkedList<HashMap<String, Object>> results = selecter.doSelect(dbName,tableName,result.data);
    }


    //拿到了IO返回的东西
    public void doIoCallBack(DBResult result) {
        this.getResult = true;
        //根据ioTask生成result
        this.result = result;
    }

    //代表拿到了锁，可以向IOThread提交io任务了
    public void getLockSuccess(){
        getLock = true;
        ioThread.commit(ioTaskTemp);
    }

}
