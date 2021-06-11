package com.example.addoiloperator;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.addoiloperator.bean.CarCommon;
import com.example.addoiloperator.bean.Common;
import com.example.addoiloperator.bean.Message2;
import com.example.addoiloperator.bean.ReplyMeachine;
import com.example.addoiloperator.bean.ReplyVideoRec;
import com.example.addoiloperator.bean.VideoRecoMessage;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MeachineService extends IntentService {

    public ServerSocketThread mReceiveThread = null;
    public DatagramSocket mSocket =null;
    public DatagramPacket datagramPacket = null;

    public static final String TAG = "MeachineService";
    public ServerSocket serverSocket = null;
    public OutputStream os = null;
    public InputStream is = null;
    public PrintWriter pw = null;
    private boolean isListen = true;   //线程监听标志位
    public ArrayList<ServerSocketThread> SST = new ArrayList<ServerSocketThread>();

    /**
     * @deprecated
     */
    public MeachineService() {
        super("MeachineService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        tcpserverConnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try{
            disConnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    //更改监听标志位
    public void setIsListen(boolean b){
        isListen = b;
    }

    public void closeSelf(){
        isListen = false;
        for (ServerSocketThread s : SST){
            s.interrupt();
            s = null;
        }
        SST.clear();
    }

    public void tcpserverConnect() {
        if (serverSocket == null || serverSocket.isClosed()) {
            try {
                //TrafficStats.setThreadStatsTag(THREAD_ID);
                serverSocket = new ServerSocket(9020);
                serverSocket.setSoTimeout(5000);
                while (isListen){
                    Log.i(TAG, "run: 开始监听...");
                    Socket socket = getSocket(serverSocket);
                    if (socket != null){
                        mReceiveThread = new ServerSocketThread(socket);
                        mReceiveThread.start();
                    }
                }
               serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                //start();
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
            System.out.println("yanyan MeachineService isRun="+isRun+",socket.isClosed()="+socket.isClosed()+",isInputShutdown="+socket.isInputShutdown());
            while (isRun && !socket.isClosed() && !socket.isInputShutdown()){
                try {
                    if ((rcvLen = is.read(buff)) != -1 ){
                        rcvMsg = new String(buff,0,rcvLen);
                        System.out.println("yanyan tcpserver 收到信息："+rcvMsg+",SST.size="+SST.size());
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
                                //{"Action":"GuideScreenInfo","Version":"1.0.0","SiteID":"BC02","MsgID":"7","RequestTime":"2021-06-07 18:50:01","Message":{"PumpID":"1","GradeID":"300366","Price":"5.32","Amount":"100.00","Volume":"18.80","OptType":"1"}}

                                if(action.equalsIgnoreCase("GuideScreenInfo")){
                                    ///////////////////////以下为向引导屏发送引导交易信息///////////
                                    JSONObject messObj = jsonObject.getJSONObject("Message");
                                    String pumpID =  messObj.getString("PumpID");
                                    String gradeID =  messObj.getString("GradeID");
                                    String price =  messObj.getString("Price");
                                    String amount =  messObj.getString("Amount");
                                    String volume =  messObj.getString("Volume");
                                    String optType =  messObj.getString("OptType");
                                    String licensePlate = messObj.getString("LicensePlate");
                                    //licensePlate = new String(licensePlate.getBytes(),"US-ASCII");
                                    System.out.println("yanyan service licensePlate="+licensePlate);
                                    Common common = new Common();
                                    Message2 message2 = new Message2();
                                    message2.setGradeID(gradeID);
                                    message2.setPumpID(pumpID);
                                    message2.setPrice(price);
                                    message2.setAmount(amount);
                                    message2.setVolume(volume);
                                    message2.setOptType(optType);
                                    message2.setLicensePlate(licensePlate);
                                    common.setMessage(message2);

                                    ReplyMeachine replyMeachine = new ReplyMeachine();
                                    replyMeachine.setAction(action);
                                    replyMeachine.setMsgID(msgID);
                                    replyMeachine.setResponseTime(System.currentTimeMillis() + "");
                                    replyMeachine.setResultCode("0");
                                    replyMeachine.setSiteID(siteID);

                                    Message2 replyMessage = new Message2();
                                    replyMessage.setPumpID(pumpID);
                                    replyMessage.setOptType(optType + "");
                                    replyMeachine.setMessage(replyMessage);

                                    EventBus.getDefault().post(common);
                                    Gson gson = new Gson();
                                    String replystr = gson.toJson(replyMeachine);
                                    System.out.println("yanyan tcpserver replystr=" + replystr);
                                    send(replystr + "$&");
                                }else{

                                  //  {"Action":"SendVideoReco","Version":"1.0.0","SiteID":"BC02","MsgID":"6","RequestTime":"2021-06-07 18:49:25","Message":{"CameraID":"1","LicensePlate":"��A12345","PumpID":"1","AlarmType":"1","AlarmValue":"0","AlarmLevel":""}}
                                    ///////////////////////以下为视频识别信息/////////////////////
                                JSONObject messVideoObj = jsonObject.getJSONObject("Message");
                                String cameraID = messVideoObj.getString("CameraID");
                                String licensePlate = messVideoObj.getString("LicensePlate");
                                String alarmType = messVideoObj.getString("AlarmType");
                                String alarmValue = messVideoObj.getString("AlarmValue");
                                String alarmLevel = messVideoObj.getString("AlarmLevel");

                                VideoRecoMessage videoRecoMessage = new VideoRecoMessage();
                                videoRecoMessage.setCameraID(cameraID);
                                videoRecoMessage.setLicensePlate(licensePlate);
                                videoRecoMessage.setAlarmType(alarmType);
                                videoRecoMessage.setAlarmValue(alarmValue);
                                videoRecoMessage.setAlarmLevel(alarmLevel);
                                CarCommon carCommon = new CarCommon();
                                carCommon.setMessage(videoRecoMessage);

                                ReplyVideoRec replyVideoRec = new ReplyVideoRec();
                                replyVideoRec.setAction(action);
                                replyVideoRec.setMsgID(msgID);
                                replyVideoRec.setResponseTime(System.currentTimeMillis() + "");
                                replyVideoRec.setResultCode("0");
                                replyVideoRec.setSiteID(siteID);

                                VideoRecoMessage replyVideoMessage = new VideoRecoMessage();
                                replyVideoMessage.setCameraID(cameraID);
                                replyVideoMessage.setAlarmLevel(alarmLevel);
                                replyVideoRec.setMessage(replyVideoMessage);

                                EventBus.getDefault().post(carCommon);
                                Gson gson = new Gson();
                                String replyvideostr = gson.toJson(replyVideoRec);
                                System.out.println("yanyan tcpserver replyvideostr=" + replyvideostr);
                                send(replyvideostr + "$&");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
//                        Intent intent =new Intent();
//                        intent.setAction("tcpServerReceiver");
//                        intent.putExtra("tcpServerReceiver",rcvMsg);
//                        MainActivity.context.sendBroadcast(intent);//将消息发送给主界面
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

//    private Handler mHandler = new Handler() {
//        @ Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String userId = (String)msg.obj;
//            if (msg.what == 2) {
//                mReceiveThread.interrupt();
//                mReceiveThread = null;
//                mReceiveThread = new ServerSocketThread(socket);
//                mReceiveThread.start();
//            }
//        }
//    };

    public void disConnect() throws Exception {
        closeSelf();

        if(serverSocket!=null){
            serverSocket.close();
            serverSocket = null;
        }

        if(mReceiveThread!=null){
            mReceiveThread.interrupt();
            mReceiveThread = null;
        }
    }
}
