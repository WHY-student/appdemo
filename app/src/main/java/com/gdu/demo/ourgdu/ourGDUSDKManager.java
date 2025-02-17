package com.gdu.demo.ourgdu;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


//import a.a.a.a.b;
//import a.a.a.b.a;
import a.b.a.c;
import a.b.a.d;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.gdu.common.ConnStateEnum;
import com.gdu.common.ConnType;
import com.gdu.common.GlobalVariable;
import com.gdu.common.error.GDUError;
import com.gdu.config.GduConfig;
import com.gdu.demo.ourgdu.ourGduCommunication3;
import com.gdu.gdusocket.GduCommunication3;
import com.gdu.demo.ourgdu.ourGduSocketManager;
import com.gdu.gdusocket.GduSocketManager;
import com.gdu.gdusocket.ce.GduCESocket3;
import com.gdu.gdusocket.ce.IGduSocket;
import com.gdu.product.ComponentKey;
import com.gdu.remotecontrol.listener.OnGDUUsbListener;
import com.gdu.remotecontrol.main.GduRCManager;
import com.gdu.remotecontrol.manager.OnChargeByUsbListener;
import com.gdu.remotecontrol.usb.manager.GduUsbManager;
import com.gdu.sdk.base.BaseComponent;
import com.gdu.sdk.base.BaseProduct;
import com.gdu.sdk.customkey.DaoMaster;
import com.gdu.sdk.customkey.DaoSession;
import com.gdu.sdk.customkey.RcCustomKeyManager;
import com.gdu.sdk.manager.GDUSDKInitEvent;
//import com.gdu.sdk.products.GDUAircraft;
import com.gdu.sdk.util.f;
import com.gdu.util.logs.RonLog;
import com.gdu.util.logs.RonLog2FileApi;
import java.io.File;
import org.greenrobot.greendao.database.DatabaseOpenHelper;


public class ourGDUSDKManager{
    private static ourGDUSDKManager a;
    private static DaoSession b;
    private ourGDUAircraft gduaircraft;
    private Context d;
    private GduRCManager e;
    private ourGduSocketManager f;
    private ourGduCommunication3 g;

    private GduSocketManager their_f;
    private GduCommunication3 their_g;
    private ourGDUSDKManager.SDKManagerCallback h;
    private boolean i;
    private boolean j;
    private boolean k;
    private c l;
    private a.a.a.a.b m;
    RcCustomKeyManager n;
    ServiceConnection o = new ServiceConnection() {
        public void onServiceConnected(ComponentName var1, IBinder var2) {
            ourGDUSDKManager.this.l = c.b.a(var2);
            if (ourGDUSDKManager.this.l != null) {
                try {
                    ourGDUSDKManager.this.l.a(new d.b() {
                        public void b(int var1, double var2) throws RemoteException {
                            if (var1 == 0) {
                                GlobalVariable.rollAngleDf = var2;
                            } else if (var1 == 1) {
                                GlobalVariable.pitchAngleDf = var2;
                            }

                        }
                    });
                } catch (Exception var3) {
                }

            }
        }

        public void onServiceDisconnected(ComponentName var1) {
        }
    };
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;
    private OnChargeByUsbListener v = (var1) -> {
        if (this.a() != null) {
            this.a().isUsbHadBreak(var1 ^ true);
        }
        if(this.oura()!=null){
            this.oura().isUsbHadBreak(var1 ^ true);
        }

        if (var1) {
            GlobalVariable.RC_usb_hadConn = 1;
        } else {
            GlobalVariable.RC_usb_hadConn = 0;
        }

    };



    public static synchronized ourGDUSDKManager getInstance() {
        if (a == null) {
            Class var0 = ourGDUSDKManager.class;
            synchronized(var0){
                if (a == null) {
                    a = new ourGDUSDKManager();
                }
            }

        }
        return a;
    }

    private ourGDUSDKManager() {
        this.c();
    }

    private void c() {
        String var2;
        String var10000 = var2 = com.gdu.sdk.util.f.d();
        String var1 = com.gdu.sdk.util.f.a();
        if (var10000.contains("tb8788") && var1.contains("alps") || var2.contains("RCSEE") && var1.contains("GDU") || var2.contains("rcsee") && var1.contains("gdu") || var2.contains("AIC02") && var1.contains("GDU") || var2.contains("aic02") && var1.contains("gdu")) {
            GlobalVariable.isRCSEE = true;
        } else if (var1.contains("Mega") || var2.contains("M18")) {
            GlobalVariable.isCustomRC = true;
        }

    }


