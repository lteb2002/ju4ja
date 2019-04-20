package com.reremouse.ju4ja.msg;

import java.io.Serializable;
import java.util.UUID;

/**
 * This class works as a template of JSON sent to Julia server
 */
public class JavaCallMsg implements Serializable {

    private String id= UUID.randomUUID().toString();
    private OperationType operation;//操作类型 operation type, supported operations are: function || script
    private String script;//脚本名 Julia script name
    private String modn;//模块名 Julia module name
    private String func;//调用的函数名 function to be called
    private Object[] args;//提供的参数 arguments
    private Class resultType;//返回值类型 return type

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getModn() {
        return modn;
    }

    public void setModn(String modn) {
        this.modn = modn;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class getResultType() {
        return resultType;
    }

    public void setResultType(Class resultType) {
        this.resultType = resultType;
    }
}
