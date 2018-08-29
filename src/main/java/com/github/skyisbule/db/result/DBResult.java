package com.github.skyisbule.db.result;

import com.github.skyisbule.db.page.Page;

import java.util.List;

//定义返回的结果
public class DBResult {

    public int          code;
    public DBResultType type;
    public String       queryResult;
    public int          transactionId;
    public byte[]       data;
    public List<Page>   page;
    public int          affectedNum;

    public DBResult(){}

    public DBResult(int code, DBResultType type, String queryResult, int transcationId) {
        this.code = code;
        this.type = type;
        this.queryResult = queryResult;
        this.transactionId = transcationId;
    }

    public void error(int code){
        this.setCode(code);
    }

    public void error(int code,String queryResult){
        this.setCode(code);
        this.setQueryResult(queryResult);
    }

    public static DBResult buildSelect(int transactionId,List<Page> pages){
        DBResult result = new DBResult();
        result.setCode(200);
        result.setTranscationId(transactionId);
        result.setType(DBResultType.SELECT);
        if (pages!=null && pages.size()!=0){
            result.setQueryResult("query ok!");
            result.setPage(pages);
        }else{
            result.setQueryResult("query ok!");
            result.setCode(201);//代表数据是空的
            result.setData(new byte[0]);
        }
        return result;
    }

    public static DBResult buildInsert(int transcationId){
        DBResult result = new DBResult();
        result.setCode(200);
        result.setTranscationId(transcationId);
        result.setType(DBResultType.INSERT);
        result.setQueryResult("query ok!");
        return result;
    }

    public static DBResult buildDelete(int transcationId){
        DBResult result = new DBResult();
        result.setCode(200);
        result.setTranscationId(transcationId);
        result.setType(DBResultType.DELETE);
        result.setQueryResult("query ok!");
        return result;
    }

    public static DBResult buildUpdate(int transcationId){
        DBResult result = new DBResult();
        result.setCode(200);
        result.setTranscationId(transcationId);
        result.setType(DBResultType.UPDATE);
        result.setQueryResult("query ok!");
        return result;
    }

    public static DBResult buildEmpty(){
        DBResult result = new DBResult();
        result.code = 404;
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DBResultType getType() {
        return type;
    }

    public void setType(DBResultType type) {
        this.type = type;
    }

    public String getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(String queryResult) {
        this.queryResult = queryResult;
    }

    public int getTranscationId() {
        return transactionId;
    }

    public void setTranscationId(int transcationId) {
        this.transactionId = transcationId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public List<Page> getPage() {
        return page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }

    public int getAffectedNum() {
        return affectedNum;
    }

    public void setAffectedNum(int affectedNum) {
        this.affectedNum = affectedNum;
    }
}
