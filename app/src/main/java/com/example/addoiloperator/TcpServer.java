package com.example.addoiloperator;

import android.content.Intent;
import android.util.Log;
import com.example.addoiloperator.bean.Message2;
import com.example.addoiloperator.bean.ReplyMeachine;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServer implements Runnable {
    private String TAG = "TcpServer";
    private int port = 1234;
    private boolean isListen = true;   //线程监听标志位
    public ArrayList<ServerSocketThread> SST = new ArrayList<ServerSocketThread>();
    public TcpServer(int port){
        this.port = port;
    }

    //更改监听标志位
    public void setIsListen(boolean b){
        isListen = b;
    }

    public void closeSelf(){
        isListen = false;
        for (ServerSocketThread s : SST){
            s.isRun = false;
        }
        SST.clear();
    }

    private Socket getSocket(ServerSocket serverSocket){
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "run: 监听超时");
            return null;
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(5000);
            while (isListen){
                Log.i(TAG, "run: 开始监听...");
                Socket socket = getSocket(serverSocket);
                if (socket != null){
                  new ServerSocketThread(socket);
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ServerSocketThread extends Thread {
        Socket socket = null;
        private PrintWriter pw;
        private InputStream is = null;
        private OutputStream os = null;
        private String ip = null;
        private boolean isRun = true;

        ServerSocketThread(Socket socket){
            this.socket = socket;
            ip = socket.getInetAddress().toString();
            Log.i(TAG, "ServerSocketThread:检测到新的客户端联入,ip:" + ip);
            try {
                socket.setSoTimeout(5000);
                os = socket.getOutputStream();
                is = socket.getInputStream();
                pw = new PrintWriter(os,true);
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String msg){
            pw.println(msg);
            pw.flush(); //强制送出数据
        }

        @Override
        public void run() {
            byte buff[]  = new byte[4096];
            String rcvMsg;
            int rcvLen;
            SST.add(this);
            while (isRun && !socket.isClosed() && !socket.isInputShutdown()){
                try {
                    if ((rcvLen = is.read(buff)) != -1 ){
                        rcvMsg = new String(buff,0,rcvLen);
                        System.out.println("yanyan tcpserver 收到信息："+rcvMsg);
                        if(rcvMsg.contains("$&")){
                           int index =  rcvMsg.indexOf("$");
                           rcvMsg = rcvMsg.substring(0,index);
                           System.out.println("yanyan tcpserver 收到信息去除特殊字符："+rcvMsg);
                            try{
                                JSONObject jsonObject = new JSONObject(rcvMsg);
                                String action = jsonObject.getString("Action");
                                String version = jsonObject.getString("Version");
                                String siteID = jsonObject.getString("SiteID");
                                String msgID = jsonObject.getString("MsgID");
                                String requestTime = jsonObject.getString("RequestTime");

                               JSONObject messObj = jsonObject.getJSONObject("Message");
                               String pumpID =  messObj.getString("PumpID");
                               String gradeID =  messObj.getString("GradeID");
                               String price =  messObj.getString("Price");
                               String amount =  messObj.getString("Amount");
                               String volume =  messObj.getString("Volume");
                               String optType =  messObj.getString("OptType");

//                            JSONObject messObj =  message.getJSONObject(0);
//                            String pumpID =  messObj.getString("PumpID");
//                            String gradeID =  messObj.getString("GradeID");
//                            String price =  messObj.getString("Price");
//                            String amount =  messObj.getString("Amount");
//                            String volume =  messObj.getString("Volume");
//                            String optType =  messObj.getString("OptType");
//                            System.out.println("yanyan message.length="+message.length());
//                                for (int i=0;i<message.length();i++){
//                                    JSONObject messObj =  message.getJSONObject(i);
//                                    String pumpID =  messObj.getString("PumpID");
//                                    String gradeID =  messObj.getString("GradeID");
//                                    String price =  messObj.getString("Price");
//                                    String amount =  messObj.getString("Amount");
//                                    String volume =  messObj.getString("Volume");
//                                    String optType =  messObj.getString("OptType");
//                                }
                                //{"Action":"GuideScreenInfo","ResultCode":"0","ResultMsg":"OK","SiteID":"BC02","MsgID":"4","ResponseTime":"2021-05-02 16:52:44","Message":{"PumpID":"1","OptType":"2"}}$&

                                ReplyMeachine replyMeachine = new ReplyMeachine();
                                replyMeachine.setAction(action);
                                replyMeachine.setMsgID(msgID);
                                replyMeachine.setResponseTime(System.currentTimeMillis()+"");
                                replyMeachine.setResultCode("0");
                                replyMeachine.setSiteID(siteID);
                                Message2 replyMessage = new Message2();
                                replyMessage.setPumpID(pumpID);
                                replyMessage.setOptType(optType+"");
                                replyMeachine.setMessage(replyMessage);
                                Gson gson = new Gson();
                                String replystr = gson.toJson(replyMeachine);
                                System.out.println("yanyan tcpserver replystr="+replystr);
                                send(replystr+"$&");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        if (rcvMsg.equals("QuitServer")){
                            isRun = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
                SST.clear();
                Log.i(TAG, "run: 断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
