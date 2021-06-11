package com.example.addoiloperator;

import com.example.addoiloperator.bean.Common;
import com.example.addoiloperator.bean.Message2;
import com.google.gson.Gson;


public class TestServer {
    public static void main(String[] args) {
        //TcpServer tcpServer = new TcpServer(1234);
        Common common = new Common();
        common.setAction("GuideScreenInfo");
        common.setVersion("1.0.0");
        common.setSiteId("12356");
        common.setMsgId("1005");
        common.setRequestTime("2021-10-15");
        Message2 message = new Message2();
        message.setAmount("5");
        message.setGradeID("2");
        message.setPrice("5.8");
        message.setOptType(1+"");
        message.setPumpID("92#");
        message.setVolume("5");
        common.setMessage(message);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(common);
        System.out.println(jsonStr);
        //tcpServer.SST.get(0).send();
    }
}
