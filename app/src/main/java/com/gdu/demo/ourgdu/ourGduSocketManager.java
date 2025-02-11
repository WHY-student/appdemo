package com.gdu.demo.ourgdu;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import android.util.Log;

import com.gdu.common.GlobalVariable;
import com.gdu.gdusocket.GduCommunication3;
import com.gdu.gdusocket.GduSocketManager;
import com.gdu.gdusocket.ce.GduCESocket3;
import com.gdu.gdusocket.ce.IGduSocket;
import com.gdu.util.logs.RonLog;
import com.gdu.util.logs.RonLog2FileApi;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ourGduSocketManager{
    public static final String a = "GduSocketManager";
    private static ourGduSocketManager b;
    private GduCESocket3 c;
    private GduCommunication3 d;

    public static ourGduSocketManager getInstance() {
        if (b == null) {
            b = new ourGduSocketManager();
        }

        return b;
    }

    private ourGduSocketManager(){
        this.a();
    }

    private void a() {
        ourGduSocketManager var10000 = this;
        ourGduSocketManager var10001 = this;
        ourGduSocketManager var10002 = this;
        RonLog2FileApi.getSingle().saveData("api初始化Socket");
        RonLog.LogD(new String[]{"api初始化Socket"});
        Log.d("newCall", "初始化ourSocket");
        this.c();
        GduCESocket3 var1;
        var10002.c = var1 = new GduCESocket3();
        var10001.d = new GduCommunication3(var1);
        var10000.b();
    }

    private void b() {
    }

    private void c() {
        int var3 = 3000;

        while(var3 < 9000) {
            try {
                (new DatagramSocket(var3)).close();
                GlobalVariable.UDP_SOCKET_PORT = var3;
                break;
            } catch (SocketException var2) {
                var2.printStackTrace();
                ++var3;
            }
        }

        GlobalVariable.UDP_SOCKET_IMG_PORT = 7078;
    }

    public GduCommunication3 getCommunication() {
        return this.d;
    }

    public GduCESocket3 getGduCESocket() {
        return this.c;
    }

    public void setConnectCallBack(IGduSocket.OnConnectListener var1) {
        this.c.setOnConnectListener(var1);
    }

    public boolean startConnect() {
        try {
            this.d.createSocket();
        } catch (Exception var1) {
            return false;
        }

        return true;
    }

    public void stopConnect() {
        GduCESocket3 var1;
        if ((var1 = this.c) != null) {
            var1.closeConnSocket();
        }

    }

    public void onDestroy() {
        b = null;
        GduCESocket3 var1;
        if ((var1 = this.c) != null) {
            var1.onDestroy();
        }

    }
}