    public static /* synthetic */ class gdu_sdk_manager_e {

        /* renamed from: a  reason: collision with root package name */
        static final /* synthetic */ int[] f542a;

        static {
            int[] iArr = new int[ComponentKey.values().length];
            f542a = iArr;
            try {
                iArr[ComponentKey.GIMBAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f542a[ComponentKey.CAMERA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f542a[ComponentKey.FLIGHT_CONTROLLER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f542a[ComponentKey.RADAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f542a[ComponentKey.REMOTE_CONTROLLER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f542a[ComponentKey.BATTERY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f542a[ComponentKey.AIR_LINK.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f542a[ComponentKey.BASE_STATION.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private GduCESocket3 a() {
        ourGduSocketManager var1;
        return (var1 = this.f) != null && var1.getGduCESocket() != null ? this.f.getGduCESocket() : null;
    }

    private GduCESocket3 oura(){
        GduSocketManager var1;
        return (var1 = this.their_f) != null && var1.getGduCESocket() != null ? this.their_f.getGduCESocket() : null;
    }
    private void b() {

        if (b == null) {
            SQLiteDatabase var1 = new a.a.a.b.b(new a.a.a.b.a(this.d, GduConfig.BaseDirectory + File.separator + "database"), "gdusdk.db").getWritableDatabase();
            b = (new DaoMaster(var1)).newSession();
        }

    }

    public static DaoSession getDaoSession() {
        return b;
    }

    private void a(Context var1) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.mega.megaimusensorservice", "com.mega.megaimusensorservice.MegaImuSensorService"));
        Log.d("IMUTest", "result = " + var1.getApplicationContext().bindService(intent, this.o, Context.BIND_AUTO_CREATE));
    }

    private void d() {
        Context var1;
        if ((var1 = this.d) != null) {
            this.e = GduRCManager.getInstance(var1);
        }
        GduSocketManager temp;
        this.their_f=temp=GduSocketManager.getInstance();
        this.their_g=temp.getCommunication();
        ourGduSocketManager var4;
        this.f = var4 = ourGduSocketManager.getInstance();
        this.g = var4.getCommunication();
        ourGDUAircraft var5;
        ourGDUAircraft var10000 = var5 = new ourGDUAircraft();
        this.gduaircraft = var5;
        if (var10000 != null) {
            var5.initContext(this.d);
        }

        ourGDUSDKManager var2 = this;
        RcCustomKeyManager var3;
        var3 = new RcCustomKeyManager();
        var2.n = var3;
    }

    private void e() {
        this.their_f.setConnectCallBack(new IGduSocket.OnConnectListener() {
            public void onConnect() {
                ourGDUSDKManager.this.f();
                RonLog2FileApi.getSingle().saveData("GduDroneApi====:onConnect===:" + ourGDUSDKManager.this.j);
                RonLog.LogD(new String[]{"GduDroneApi====:onConnect===:" + ourGDUSDKManager.this.j});
                Log.d("theirCall","GDUSDKManager onConnect");
            }

            public void onDisConnect() {
                RonLog2FileApi.getSingle().saveData("GduDroneApi====:onDisConnect===:" + ourGDUSDKManager.this.j);
                RonLog.LogD(new String[]{"GduDroneApi====:onDisConnect===:" + ourGDUSDKManager.this.j});
                if (ourGDUSDKManager.this.j) {
                    if (ourGDUSDKManager.this.h != null) {
                        ourGDUSDKManager.this.h.onProductDisconnect();
                    }

                    ourGDUSDKManager.this.j = false;
                    ourGDUSDKManager.this.e.disconnect();
                    ourGDUSDKManager.this.e.closeConnect();
                    ourGDUSDKManager.this.p = false;
                    ourGDUSDKManager.this.q = false;
                    ourGDUSDKManager.this.r = false;
                    ourGDUSDKManager.this.t = false;
                    ourGDUSDKManager.this.u = false;
                }
            }

            public void onConnectDelay(boolean var1) {
            }

            public void onConnectMore() {
                ourGDUSDKManager.this.j = false;
            }

            public void onComponentChange(ComponentKey var1) {
//                Log.d("theirCall", "onComponentChange: ");
                ourGDUSDKManager.this.a(var1);
            }
        });
        this.their_f.getGduCESocket().setACDataReceivedListener(new IGduSocket.OnDataReceivedListener() {
            public void onDataReceived(Object var1) {
                if (!ourGDUSDKManager.this.j) {
                    ourGDUSDKManager.this.j = true;
                    if (ourGDUSDKManager.this.gduaircraft == null) {
                        ourGDUSDKManager.this.gduaircraft = new ourGDUAircraft();
                    }

                    ourGDUSDKManager.this.h.onProductConnect(ourGDUSDKManager.this.gduaircraft);
                }

            }
        });


        this.f.setConnectCallBack(new IGduSocket.OnConnectListener() {
            public void onConnect() {
                ourGDUSDKManager.this.ourf();
                RonLog2FileApi.getSingle().saveData("GduDroneApi====:onConnect===:" + ourGDUSDKManager.this.j);
                RonLog.LogD(new String[]{"GduDroneApi====:onConnect===:" + ourGDUSDKManager.this.j});
                Log.d("ourCall", "onConnect: ");
            }

            public void onDisConnect() {
                RonLog2FileApi.getSingle().saveData("GduDroneApi====:onDisConnect===:" + ourGDUSDKManager.this.j);
                RonLog.LogD(new String[]{"GduDroneApi====:onDisConnect===:" + ourGDUSDKManager.this.j});
                Log.d("ourGDUSDKManager", "onDisConnect: ");
                GlobalVariable.connStateEnum = ConnStateEnum.Conn_Sucess;
//
//                if (ourGDUSDKManager.this.j) {
//                    if (ourGDUSDKManager.this.h != null) {
//                        ourGDUSDKManager.this.h.onProductDisconnect();
//                    }
//
//                    ourGDUSDKManager.this.j = false;
//                    ourGDUSDKManager.this.e.disconnect();
//                    ourGDUSDKManager.this.e.closeConnect();
//                    ourGDUSDKManager.this.p = false;
//                    ourGDUSDKManager.this.q = false;
//                    ourGDUSDKManager.this.r = false;
//                    ourGDUSDKManager.this.t = false;
//                    ourGDUSDKManager.this.u = false;
//                }
            }

            public void onConnectDelay(boolean var1) {
//                GlobalVariable.wifiDelay = false;
//                GlobalVariable.connStateEnum = ConnStateEnum.Conn_Sucess;
//                GlobalVariable.sRCConnState = ConnStateEnum.Conn_Sucess;
//                ourGDUSDKManager.this.ourf();
                Log.d("ourCall", "onConnectDelay: ");

            }

            public void onConnectMore() {
                Log.d("ourCall", "onConnectMore: ");

                ourGDUSDKManager.this.j = false;
            }

            public void onComponentChange(ComponentKey var1) {
//                Log.d("ourCall", "onComponentChange: ");
                ourGDUSDKManager.this.a(var1);
            }
        });
        this.f.getGduCESocket().setACDataReceivedListener(new IGduSocket.OnDataReceivedListener() {
            public void onDataReceived(Object var1) {
                if (!ourGDUSDKManager.this.j) {
                    ourGDUSDKManager.this.j = true;
                    if (ourGDUSDKManager.this.gduaircraft == null) {
                        ourGDUSDKManager.this.gduaircraft = new ourGDUAircraft();
                    }

                    Log.d("ourGDUSDKManager", "onConnect: ");


                    ourGDUSDKManager.this.h.onProductConnect(ourGDUSDKManager.this.gduaircraft);
                }

            }
        });
    }

    private void a(ComponentKey var1) {
        Object var2 = null;
        if (this.gduaircraft == null) {
            this.getProduct();
        }

        switch (gdu_sdk_manager_e.f542a[var1.ordinal()]) {
            case 1:
                if (this.p) {
                    return;
                }

                this.p = true;
                var2 = this.gduaircraft.getGimbal();
                break;
            case 2:
                if (this.q) {
                    return;
                }

                this.q = true;
                var2 = this.gduaircraft.getCamera();
                break;
            case 3:
                if (this.r) {
                    return;
                }

                this.r = true;
                var2 = this.gduaircraft.getFlightController();
            case 4:
            default:
                break;
            case 5:
                if (this.s) {
                    return;
                }

                this.s = true;
                var2 = this.gduaircraft.getRemoteController();
                break;
            case 6:
                if (this.t) {
                    return;
                }

                this.t = true;
                var2 = this.gduaircraft.getBattery();
                break;
            case 7:
                if (this.u) {
                    return;
                }

                this.u = true;
                var2 = this.gduaircraft.getAirLink();
        }

        ourGDUSDKManager.SDKManagerCallback var3;
        if ((var3 = this.h) != null && var2 != null) {
            var3.onComponentChange((BaseComponent)null, (BaseComponent)var2);
        }

    }

    private void f() {
        this.their_g.synTime((var0, var1) -> {
        });
    }

    private void ourf() {
        this.g.synTime((var0, var1) -> {
        });
    }

    public BaseProduct getProduct() {
        if (this.gduaircraft == null) {
            this.gduaircraft = new ourGDUAircraft();
        }

        return this.gduaircraft;
    }

    public void registerApp(Context var1, ourGDUSDKManager.SDKManagerCallback var2) {
        this.d = var1.getApplicationContext();
        this.h = var2;
        this.d();
        this.e();
        if (var2 != null) {
            var2.onRegister(GDUError.REGISTRATION_SUCCESS);
        }

    }

    public Context getContext() {
        return this.d;
    }

    public void setCustomRCEnable(boolean var1) {
        GlobalVariable.isCustomRCEnable = var1;
        GlobalVariable.isCustomRC = var1;
    }

    public boolean getCustomRCEnable() {
        return GlobalVariable.isCustomRCEnable;
    }

    public void startConnectionToProduct() {
        RonLog2FileApi.getSingle().saveData("connectUSB2 ===被调用，连接状态 " + GlobalVariable.connStateEnum);
        RonLog.LogD(new String[]{"connectUSB2 ===被调用，连接状态 " + GlobalVariable.connStateEnum});
        Log.d("ToProduct", "connectUSB2 ===被调用，连接状态 " + GlobalVariable.connStateEnum);
        if (GlobalVariable.connStateEnum != ConnStateEnum.Conn_Sucess) {
            if (!GlobalVariable.isRCSEE && !GlobalVariable.isCustomRC) {
                GduUsbManager.getInstance().onCreate(this.d.getApplicationContext());
                GduUsbManager.getInstance().setOnChargeByUsbListener(this.v);
                GduUsbManager.getInstance().setOnGDUUsbListener(new OnGDUUsbListener() {
                    public void openUsbModel() {
                        if (ourGDUSDKManager.this.f != null) {
                            ourGDUSDKManager.this.i = true;
                            GlobalVariable.RC_usb_hadConn = 1;
                            GlobalVariable.connType = ConnType.MGP03_RC_USB;
                            RonLog2FileApi.getSingle().saveData("开启socket通信");
                            RonLog.LogD(new String[]{"开启socket通信"});
                            Log.d("this.fsocket", "开启this.fsocket通信: ");
                            ourGDUSDKManager.this.f.startConnect();
                        }
                        if (ourGDUSDKManager.this.their_f != null) {
                            ourGDUSDKManager.this.i = true;
                            GlobalVariable.RC_usb_hadConn = 1;
                            GlobalVariable.connType = ConnType.MGP03_RC_USB;
                            RonLog2FileApi.getSingle().saveData("开启socket通信");
                            RonLog.LogD(new String[]{"开启socket通信"});
                            Log.d("this.their_f socket", "开启this.their_f socket通信: ");
                            ourGDUSDKManager.this.their_f.startConnect();
                        }
                    }

                    public void closeUsbModel() {
                        GlobalVariable.RC_usb_hadConn = 0;
                        GlobalVariable.connType = ConnType.MGP03_NONE;
                    }
                });
            } else {

//                ourGduSocketManager temp;
//                if ((temp = this.f) == null) {
//                    return;
//                }
//                try {
//                    temp.startConnect();
//                    Log.d("IMUInit", "ourGduSocketManager开始连接");
//                }
//                catch (Exception e){
//                    Log.d("IMUInit", "ourGduSocketManager连接错误");
//                }


                GduSocketManager var1;
                if ((var1 = this.their_f) == null) {
                    return;
                }

                var1.startConnect();

            }

        }
    }

    public String getSDKVersion(Context var1) {
        return "2.0.54";
    }

    public void stopConnectionToProduct() {
    }

    public interface SDKManagerCallback {
        void onRegister(GDUError var1);

        void onProductDisconnect();

        void onProductConnect(BaseProduct var1);

        void onProductChanged(BaseProduct var1);

        void onComponentChange(BaseComponent var1, BaseComponent var2);

        void onInitProcess(GDUSDKInitEvent var1, int var2);
    }
}
