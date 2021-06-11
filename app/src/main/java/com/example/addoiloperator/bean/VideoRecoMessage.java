package com.example.addoiloperator.bean;

public class VideoRecoMessage {
    public String CameraID; //摄像头编号
    public String LicensePlate; //车牌号
    public String PumpID;  //油枪号
    public String AlarmType; //报警类型。1：边界入侵，2：车辆位置
    //报警值：
    //边界入侵：0无入侵，1有入侵；
    //车辆位置：0车辆位置正确，1车辆靠前，2车辆靠后，3车辆靠左，4车辆靠右
    public String AlarmValue;
    //报警等级
    public String AlarmLevel;

    public String getCameraID() {
        return CameraID;
    }

    public void setCameraID(String cameraID) {
        CameraID = cameraID;
    }

    public String getLicensePlate() {
        return LicensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        LicensePlate = licensePlate;
    }

    public String getPumpID() {
        return PumpID;
    }

    public void setPumpID(String pumpID) {
        PumpID = pumpID;
    }

    public String getAlarmType() {
        return AlarmType;
    }

    public void setAlarmType(String alarmType) {
        AlarmType = alarmType;
    }

    public String getAlarmValue() {
        return AlarmValue;
    }

    public void setAlarmValue(String alarmValue) {
        AlarmValue = alarmValue;
    }

    public String getAlarmLevel() {
        return AlarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        AlarmLevel = alarmLevel;
    }
}
