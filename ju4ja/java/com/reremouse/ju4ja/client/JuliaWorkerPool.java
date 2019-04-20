package com.reremouse.ju4ja.client;


import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Julia实例池包装器
 */
public class JuliaWorkerPool {

    private static GenericObjectPool pool;
    private static int activeNum=0;
    static Logger logger= LoggerFactory.getLogger(JuliaWorkerPool.class);

    public JuliaWorkerPool(String ip, int port){
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        config.maxActive = 200;//最大200个TCP连接
        config.minIdle=1;
        config.maxWait = 30000;//30秒内worker未使用，即销毁
        JuliaWorkerFactory factory = new JuliaWorkerFactory(ip,port);
        pool = new GenericObjectPool(factory, config);
    }


    /**
     * 借出一个实例对象
     * @return
     * @throws Exception
     */
    public JuliaWorker borrowWorker() throws Exception {
        JuliaWorker mm = (JuliaWorker) pool.borrowObject();
        int count=pool.getNumActive();
        if(count!=activeNum){
            synchronized(JuliaWorkerPool.class){
                activeNum=count;
                logger.warn("Current active Julia worker num.:"+activeNum);
            }
        }
        return mm;
    }

    /**
     * 归还实例
     * @param m
     */
    public void returnWorker(JuliaWorker m) {
        try {
            pool.returnObject(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/**
 * 真.实例池
 */
class JuliaWorkerFactory extends BasePoolableObjectFactory {
    private String ipAddr ;//Julia server ip address
    private int port ;//Julia server tcp port

    /**
     *
     * @param ip
     * @param port
     */
    public JuliaWorkerFactory(String ip, int port){
        this.ipAddr = ip;
        this.port = port;
    }

    @Override
    public Object makeObject() throws Exception {
        JuliaWorker worker=new JuliaWorker(ipAddr,port);
        JuliaWorkerPool.logger.warn("A new Julia worker was created.");
        return worker;
    }

    @Override
    public void destroyObject(Object obj) throws Exception {
        super.destroyObject(obj);
        JuliaWorker worker=(JuliaWorker)obj;
        worker.close();
    }
}

