package com.gdu.demo.ourgdu;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import android.util.Log;
import com.gdu.common.error.GDUError;
import com.gdu.drone.TargetMode;
import com.gdu.gdusocket.GduCommunication3;
import com.gdu.demo.ourgdu.ourGduCommunication3;
//import com.gdu.gdusocket.GduSocketManager;
import com.gdu.demo.ourgdu.ourGduSocketManager;

import com.gdu.gdusocket.SocketCallBack3;
import com.gdu.sdk.util.CommonCallbacks;
import com.gdu.sdk.vision.GDUVision;
import com.gdu.sdk.vision.OnTargetDetectListener;
import com.gdu.sdk.vision.OnTargetTrackListener;
import com.gdu.socketmodel.GduFrame3;
import com.gdu.socketmodel.GduSocketConfig3;
import com.gdu.util.ByteUtilsLowBefore;
import com.gdu.util.RectUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ourGDUVision extends GDUVision {
    private ourGduCommunication3 mGduCommunication3;
    private OnTargetDetectListener targetDetectListener;
    private OnTargetTrackListener targetTrackListener;
    private final List<TargetMode> mTargetModeList;
    private final SocketCallBack3 targetDetectCallBack;
    private final SocketCallBack3 videoTrackCallback;
    private final SocketCallBack3 multipleTargetTrackCb;

    public ourGDUVision() {
        SocketCallBack3 socketCallBack3 = new SocketCallBack3() { // from class: com.gdu.sdk.vision.GDUVision.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v2, types: [byte[]] */
            /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Exception] */
            /* JADX WARN: Type inference failed for: r0v8, types: [com.gdu.sdk.vision.GDUVision] */
            @Override // com.gdu.gdusocket.SocketCallBack3
            public void callBack(byte b, GduFrame3 gduFrame3) {
                byte[] r0;
                if (gduFrame3 == null || (r0 = gduFrame3.frameContent) == null) {
                    return;
                }
                try {
                    ourGDUVision.this.getDetectTargetNew(r0);
                } catch (Exception exception) {
                    Log.i("获取监测目标出错", exception.toString());
                }
            }
        };
        this.targetDetectCallBack = socketCallBack3;
        SocketCallBack3 socketCallBack32 = (b, gduFrame3) -> {
            byte[] bArr = gduFrame3.frameContent;
            if (bArr == null) {
                return;
            }
            getTrackResult(bArr[0], ByteUtilsLowBefore.byte2short(bArr, 1), ByteUtilsLowBefore.byte2short(gduFrame3.frameContent, 3), ByteUtilsLowBefore.byte2short(gduFrame3.frameContent, 5), ByteUtilsLowBefore.byte2short(gduFrame3.frameContent, 7));
        };
        this.videoTrackCallback = socketCallBack32;
        SocketCallBack3 socketCallBack33 = new SocketCallBack3() { // from class: com.gdu.sdk.vision.GDUVision.2
            @Override // com.gdu.gdusocket.SocketCallBack3
            public void callBack(byte b2, GduFrame3 gduFrame32) {
                byte[] bArr;
                byte b3;
                if (b2 != 0 || gduFrame32 == null || (bArr = gduFrame32.frameContent) == null || (b3 = bArr[0]) == 33) {
                    return;
                }
                if (b3 == 32) {
                    ourGDUVision.this.getMultiDetectTargetNew(bArr);
                    return;
                }
                short s = 0;
                short s2 = 0;
                short s3 = 0;
                short s4 = 0;
                if (bArr.length >= 9) {
                    s = (short) (((bArr[1] & 255) * 1920) / 255.0d);
                    s2 = (short) (((bArr[2] & 255) * 1080) / 255.0d);
                    s3 = (short) (((bArr[3] & 255) * 1920) / 255.0d);
                    s4 = (short) (((bArr[4] & 255) * 1080) / 255.0d);
                }
                ourGDUVision.this.getTrackResult(b3, s, s2, s3, s4);
            }
        };
        this.multipleTargetTrackCb = socketCallBack33;
        this.mGduCommunication3 = ourGduSocketManager.getInstance().getCommunication();
        this.mTargetModeList = new ArrayList();
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_TARGET_DETECT, socketCallBack3);
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_TARGET_DETECT_NEW, socketCallBack3);
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_FOUR_LIGHT_TARGET_DETECT, socketCallBack3);
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_VISION_TRACK, socketCallBack32);
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_VISION_TRACK_NEW, socketCallBack32);
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_MULTIPLE_TARGET_TRACK, socketCallBack33);
        this.mGduCommunication3.addCycleACKCB(GduSocketConfig3.CYCLE_ACK_MULTIPLE_TARGET_TRACK_NEW, socketCallBack33);

    }

    private void getDetectTargetNew(byte[] var1) {
        int var2;
        if ((var2 = var1.length) >= 3) {
            int var10000 = var2 - 2;
            int var10002 = var2 - 2;
            byte var10001 = var1[var2 - 2];
            byte var12 = var1[var10002];
            byte var10003 = var1[var2 - 1];
            byte var11 = (byte)(var12 >> 4 & 255);
            byte var3 = (byte)(var10001 & 15);
            Log.i("getDetectTargetNew()", "totalNum = " + var11 + ",currentNum = " + var3);
            int var4 = var10000 / 10;
            CopyOnWriteArrayList var5 = new CopyOnWriteArrayList();

            for(int var6 = 0; var6 < var4; ++var6) {
                byte[] var7 = new byte[10];

                for(int var8 = 0; var8 < 10; ++var8) {
                    var7[var8] = var1[var6 * 10 + var8];
                }

                var5.add(this.parseTargetModeNew(var7));
            }

            List var10;
            if ((var10 = this.mTargetModeList) != null) {
                if (var3 == 0) {
                    var10.clear();
                }

                this.mTargetModeList.addAll(var5);
                OnTargetDetectListener var9;
                if (var3 == var11 - 1 && (var9 = this.targetDetectListener) != null) {
                    var9.onTargetDetecting(var5);
                }
            }

        }
    }

    private TargetMode parseTargetModeNew(byte[] var1) {
        TargetMode var10000 = new TargetMode();
        byte[] var10007 = var1;
        byte[] var10008 = var1;
        byte[] var10009 = var1;
        byte[] var10010 = var1;

        short var7 = ByteUtilsLowBefore.byte2short(var1, 0);
        short var8 = ByteUtilsLowBefore.byte2short(var1, 2);
        short var2 = ByteUtilsLowBefore.byte2short(var10010, 4);
        short var3 = ByteUtilsLowBefore.byte2short(var10009, 6);
        byte var4 = var10008[8];
        byte var5 = (byte)(var10007[9] & 1 & 255);
        var10000.setHeight(var3);
        var10000.setWidth(var2);
        var10000.setLeftX(var7);
        var10000.setLeftY(var8);
        var10000.setTargetConfidence((short)var4);
        var10000.setFlawType(var5);
        return var10000;
    }

    private void getMultiDetectTargetNew(byte[] var1) {
        int var2;
        if ((var2 = var1.length) >= 3) {
            byte var10001 = var1[var2 - 3];
            byte var11 = (byte)(var1[var2 - 3] >> 4 & 255);
            byte var3 = (byte)(var10001 & 15);
            int var4 = (var2 - 4) / 10;
            CopyOnWriteArrayList var5 = new CopyOnWriteArrayList();

            for(int var6 = 0; var6 < var4; ++var6) {
                byte[] var7 = new byte[10];

                for(int var8 = 0; var8 < 10; ++var8) {
                    var7[var8] = var1[var6 * 10 + 1 + var8];
                }

                var5.add(this.parseMultiTargetModeNew(var7));
            }

            List var9;
            if ((var9 = this.mTargetModeList) != null) {
                if (var3 == 0) {
                    var9.clear();
                }

                this.mTargetModeList.addAll(var5);
                OnTargetTrackListener var10;
                if (var3 == var11 - 1 && (var10 = this.targetTrackListener) != null) {
                    var10.onTargetDetecting(this.mTargetModeList);
                }
            }

        }
    }

    private TargetMode parseMultiTargetModeNew(byte[] var1) {
        TargetMode var10000 = new TargetMode();
        byte[] var10011 = var1;
        byte[] var10012 = var1;
        byte[] var10013 = var1;
        byte[] var10014 = var1;
        byte[] var10015 = var1;
        byte[] var10016 = var1;
        byte[] var10017 = var1;
        short var10 = (short)((int)((double)((var1[0] & 255) * 1920) / 255.0));
        short var11 = (short)((int)((double)((var1[1] & 255) * 1080) / 255.0));
        short var2 = (short)((int)((double)((var10017[2] & 255) * 1920) / 255.0));
        short var3 = (short)((int)((double)((var10016[3] & 255) * 1080) / 255.0));
        byte var4 = var10015[4];
        byte var5 = var10014[5];
        short var6 = ByteUtilsLowBefore.byte2short(var10013, 6);
        short var7 = (short)((int)((double)((var10012[8] & 255) * 1920) / 255.0));
        short var8 = (short)((int)((double)((var10011[9] & 255) * 1080) / 255.0));
        var10000.setHeight(var3);
        var10000.setWidth(var2);
        var10000.setLeftX(var10);
        var10000.setLeftY(var11);
        var10000.setTargetCenterPointX(var7);
        var10000.setTargetCenterPointY(var8);
        var10000.setTargetType((short)var4);
        var10000.setTargetConfidence((short)var5);
        var10000.setId(var6);
        var10000.setType(1);
        return var10000;
    }

    private void getTrackResult(byte var1, short var2, short var3, short var4, short var5) {
        OnTargetTrackListener var6;
        switch (var1) {
            case 0:
            case 3:
            case 4:
            case 9:
                if (this.targetTrackListener != null) {
                    ourGDUVision var10000 = this;
                    TargetMode var7;
                    TargetMode var10001 = var7 = new TargetMode();
                    var7.setHeight(var5);
                    var7.setWidth(var4);
                    var7.setLeftX(var2);
                    var10001.setLeftY(var3);
                    var10000.targetTrackListener.onTargetTracking(var7);
                }
                break;
            case 1:
                if ((var6 = this.targetTrackListener) != null) {
                    var6.onTargetTrackStop();
                }
                break;
            case 2:
                RectUtil.videoPoint2ScreenArg(var2, var3, var4, var5);
            case 5:
            case 6:
            case 8:
            default:
                break;
            case 7:
                if ((var6 = this.targetTrackListener) != null) {
                    var6.onTargetTrackModelClose();
                }
        }

    }

    public void setOnTargetDetectListener(OnTargetDetectListener var1) {
        this.targetDetectListener = var1;
    }

    public void setOnTargetTrackListener(OnTargetTrackListener var1) {
        this.targetTrackListener = var1;
    }

    public void startTargetDetect(CommonCallbacks.CompletionCallback var1) {
        ourGduCommunication3 var10000 = this.mGduCommunication3;
        SocketCallBack3 var2 = (var1x, var2x) -> {
            if (var1 != null) {
                if (var1x == 0) {
                    var1.onResult((GDUError)null);
                } else {
                    var1.onResult(GDUError.COMMON_TIMEOUT);
                }
            }

        };
        var10000.targetDetect((byte)1, (short)0, (short)0, (short)0, (short)0, (byte)0, (byte)0, var2);
    }

    public void stopTargetDetect(CommonCallbacks.CompletionCallback var1) {
        ourGduCommunication3 var10000 = this.mGduCommunication3;
        SocketCallBack3 var2 = (var2x, var3) -> {
            if (var1 != null) {
                if (var2x == 0) {
                    var1.onResult((GDUError)null);
                    OnTargetDetectListener var4;
                    if ((var4 = this.targetDetectListener) != null) {
                        var4.onTargetDetectFinished();
                    }
                } else {
                    var1.onResult(GDUError.COMMON_TIMEOUT);
                }
            }

        };
        var10000.targetDetect((byte)2, (short)0, (short)0, (short)0, (short)0, (byte)0, (byte)0, var2);
    }

    public void startSmartTrack(CommonCallbacks.CompletionCallback var1) {
        ourGduCommunication3 var10000 = this.mGduCommunication3;
        SocketCallBack3 var2 = (var2x, var3) -> {
            if (var1 != null) {
                if (var2x == 0) {
                    var1.onResult((GDUError)null);
                    OnTargetTrackListener var4;
                    if ((var4 = this.targetTrackListener) != null) {
                        var4.onTargetTrackStart();
                    }
                } else {
                    var1.onResult(GDUError.COMMON_TIMEOUT);
                }
            }

        };
        var10000.videoTrackOrSurrondALG((byte)1, (byte)0, (short)0, (short)0, (short)0, (short)0, (short)0, (byte)0, var2);
    }

    public void detectTarget(byte var1, short var2, int var3, int var4, int var5, int var6, byte var7, CommonCallbacks.CompletionCallback var8) {
        Log.i("detectTarget", "detectTarget() targetType = " + var1 + ", targetId = " + var2 + ", pointX = " + var3 + "; pointY = " + var4 + "; pointX1 = " + var5 + "; pointY1 = " + var6);
        if (var1 == 0) {
            List var10000 = RectUtil.screenPoint2VideoArg(var3, var5, var4, var6);
            var3 = (Short)var10000.get(0);
            var4 = (Short)var10000.get(1);
            var5 = (Short)var10000.get(2);
            var6 = (Short)var10000.get(3);
        }

        ourGduCommunication3 var12 = this.mGduCommunication3;
        short var9 = (short)var3;
        short var10 = (short)var4;
        short var11 = (short)var5;
        short var13 = (short)var6;
        SocketCallBack3 var14 = (var1x, var2x) -> {
            if (var8 != null) {
                if (var1x == 0) {
                    var8.onResult((GDUError)null);
                } else {
                    var8.onResult(GDUError.COMMON_TIMEOUT);
                }
            }

        };
        var12.videoTrackOrSurrondALG((byte)3, var1, var2, var9, var10, var11, var13, var7, var14);
    }

    public void stopSmartTrack(CommonCallbacks.CompletionCallback var1) {
        ourGduCommunication3 var10000 = this.mGduCommunication3;
        SocketCallBack3 var2 = (var1x, var2x) -> {
            if (var1 != null) {
                if (var1x == 0) {
                    var1.onResult((GDUError)null);
                } else {
                    var1.onResult(GDUError.COMMON_TIMEOUT);
                }
            }

        };
        var10000.videoTrackOrSurrondALG((byte)4, (byte)0, (short)0, (short)0, (short)0, (short)0, (short)0, (byte)0, var2);
    }

    public void cancelSmartTrack(CommonCallbacks.CompletionCallback var1) {
        ourGduCommunication3 var10000 = this.mGduCommunication3;
        SocketCallBack3 var2 = (var1x, var2x) -> {
            if (var1 != null) {
                if (var1x == 0) {
                    var1.onResult((GDUError)null);
                } else {
                    var1.onResult(GDUError.COMMON_TIMEOUT);
                }
            }

        };
        var10000.videoTrackOrSurrondALG((byte)2, (byte)0, (short)0, (short)0, (short)0, (short)0, (short)0, (byte)0, var2);
    }
}

