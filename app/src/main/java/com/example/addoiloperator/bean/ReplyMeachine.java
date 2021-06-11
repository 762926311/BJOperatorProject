package com.example.addoiloperator.bean;

public class ReplyMeachine {
    public String Action;
    public String ResultCode;
    public String ResultMsg;
    public String SiteID;
    public String MsgID;
    public String ResponseTime;
    public Message2 Message;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        ResultMsg = resultMsg;
    }

    public String getSiteID() {
        return SiteID;
    }

    public void setSiteID(String siteID) {
        SiteID = siteID;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(String responseTime) {
        ResponseTime = responseTime;
    }

    public Message2 getMessage() { return Message; }

    public void setMessage(Message2 message) { Message = message; }

    //    {"Action":"GuideScreenInfo",
//     "ResultCode":"0",
//     "ResultMsg":"OK",
//     "SiteID":"BC02",
//     "MsgID":"4",
//     "ResponseTime":"2021-05-02 16:52:44",
//     "Message":{"PumpID":"1","OptType":"2"}
//    }
//    $&
}
