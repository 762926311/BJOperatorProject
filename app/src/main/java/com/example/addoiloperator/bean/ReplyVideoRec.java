package com.example.addoiloperator.bean;

public class ReplyVideoRec {
    public String Action;
    public String ResultCode;
    public String ResultMsg;
    public String SiteID;
    public String MsgID;
    public String ResponseTime;
    public VideoRecoMessage Message;

    public ReplyVideoRec(String action, String resultCode, String resultMsg, String siteID, String msgID, String responseTime, VideoRecoMessage message) {
        Action = action;
        ResultCode = resultCode;
        ResultMsg = resultMsg;
        SiteID = siteID;
        MsgID = msgID;
        ResponseTime = responseTime;
        Message = message;
    }

    public ReplyVideoRec() {
    }

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

    public VideoRecoMessage getMessage() {
        return Message;
    }

    public void setMessage(VideoRecoMessage message) {
        Message = message;
    }
}
