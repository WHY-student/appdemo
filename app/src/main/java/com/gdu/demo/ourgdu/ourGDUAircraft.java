//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gdu.demo.ourgdu;

import android.content.Context;
import android.text.TextUtils;
import com.gdu.common.ConnStateEnum;
import com.gdu.common.GlobalVariable;
import com.gdu.common.error.GDUError;
import com.gdu.drone.BinocularInfo;
import com.gdu.drone.GimbalType;
import com.gdu.drone.PlanType;
import com.gdu.gdusocket.GduSocketManager;
import com.gdu.gdusocket.SocketCallBack3;
import com.gdu.sdk.airlink.GDUAirLink;
import com.gdu.sdk.base.BaseComponent;
import com.gdu.sdk.base.BaseProduct;
import com.gdu.sdk.base.GDUDiagnostics;
import com.gdu.sdk.base.BaseProduct.Model;
import com.gdu.sdk.base.GDUDiagnostics.GDUDiagnosticsType;
import com.gdu.sdk.battery.GDUBattery;
import com.gdu.sdk.camera.GDUCamera;
import com.gdu.sdk.camera.c;
import com.gdu.sdk.camera.e;
import com.gdu.sdk.camera.i;
import com.gdu.sdk.camera.j;
import com.gdu.sdk.camera.o;
import com.gdu.sdk.custommsg.GDUCustomMsg;
import com.gdu.sdk.flightcontroller.GDUFlightController;
import com.gdu.sdk.gimbal.GDUGimbal;
import com.gdu.sdk.gimbal.b;
import com.gdu.sdk.gimbal.d;
import com.gdu.sdk.gimbal.f;
import com.gdu.sdk.gimbal.g;
import com.gdu.sdk.gimbal.h;
import com.gdu.sdk.gimbal.k;
import com.gdu.sdk.lte.GDULTE;
import com.gdu.sdk.noflyzone.GDUNoFlyZone;
import com.gdu.sdk.psdk.Megaphone;
import com.gdu.sdk.radar.GDURadar;
import com.gdu.sdk.remotecontroller.GDURemoteController;
import com.gdu.sdk.util.CommonCallbacks;
import com.gdu.sdk.util.a;
import com.gdu.sdk.vision.GDUVision;
import com.gdu.socketmodel.GduFrame3;
import com.gdu.util.ByteUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ourGDUAircraft extends BaseProduct {
    private Context c;
    private GDUFlightController d;
    private GDUGimbal e;
    private GDUCamera f;
    private GDUBattery g;
    private GDURadar h;
    private GDURemoteController i;
    private GDUAirLink j;
    private GDUVision k;
    private GDULTE l;
    private GDUCustomMsg m;
    private GDUNoFlyZone n;
    private Megaphone o;
    private GDUDiagnostics.DiagnosticsInformationCallback p;
    private Timer q;

    public ourGDUAircraft() {
        this.c();
        this.n = new GDUNoFlyZone();
    }

    static /* synthetic */ class synthetic {

        /* renamed from: a  reason: collision with root package name */
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[PlanType.values().length];
            b = iArr;
            try {
                iArr[PlanType.S200.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[PlanType.S220.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[PlanType.S220ProS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[PlanType.MGP12.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[GimbalType.values().length];
            a = iArr2;
            try {
                iArr2[GimbalType.ByrT_6k.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[GimbalType.GIMBAL_8KC.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[GimbalType.ByrT_IR_1K.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[GimbalType.GIMBAL_FOUR_LIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[GimbalType.Small_Double_Light.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[GimbalType.GIMBAL_PDL_S200.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[GimbalType.GIMBAL_PDL_S220.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[GimbalType.GIMBAL_MICRO_FOUR_LIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[GimbalType.GIMBAL_PDL_300C.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                a[GimbalType.ByrdT_30X_Zoom.ordinal()] = 10;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                a[GimbalType.GIMBAL_PDL_S220PRO_FOUR_LIGHT.ordinal()] = 11;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                a[GimbalType.GIMBAL_PDL_S220PRO_SX_FOUR_LIGHT.ordinal()] = 12;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }


    private void c() {
        Timer var1;
        Timer var10000 = var1 = new Timer();
        this.q = var1;
        TimerTask var2;
        var2 = new TimerTask() {
            public void run() {
                List var1;
                if ((var1 = ourGDUAircraft.this.b()) != null && var1.size() > 0 && ourGDUAircraft.this.p != null) {
                    ourGDUAircraft.this.p.onUpdate(var1);
                }

            }
        };
        var10000.schedule(var2, 1000L, 1000L);
    }

    private GDUNoFlyZone a() {
        if (this.n == null) {
            GDUNoFlyZone var1;
            var1 = new GDUNoFlyZone();
            this.n = var1;
        }

        return this.n;
    }

    private List<GDUDiagnostics> b() {
        if (GlobalVariable.connStateEnum != ConnStateEnum.Conn_Sucess) {
            return null;
        } else {
            ArrayList var1;
            var1 = new ArrayList();
            GDUDiagnostics var10001;
            byte var2;
            if (GlobalVariable.imuAbnormal != 0 || GlobalVariable.sIMUNotCalibration != 0 || GlobalVariable.sIMURestartLabel == 1) {
                if ((var2 = GlobalVariable.imuAbnormal) != 1) {
                    if (var2 == 2) {
                        var10001 = new GDUDiagnostics();
                        var10001.setType(GDUDiagnosticsType.h);
                        var10001.setReason("IMU数据异常");
                        var1.add(var10001);
                    }
                } else {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.h);
                    var10001.setReason("IMU通信异常");
                    var1.add(var10001);
                }

                if (GlobalVariable.sIMUNotCalibration != 0) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.h);
                    var10001.setReason("IMU未校准");
                    var1.add(var10001);
                }

                if (GlobalVariable.sIMURestartLabel == 1) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.h);
                    var10001.setReason("IMU未重启");
                    var1.add(var10001);
                }

                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.h);
                var10001.setReason("陀螺仪零偏");
                var1.add(var10001);
            }

            if ((var2 = GlobalVariable.gpsAbnormal) == 1) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.h);
                var10001.setReason("GPS通信异常");
                var1.add(var10001);
            } else if (var2 == 2) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.h);
                var10001.setReason("GPS数据异常");
                var1.add(var10001);
            }

            if ((var2 = GlobalVariable.glassAbnormal) != 0) {
                if (var2 == 1) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.h);
                    var10001.setReason("气压计通信异常");
                    var1.add(var10001);
                } else if (var2 == 2) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.h);
                    var10001.setReason("气压计数据异常");
                    var1.add(var10001);
                }
            }

            var1.addAll(com.gdu.sdk.util.a.g(this.c));
            var1.addAll(this.d());
            if (GlobalVariable.gimbalType != GimbalType.ByrdT_None_Zoom) {
                byte var10000 = (byte)ByteUtils.getBits(GlobalVariable.HolderIsErr, 0, 4);
                var2 = (byte)ByteUtils.getBits(GlobalVariable.HolderIsErr, 4, 4);
                if (var10000 == 1) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.d);
                    var10001.setReason("相机连接异常");
                    var1.add(var10001);
                }

                if (var2 == 1) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.d);
                    var10001.setReason("云台堵转");
                    var1.add(var10001);
                } else if (var2 == 2) {
                    var10001 = new GDUDiagnostics();
                    var10001.setType(GDUDiagnosticsType.d);
                    var10001.setReason("云台自检异常");
                    var1.add(var10001);
                }
            }

            var1.addAll(com.gdu.sdk.util.a.d(this.c));
            return var1;
        }
    }

    private List<GDUDiagnostics> d() {
        ArrayList var1;
        var1 = new ArrayList();
        GDUDiagnostics var10001;
        byte var2;
        if ((var2 = GlobalVariable.eyesAbnormal) == 1) {
            var10001 = new GDUDiagnostics();
            var10001.setType(GDUDiagnosticsType.f);
            var10001.setReason("双目通信异常");
            var1.add(var10001);
        } else if (var2 == 2) {
            var10001 = new GDUDiagnostics();
            var10001.setType(GDUDiagnosticsType.f);
            var10001.setReason("双目数据异常");
            var1.add(var10001);
        }

        var1.addAll(this.e());
        BinocularInfo var3;
        if ((var3 = GlobalVariable.sBinocularInfo) == null || !var3.hasAbnormal()) {
            return var1;
        } else {
            byte var4;
            if ((var4 = BinocularInfo.backBinocular) == 1) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("后视双目硬件异常");
                var1.add(var10001);
            } else if (var4 == 2) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("后视环境光照度低");
                var1.add(var10001);
            }

            if ((var4 = GlobalVariable.sBinocularInfo.forwardBinocular) == 1) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("前视双目硬件异常");
                var1.add(var10001);
            } else if (var4 == 2) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("前视环境光照度低");
                var1.add(var10001);
            }

            if (GlobalVariable.sBinocularInfo.forwardBinocularCalibrationParam != 0) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("无前视标定参数");
                var1.add(var10001);
            }

            if (GlobalVariable.sBinocularInfo.backBinocularCalibrationParam != 0) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("无后视标定参数");
                var1.add(var10001);
            }

            if ((var4 = GlobalVariable.sBinocularInfo.downMono) == 1) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("下视单目硬件异常");
                var1.add(var10001);
            } else if (var4 == 2) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("下视环境光照度低");
                var1.add(var10001);
            }

            if (GlobalVariable.sBinocularInfo.downMonoCalibration != 0) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.f);
                var10001.setReason("无下视标定参数");
                var1.add(var10001);
            }

            return var1;
        }
    }

    private List<GDUDiagnostics> e() {
        ArrayList var3;
        var3 = new ArrayList();
        if (GlobalVariable.sRadarInfo != null) {
            StringBuilder var1;
            StringBuilder var10000 = var1 = new StringBuilder();
            var10000.append("雷达异常");
            boolean var2 = false;
            if (GlobalVariable.sRadarInfo.forwardRadar < 0) {
                var2 = true;
                var1.append(":");
                var1.append("前视");
            }

            if (GlobalVariable.sRadarInfo.leftRadar < 0) {
                if (var2) {
                    var1.append(",");
                } else {
                    var1.append(":");
                }

                var2 = true;
                var1.append("左视");
            }

            if (GlobalVariable.sRadarInfo.rightRadar < 0) {
                if (var2) {
                    var1.append(",");
                } else {
                    var1.append(":");
                }

                var2 = true;
                var1.append("右视");
            }

            if (GlobalVariable.sRadarInfo.downLocateHeightRadar < 0) {
                if (var2) {
                    var1.append(",");
                } else {
                    var1.append(":");
                }

                var2 = true;
                var1.append("下视定高");
            }

            GDUDiagnostics var10001;
            if (var2) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.i);
                var10001.setReason(var1.toString());
                var3.add(var10001);
            }

            boolean var4 = false;
            StringBuilder var5;
            var10000 = var5 = new StringBuilder();
            var10000.append("TOF异常");
            if (GlobalVariable.sRadarInfo.backTOF < 0) {
                var4 = true;
                var5.append(":");
                var5.append("后视");
            }

            if (GlobalVariable.sRadarInfo.upTOF < 0) {
                if (var4) {
                    var5.append(",");
                } else {
                    var5.append(":");
                }

                var4 = true;
                var5.append("上视");
            }

            if (GlobalVariable.sRadarInfo.downTOF < 0) {
                if (var4) {
                    var5.append(",");
                } else {
                    var5.append(":");
                }

                var4 = true;
                var5.append("下视");
            }

            if (var4) {
                var10001 = new GDUDiagnostics();
                var10001.setType(GDUDiagnosticsType.i);
                var10001.setReason(var5.toString());
                var3.add(var10001);
            }
        }

        return var3;
    }

    public void initContext(Context var1) {
        this.c = var1;
    }

    public GDUFlightController getFlightController() {
        if (this.d == null) {
            GDUFlightController var1;
            var1 = new GDUFlightController();
            this.d = var1;
        }

        return this.d;
    }

    public GDUBattery getBattery() {
        if (this.g == null) {
            GDUBattery var1;
            var1 = new GDUBattery();
            this.g = var1;
        }

        return this.g;
    }

    public GDURemoteController getRemoteController() {
        if (this.i == null) {
            GDURemoteController var1;
            var1 = new GDURemoteController();
            this.i = var1;
        }

        return this.i;
    }

    public GDULTE getLTE() {
        if (this.l == null) {
            this.l = new GDULTE(this.c);
        }

        return this.l;
    }

    public Megaphone getMegaphone() {
        if (this.o == null) {
            Megaphone var1;
            var1 = new Megaphone();
            this.o = var1;
        }

        return this.o;
    }

    public GDUCustomMsg getCustomMsg() {
        if (this.m == null) {
            GDUCustomMsg var1;
            var1 = new GDUCustomMsg();
            this.m = var1;
        }

        return this.m;
    }

    public BaseComponent getGimbal() {
        Object var1 = null;
        switch (synthetic.a[GlobalVariable.gimbalType.ordinal()]) {
            case 1:
                var1 = new com.gdu.sdk.gimbal.a();
                this.e = (GDUGimbal)var1;
                break;
            case 2:
                var1 = new com.gdu.sdk.gimbal.a();
                this.e = (GDUGimbal)var1;
                break;
            case 3:
                var1 = new b();
                this.e = (GDUGimbal)var1;
                break;
            case 4:
                var1 = new d();
                break;
            case 5:
                var1 = new k();
                break;
            case 6:
                var1 = new g();
                break;
            case 7:
                var1 = new h();
                break;
            case 8:
                var1 = new f();
        }

        return (BaseComponent)var1;
    }

    public BaseComponent getCamera() {
        Object var1 = null;
        if (GlobalVariable.gimbalType != GimbalType.ByrdT_None_Zoom) {
            switch (synthetic.a[GlobalVariable.gimbalType.ordinal()]) {
                case 1:
                case 2:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.b)) {
                        var1 = new com.gdu.sdk.camera.b();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 3:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.c)) {
                        var1 = new com.gdu.sdk.camera.c();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 4:
                    if ((var1 = this.f) == null || !(var1 instanceof e)) {
                        var1 = new e();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 5:
                case 9:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.d)) {
                        var1 = new com.gdu.sdk.camera.d();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 6:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.h)) {
                        var1 = new com.gdu.sdk.camera.h();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 7:
                    if ((var1 = this.f) == null || !(var1 instanceof i)) {
                        var1 = new i();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 8:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.g)) {
                        var1 = new com.gdu.sdk.camera.g();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 10:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.a)) {
                        var1 = new com.gdu.sdk.camera.a();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 11:
                    if ((var1 = this.f) == null || !(var1 instanceof j)) {
                        var1 = new j();
                        this.f = (GDUCamera)var1;
                    }
                    break;
                case 12:
                    if ((var1 = this.f) == null || !(var1 instanceof com.gdu.sdk.camera.k)) {
                        var1 = new com.gdu.sdk.camera.k();
                        this.f = (GDUCamera)var1;
                    }
            }
        } else if (GlobalVariable.sPSDKCompId > 0 && ((var1 = this.f) == null || !(var1 instanceof o))) {
            var1 = new o();
            this.f = (GDUCamera)var1;
        }

        return (BaseComponent)var1;
    }

    public List<GDUCamera> getCameras() {
        return super.b;
    }

    public List<GDUGimbal> getGimbals() {
        return super.a;
    }

    public BaseComponent getRadar() {
        if (this.h == null) {
            GDURadar var1;
            var1 = new GDURadar();
            this.h = var1;
        }

        return this.h;
    }

    public BaseComponent getAirLink() {
        if (this.j == null) {
            GDUAirLink var1;
            var1 = new GDUAirLink();
            this.j = var1;
        }

        return this.j;
    }

    public GDUVision getGduVision() {
        if (this.k == null) {
            GDUVision var1;
            var1 = new GDUVision();
            this.k = var1;
        }

        return this.k;
    }

    public void getProductSN(final CommonCallbacks.CompletionCallbackWith<String> var1) {
        if (TextUtils.isEmpty(GlobalVariable.SN)) {
            GduSocketManager.getInstance().getCommunication().getUnique(new SocketCallBack3() {
                public void callBack(byte var1x, GduFrame3 var2) {
                    CommonCallbacks.CompletionCallbackWith var3;
                    byte[] var4;
                    if (var1x == 0 && var2 != null && (var4 = var2.frameContent) != null && var4.length >= 17) {
                        byte[] var6;
                        if (var4.length > 19) {
                            var6 = new byte[20];
                        } else {
                            var6 = new byte[17];
                        }

                        byte[] var10000 = var4;
//                        var1x = var4.length - 2;

//                        System.arraycopy(var4, 2, var1x, 0, var4.length - 2);

                        System.arraycopy(var10000, 2, var6, 0, var4.length - 2);
                        String var5;
                        String var7 = var5 = new String(var6);
                        if (!var7.contains("GDU") && var5.length() == 20) {
                            var5 = var5.substring(0, 17);
                        }

                        GlobalVariable.SN = var5;
                        if ((var3 = var1) != null) {
                            var3.onSuccess(var5);
                        }
                    } else if ((var3 = var1) != null) {
                        var3.onFailure(GDUError.TIMEOUT);
                    }

                }
            });
        } else if (var1 != null) {
            var1.onSuccess(GlobalVariable.SN);
        }

    }

    public BaseProduct.Model getModel() {
        BaseProduct.Model var1 = Model.a;
        switch (synthetic.b[GlobalVariable.planType.ordinal()]) {
            case 1:
                var1 = Model.c;
                break;
            case 2:
                var1 = Model.d;
                break;
            case 3:
                var1 = Model.e;
                break;
            case 4:
                var1 = Model.b;
        }

        return var1;
    }

    public void setDiagnosticsInformationCallback(GDUDiagnostics.DiagnosticsInformationCallback var1) {
        this.p = var1;
    }

    public boolean isConnected() {
        return GlobalVariable.connStateEnum == ConnStateEnum.Conn_Sucess;
    }
}
