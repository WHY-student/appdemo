package com.gdu.demo.ourgdu;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import android.util.Log;
import com.gdu.common.ConnStateEnum;
import com.gdu.common.GlobalVariable;
import com.gdu.drone.GimbalType;
import com.gdu.drone.TargetMode;
import com.gdu.gdusocket.GduImgUdpSocket;
import com.gdu.gdusocket.SocketCallBack;
import com.gdu.gdusocket.SocketCallBack3;
import com.gdu.gdusocket.ce.GduCESocket3;
import com.gdu.gdusocket.ce.IGduSocket;
import com.gdu.gdusocket.util.GduFrameUtil3;
import com.gdu.gdusocket.util.a;
import com.gdu.gdusocket.util.b;
import com.gdu.gdusocket.util.c;
import com.gdu.gdusocket.util.d;
import com.gdu.gdusocketmodel.GduFrame;
import com.gdu.product.ComponentKey;
import com.gdu.sdk.vision.OnTargetDetectListener;
import com.gdu.socketmodel.GduFrame3;
import com.gdu.socketmodel.GduSocketConfig3;
import com.gdu.util.ByteUtilsLowBefore;
import com.gdu.util.GimbalUtil;
import com.gdu.util.XOR;
import com.gdu.util.logs.RonLog;
import com.gdu.util.logs.RonLog2File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class ourGduCESocket3 extends GduCESocket3 {

    private GduFrameUtil3 D = new GduFrameUtil3();
//    private DatagramPacket i;


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
            Log.d("detection", String.format("%d", ((TargetMode) var5.get(0)).getLeftX()));

//            List var10;
//            if ((var10 = this.mTargetModeList) != null) {
//                if (var3 == 0) {
//                    var10.clear();
//                }
//
//                this.mTargetModeList.addAll(var5);
//                OnTargetDetectListener var9;
//                if (var3 == var11 - 1 && (var9 = this.targetDetectListener) != null) {
//                    var9.onTargetDetecting(var5);
//                }
//            }

        }
    }

    @Override
    public boolean handlerReceiveData(byte[] bArr, boolean z){

//        Log.d("ReceiveData", String.format("handlerReceiveData: %s", new String(bArr)));



        // 解析cmd
        int var6 = 14;
        boolean var3 = false;
        if ((bArr[3] & 16) != 0) {
            var6 = 15;
            var3 = true;
        }

        if ((bArr[3] & 8) != 0) {
            ++var6;
        }

        if ((var6 += this.D.checkFrameHead3(bArr, 0, var3)) < 3) {
            Log.i("FrameHead", "get Data length Err:" + var6);
            return true;
        } else {
            short var7 = ByteUtilsLowBefore.byte2short(bArr, 8);
            Log.i("NormalData", "start parse data: ");
//            this.a(bArr, this.i.getOffset(), var6);
            GduFrame3 gduFrame3 = GduFrameUtil3.byteArrayToGduFrame3(bArr, 0, var6);
            Log.i("frameCMD", "frameCMD : " + gduFrame3.frameCMD);
            try {
                byte[] newbArr = gduFrame3.frameContent;
                this.getDetectTargetNew(newbArr);
            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
        }


        return super.handlerReceiveData(bArr, z);
    }
}

