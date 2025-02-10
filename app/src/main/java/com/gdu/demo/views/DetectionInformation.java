package com.gdu.demo.views;

import com.gdu.drone.TargetMode;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetectionInformation {
//    private long Timestamp;
//    private int ModelID;
//    private int PtzDirectionAngle;
//    private int PtzScrollAngle;
//    private int PtzPitchAngle;
//    private int UAVCourseAngle;
//    private int UAVRollAngle;
//    private int UAVPitchAngle;
//    private int UAVLongitude;
//    private int UAVLatitude;
//    private int UAVAltitude;
//    private int Length;
//    private List<DetectionBox> Detections;
@JsonProperty("Timestamp")
private long timestamp;

    @JsonProperty("ModelID")
    private int modelID;

    @JsonProperty("PtzDirectionAngle")
    private int ptzDirectionAngle;

    @JsonProperty("PtzScrollAngle")
    private int ptzScrollAngle;

    @JsonProperty("PtzPitchAngle")
    private int ptzPitchAngle;

    @JsonProperty("UAVCourseAngle")
    private int uavCourseAngle;

    @JsonProperty("UAVRollAngle")
    private int uavRollAngle;

    @JsonProperty("UAVPitchAngle")
    private int uavPitchAngle;

    @JsonProperty("UAVLongitude")
    private int uavLongitude;

    @JsonProperty("UAVLatitude")
    private int uavLatitude;

    @JsonProperty("UAVAltitude")
    private int uavAltitude;

    @JsonProperty("Length")
    private int length;

    @JsonProperty("Detections")
    private List<DetectionBox> detections;


    public int getUavAltitude() {
        return uavAltitude;
    }

    public void setUavAltitude(int uavAltitude) {
        this.uavAltitude = uavAltitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public int getPtzDirectionAngle() {
        return ptzDirectionAngle;
    }

    public void setPtzDirectionAngle(int ptzDirectionAngle) {
        this.ptzDirectionAngle = ptzDirectionAngle;
    }

    public int getPtzScrollAngle() {
        return ptzScrollAngle;
    }

    public void setPtzScrollAngle(int ptzScrollAngle) {
        this.ptzScrollAngle = ptzScrollAngle;
    }

    public int getPtzPitchAngle() {
        return ptzPitchAngle;
    }

    public void setPtzPitchAngle(int ptzPitchAngle) {
        this.ptzPitchAngle = ptzPitchAngle;
    }

    public int getUavCourseAngle() {
        return uavCourseAngle;
    }

    public void setUavCourseAngle(int uavCourseAngle) {
        this.uavCourseAngle = uavCourseAngle;
    }

    public int getUavRollAngle() {
        return uavRollAngle;
    }

    public void setUavRollAngle(int uavRollAngle) {
        this.uavRollAngle = uavRollAngle;
    }

    public int getUavPitchAngle() {
        return uavPitchAngle;
    }

    public void setUavPitchAngle(int uavPitchAngle) {
        this.uavPitchAngle = uavPitchAngle;
    }

    public int getUavLongitude() {
        return uavLongitude;
    }

    public void setUavLongitude(int uavLongitude) {
        this.uavLongitude = uavLongitude;
    }

    public int getUavLatitude() {
        return uavLatitude;
    }

    public void setUavLatitude(int uavLatitude) {
        this.uavLatitude = uavLatitude;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<DetectionBox> getDetections() {
        return detections;
    }

    public void setDetections(List<DetectionBox> detections) {
        this.detections = detections;
    }

}
