package com.gdu.demo.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gdu.demo.R;

public  class MetaData {

    private int flag;
    private int type;
    private int msgId;
    private int srcSys;
    private int srcCom;
    private int dstSys;
    private int dstCom;
    private int frameSeq;
    private String dataType;
    private String appData;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getSrcSys() {
        return srcSys;
    }

    public void setSrcSys(int srcSys) {
        this.srcSys = srcSys;
    }

    public int getSrcCom() {
        return srcCom;
    }

    public void setSrcCom(int srcCom) {
        this.srcCom = srcCom;
    }

    public int getDstSys() {
        return dstSys;
    }

    public void setDstSys(int dstSys) {
        this.dstSys = dstSys;
    }

    public int getDstCom() {
        return dstCom;
    }

    public void setDstCom(int dstCom) {
        this.dstCom = dstCom;
    }

    public int getFrameSeq() {
        return frameSeq;
    }

    public void setFrameSeq(int frameSeq) {
        this.frameSeq = frameSeq;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }
}
