package com.reremouse.ju4ja.msg;

import java.io.Serializable;
import java.util.UUID;

public class JavaCallResult implements Serializable {

    private String id;
    private Object resultStr;//结果（返回值）
    private String resultType;//
    private String status;//函数执行结果状态



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResultStr() {
        return resultStr;
    }

    public void setResultStr(Object resultStr) {
        this.resultStr = resultStr;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public <T> T inferResult(){
        return null;
    }



}
