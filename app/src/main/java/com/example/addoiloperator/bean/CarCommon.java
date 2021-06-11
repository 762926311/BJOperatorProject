package com.example.addoiloperator.bean;

public class CarCommon {
    public String action;
    public String version;
    public String siteId;
    public String msgId;
    public String requestTime;
    public VideoRecoMessage message;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public VideoRecoMessage getMessage() {
        return message;
    }

    public void setMessage(VideoRecoMessage message) {
        this.message = message;
    }
}
