package com.gdu.demo.ourgdu;

import android.text.TextUtils;
import android.util.Log;
import com.gdu.common.ConnType;
import com.gdu.common.GlobalVariable;
import com.gdu.drone.FocusMotion;
import com.gdu.drone.FocusType;
import com.gdu.drone.GimbalType;
import com.gdu.drone.SwitchType;
import com.gdu.drone.ZoomMotion;
import com.gdu.gdusocket.GduCommunication3;
import com.gdu.gdusocket.SocketCallBack;
import com.gdu.gdusocket.SocketCallBack3;
import com.gdu.gdusocket.ce.GduCESocket3;
import com.gdu.gdusocket.util.e;
import com.gdu.gimbal.RotationMode;
import com.gdu.socketmodel.GduFrame3;
import com.gdu.socketmodel.GduSocketConfig3;
import com.gdu.util.ByteUtils;
import com.gdu.util.ByteUtilsLowBefore;
import com.gdu.util.logs.RonLog;
import com.gdu.util.logs.RonLog2File_Send;
import com.gdu.util.logs.YhLog;
import java.util.Calendar;
import java.util.TimeZone;

public class ourGduCommunication3 {
    private ourGduCESocket3 a;
    private final byte b = 0;
    private final byte c = 1;
    private final byte d = 2;
    private e e;
    private String f;
    private int g;
    private String h;
    private int i;
    private byte j = 0;
    //private ourGduCommunication3 aa;
    public static /* synthetic */ class SyntheticClass_1 {

        /* renamed from: a  reason: collision with root package name */
        static final /* synthetic */ int[] f246a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[ZoomMotion.values().length];
            b = iArr;
            try {
                iArr[ZoomMotion.ADD_ZOOM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[ZoomMotion.SUB_ZOOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[ZoomMotion.STOP_ZOOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[FocusMotion.values().length];
            f246a = iArr2;
            try {
                iArr2[FocusMotion.ADD_FOCUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f246a[FocusMotion.SUB_FOCUS.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f246a[FocusMotion.STOP_FOCUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }


    /*public ourGduCommunication3(GduCESocket3 gduCESocket3) {
        this.a = gduCESocket3;
        if (GlobalVariable.isRCSEE) {
            this.f = GduSocketConfig3.RCSrcIpAdress;
            this.g = GduSocketConfig3.RC_DATA_SOCKET_SRC_PORT;
            this.h = GduSocketConfig3.RCSrcIpAdress;
            this.i = 7079;
        } else if (!GlobalVariable.isCustomRC) {
            this.f = "127.0.0.1";
            this.h = "127.0.0.1";
            this.g = GlobalVariable.UDP_SOCKET_PORT;
        } else {
            this.f = GduSocketConfig3.RCSrcIpAdress;
            this.g = 7079;
            this.h = GduSocketConfig3.RCSrcIpAdress;
            this.i = GduSocketConfig3.CUSTOM_RC_DATA_SOCKET_DST_PORT;
        }
    }*/


    public ourGduCommunication3(ourGduCESocket3 var1) {
        this.a = var1;
        if (GlobalVariable.isRCSEE) {
            this.f = "145.192.1.81";
            this.g = 7077;
            this.h = "145.192.1.81";
            this.i = 7079;
        } else if (GlobalVariable.isCustomRC) {
            this.f = "145.192.1.81";
            this.g = 7079;
            this.h = "145.192.1.81";
            this.i = 7080;
        } else {
            this.f = "127.0.0.1";
            this.h = "127.0.0.1";
            this.g = GlobalVariable.UDP_SOCKET_PORT;
        }

    }
    private void a(short var1, byte[] var2, SocketCallBack3 var3) {
        byte var4;
        if (var1 <= 255) {
            var4 = GlobalVariable.mGimbalCompId;
        } else {
            var4 = GlobalVariable.mCameraCompId;
        }

        this.a.sendMsg3(var3, var1, (byte)2, var4, (byte)1, (byte)0, var2);
    }

    public boolean createSocket() {
        try {
            return this.a.createSocket(this.f, this.h, this.g, this.i);
        } catch (Exception e) {
            e.printStackTrace();
            RonLog.LogD("test createSocket " + e.getMessage());
            return false;
        }

    }
    public void disconnSocket() {
        GduCESocket3 var1;
        if ((var1 = this.a) != null) {
            var1.closeConnSocket();
        }
    }

    public void usbBreak(boolean var1) {
        GduCESocket3 var2;
        if ((var2 = this.a) != null) {
            var2.usbBreak(var1);
        }

    }

    public void setSaveLog(boolean var1) {
        GduCESocket3 var2;
        if ((var2 = this.a) != null) {
            var2.setIsRecord(var1);
        }

    }

    public void getUnique(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)337, (byte)1, (byte)1, (byte)1, (byte)0, (byte[])null);
    }

    public void lockOrUnLockDrone(boolean var1, SocketCallBack var2) {
        RonLog.LogE(new String[]{"lockAndlock", "isUnlock" + var1});
    }

    public GduCESocket3 getGduSocket() {
        return this.a;
    }

    public void reSetInetAdd(String var1, int var2) {
        this.f = var1;
        this.g = var2;
        GduCESocket3 var3;
        if ((var3 = this.a) != null) {
            var3.setIpAndPort(var1, var2);
        }

    }

    public void matchRC(SocketCallBack var1) {
        this.a.sendMsg((SocketCallBack)null, (byte)-128, (byte)10, (byte[])null);
    }

    public void getDroneHome(SocketCallBack3 var1) {
    }

    public void oneKeyFly(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[4];
        System.arraycopy(ByteUtilsLowBefore.short2byte((short)500), 0, var2, 0, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte((short)100), 0, var2, 2, 2);
        var10000.a.sendMsg3(var1, (short)16, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void oneKeyFlyPrecise(short var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3 = new byte[4];
        System.arraycopy(ByteUtilsLowBefore.short2byte(var1), 0, var3, 0, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte((short)100), 0, var3, 2, 2);
        var10000.a.sendMsg3(var2, (short)16, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void switchPrecisFly(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 1;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)26, (byte)1, (byte)2, (byte)1, (byte)0, var3);
    }

    public void switchPrecisBack(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var6 = new byte[18];
        byte var3 = 0;
        if (var10000) {
            var6[var3] = 2;
        } else {
            var6[var3] = 0;
        }

        var3 = 2;
        var6[1] = 0;
        short var4;
        if ((var4 = (short)(GlobalVariable.backHeight * 10)) < 500 || var4 > '썐') {
            var4 = (short)GlobalVariable.height_drone;
        }

        byte[] var10001 = ByteUtilsLowBefore.short2byte(var4);
        var6[var3] = var10001[0];
        var3 = 4;
        var6[3] = var10001[1];
        if ((var4 = GlobalVariable.sBackSpeed) < 200 || var4 > 1500) {
            var4 = 500;
        }

        ourGduCommunication3 var7 = this;
        byte[] var5;
        byte[] var10002 = var5 = ByteUtilsLowBefore.short2byte(var4);
        var6[var3] = var5[0];
        var6[5] = var10002[1];
        var7.a.sendMsg3(var2, (short)28, (byte)1, (byte)2, (byte)1, (byte)0, var6);
    }

    public void oneKeyBack(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[2];
        System.arraycopy(ByteUtilsLowBefore.short2byte((short)1000), 0, var2, 0, 2);
        var10000.a.sendMsg3(var1, (short)48, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void lowBatteryBack(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 2;
        } else {
            var3[0] = 1;
        }

        this.a.sendMsg3(var2, (short)23, (byte)1, (byte)4, (byte)1, (byte)0, var3);
    }

    public void verticalLanding(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3 = ByteUtilsLowBefore.short2byte((short)100);
        byte[] var2;
        byte[] var10001 = var2 = new byte[3];
        var2[0] = var3[0];
        var10001[1] = var3[1];
        var10001[2] = 0;
        var10000.a.sendMsg3(var1, (short)32, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void controlDrone(short var1, short var2, short var3, short var4) {
        ByteUtilsLowBefore.short2byte(var1);
        ByteUtilsLowBefore.short2byte(var2);
        ByteUtilsLowBefore.short2byte(var3);
        ByteUtilsLowBefore.short2byte(var4);
    }

    public void controlSmartDrone() {
    }

    /** @deprecated */
    public void setDroneLimitSwitch(boolean var1, SocketCallBack var2) {
    }

    public void setDroneLimitHeight(boolean var1, short var2, SocketCallBack3 var3) {
        boolean var10000 = var1;
        byte[] var4 = new byte[3];
        if (var10000) {
            var4[0] = 1;
        } else {
            var4[0] = 0;
            var2 = 0;
        }

        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[1] = var10002[0];
        var4[2] = var10002[1];
        this.a.sendMsg3(var3, (short)208, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void setDroneLimitDistance(boolean var1, short var2, SocketCallBack3 var3) {
        boolean var10000 = var1;
        byte[] var4 = new byte[3];
        if (var10000) {
            var4[0] = 1;
        } else {
            var4[0] = 0;
            var2 = 0;
        }

        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[1] = var10002[0];
        var4[2] = var10002[1];
        this.a.sendMsg3(var3, (short)210, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void getDroneLimitArgs(SocketCallBack3 var1) {
    }

    public void getDroneLimitHeight(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)209, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void getDroneLimitDistance(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)211, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void setDroneBackHeight(short var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3 = ByteUtilsLowBefore.short2byte(var1);
        var10000.a.sendMsg3(var2, (short)51, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void getDroneBackInfo(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)50, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void sendCheckNorth(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        var10001[0] = var1;
        var10001[1] = 100;
        var10000.a.sendMsg3(var2, (short)192, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void oneKeyLockDrone(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[1])[0] = 1;
        var10000.a.sendMsg3(var1, (short)272, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void checkIMU(byte var1, byte var2, byte var3, SocketCallBack3 var4) {
        ourGduCommunication3 var10000 = this;
        byte[] var5;
        byte[] var10001 = var5 = new byte[5];
        var10001[0] = var1;
        var10001[1] = var2;
        var10001[2] = var3;
        byte[] var10002 = ByteUtilsLowBefore.short2byte((short)100);
        var5[3] = var10002[0];
        var10001[4] = var10002[1];
        var10000.a.sendMsg3(var4, (short)194, (byte)1, (byte)1, (byte)1, (byte)0, var5);
    }

    public void checkHandleIMU(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[3])[0] = var1;
        var10000.a.sendMsg3(var2, (short)195, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void checkGyroscope(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[1])[0] = 1;
        var10000.a.sendMsg3(var1, (short)197, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void beginOrPausePreCamera(boolean var1, SocketCallBack3 var2) {
        if (GlobalVariable.gimbalType != GimbalType.ByrdT_None_Zoom) {
            byte[] var5 = new byte[3];
            byte var3 = 0;
            byte var4;
            if (var1) {
                var4 = 2;
            } else {
                var4 = 1;
            }

            var5[var3] = var4;
            if (GlobalVariable.connType != ConnType.MGP03_RC_USB) {
                System.arraycopy(ByteUtilsLowBefore.short2byte((short)GlobalVariable.UDPSocketIMGPort), 0, var5, 1, 2);
            } else {
                System.arraycopy(ByteUtilsLowBefore.short2byte((short)7078), 0, var5, 1, 2);
            }

            RonLog.LogE(new String[]{"开启视频流：" + var1});
            if (GlobalVariable.gimbalType != GimbalType.ByrdT_None_Zoom) {
                this.a.sendMsg3((SocketCallBack3)null, (short)24, (byte)1, (byte)3, (byte)1, (byte)0, var5);
            }

        }
    }

    public void beginControlHolder(byte var1, byte var2, byte var3) {
        e var4;
        if ((var4 = this.e) == null || !((Thread)var4).isAlive()) {
            (this.e = new e(this.a)).a();
        }

        this.e.a(var1, var2, var3);
    }

    public void stopControlHolder() {
        e var1;
        if ((var1 = this.e) != null) {
            var1.b();
        }

    }

    public void setImgMode(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)775, var3, var2);
    }

    public void setInfraredVideoFps(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)258, var3, var1);
    }

    public void setLightingAlgorithm(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)260, var3, var1);
    }

    public void setAmplification(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)261, var3, var1);
    }

    public void setMirrorImage(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)285, var3, var1);
    }

    public void setColor(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)262, var3, var1);
    }

    public void setOverlay(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)263, var3, var1);
    }

    public void setTmsOverlay(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        Log.d("zhaijiang", "b7--------->" + var2);
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)263, var3, var1);
    }

    public void setTempSwitch(SocketCallBack3 var1, boolean var2) {
        boolean var10000 = var2;
        byte[] var5 = new byte[2];
        byte var3 = 0;
        byte var4 = (byte)(var10000 ? 0 : 1);
        var5[var3] = var4;
        this.a((short)264, var5, var1);
    }

    public void setElectricityEnlarge(SocketCallBack3 var1, byte var2) {
        if (var2 >= 0) {
            ourGduCommunication3 var10000 = this;
            byte[] var3;
            (var3 = new byte[2])[0] = var2;
            var10000.a((short)519, var3, var1);
        }
    }

    public void setPhotoParam(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)533, var3, var1);
    }

    public void setCameraDefault(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[2])[0] = 1;
        var10000.a((short)770, var2, var1);
    }

    public void setThermometryPointType(SocketCallBack3 var1, byte var2) {
    }

    public void setThermometryType(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)265, var3, var1);
    }

    public void setShutterAutoOnOff(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)281, var3, var1);
    }

    public void updateIrFirmware(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)286, var3, var1);
    }

    public void setLuminosity(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        var10001[0] = var2;
        var10001[1] = -18;
        var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)267, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void setContrast(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        var10001[0] = var2;
        var10001[1] = -18;
        var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)268, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void setPointPosition(SocketCallBack3 var1, short var2, short var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4 = ByteUtils.short2byte(var2);
        byte[] var10001 = ByteUtils.short2byte(var3);
        byte[] var10002 = var4;
        (var4 = new byte[6])[0] = 0;
        System.arraycopy(var10002, 0, var4, 1, 2);
        System.arraycopy(var10001, 0, var4, 3, 2);
        var10000.a((short)271, var4, var1);
    }

    public void setRectangle(SocketCallBack3 var1, short var2, short var3, short var4, short var5) {
        ourGduCommunication3 var10000 = this;
        byte[] var6 = ByteUtils.short2byte(var2);
        byte[] var7 = ByteUtils.short2byte(var3);
        byte[] var8 = ByteUtils.short2byte(var4);
        byte[] var10001 = ByteUtils.short2byte(var5);
        byte[] var10004 = var6;
        var6 = new byte[9];
        System.arraycopy(var10004, 0, var6, 0, 2);
        System.arraycopy(var7, 0, var6, 2, 2);
        System.arraycopy(var8, 0, var6, 4, 2);
        System.arraycopy(var10001, 0, var6, 6, 2);
        var10000.a((short)272, var6, var1);
    }

    public void setFocalDistance(SocketCallBack3 var1, byte var2, byte var3) {
        this.focus(FocusType.AUTO_FOCUS, FocusMotion.STOP_FOCUS, (SocketCallBack3)null);
    }

    public void setPicSaveType(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)276, var3, var1);
    }

    public void setVideoSaveType(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)277, var3, var1);
    }

    public void setShutterMakeup(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = 1;
        byte var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)278, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void setSceneMakeUp(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = 1;
        byte var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)279, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void getCloundInfo(SocketCallBack3 var1) {
    }

    public void getCameraInfo(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = 1;
        byte var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)256, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void setTakePhoto(SocketCallBack3 var1, byte var2, byte var3, byte var4) {
        ourGduCommunication3 var10000 = this;
        byte[] var5;
        byte[] var10001 = var5 = new byte[4];
        var10001[0] = var2;
        var10001[1] = var3;
        var10001[2] = var4;
        var10000.a((short)259, var5, var1);
    }

    public void laserRanging(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[2])[0] = 1;
        var10000.a((short)1024, var2, var1);
    }

    public void setEnhanceIntensity(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[3];
        var10001[0] = 1;
        var10001[1] = var1;
        var10000.a((short)270, var3, var2);
    }

    public void setEnhance(boolean var1, SocketCallBack var2) {
    }

    public void setDenoiseIntensity(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[3];
        var10001[0] = 1;
        var10001[1] = var1;
        var10000.a((short)269, var3, var2);
    }

    public void setDenoise(boolean var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte var10002;
        byte[] var10001 = var3 = new byte[3];
        if(var1){
            var10002=1;
        }
        else{
            var10002=0;
        }
        var3[0] =0;
        var1 = true;
        int intValue = var1 ? 1 : 0;
        var10001[i] = (byte)var10002;
        var10001[intValue] = (byte)var10002;
        var10000.a((short)269, var3, var2);
    }

    public void focus(FocusType var1, FocusMotion var2, SocketCallBack3 var3) {
        byte[] var5 = new byte[7];
        if (GlobalVariable.gimbalType != GimbalType.ByrdT_GTIR800 && GlobalVariable.gimbalType != GimbalType.ByrT_IR_1K) {
            var5[0] = 2;
        } else {
            var5[0] = 1;
        }

        if (var1 == FocusType.AUTO_FOCUS) {
            var5[1] = 0;
        } else if (var1 == FocusType.MANUAL_FOCUS) {
            int var4;
            if ((var4 = SyntheticClass_1.f246a[var2.ordinal()]) != 1) {
                if (var4 != 2) {
                    if (var4 == 3) {
                        var5[1] = 3;
                    }
                } else {
                    var5[1] = 2;
                }
            } else {
                var5[1] = 1;
            }
        }

        this.a((short)778, var5, (SocketCallBack3)null);
    }

    public void closeZoomCamera() {
    }

    public void zoneFocus(byte[] var1, SocketCallBack3 var2) {
        Log.d("zhaijiang", "zoneFocus");
        byte[] var3 = new byte[7];
        if (GlobalVariable.gimbalType != GimbalType.ByrdT_GTIR800 && GlobalVariable.gimbalType != GimbalType.ByrT_IR_1K) {
            var3[0] = 2;
        } else {
            var3[0] = 1;
        }

        var3[1] = 4;
        var3[2] = var1[0];
        var3[3] = var1[2];
        var3[4] = var1[1];
        var3[5] = var1[3];
        this.a((short)778, var3, (SocketCallBack3)null);
    }

    public void zoom(ZoomMotion var1, SocketCallBack3 var2) {
        byte[] var4 = new byte[3];
        if (GlobalVariable.gimbalType != GimbalType.ByrdT_GTIR800 && GlobalVariable.gimbalType != GimbalType.GIMBAL_IR_1KG) {
            var4[0] = 2;
        } else {
            var4[0] = 1;
        }

        int var3;
        if ((var3 = SyntheticClass_1.b[var1.ordinal()]) != 1) {
            if (var3 != 2) {
                if (var3 == 3) {
                    var4[1] = 2;
                }
            } else {
                var4[1] = 1;
            }
        } else {
            var4[1] = 0;
        }

        this.a((short)777, var4, (SocketCallBack3)null);
    }

    public void zoomNew(ZoomMotion var1, SocketCallBack3 var2) {
        byte[] var3 = new byte[3];
        if (GlobalVariable.gimbalType != GimbalType.ByrdT_GTIR800 && GlobalVariable.gimbalType != GimbalType.ByrT_IR_1K && GlobalVariable.gimbalType != GimbalType.Small_Double_Light) {
            var3[0] = 2;
        } else {
            var3[0] = 1;
        }

        int var4;
        if ((var4 = SyntheticClass_1.b[var1.ordinal()]) != 1) {
            if (var4 != 2) {
                if (var4 == 3) {
                    var3[1] = 2;
                }
            } else {
                var3[1] = 4;
            }
        } else {
            var3[1] = 3;
        }

        this.a((short)777, var3, var2);
    }

    public void switchCameraRecordModel(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var4 = new byte[2];
        if (var10000) {
            var4[0] = 0;
        } else {
            var4[0] = 1;
        }

        ourGduCommunication3 var5 = this;
        byte var3 = GlobalVariable.podCompId;
        var5.a.sendMsg3(var2, (short)776, (byte)2, var3, (byte)1, (byte)0, var4);
    }

    public void takePhoto(byte var1, byte var2, SocketCallBack3 var3) {
        RonLog.LogD(new String[]{"takePhoto() type = " + var1 + "; arg2 = " + var2});
        byte[] var4 = new byte[54];
        if (var1 == 0) {
            if (GlobalVariable.gimbalType == GimbalType.ByrdT_GTIR800) {
                var1 = 1;
            } else {
                var1 = 0;
            }
        }

        var4[0] = var1;
        var4[1] = var2;
        this.a((short)768, var4, var3);
    }

    public void recordVideo(byte var1, byte var2, SocketCallBack3 var3) {
        byte var10000 = var1;
        RonLog.LogD(new String[]{"recordVideo() type = " + var1 + "; scaleIndex = " + var2 + "; sendTime = " + System.currentTimeMillis()});
        byte[] var4;
        (var4 = new byte[54])[0] = 0;
        if (var10000 == 2) {
            var4[1] = 2;
        } else {
            var4[1] = 1;
        }

        this.a((short)769, var4, var3);
    }

    public void getCameraArgs(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = 1;
        byte var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)512, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void setCameraEV(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        RonLog.LogD(new String[]{"test setCameraEV valueIndex " + var1});
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)522, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setCameraWB(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[4])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)523, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setCameraISO(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)521, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setCameraWBGain(SocketCallBack3 var1, int var2, int var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[4];
        var4[0] = 10;
        var4[1] = (byte)var2;
        var10001[2] = (byte)var3;
        var10000.a((short)523, var4, var1);
    }

    public void setCameraES(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)535, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setRecordSize(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        (var4 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var3, (short)514, (byte)2, var1, (byte)1, (byte)0, var4);
    }

    public void setPreVideoSize(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[2];
        var10001[0] = var1;
        var10001[1] = var2;
        var10000.a((short)547, var4, var3);
    }

    public void setPhotoSize(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)515, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setOSDWatermarkSwitch(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[2];
        var10001[0] = var1;
        var10001[1] = var2;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var3, (short)536, (byte)2, var1, (byte)1, (byte)0, var4);
    }

    public void setPhotoSaveFormat(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)539, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setVideoStream(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)541, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void clearMedia(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[2])[0] = 3;
        var10000.a((short)771, var2, var1);
    }

    public void clearSDMedia(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var2;
        var10000.a((short)771, var3, var1);
    }

    public void addCycleACKCB(byte var1, SocketCallBack var2) {
    }

    public void addCycleACKCB(byte var1, SocketCallBack3 var2) {
        this.a.addCycleAck(var1, var2);
    }

    public void addCycleACKCB(short var1, SocketCallBack var2) {
    }

    public void addCycleACKCB(int var1, SocketCallBack3 var2) {
        this.a.addCycleAck(var1, var2);
    }

    public void removeCycleACKCB(byte var1) {
        this.a.removeCycleAck(var1);
    }

    public void removeCycleACKCB(short var1) {
        this.a.removeCycleAck(var1);
    }

    public void removeCycleACKCB(int var1) {
        this.a.removeCycleAck(var1);
    }

    public void videoTrackOrSurrondALG(byte var1, byte var2, short var3, short var4, short var5, short var6, short var7, byte var8, SocketCallBack3 var9) {
        byte[] var10;
        byte[] var10001 = var10 = new byte[14];
        byte[] var11 = ByteUtilsLowBefore.short2byte(var4);
        byte[] var12 = ByteUtilsLowBefore.short2byte(var5);
        byte[] var13 = ByteUtilsLowBefore.short2byte(var6);
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var7);
        var10[0] = var1;
        System.arraycopy(var11, 0, var10, 1, 2);
        System.arraycopy(var12, 0, var10, 3, 2);
        System.arraycopy(var13, 0, var10, 5, 2);
        System.arraycopy(var10002, 0, var10, 7, 2);
        var10001[9] = var2;
        if (var2 == 1) {
            System.arraycopy(ByteUtilsLowBefore.short2byte(var3), 0, var10, 10, 2);
        }

        var10[12] = var8;
        if (GlobalVariable.gimbalType == GimbalType.GIMBAL_PTL600 || GlobalVariable.gimbalType == GimbalType.GIMBAL_PDL_10X || GlobalVariable.gimbalType == GimbalType.Small_Double_Light) {
            this.a.sendMsg3(var9, (short)16, (byte)2, (byte)-16, (byte)1, (byte)0, var10);
        } else {
            this.a.sendMsg3(var9, (short)16, (byte)1, (byte)2, (byte)1, (byte)0, var10);
        }

    }

    public void obstacleALG(boolean var1, SwitchType var2, SocketCallBack3 var3) {
        boolean var10000 = var1;
        RonLog.LogE(new String[]{"避障算法是否开启:" + var1});
        byte[] var4 = new byte[1];
        if (var10000) {
            var4[0] = 0;
        } else {
            var4[0] = 1;
        }

        this.a.sendMsg3(var3, (short)19, (byte)1, (byte)2, (byte)1, (byte)0, var4);
    }

    public void switchObstacleStrategy(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        RonLog.LogE(new String[]{"避障策略是否开启:" + var1});
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 0;
        } else {
            var3[0] = 1;
        }

        this.a.sendMsg3(var2, (short)25, (byte)1, (byte)2, (byte)1, (byte)0, var3);
    }

    public void getObstacleSwitch(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)24, (byte)1, (byte)2, (byte)1, (byte)0, (byte[])null);
    }

    public void setGPSSurroundEnable(byte var1, boolean var2, double var3, double var5, byte var7, short var8, short var9, short var10, SocketCallBack3 var11) {
        boolean var10000 = var2;
        byte[] var12;
        byte[] var10003 = var12 = new byte[17];
        double var10004 = var3;
        byte[] var13 = ByteUtilsLowBefore.int2byte((int)(var5 * 1.0E7));
        byte[] var14 = ByteUtilsLowBefore.int2byte((int)(var10004 * 1.0E7));
        var12[0] = var1;
        System.arraycopy(var13, 0, var12, 1, 4);
        System.arraycopy(var14, 0, var12, 5, 4);
        var10003[9] = var7;
        System.arraycopy(ByteUtilsLowBefore.short2byte(var8), 0, var12, 10, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var10), 0, var12, 12, 2);
        var1 = 14;
        if (var10000) {
            var12[var1] = 0;
        } else {
            var12[var1] = 1;
        }

        System.arraycopy(ByteUtilsLowBefore.short2byte(var9), 0, var12, 15, 2);
        this.a.sendMsg3(var11, (short)64, (byte)1, (byte)29, (byte)1, (byte)0, var12);
    }

    public void setGpsSurroundHeading(byte var1, short var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        (var4 = new byte[3])[0] = var1;
        System.arraycopy(ByteUtilsLowBefore.short2byte(var2), 0, var4, 1, 2);
        var10000.a.sendMsg3(var3, (short)68, (byte)1, (byte)29, (byte)1, (byte)0, var4);
    }

    public void setGpsSurroundRadiusAndAngularVelocity(short var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[3];
        System.arraycopy(ByteUtilsLowBefore.short2byte(var1), 0, var4, 0, 2);
        var10001[2] = var2;
        var10000.a.sendMsg3(var3, (short)69, (byte)1, (byte)29, (byte)1, (byte)0, var4);
    }

    public void setGpsSurroundStartPoint(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)70, (byte)1, (byte)29, (byte)1, (byte)0, var3);
    }

    public void setGpsSurroundGimbalPitchAngular(byte var1, short var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[3];
        var4[0] = var1;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[1] = var10002[0];
        var10001[2] = var10002[1];
        var10000.a.sendMsg3(var3, (short)71, (byte)1, (byte)29, (byte)1, (byte)0, var4);
    }

    public void changeScalePhotosALG(SocketCallBack3 var1) {
    }

    public void getAllALGStatus(SocketCallBack var1) {
    }

    public void getFileTransmissionVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)13, (byte)1, (byte)17, (byte)1, (byte)0, (byte[])null);
    }

    public void getFCCoprocessorVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)19, (byte)1, (byte)19, (byte)1, (byte)0, (byte[])null);
    }

    public void getSetTimeVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)16, (byte)1, (byte)13, (byte)1, (byte)0, (byte[])null);
    }

    public void getTaskManagerVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)11, (byte)1, (byte)14, (byte)1, (byte)0, (byte[])null);
    }

    public void getImageTransmissionVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)13, (byte)1, (byte)9, (byte)1, (byte)0, (byte[])null);
    }

    public void getUpgradeCompVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)10, (byte)1, (byte)5, (byte)1, (byte)0, (byte[])null);
    }

    public void getACVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)22, (byte)1, (byte)4, (byte)1, (byte)0, (byte[])null);
    }

    public void getVisionVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)27, (byte)1, (byte)2, (byte)1, (byte)0, (byte[])null);
    }

    public void getRTKVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)13, (byte)1, (byte)10, (byte)1, (byte)0, (byte[])null);
    }

    public void getMCUVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)20, (byte)1, (byte)11, (byte)1, (byte)0, (byte[])null);
    }

    public void getOTAVersions(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[1])[0] = 0;
        var10000.a.sendMsg3(var1, (short)21, (byte)1, (byte)4, (byte)1, (byte)0, var2);
    }

    public void sendUpdateFM(int var1, byte var2, byte[] var3, SocketCallBack var4) {
        if (var3 != null && var3.length == 16) {
            byte[] var5;
            byte[] var10002 = var5 = new byte[18];
            var10002[0] = (byte)var1;
            var10002[1] = var2;
            var1 = var3.length;
            System.arraycopy(var3, 0, var5, 2, var1);
        } else {
            throw new IllegalArgumentException("length of MD5Code must be 16");
        }
    }

    public void sendUpdateFC(String var1, String var2, SocketCallBack3 var3) {
        byte[] var5;
        byte[] var10002 = var5 = new byte[128];
        var5[0] = 1;
        int var6;
        var5[1] = (byte)(var6 = var1.length());
        System.arraycopy(var1.getBytes(), 0, var5, 2, var6);
        var6 += 2;
        int var7;
        int var10003 = var7 = var2.length();
        var5[var6++] = 2;
        int var4 = var6 + 1;
        var10002[var6] = (byte)var10003;
        System.arraycopy(var2.getBytes(), 0, var5, var4, var7);
        this.a.sendMsg3(var3, (short)517, (byte)1, (byte)1, (byte)1, (byte)0, var5);
    }

    public void sendUpdateOTA(long var1, SocketCallBack var3) {
    }

    public void sendUpdateOTA(long var1, byte[] var3, SocketCallBack var4) {
        byte[] var5 = new byte[24];
        System.arraycopy(ByteUtilsLowBefore.long2byte(var1), 0, var5, 0, 8);
        System.arraycopy(var3, 0, var5, 8, 16);
    }

    public void getRCSet(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)10, (byte)1, (byte)12, (byte)1, (byte)0, var2);
    }

    public void getRCC1AndC2(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)13, (byte)1, (byte)12, (byte)1, (byte)0, var2);
    }

    public void setRCC1AndC2(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[4];
        var10001[0] = var1;
        var10001[1] = var2;
        var10000.a.sendMsg3(var3, (short)12, (byte)1, (byte)12, (byte)1, (byte)0, var4);
    }

    public void setRCControlHand(SocketCallBack3 var1, byte var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var2;
        var10000.a.sendMsg3(var1, (short)35, (byte)3, (byte)3, (byte)1, (byte)0, var3);
    }

    public void setRCControlGimbal(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[2])[0] = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)14, (byte)1, (byte)12, (byte)1, (byte)0, var2);
    }

    public void holderBack2Center(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[4];
        var10001[0] = 16;
        var10001[1] = -128;
        var10001[2] = -128;
        byte var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)16, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void setGimbalPitchAngle(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[5];
        byte[] var10003 = ByteUtilsLowBefore.short2byte((short)var1);
        var3[0] = var10003[0];
        var10001[1] = var10003[1];
        byte[] var10002 = ByteUtilsLowBefore.short2byte((short)1000);
        var3[2] = var10002[0];
        var10001[3] = var10002[1];
        var10000.a((short)18, var3, var2);
    }

    public void setGimbalAngle(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[5];
        byte[] var10004 = ByteUtilsLowBefore.short2byte((short)var1);
        var4[0] = var10004[0];
        var4[1] = var10004[1];
        byte[] var10002 = ByteUtilsLowBefore.short2byte((short)var2);
        var4[2] = var10002[0];
        var10001[3] = var10002[1];
        var10000.a((short)18, var4, var3);
    }

    public void setGimbalCourseAngle(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[5];
        byte[] var10004 = ByteUtilsLowBefore.short2byte((short)1000);
        var3[0] = var10004[0];
        var3[1] = var10004[1];
        byte[] var10002 = ByteUtilsLowBefore.short2byte((short)var1);
        var3[2] = var10002[0];
        var10001[3] = var10002[1];
        var10000.a((short)18, var3, var2);
    }

    public void holderRollChange(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)25, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void synTime1(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        Calendar var5;
        Calendar var10001 = var5 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int var2 = var10001.get(15);
        int var3 = var10001.get(16);
        byte[] var4;
        byte[] var7 = var4 = new byte[17];
        var4[0] = (byte)(var5.get(1) % 100);
        var4[1] = (byte)var5.get(2);
        var4[2] = (byte)var5.get(5);
        var4[3] = (byte)var5.get(11);
        var4[4] = (byte)var5.get(12);
        var4[5] = (byte)var5.get(13);
        byte[] var10007 = ByteUtilsLowBefore.short2byte((short)var5.get(14));
        var4[6] = var10007[0];
        var4[7] = var10007[1];
        var4[8] = 1;
        byte[] var6;
        byte[] var10004 = var6 = ByteUtilsLowBefore.int2byte(var2);
        var4[9] = var6[0];
        var4[10] = var6[1];
        var4[11] = var6[2];
        var4[12] = var10004[3];
        byte[] var10002 = var6 = ByteUtilsLowBefore.int2byte(var3);
        var4[13] = var6[0];
        var4[14] = var6[1];
        var4[15] = var6[2];
        var7[16] = var10002[3];
        var10000.a.sendMsg3(var1, (short)17, (byte)1, (byte)13, (byte)1, (byte)0, var4);
    }

    public void synTime(SocketCallBack3 socketCallBack3) {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(15);
        int i2 = calendar.get(16);
        System.arraycopy(ByteUtilsLowBefore.long2byte(calendar.getTimeInMillis()), 0, new byte[16], 0, 8);
        byte[] int2byte = ByteUtilsLowBefore.int2byte(i / 60000);
        byte[] int2byte2 = ByteUtilsLowBefore.int2byte(i2);
        byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0, int2byte[0], int2byte[1], int2byte[2], int2byte[3], int2byte2[0], int2byte2[1], int2byte2[2], int2byte2[3]};
        this.a.sendMsg3(socketCallBack3, (short) 17, (byte) 1, (byte) 13, (byte) 1, (byte) 0, bArr);
    }

    public void updateBattery(SocketCallBack3 var1, String var2) {
        ourGduCommunication3 var10000 = this;
        String var10001 = var2;
        int var3 = var2.length();
        byte[] var4;
        (var4 = new byte[128])[0] = (byte)var3;
        System.arraycopy(var10001.getBytes(), 0, var4, 1, var3);
        var10000.a.sendMsg3(var1, (short)13, (byte)1, (byte)19, (byte)1, (byte)-128, var4);
    }

    public void updateBattery(SocketCallBack3 var1, byte[] var2) {
    }

    public void updateHolder(SocketCallBack var1) {
    }

    public void updateHolder(SocketCallBack var1, byte[] var2) {
    }

    public void getBatterInfo(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)14, (byte)1, (byte)8, (byte)1, (byte)0, (byte[])null);
    }

    public void getBatterFactoryInfo(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)15, (byte)1, (byte)8, (byte)1, (byte)0, (byte[])null);
    }

    public void getImageTransmissionRelayVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)16, (byte)3, (byte)1, (byte)1, (byte)0, (byte[])null);
    }

    public void getGimbalInfo(SocketCallBack var1) {
    }

    public void setSpeedLimit(short var1, short var2, short var3, SocketCallBack3 var4) {
        byte[] var5 = new byte[6];
        System.arraycopy(ByteUtilsLowBefore.short2byte(var1), 0, var5, 0, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var2), 0, var5, 2, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var3), 0, var5, 4, 2);
    }

    public void videoLocation(boolean var1, SocketCallBack var2, short var3, short var4, short var5, short var6) {
        ourGduCommunication3 var10000 = this;
        short var10004 = var3;
        byte[] var7;
        byte[] var10005 = var7 = new byte[9];
        byte var10006;
        if(var1){
            var10006=1;
        }
        else{
            var10006=0;
        }
        int temp = 0;
        byte var8 = 1;
        var10005[temp] = (byte)(var10006 ^ 1);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var10004), 0, var7, var8, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var4), 0, var7, 3, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var5), 0, var7, 5, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var6), 0, var7, 7, 2);
        var10000.a.sendMsg(var2, (byte)68, (byte)4, var7);
    }

    public void breakBackModel(byte var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, (short)96, (byte)1, (byte)1, (byte)1, (byte)0, (byte[])null);
    }

    public void changeGauzeMode(int var1, SocketCallBack var2) {
        RonLog.LogE(new String[]{"开启网罩模式"});
    }

    public void setHolderAngle(short var1, short var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[5];
        byte[] var10004 = ByteUtilsLowBefore.short2byte(var1);
        var4[0] = var10004[0];
        var4[1] = var10004[1];
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[2] = var10002[0];
        var10001[3] = var10002[1];
        byte var5 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var3, (short)18, (byte)2, var5, (byte)1, (byte)0, var4);
    }

    public void setHolderAngleMinus(byte var1, SocketCallBack var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[5];
        var10001[0] = var1;
        var10001[1] = 0;
        byte[] var10002 = ByteUtilsLowBefore.short2byte((short)1000);
        var3[2] = var10002[0];
        var10001[3] = var10002[1];
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3((SocketCallBack3)null, (short)18, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void setImgSet(byte var1, byte var2, SocketCallBack var3) {
    }

    public void setCloundCheck(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)19, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void getGimbalSNAndVersion(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = 1;
        byte var2 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var1, (short)24, (byte)2, var2, (byte)1, (byte)0, var3);
    }

    public void getCameraVersion(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)781, var3, var2);
    }

    public void getGimbalVersion(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        RonLog.LogD(new String[]{"test getGimbalVersion gduCom"});
        byte[] var2;
        (var2 = new byte[2])[0] = 1;
        var10000.a((short)39, var2, var1);
    }

    public void getGimbalSNNew(SocketCallBack3 var1) {
        this.getGimbalSNNewById(GlobalVariable.mGimbalCompId, var1);
    }

    public void getGimbalSNNewById(byte var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, (short)51, (byte)2, var1, (byte)1, (byte)0, (byte[])null);
    }

    public void setThumbWheelSpeed(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var1 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)23, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void onDestory() {
        GduCESocket3 var1;
        if ((var1 = this.a) != null) {
            var1.closeConnSocket();
        }
    }

    public void setVideoCompressFormat(int var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = (byte)var1;
        var10000.a((short)537, var3, var2);
    }

    public void setRecordResolution(int var1, SocketCallBack3 var2) {
        switch (var1) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
        }
    }

    public void setPhotoResolution(int var1, SocketCallBack3 var2) {
        if (var1 != 0 && var1 != 1) {
            boolean var10000 = true;
        }

    }

    public void setAntiFlicker(int var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = (byte)var1;
        byte var4 = GlobalVariable.podCompId;
        var10000.a.sendMsg3(var2, (short)525, (byte)2, var4, (byte)1, (byte)0, var3);
    }

    public void set4KCAntiFlicker(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)525, var3, var2);
    }

    public void set4KCES(byte var1, SocketCallBack var2) {
    }

    public void setMeteringMode(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)546, var3, var2);
    }

    public void setAELock(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)542, var3, var2);
    }

    public void setAntiFog(int var1, SocketCallBack3 var2) {
        int var10000 = var1;
        byte[] var3 = new byte[2];
        switch (var10000) {
            case 0:
                var3[0] = 3;
                break;
            case 1:
                var3[0] = 4;
                break;
            case 2:
                var3[0] = 5;
                break;
            case 3:
                var3[0] = 2;
                break;
            case 4:
                var3[0] = 1;
        }

        this.a((short)531, var3, var2);
    }

    public void setExposure(int var1, SocketCallBack3 var2) {
        switch (var1) {
            case 0:
                if ((var1 = GlobalVariable.zoom30x_ExposureIndex) == 1) {
                    this.setBackLightCompensation(false, var2);
                } else if (var1 == 2) {
                    this.setAntiHighlight(false, (byte)0, var2);
                } else if (var1 == 3) {
                    this.setWideDynamic(false, (byte)0, var2);
                }
                break;
            case 1:
                this.setBackLightCompensation(true, var2);
                break;
            case 2:
                this.setAntiHighlight(true, (byte)50, var2);
                break;
            case 3:
                this.setWideDynamic(true, (byte)50, var2);
        }

    }

    public void setExposureIntensity(byte var1, SocketCallBack3 var2) {
        int var3;
        if ((var3 = GlobalVariable.zoom30x_ExposureIndex) == 2) {
            this.setAntiHighlight(true, var1, var2);
        } else if (var3 == 3) {
            this.setWideDynamic(true, var1, var2);
        }

    }

    public void setDisplayColor(int var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = (byte)var1;
        var10000.a((short)532, var3, var2);
    }

    public void setAntiShake(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var5 = new byte[2];
        byte var3 = 0;
        byte var4 = (byte)(var10000 ? 1 : 2);
        var5[var3] = var4;
        this.a((short)529, var5, var2);
    }

    public void setWideDynamic(boolean var1, byte var2, SocketCallBack3 var3) {
        boolean var10000 = var1;
        byte[] var6 = new byte[3];
        byte var4 = 0;
        byte var5 = (byte)(var10000 ? 1 : 2);
        var6[var4] = var5;
        var6[1] = var2;
        this.a((short)526, var6, var3);
    }

    public void setAntiHighlight(boolean var1, byte var2, SocketCallBack3 var3) {
        boolean var10000 = var1;
        byte[] var6 = new byte[3];
        byte var4 = 0;
        byte var5 = (byte)(var10000 ? 1 : 2);
        var6[var4] = var5;
        var6[1] = var2;
        this.a((short)527, var6, var3);
    }

    public void setBackLightCompensation(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var5 = new byte[3];
        byte var3 = 0;
        byte var4 = (byte)(var10000 ? 1 : 2);
        var5[var3] = var4;
        this.a((short)528, var5, var2);
    }

    public void sendIrTestCmd(SocketCallBack var1) {
    }

    public void pathPing(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)14, (byte)1, (byte)9, (byte)1, (byte)0, var3);
    }

    public void changControlModel(boolean var1) {
    }

    public void targetDetect(byte var1, short var2, short var3, short var4, short var5, byte var6, byte var7, SocketCallBack3 var8) {
        Log.i("targetDetect", "detectType = " + var1 + "; leftX = " + var2 + "; leftY = " + var3 + "; width = " + var4 + "; height = " + var5 + "; detectMethod = " + var6 + "; lightType = " + var7 + "; gimbalType = " + GlobalVariable.gimbalType);
        if (GlobalVariable.gimbalType == GimbalType.GIMBAL_FOUR_LIGHT) {
            this.fourLightTargetDetect((byte)var1, var2, var3, var4, var5, var7, var8);
        } else {
            int temp;
            byte[] var9;
            byte[] var10000 = var9 = new byte[13];
            var9[0] = (byte)var1;
            byte[] var10006 = ByteUtilsLowBefore.short2byte(var2);
            temp = var10006.length;
            System.arraycopy(var10006, 0, var9, 1, temp);
            byte[] var10005 = ByteUtilsLowBefore.short2byte(var3);
            temp = var10005.length;
            System.arraycopy(var10005, 0, var9, 3, temp);
            byte[] var10004 = ByteUtilsLowBefore.short2byte(var4);
            temp = var10004.length;
            System.arraycopy(var10004, 0, var9, 5, temp);
            byte[] var10003 = ByteUtilsLowBefore.short2byte(var5);
            temp = var10003.length;
            System.arraycopy(var10003, 0, var9, 7, temp);
            var10000[9] = 0;
            var10000[10] = var6;
            var10000[11] = var7;
            if (GlobalVariable.gimbalType == GimbalType.Small_Double_Light || GlobalVariable.gimbalType == GimbalType.GIMBAL_PTL600) {
                this.a.sendMsg3(var8, (short)21, (byte)2, (byte)-16, (byte)1, (byte)0, var9);
            } else {
                this.a.sendMsg3(var8, (short)21, (byte)1, (byte)2, (byte)1, (byte)0, var9);
            }

        }
    }

    public void fourLightTargetDetect(byte var1, short var2, short var3, short var4, short var5, byte var6, SocketCallBack3 var7) {
        ourGduCommunication3 var10000 = this;
        byte[] var8;
        byte[] var10001 = var8 = new byte[16];
        int temp;
        var8[0] = (byte)var1;
        byte[] var10011 = ByteUtilsLowBefore.short2byte(var2);
        temp= var10011.length;
        System.arraycopy(var10011, 0, var8, 1, temp);
        byte[] var10010 = ByteUtilsLowBefore.short2byte(var3);
        temp = var10010.length;
        System.arraycopy(var10010, 0, var8, 3, temp);
        byte[] var10009 = ByteUtilsLowBefore.short2byte(var4);
        temp = var10009.length;
        System.arraycopy(var10009, 0, var8, 5, temp);
        byte[] var10008 = ByteUtilsLowBefore.short2byte(var5);
        temp = var10008.length;
        System.arraycopy(var10008, 0, var8, 7, temp);
        var10001[9] = var6;
        var10001[10] = 1;
        var10001[11] = -2;
        var10001[12] = -1;
        var10001[13] = -1;
        var10001[14] = -1;
        var10001[15] = -1;
        var10000.a((short)792, var8, var7);
    }

    public void targetLocate(short var1, short var2, short var3, short var4, byte var5, SocketCallBack3 var6) {
        this.targetLocate(var1, var2, var3, var4, var5, (byte)0, (short)0, var6);
    }

    public void targetLocate(short var1, short var2, short var3, short var4, byte var5, byte var6, short var7, SocketCallBack3 var8) {
        ourGduCommunication3 var10000 = this;
        byte[] var9;
        byte[] var10001 = var9 = new byte[16];
        byte[] var10012 = ByteUtilsLowBefore.short2byte(var1);
        var9[0] = var10012[0];
        var9[1] = var10012[1];
        byte[] var10010 = ByteUtilsLowBefore.short2byte(var2);
        var9[2] = var10010[0];
        var9[3] = var10010[1];
        byte[] var10008 = ByteUtilsLowBefore.short2byte(var3);
        var9[4] = var10008[0];
        var9[5] = var10008[1];
        byte[] var10006 = ByteUtilsLowBefore.short2byte(var4);
        var9[6] = var10006[0];
        var9[7] = var10006[1];
        var9[8] = var5;
        var9[9] = var6;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var7);
        var9[10] = var10002[0];
        var10001[11] = var10002[1];
        var10000.a.sendMsg3(var8, (short)22, (byte)1, (byte)2, (byte)1, (byte)0, var9);
    }

    public void routePlanOperate(String var1, byte var2, SocketCallBack3 var3) {
        if (!TextUtils.isEmpty(var1) && var1.getBytes().length >= 14) {
            ourGduCommunication3 var10000 = this;
            byte[] var4;
            byte[] var10001 = var4 = new byte[17];
            System.arraycopy(var1.getBytes(), 0, var4, 0, 14);
            var10001[14] = var2;
            YhLog.LogE(new String[]{"op=" + var2});
            var10000.a.sendMsg3(var3, (short)10, (byte)1, (byte)14, (byte)1, (byte)0, var4);
        }
    }

    public void getIMGVersion_Z4B(SocketCallBack var1) {
    }

    public void getDataLinkVersion(SocketCallBack var1) {
    }

    public void getRCAVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)17, (byte)1, (byte)12, (byte)1, (byte)0, (byte[])null);
    }

    public void setAntiMagnetic(boolean var1, SocketCallBack3 var2) {
    }

    public void setRtkType(byte var1, byte var2, String var3, short var4, String var5, String var6, String var7, SocketCallBack3 var8) {
        byte var10000 = var1;
        byte[] var25 = new byte[116];
        if (var10000 == 1) {
            label144: {
                String var31 = var3;
                var25[0] = 1;
                var25[1] = var2;

                boolean var10001;
                boolean var32;
                try {
                    var32 = TextUtils.isEmpty(var31);
                } catch (Exception var24) {
                    var10001 = false;
                    break label144;
                }

                if (!var32) {
                    label143: {
                        String var10002;
                        int var10003;
                        byte[] var10004;
                        String var34;
                        try {
                            var31 = var7;
                            var34 = var6;
                            var10002 = var5;
                            var10003 = var4;
                            var10004 = var3.getBytes("UTF-8");
                        } catch (Exception var23) {
                            var10001 = false;
                            break label143;
                        }

                        byte[] var26 = var10004;

                        byte[] var37;
                        try {
                            var37 = ByteUtilsLowBefore.short2byte((short)var10003);
                        } catch (Exception var22) {
                            var10001 = false;
                            break label143;
                        }

                        byte[] var27 = var37;

                        byte[] var35;
                        try {
                            var35 = var10002.getBytes("UTF-8");
                        } catch (Exception var21) {
                            var10001 = false;
                            break label143;
                        }

                        byte[] var29 = var35;

                        byte[] var36;
                        try {
                            var36 = var34.getBytes("UTF-8");
                        } catch (Exception var20) {
                            var10001 = false;
                            break label143;
                        }

                        byte[] var30 = var36;

                        byte[] var33;
                        try {
                            var33 = var31.getBytes("UTF-8");
                        } catch (Exception var19) {
                            var10001 = false;
                            break label143;
                        }

                        var34 = var7;
                        var35 = var30;
                        String var38 = var6;
                        var10004 = var29;
                        byte[] var10005 = var29;
                        byte[] var10006 = var27;
                        byte[] var10007 = var27;
                        byte[] var10008 = var26;
                        byte[] var10009 = var26;
                        var2 = 0;
                        byte var28 = 2;

                        int var42;
                        try {
                            var42 = var10009.length;
                        } catch (Exception var18) {
                            var10001 = false;
                            break label143;
                        }
                        int temp=var42;

                        try {
                            System.arraycopy(var10008, var2, var25, var28, temp);
                        } catch (Exception var17) {
                            var10001 = false;
                            break label143;
                        }

                        var2 = 0;
                        var28 = 34;

                        int var41;
                        try {
                            var41 = var10007.length;
                        } catch (Exception var16) {
                            var10001 = false;
                            break label143;
                        }

                        temp = var41;

                        try {
                            System.arraycopy(var10006, var2, var25, var28, temp);
                        } catch (Exception var15) {
                            var10001 = false;
                            break label143;
                        }

                        var2 = 0;
                        var28 = 36;

                        int var39;
                        try {
                            var39 = var10005.length;
                        } catch (Exception var14) {
                            var10001 = false;
                            break label143;
                        }

                        temp = var39;

                        try {
                            System.arraycopy(var10004, var2, var25, var28, temp);
                        } catch (Exception var13) {
                            var10001 = false;
                            break label143;
                        }

                        var2 = 0;
                        var28 = 52;

                        try {
                            var10003 = var38.length();
                        } catch (Exception var12) {
                            var10001 = false;
                            break label143;
                        }

                        temp = var10003;

                        try {
                            System.arraycopy(var35, var2, var25, var28, temp);
                        } catch (Exception var11) {
                            var10001 = false;
                            break label143;
                        }

                        var2 = 0;
                        var28 = 84;

                        int var40;
                        try {
                            var40 = var34.length();
                        } catch (Exception var10) {
                            var10001 = false;
                            break label143;
                        }

                        temp = var40;

                        try {
                            System.arraycopy(var33, var2, var25, var28, temp);
                        } catch (Exception var9) {
                            var10001 = false;
                        }
                    }
                }
            }
        } else {
            var25[0] = 0;
            var25[1] = var2;
        }

        this.a.sendMsg3(var8, (short)16, (byte)1, (byte)24, (byte)1, (byte)0, var25);
    }

    public void switchRTK(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3;
        (var3 = new byte[1])[0] = 0;
        if (var10000) {
            var3[0] = 1;
        }

        this.a.sendMsg3(var2, (short)15, (byte)1, (byte)10, (byte)1, (byte)0, var3);
    }

    public void setUseRTKDataType(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)17, (byte)1, (byte)10, (byte)1, (byte)0, var3);
    }

    public void change482RtkStates(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[15];
        var10001[0] = var1;
        var10001[1] = 0;
        var10000.a.sendMsg3(var2, (short)25, (byte)1, (byte)19, (byte)1, (byte)0, var3);
    }

    public void sendRTCM2Drone(byte[] var1, SocketCallBack3 var2) {
        this.a.sendMsg3((SocketCallBack3)null, (short)14, (byte)1, (byte)10, (byte)1, (byte)0, var1);
    }

    public void setElevationType(byte var1, SocketCallBack3 var2) {
    }

    public void getElevationType(SocketCallBack3 var1) {
    }

    public void switchSimulateFight(boolean var1, SocketCallBack3 var2) {
    }

    public void setParam(boolean var1, short var2, double var3, double var5, byte var7, byte var8, int var9, SocketCallBack3 var10) {
        boolean var10000 = var1;
        byte[] var12 = new byte[17];
        if (var10000) {
            var12[0] = 1;
        } else {
            var12[0] = 0;
        }

        ourGduCommunication3 var15 = this;
        short var10003 = var2;
        byte[] var11 = ByteUtilsLowBefore.int2byte((int)(var3 * 1.0E7));
        byte[] var13 = ByteUtilsLowBefore.int2byte((int)(var5 * 1.0E7));
        byte[] var14 = ByteUtilsLowBefore.int2byte(var9 * 100);
        byte[] var16 = ByteUtilsLowBefore.short2byte(var10003);
        System.arraycopy(var11, 0, var12, 1, 4);
        System.arraycopy(var13, 0, var12, 5, 4);
        System.arraycopy(var14, 0, var12, 9, 4);
        System.arraycopy(var16, 0, var12, 13, 2);
        var12[15] = var7;
        var12[16] = var8;
        var15.a.sendMsg3(var10, (short)256, (byte)1, (byte)1, (byte)1, (byte)0, var12);
    }

    public void rcShield(boolean var1, SocketCallBack3 var2) {
        byte[] var4 = new byte[4];
        int var3;
        if (var1) {
            for(var3 = 0; var3 < 4; ++var3) {
                var4[var3] = 3;
            }
        } else {
            for(var3 = 0; var3 < 4; ++var3) {
                var4[var3] = 4;
            }
        }

        this.a.sendMsg3(var2, (short)449, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void getLaserLocation(SocketCallBack3 var1) {
        this.a((short)1028, new byte[2], var1);
    }

    public void targetCenterLocate(short var1, short var2, short var3, short var4, byte var5, byte var6, short var7, SocketCallBack3 var8) {
        ourGduCommunication3 var10000 = this;
        byte[] var9;
        byte[] var10001 = var9 = new byte[12];
        byte[] var10012 = ByteUtilsLowBefore.short2byte(var1);
        var9[0] = var10012[0];
        var9[1] = var10012[1];
        byte[] var10010 = ByteUtilsLowBefore.short2byte(var2);
        var9[2] = var10010[0];
        var9[3] = var10010[1];
        byte[] var10008 = ByteUtilsLowBefore.short2byte((short)((int)((double)var3 * 1.5)));
        var9[4] = var10008[0];
        var9[5] = var10008[1];
        byte[] var10006 = ByteUtilsLowBefore.short2byte((short)((int)((double)var4 * 1.5)));
        var9[6] = var10006[0];
        var9[7] = var10006[1];
        var9[8] = var5;
        var9[9] = var6;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var7);
        var9[10] = var10002[0];
        var10001[11] = var10002[1];
        var10000.a.sendMsg3(var8, (short)22, (byte)1, (byte)2, (byte)1, (byte)0, var9);
    }

    public void setParam(short var1, double var2, double var4, byte var6, int var7, int var8, int var9, SocketCallBack var10) {
        byte[] var11 = new byte[23];
        byte[] var12 = ByteUtilsLowBefore.short2byte(var1);
        byte[] var13 = ByteUtilsLowBefore.int2byte((int)(var2 * 1.0E7));
        byte[] var3 = ByteUtilsLowBefore.int2byte((int)(var4 * 1.0E7));
        byte[] var14 = ByteUtilsLowBefore.int2byte(var7 * 100);
        byte[] var5 = ByteUtilsLowBefore.int2byte(var8 * 100);
        byte[] var10000 = ByteUtilsLowBefore.int2byte(var9 * 100);
        System.arraycopy(var12, 0, var11, 0, 2);
        System.arraycopy(var13, 0, var11, 2, 4);
        System.arraycopy(var3, 0, var11, 6, 4);
        var11[10] = var6;
        System.arraycopy(var14, 0, var11, 11, 4);
        System.arraycopy(var5, 0, var11, 15, 4);
        System.arraycopy(var10000, 0, var11, 19, 4);
    }

    public void setTripodMode(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 1;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)279, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void barycenterCalibration(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2;
        (var2 = new byte[1])[0] = 1;
        var10000.a.sendMsg3(var1, (short)280, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void setBackSpeed(short var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3 = ByteUtilsLowBefore.short2byte((short)(var1 * 100));
        var10000.a.sendMsg3(var2, (short)52, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void sendAAC(byte[] var1, SocketCallBack var2) {
    }

    public void setOutOfControlAction(byte var1, SocketCallBack3 var2) {
        byte var3 = 0;
        if (var1 != 0) {
            if (var1 == 1 || var1 == 2) {
                var3 = 2;
            }
        } else {
            var3 = 0;
        }

        ourGduCommunication3 var10000 = this;
        byte[] var4;
        (var4 = new byte[5])[0] = var3;
        System.arraycopy(ByteUtilsLowBefore.short2byte((short)10), 0, var4, 1, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte((short)60), 0, var4, 3, 2);
        var10000.a.sendMsg3(var2, (short)222, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void getOutOfControlAction(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)223, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void switchSmartBattery(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 1;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)233, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void getSmartBattery(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)234, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void setImageTransmissionInfo(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        var10001[0] = 1;
        var10001[1] = var1;
        var10000.a.sendMsg3(var2, (short)10, (byte)1, (byte)9, (byte)1, (byte)0, var3);
    }

    public void getImageTransmissionInfo(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)17, (byte)1, (byte)9, (byte)1, (byte)0, var3);
    }

    public void setCodedFormat(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)783, var3, var2);
    }

    public void setOutputStream(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a((short)784, var3, var2);
    }

    public void setNetworking(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[2];
        var10001[0] = var1;
        var10001[1] = var2;
        var10000.a.sendMsg3(var3, (short)17, (byte)3, (byte)1, (byte)1, (byte)0, var4);
    }

    public void setHomePoint(double var1, double var3, byte var5, SocketCallBack3 var6) {
        ourGduCommunication3 var10000 = this;
        byte[] var7;
        byte[] var10001 = var7 = new byte[9];
        double var10002 = var1;
        byte[] var8 = ByteUtilsLowBefore.int2byte((int)(var3 * 1.0E7));
        byte[] var9 = ByteUtilsLowBefore.int2byte((int)(var10002 * 1.0E7));
        System.arraycopy(var8, 0, var7, 0, 4);
        System.arraycopy(var9, 0, var7, 4, 4);
        var10001[8] = var5;
        var10000.a.sendMsg3(var6, (short)49, (byte)1, (byte)1, (byte)1, (byte)0, var7);
    }

    public void switchFillInLight(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)16, (byte)1, (byte)4, (byte)1, (byte)0, var3);
    }

    public void setEXP(byte var1, byte var2, byte var3, SocketCallBack3 var4) {
        ourGduCommunication3 var10000 = this;
        byte[] var5;
        byte[] var10001 = var5 = new byte[3];
        var10001[0] = var1;
        var10001[1] = var2;
        var10001[2] = var3;
        var10000.a.sendMsg3(var4, (short)226, (byte)1, (byte)1, (byte)1, (byte)0, var5);
    }

    public void getEXP(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)227, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void setSensitivity(byte var1, byte var2, byte var3, byte var4, SocketCallBack3 var5) {
        ourGduCommunication3 var10000 = this;
        byte[] var6;
        byte[] var10001 = var6 = new byte[4];
        var10001[0] = var1;
        var10001[1] = var2;
        var10001[2] = var3;
        var10001[3] = var4;
        var10000.a.sendMsg3(var5, (short)236, (byte)1, (byte)1, (byte)1, (byte)0, var6);
    }

    public void getSensitivity(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)237, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void controlLedLight(byte var1, byte var2, byte var3, byte var4, int var5, SocketCallBack3 var6) {
        ourGduCommunication3 var10000 = this;
        byte[] var7;
        byte[] var10001 = var7 = new byte[10];
        var7[0] = var1;
        var7[1] = var2;
        var7[2] = var3;
        var7[3] = var4;
        System.arraycopy(ByteUtilsLowBefore.int2byte(var5), 0, var7, 4, 4);
        var10001[8] = 0;
        var10001[9] = 0;
        var10000.a.sendMsg3(var6, (short)16, (byte)1, (byte)19, (byte)1, (byte)0, var7);
    }

    public void sendPatchInfo(String var1, String var2, String var3, byte var4, SocketCallBack3 var5) {
        ourGduCommunication3 var10000 = this;
        byte[] var6;
        byte[] var10001 = var6 = new byte[193];
        byte[] var10004 = var1.getBytes();
        int var7 = var1.getBytes().length;
        System.arraycopy(var10004, 0, var6, 0, var7);
        byte[] var10003 = var2.getBytes();
        var7 = var2.getBytes().length;
        System.arraycopy(var10003, 0, var6, 128, var7);
        byte[] var10002 = var3.getBytes();
        var7 = var3.getBytes().length;
        System.arraycopy(var10002, 0, var6, 160, var7);
        var10001[192] = var4;
        var10000.a.sendMsg3(var5, (short)11, (byte)1, (byte)5, (byte)1, (byte)0, var6);
    }

    public void switchPowerSilenceModel(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 1;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)17, (byte)1, (byte)8, (byte)1, (byte)0, var3);
    }

    public void switchFCBootLoader(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)513, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void switchFCCopBootLoader(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[2];
        var10001[0] = var1;
        var10001[1] = var2;
        var10000.a.sendMsg3(var3, (short)23, (byte)1, (byte)19, (byte)1, (byte)0, var4);
    }

    public void requestFCUpgrade(byte[] var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, (short)24, (byte)1, (byte)19, (byte)1, (byte)0, var1);
    }

    public void switchNightLight(boolean var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        byte var10002;
        if(var1){
            var10002=1;
        }
        else{
            var10002=0;
        }
        int temp = 0;
        var10001[temp] = (byte)var10002;
        var10000.a.sendMsg3(var2, (short)22, (byte)1, (byte)19, (byte)1, (byte)0, var3);
    }

    public void nonFlyZoneManager(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        var10001[0] = var1;
        var10001[1] = -1;
        var10000.a.sendMsg3(var2, (short)162, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void uploadVirtualFence(byte[] var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, (short)144, (byte)1, (byte)1, (byte)1, (byte)0, var1);
    }

    public void closeAndClearElectronicFence(SocketCallBack3 var1) {
        GduCESocket3 var10000 = this.a;
        byte[] var2 = new byte[0];
        var10000.sendMsg3(var1, (short)146, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void uploadNoFlyStatus(boolean var1, int var2, double var3, double var5, SocketCallBack3 var7) {
        boolean var10000 = var1;
        byte[] var10 = new byte[10];
        byte var8 = 0;
        byte var9 = (byte)(var10000 ? 1 : 2);
        var10[var8] = var9;
        var10[1] = (byte)var2;
        System.arraycopy(ByteUtilsLowBefore.long2byte((long)(var3 * 1.0E7)), 0, var10, 2, 4);
        System.arraycopy(ByteUtilsLowBefore.long2byte((long)(var5 * 1.0E7)), 0, var10, 6, 4);
        this.a.sendMsg3(var7, (short)163, (byte)1, (byte)1, (byte)1, (byte)0, var10);
    }

    public void setTTSReportContent(byte[] var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, (short)1024, (byte)2, (byte)29, (byte)1, (byte)0, var1);
    }

    public void setTTSReportState(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1025, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void setTTSReportCycleMode(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1026, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void setTTSAngle(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1027, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void setTTSVolume(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1028, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void getTTSAudioList(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1029, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void playAudio(String var1, SocketCallBack3 var2) {
        if (!TextUtils.isEmpty(var1)) {
            ourGduCommunication3 var10000;
            boolean var10001;
            byte[] var6;
            try {
                var10000 = this;
                var6 = var1.getBytes("UTF-8");
            } catch (Exception var4) {
                var10001 = false;
                return;
            }

            byte[] var5 = var6;

            try {
                var10000.a.sendMsg3(var2, (short)1030, (byte)2, (byte)29, (byte)1, (byte)0, var5);
            } catch (Exception var3) {
                var10001 = false;
            }

        }
    }

    public void changeAudioPlayState(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1031, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void changeAudioPlayMode(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)1032, (byte)2, (byte)29, (byte)1, (byte)0, var3);
    }

    public void setAirlinkType(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)17, (byte)1, (byte)25, (byte)1, (byte)0, var3);
    }

    public void getPTZArmVersion(byte var1, SocketCallBack3 var2) {
        this.getPTZArmVersion(GlobalVariable.podCompId, var1, var2);
    }

    public void getPTZArmVersion(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        (var4 = new byte[2])[0] = var2;
        var10000.a.sendMsg3(var3, (short)42, (byte)2, var1, (byte)1, (byte)0, var4);
    }

    public void requestConnectFtp(SocketCallBack3 var1, byte[] var2) {
        this.a.sendMsg3(var1, (short)0, (byte)1, (byte)17, (byte)1, (byte)0, var2);
    }

    public void requestFTPUpload(SocketCallBack3 var1, byte[] var2) {
        this.a.sendMsg3(var1, (short)7, (byte)1, (byte)17, (byte)1, (byte)0, var2);
    }

    public void uploadFTP(SocketCallBack3 var1, byte[] var2) {
        this.a.sendMsg3(var1, (short)8, (byte)1, (byte)17, (byte)1, (byte)0, var2);
    }

    public void uploadFTPComplete(SocketCallBack3 var1, byte[] var2) {
        this.a.sendMsg3(var1, (short)9, (byte)1, (byte)17, (byte)1, (byte)0, var2);
    }

    public void disconnectFTP(SocketCallBack3 var1, byte[] var2) {
        this.a.sendMsg3(var1, (short)1, (byte)1, (byte)17, (byte)1, (byte)0, var2);
    }

    public void get5GFirmwareVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)16, (byte)1, (byte)25, (byte)1, (byte)0, (byte[])null);
    }

    public void switchLandingProtect(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)33, (byte)1, (byte)2, (byte)1, (byte)0, var3);
    }

    public void getLandingProtectState(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)35, (byte)1, (byte)2, (byte)1, (byte)0, (byte[])null);
    }

    public void sendRockerInfo(SocketCallBack3 var1, byte[] var2) {
        this.a.sendMsg3(var1, (short)4, (byte)1, (byte)12, (byte)1, (byte)3, var2);
    }

    public void connectNPS(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[14];
        var10001[0] = 4;
        var10001[1] = 1;
        var10001[2] = 0;
        var10001[3] = 0;
        var10001[4] = 0;
        var10001[5] = 0;
        var10001[6] = 2;
        var10001[7] = 0;
        var10001[8] = 0;
        byte[] var2;
        byte[] var10003 = var2 = ByteUtilsLowBefore.int2byte(6);
        var3[9] = var2[0];
        var3[10] = var2[1];
        var3[11] = var2[2];
        var10001[12] = var10003[3];
        var10001[13] = 0;
        var10000.a.sendMsg3(var1, (short)10, (byte)1, (byte)0, (byte)0, (byte)0, var3);
    }

    public void change4GVideoStream(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)18, (byte)1, (byte)25, (byte)1, (byte)0, var3);
    }

    public void setImageType(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)19, (byte)1, (byte)25, (byte)1, (byte)0, var3);
    }

    public void set4GServiceIp(byte[] var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, (short)20, (byte)1, (byte)25, (byte)1, (byte)0, var1);
    }

    public void subscribeCycleCmd(byte var1, byte var2, byte var3, byte var4, short var5, SocketCallBack3 var6) {
        ourGduCommunication3 var10000 = this;
        byte[] var7;
        byte[] var10002 = var7 = new byte[6];
        var10002[0] = var1;
        var10002[1] = var2;
        var10002[2] = var3;
        var10002[3] = var4;
        System.arraycopy(ByteUtilsLowBefore.short2byte(var5), 0, var7, 4, 2);
        var10000.a.sendMsg3(var6, (short)12, (byte)1, (byte)0, (byte)1, (byte)0, var7);
    }

    public void switchGoHomeObstacle(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)36, (byte)1, (byte)2, (byte)1, (byte)0, var3);
    }

    public void getGoHomeObstacleSwitch(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)37, (byte)1, (byte)2, (byte)1, (byte)0, (byte[])null);
    }

    public void getObstacleFwVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)38, (byte)1, (byte)2, (byte)1, (byte)0, (byte[])null);
    }

    public void setStreamPercent(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[10];
        var10001[0] = 1;
        var10001[1] = var1;
        var10001[2] = (byte)GlobalVariable.gimbalType.getKey();
        var10001[3] = (byte)(100 - var1);
        var10000.a.sendMsg3(var2, (short)18, (byte)1, (byte)9, (byte)1, (byte)0, var3);
    }
    public void smartBack(boolean var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        if(var1){
            (var3 = new byte[1])[0] = 1;
        }
        else{
            (var3 = new byte[1])[0] = 0;
        }
        var10000.a.sendMsg3(var2, (short)53, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void getObstacleDirectionDistance(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)34, (byte)1, (byte)2, (byte)1, (byte)0, (byte[])null);
    }

    public void setObstacleDirectionDistance(SocketCallBack3 var1, byte var2, short var3, short var4, byte var5, short var6, short var7, byte var8, short var9, short var10) {
        ourGduCommunication3 var10000 = this;
        byte[] var11 = new byte[15];
        byte[] var12 = ByteUtilsLowBefore.short2byte(var3);
        byte[] var13 = ByteUtilsLowBefore.short2byte(var4);
        byte[] var14 = ByteUtilsLowBefore.short2byte(var6);
        byte[] var15 = ByteUtilsLowBefore.short2byte(var7);
        byte[] var16 = ByteUtilsLowBefore.short2byte(var9);
        byte[] var10001 = ByteUtilsLowBefore.short2byte(var10);
        var11[0] = var2;
        System.arraycopy(var12, 0, var11, 1, 2);
        System.arraycopy(var13, 0, var11, 3, 2);
        var11[5] = var5;
        System.arraycopy(var14, 0, var11, 6, 2);
        System.arraycopy(var15, 0, var11, 8, 2);
        var11[10] = var8;
        System.arraycopy(var16, 0, var11, 11, 2);
        System.arraycopy(var10001, 0, var11, 13, 2);
        var10000.a.sendMsg3(var1, (short)32, (byte)1, (byte)2, (byte)1, (byte)0, var11);
    }

    public void sendSetBatteryWaring(SocketCallBack3 var1, byte var2, byte var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[2];
        var10001[0] = var2;
        var10001[1] = var3;
        var10000.a.sendMsg3(var1, (short)224, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void sendGetBatteryWaring(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)225, (byte)1, (byte)1, (byte)1, (byte)0, (byte[])null);
    }

    public void setSDKHorizontalSpeedEnable(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 2;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)112, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void setSDKHorizontalSpeed(short var1, short var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[6];
        var4[0] = 1;
        var4[1] = 0;
        byte[] var10004 = ByteUtilsLowBefore.short2byte(var1);
        var4[2] = var10004[0];
        var4[3] = var10004[1];
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[4] = var10002[0];
        var10001[5] = var10002[1];
        var10000.a.sendMsg3(var3, (short)113, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void setSDKVerticalSpeedEnable(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 2;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)114, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void setSDKVerticalSpeed(short var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[3];
        var3[0] = 1;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var1);
        var3[1] = var10002[0];
        var10001[2] = var10002[1];
        var10000.a.sendMsg3(var2, (short)115, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void setSDKYawEnable(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 2;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)116, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void setSDKYawAngularVelocity(short var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[3];
        var3[0] = 1;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var1);
        var3[1] = var10002[0];
        var10001[2] = var10002[1];
        var10000.a.sendMsg3(var2, (short)117, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void setSDKYawAngular(RotationMode var1, short var2, SocketCallBack3 var3) {
        byte[] var4 = new byte[3];
        if (var1 == RotationMode.ABSOLUTE_ANGLE) {
            var4[0] = 0;
        } else if (var1 == RotationMode.RELATIVE_ANGLE) {
            var4[0] = 1;
        }

        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[1] = var10002[0];
        var4[2] = var10002[1];
        this.a.sendMsg3(var3, (short)118, (byte)1, (byte)1, (byte)1, (byte)0, var4);
    }

    public void setSDKHorizonEnable(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 1;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)119, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void setSDKHorizonPosition(byte var1, byte var2, short var3, short var4, int var5, int var6, int var7, SocketCallBack3 var8) {
        ourGduCommunication3 var10000 = this;
        byte[] var9;
        byte[] var10001 = var9 = new byte[18];
        var9[0] = var1;
        var9[1] = var2;
        byte[] var10010 = ByteUtilsLowBefore.short2byte(var3);
        var9[2] = var10010[0];
        var9[3] = var10010[1];
        byte[] var10008 = ByteUtilsLowBefore.short2byte(var4);
        var9[4] = var10008[0];
        var9[5] = var10008[1];
        byte[] var10;
        byte[] var10006 = var10 = ByteUtilsLowBefore.int2byte(var5);
        var9[6] = var10[0];
        var9[7] = var10[1];
        var9[8] = var10[2];
        var9[9] = var10006[3];
        byte[] var10004 = var10 = ByteUtilsLowBefore.int2byte(var6);
        var9[10] = var10[0];
        var9[11] = var10[1];
        var9[12] = var10[2];
        var9[13] = var10004[3];
        byte[] var10002 = var10 = ByteUtilsLowBefore.int2byte(var7);
        var9[14] = var10[0];
        var9[15] = var10[1];
        var9[16] = var10[2];
        var10001[17] = var10002[3];
        var10000.a.sendMsg3(var8, (short)120, (byte)1, (byte)1, (byte)1, (byte)0, var9);
    }

    public void setPointFly(double var1, double var3, int var5, int var6, int var7, int var8, byte var9, SocketCallBack3 var10) {
        this.setPointFly(var1, var3, var5, var6, var7, var8, var9, (byte)2, var10);
    }

    public void setPointFly(double var1, double var3, int var5, int var6, int var7, int var8, byte var9, byte var10, SocketCallBack3 var11) {
        ourGduCommunication3 var10000 = this;
        byte[] var12;
        byte[] var10001 = var12 = new byte[25];
        byte[] var13 = ByteUtilsLowBefore.int2byte((int)(var1 * 1.0E7));
        byte[] var2 = ByteUtilsLowBefore.int2byte((int)(var3 * 1.0E7));
        byte[] var14 = ByteUtilsLowBefore.int2byte(var5);
        byte[] var4 = ByteUtilsLowBefore.short2byte((short)var6);
        byte[] var15 = ByteUtilsLowBefore.short2byte((short)var7);
        byte[] var10004 = ByteUtilsLowBefore.short2byte((short)var8);
        System.arraycopy(var13, 0, var12, 0, 4);
        System.arraycopy(var2, 0, var12, 4, 4);
        System.arraycopy(var14, 0, var12, 9, 4);
        System.arraycopy(var4, 0, var12, 13, 2);
        System.arraycopy(var15, 0, var12, 15, 2);
        System.arraycopy(var10004, 0, var12, 17, 2);
        var10001[8] = var10;
        var10001[19] = var9;
        var10001[22] = 0;
        var10000.a.sendMsg3(var11, (short)80, (byte)1, (byte)1, (byte)1, (byte)0, var12);
    }

    public void exitPointFly(SocketCallBack3 var1) {
        ourGduCommunication3 var10000 = this;
        byte[] var2 = new byte[1];
        var10000.a.sendMsg3(var1, (short)96, (byte)1, (byte)1, (byte)1, (byte)0, var2);
    }

    public void setGPSTrackEnable(boolean var1, double var2, double var4, float var6, byte var7, byte var8, SocketCallBack3 var9) {
        boolean var10000 = var1;
        byte[] var11 = new byte[18];
        byte var10 = 0;
        if (var10000) {
            var11[var10] = 1;
        } else {
            var11[var10] = 2;
        }

        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var4 * 1.0E7)), 0, var11, 1, 4);
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var2 * 1.0E7)), 0, var11, 5, 4);
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var6 * 100.0F)), 0, var11, 9, 4);
        var11[13] = var7;
        var11[14] = var8;
        this.a.sendMsg3(var9, (short)13, (byte)1, (byte)29, (byte)1, (byte)0, var11);
    }

    public void updateGPSTarget(double var1, double var3, SocketCallBack3 var5) {
        ourGduCommunication3 var10000 = this;
        byte[] var6 = new byte[10];
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var3 * 1.0E7)), 0, var6, 0, 4);
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var1 * 1.0E7)), 0, var6, 4, 4);
        var10000.a.sendMsg3(var5, (short)14, (byte)1, (byte)29, (byte)1, (byte)0, var6);
    }

    public void setHighPrecisionGPSTrackEnable(boolean var1, double var2, double var4, byte var6, byte var7, float var8, float var9, byte var10, float var11, SocketCallBack3 var12) {
        boolean var10000 = var1;
        byte[] var14 = new byte[28];
        byte var13 = 0;
        if (var10000) {
            var14[var13] = 1;
        } else {
            var14[var13] = 2;
        }

        var14[1] = var7;
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var8 * 100.0F)), 0, var14, 2, 4);
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var9 * 100.0F)), 0, var14, 6, 4);
        var14[10] = var10;
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var11 * 100.0F)), 0, var14, 11, 4);
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var4 * 1.0E7)), 0, var14, 15, 4);
        System.arraycopy(ByteUtilsLowBefore.int2byte((int)(var2 * 1.0E7)), 0, var14, 19, 4);
        var14[23] = var6;
        this.a.sendMsg3(var12, (short)15, (byte)1, (byte)29, (byte)1, (byte)0, var14);
    }

    public void startHighPrecisionGPSTrack(double var1, double var3, byte var5, byte var6, float var7, float var8, byte var9, float var10, SocketCallBack3 var11) {
        RonLog2File_Send.getSingle().saveData("startHighPrecisionGPSTrack x = " + var7 + ", y = " + var8);
        this.setHighPrecisionGPSTrackEnable(true, var1, var3, var5, var6, var7, var8, var9, var10, var11);
    }

    public void stopHighPrecisionGPSTrack(SocketCallBack3 var1) {
        this.setHighPrecisionGPSTrackEnable(false, 0.0, 0.0, (byte)0, (byte)0, 0.0F, 0.0F, (byte)0, 0.0F, var1);
    }

    public void getHighPrecisionGPSTrackEnable(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)17, (byte)1, (byte)29, (byte)1, (byte)0, (byte[])null);
    }

    public void setGPSTrackHeading(byte var1, short var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[3];
        var4[0] = var1;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[1] = var10002[0];
        var10001[2] = var10002[1];
        var10000.a.sendMsg3(var3, (short)20, (byte)1, (byte)29, (byte)1, (byte)0, var4);
    }

    public void setGPSTrackGimbalPitch(byte var1, short var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[3];
        var4[0] = var1;
        byte[] var10002 = ByteUtilsLowBefore.short2byte(var2);
        var4[1] = var10002[0];
        var10001[2] = var10002[1];
        var10000.a.sendMsg3(var3, (short)22, (byte)1, (byte)29, (byte)1, (byte)0, var4);
    }

    public void setSmartReturnToHomeEnabled(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[1];
        if (var10000) {
            var3[0] = 1;
        } else {
            var3[0] = 0;
        }

        this.a.sendMsg3(var2, (short)57, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void getSmartReturnToHomeEnabled(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)58, (byte)1, (byte)1, (byte)1, (byte)0, (byte[])null);
    }

    public void setRCControlRodValue(short var1, short var2, short var3, short var4, SocketCallBack3 var5) {
        byte[] var6 = new byte[13];
        System.arraycopy(ByteUtilsLowBefore.short2byte(var1), 0, var6, 0, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var2), 0, var6, 2, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var3), 0, var6, 4, 2);
        System.arraycopy(ByteUtilsLowBefore.short2byte(var4), 0, var6, 6, 2);
        if (++this.j < -5) {
            this.j = 0;
        }

        var6[12] = this.j;
        this.a.sendMsg3(var5, (short)448, (byte)1, (byte)1, (byte)1, (byte)0, var6);
    }

    public void zoomCustomSizeRatio(short var1, SocketCallBack3 var2) {
        if (var1 >= 0) {
            ourGduCommunication3 var10000 = this;
            byte[] var3 = new byte[3];
            System.arraycopy(ByteUtilsLowBefore.short2byte(var1), 0, var3, 0, 2);
            var10000.a((short)553, var3, var2);
        }
    }

    public void zoomNew(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = new byte[3];
        var10001[0] = var1;
        var10001[1] = var2;
        var10000.a((short)777, var4, var3);
    }

    public void setRCCalibrationState(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[1])[0] = var1;
        var10000.a.sendMsg3(var2, (short)448, (byte)1, (byte)1, (byte)1, (byte)0, var3);
    }

    public void switchDownloadModel(byte var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        ourGduCommunication3 var10000 = this;
        byte[] var6;
        (var6 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var6);
    }

    public void refreshMediaFileList(short var1, byte var2, byte var3, SocketCallBack3 var4) {
        ourGduCommunication3 var10000 = this;
        byte[] var5 = new byte[1];
        var10000.a.sendMsg3(var4, var1, var2, var3, (byte)1, (byte)0, var5);
    }

    public void deleteFile(byte[] var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        this.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var1);
    }

    public void videoPlayStart(byte[] var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        this.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var1);
    }

    public void videoPlayStop(byte[] var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        this.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var1);
    }

    public void videoPlayPause(byte[] var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        this.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var1);
    }

    public void videoPlaySeek(byte[] var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        this.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var1);
    }

    public void videoPlayState(byte[] var1, short var2, byte var3, byte var4, SocketCallBack3 var5) {
        this.a.sendMsg3(var5, var2, var3, var4, (byte)1, (byte)0, var1);
    }

    public void stopStream(boolean var1, SocketCallBack3 var2) {
        boolean var10000 = var1;
        byte[] var3 = new byte[3];
        if (var10000) {
            var3[0] = 2;
        } else {
            var3[0] = 1;
        }

        this.a.sendMsg3(var2, (short)24, (byte)1, (byte)3, (byte)1, (byte)0, var3);
    }

    public void downloadFile(GduFrame3 var1, SocketCallBack3 var2) {
        this.a.sendMsg3(var2, var1);
    }

    public void getFlightVersion(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)20, (byte)1, (byte)4, (byte)1, (byte)0, (byte[])null);
    }

    public void getPTZArmSystemVersion(byte var1, SocketCallBack3 var2) {
        this.getPTZArmSystemVersion(GlobalVariable.podCompId, var1, var2);
    }

    public void getPTZArmSystemVersion(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        (var4 = new byte[2])[0] = var2;
        var10000.a.sendMsg3(var3, (short)43, (byte)2, var1, (byte)1, (byte)0, var4);
    }

    public void getGimbalVersionById(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = 1;
        var10000.a.sendMsg3(var2, (short)39, (byte)2, var1, (byte)1, (byte)0, var3);
    }

    public void switchAdapterRingBootLoader(byte var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        (var3 = new byte[2])[0] = var1;
        var10000.a.sendMsg3(var2, (short)32, (byte)2, (byte)-14, (byte)1, (byte)0, var3);
    }

    public void switchGimbalBootLoader(boolean var1, byte var2, SocketCallBack3 var3) {
        boolean var10000 = var1;
        byte[] var4 = new byte[2];
        if (var10000) {
            var4[0] = 1;
        } else {
            var4[0] = 0;
        }

        this.a.sendMsg3(var3, (short)32, (byte)2, var2, (byte)1, (byte)0, var4);
    }

    public void upgradeAdapterRingVersion(String var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        String var10001 = var1;
        byte var3;
        byte[] var4;
        byte[] var10002 = var4 = new byte[(var3 = (byte)var1.length()) + 4];
        var10002[0] = 1;
        var10002[1] = var3;
        System.arraycopy(var10001.getBytes(), 0, var4, 2, var3);
        var10000.a.sendMsg3(var2, (short)33, (byte)2, (byte)-14, (byte)1, (byte)0, var4);
    }

    public void updateGimbalById(SocketCallBack3 var1, byte var2, String var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        byte[] var10001 = var4 = var3.getBytes();
        byte[] var6;
        byte[] var10003 = var6 = new byte[var10001.length + 2];
        var6[0] = 1;
        var10003[1] = (byte)var4.length;
        int var5 = var10001.length;
        System.arraycopy(var10001, 0, var6, 2, var5);
        var10000.a.sendMsg3(var1, (short)33, (byte)2, var2, (byte)1, (byte)0, var6);
    }

    public void psdkCameraCmd(byte var1, byte var2, byte var3, SocketCallBack3 var4) {
        ourGduCommunication3 var10000 = this;
        byte[] var5;
        byte[] var10001 = var5 = new byte[3];
        var10001[0] = var1;
        var10001[1] = var2;
        var10001[2] = var3;
        GduCESocket3 var6 = var10000.a;
        var1 = GlobalVariable.sPSDKCompId;
        var6.sendMsg3(var4, (short)26, (byte)5, var1, (byte)1, (byte)0, var5);
    }

    public void psdkGetCustomWidgetCmd(byte[] var1, SocketCallBack3 var2) {
        GduCESocket3 var10000 = this.a;
        byte var3 = GlobalVariable.sPSDKCompId;
        var10000.sendMsg3(var2, (short)106, (byte)5, var3, (byte)1, (byte)0, var1);
    }

    public void exactBack(boolean var1, short var2, short var3, short var4, SocketCallBack3 var5) {
        boolean var10000 = var1;
        byte[] var6 = new byte[10];
        if (var10000) {
            var6[0] = 1;
            byte[] var10002 = ByteUtilsLowBefore.short2byte((short)var2);
            int temp;
            temp = var10002.length;
            System.arraycopy(var10002, 0, var6, 1, temp);
            byte[] var10001 = ByteUtilsLowBefore.short2byte(var3);
            temp = var10001.length;
            System.arraycopy(var10001, 0, var6, 3, temp);
            byte[] var7 = ByteUtilsLowBefore.short2byte(var4);
            temp = var7.length;
            System.arraycopy(var7, 0, var6, 5, temp);
        } else {
            var6[0] = 2;
        }

        this.a.sendMsg3(var5, (short)608, (byte)1, (byte)1, (byte)1, (byte)0, var6);
    }

    public void laserRangingContinue(boolean var1, SocketCallBack3 var2) {
        ourGduCommunication3 var10000 = this;
        byte[] var3;
        byte[] var10001 = var3 = new byte[2];
        byte var10002 ;
        if(var1){
            var10002=1;
        }
        else{
            var10002=0;
        }
        int i = 0;
        var10001[i] = (byte)var10002;
        var10000.a((short)1030, var3, var2);
    }

    public void getRemoteID(SocketCallBack3 var1) {
        this.a.sendMsg3(var1, (short)16, (byte)1, (byte)40, (byte)1, (byte)0, (byte[])null);
    }

    public void startSend(byte var1, byte var2, String var3, long var4, byte[] var6, byte var7, SocketCallBack3 var8) {
        byte[] var10;
        byte[] var10001 = var10 = new byte[57];
        var10001[0] = var1;
        var10001[1] = var2;
        if (var6 != null && var6.length > 0) {
            System.arraycopy(var6, 0, var10, 40, 16);
        }

        if (var2 == 1) {
            var10[56] = var7;
        }

        if (var1 == 2) {
            System.arraycopy(ByteUtilsLowBefore.int2byte((int)var4), 0, var10, 32, 4);
        }

        GduCESocket3 var10000 = this.a;
        byte var9 = GlobalVariable.sPSDKCompId;
        var10000.sendMsg3(var8, (short)258, (byte)5, var9, (byte)1, (byte)0, var10);
    }

    public void sendData(int var1, byte[] var2, byte var3, SocketCallBack3 var4) {
        ourGduCommunication3 var10000 = this;
        byte[] var5;
        byte[] var10003 = var5 = new byte[var2.length + 7];
        System.arraycopy(ByteUtilsLowBefore.int2byte(var1), 0, var5, 0, 4);
        var5[4] = var3;
        byte[] var10004 = ByteUtilsLowBefore.short2byte((short)var2.length);
        var5[5] = var10004[0];
        var10003[6] = var10004[1];
        var1 = var2.length;
        System.arraycopy(var2, 0, var5, 7, var1);
        GduCESocket3 var7 = var10000.a;
        byte var6 = GlobalVariable.sPSDKCompId;
        var7.sendMsg3NoRetry(var4, (short)259, (byte)5, var6, (byte)1, (byte)0, var5);
    }

    public void setPsdkMegaphonePlay(byte var1, byte var2, SocketCallBack3 var3) {
        ourGduCommunication3 var10000 = this;
        byte[] var4;
        (var4 = new byte[9])[0] = var1;
        System.arraycopy(ByteUtilsLowBefore.int2byte(var2), 0, var4, 1, 4);
        GduCESocket3 var5 = var10000.a;
        var1 = GlobalVariable.sPSDKCompId;
        var5.sendMsg3(var3, (short)257, (byte)5, var1, (byte)1, (byte)0, var4);
    }

    public void sendCustomMsg(byte[] var1, SocketCallBack3 var2) {
        GduCESocket3 var10000 = this.a;
        byte var3 = GlobalVariable.sPSDKCompId;
        var10000.sendMsg3(var2, (short)12, (byte)5, var3, (byte)1, (byte)0, var1);
    }
}
