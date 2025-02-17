package com.gdu.demo.ourgdu;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import android.util.Log;

import com.gdu.drone.TargetMode;
import com.gdu.gdusocket.ce.GduCESocket3;
import com.gdu.gdusocket.util.GduFrameUtil3;
import com.gdu.socketmodel.GduFrame3;
import com.gdu.util.ByteUtilsLowBefore;

import java.net.DatagramPacket;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ourGduCESocket3 extends GduCESocket3 {

    private GduFrameUtil3 D = new GduFrameUtil3();
//    private DatagramPacket i;
    private ArrayList<Integer> cmdList = new ArrayList<>();

    private TargetMode ourParseTargetModeNew(byte[] var1) {
        TargetMode var10000 = new TargetMode();
        //byte[] var10007 = var1;
        //byte[] var10008 = var1;
        //byte[] var10009 = var1;
        byte[] var10010 = var1;

        short var7 = ByteUtilsLowBefore.byte2short(var1, 0);
        short var8 = ByteUtilsLowBefore.byte2short(var1, 2);
        short var2 = ByteUtilsLowBefore.byte2short(var1, 4);
        short var3 = ByteUtilsLowBefore.byte2short(var1, 6);
        short var4 =ByteUtilsLowBefore.byte2short(var1,8);
        int id =ByteUtilsLowBefore.byte2Int(var1,10);
        //byte var4 = var10008[8];
        //byte var5 = (byte)(var10007[9] & 1 & 255);
        var10000.setHeight(var3);
        var10000.setWidth(var2);
        var10000.setLeftX(var7);
        var10000.setLeftY(var8);
        var10000.setTargetConfidence((short)var4);
        //var10000.setFlawType(var5);
        var10000.setId(id);
        return var10000;
    }

    private void ourGetDetectTargetNew(byte[] var1) {
        //int var2;
        int startIndex=10;
        if(var1.length>startIndex){
            int desLength=var1.length-startIndex;
            byte[] targetBox = new byte[desLength];
            System.arraycopy(var1,startIndex,targetBox,0,desLength);
            int var4=desLength/14;
            CopyOnWriteArrayList var5 = new CopyOnWriteArrayList();

            for(int var6 = 0; var6 < var4; ++var6) {
                byte[] var7 =Arrays.copyOfRange(targetBox,var6*14,var6*14+14);


                var5.add(this.ourParseTargetModeNew(var7));
            }
            Log.d("detection", String.format("%d", ((TargetMode) var5.get(0)).getLeftX()));

        /*if ((var2 = var1.length) >= 3) {
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
            Log.d("detection", String.format("%d", ((TargetMode) var5.get(0)).getLeftX()));*/

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
//        this.H = 100;

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

//        try {
//            this.printI();
//        } catch (Exception e) {
////            throw new RuntimeException(e);
//        }

        if ((var6 += this.D.checkFrameHead3(bArr, 0, var3)) < 3) {
            Log.i("FrameHead", "get Data length Err:" + var6);
            return true;
        } else {
            short var7 = ByteUtilsLowBefore.byte2short(bArr, 8);
//            Log.i("NormalData", "start parse data: ");
//            this.a(bArr, this.i.getOffset(), var6);
            GduFrame3 gduFrame3 = GduFrameUtil3.byteArrayToGduFrame3(bArr, 0, var6);
            try {
                if (!cmdList.contains(gduFrame3.frameCMD)) {
                    cmdList.add(gduFrame3.frameCMD);
                    Log.i("frameCMD", "frameCMD : " + gduFrame3.frameCMD);

                    byte[] newbArr = gduFrame3.frameContent;
                    this.ourGetDetectTargetNew(newbArr);
//                    System.out.println(a);
                }
            } catch (Exception e) {
//              Log.d("gduFrame","解析数据失败");
            }
        }


        return super.handlerReceiveData(bArr, z);
    }

    private void f(){

    }
}

