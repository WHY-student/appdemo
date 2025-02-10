package com.gdu.demo.views;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetectionBox {
    @JsonProperty("X")
    private int x;

    @JsonProperty("Y")
    private int y;

    @JsonProperty("W")
    private int w;

    @JsonProperty("H")
    private int h;

    @JsonProperty("Confidence")
    private float confidence;

    @JsonProperty("LabelIndex")
    private int labelIndex;
    @JsonProperty("LabelIndex")
    private String LabelName;

    @JsonProperty("ModelID")
    private int modelID;

    public int getLabelIndex() {
        return labelIndex;
    }

    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }
}
