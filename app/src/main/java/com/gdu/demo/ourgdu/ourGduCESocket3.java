package com.gdu.demo.ourgdu;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import android.util.Log;
import com.gdu.common.ConnStateEnum;
import com.gdu.common.GlobalVariable;
import com.gdu.drone.GimbalType;
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
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ourGduCESocket3 extends GduCESocket3 {

    @Override
    public boolean handlerReceiveData(byte[] bArr, boolean z){
        Log.d("ReceiveData", "handlerReceiveData: ");
        return super.handlerReceiveData(bArr, z);
    }
}

