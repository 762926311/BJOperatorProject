package com.example.addoiloperator.bean;

public class Message2 {
    public String pumpID; //油品号码
    public String gradeID;  //油枪号码
    public String price;  //单价
    public String amount; //加油金额
    public String volume; //加油升数
    public String optType;  //消息类型
    public String licensePlate;  //车牌号

    public String getPumpID() {
        return pumpID;
    }

    public void setPumpID(String pumpID) {
        this.pumpID = pumpID;
    }

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getLicensePlate() { return licensePlate; }

    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
}
