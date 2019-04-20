package com.reremouse.ju4ja.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reremouse.ju4ja.msg.JavaCallMsg;
import com.reremouse.ju4ja.msg.JavaCallResult;
import com.reremouse.ju4ja.msg.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * This class works as a client to call Julia server
 */
public class Ju4jaClient {

    private String ipAddr = "localhost";
    private int port = 9527;
    private Socket socket;
    Logger logger = LoggerFactory.getLogger(Ju4jaClient.class);

    public Ju4jaClient(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }

    /**
     * Send JSON message to Julia server
     * @param msg Json messages
     * @return
     */
    private JavaCallResult sendCommand(String msg) {
        JavaCallResult result = null;
        try {
            //创建客户端Socket，指定服务器地址和端口
            socket = new Socket(ipAddr, port);
            //输出流，向服务器端发送信息
            OutputStream os = new BufferedOutputStream(socket.getOutputStream());
            PrintWriter pw = new PrintWriter(os);
            pw.println(msg);
            pw.flush();
            //输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder rs = new StringBuilder();
            String info = null;
            while ((info = br.readLine()) != null) {
                rs.append(info);
                logger.debug(info);
            }
            //关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
            String json = rs.toString();
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(json, JavaCallResult.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
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
            JavaCallMsg ins = new JavaCallMsg();
            ins.setFunc(func);
            ins.setModn(moduleName);
            //ins.setResultType(T);
            ins.setOperation(OperationType.FUNCTION);
            ins.setArgs(args);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ins);
            logger.debug(json);
            re=this.sendCommand(json);
            if(re == null){
                logger.error("Fatal inner error caused by remote Julia server or network.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return re;
    }


}
