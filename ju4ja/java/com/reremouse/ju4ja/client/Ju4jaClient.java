package com.reremouse.ju4ja.client;

import com.reremouse.ju4ja.msg.JavaCallResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class works as a client to call Julia server
 */
public class Ju4jaClient {


    //Pool storing Julia workers
    JuliaWorkerPool pool;
    Logger logger = LoggerFactory.getLogger(Ju4jaClient.class);


    /**
     * 初始化工作实例池
     * @param ip IP地址
     * @param port 端口
     */
    public Ju4jaClient(String ip, int port) {
        pool= new JuliaWorkerPool(ip,port);
    }



    /**
     * @param func function to be called in Julia
     * @param moduleName module name of function in Julia
     * @param args arguments passed to Julia function
     * @return
     */
    public <T> JavaCallResult invokeFunction(String func, String moduleName, Object... args) {
        JavaCallResult re=null;
        try {
            //从实例池中借出一个worker
            JuliaWorker worker=this.pool.borrowWorker();
            re=worker.invokeFunction(func,moduleName,args);
            //有借有还，再借不难
            this.pool.returnWorker(worker);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return re;
    }


}
