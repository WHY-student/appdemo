package com.gdu.demo.views;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.gdu.drone.TargetMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class PaintView extends AppCompatImageView {

    List<TargetMode> detectionBox = new ArrayList<>();
    List<String> class_label = new ArrayList<>();
    {
        class_label.add("bus");
        class_label.add("car");
        class_label.add("SUV");
        class_label.add("van");
        class_label.add("small_freight_car");
        class_label.add("small_truck");
        class_label.add("new1");
        class_label.add("new2");
        class_label.add("new3");
        class_label.add("unknown");
    }
//    private String text = "Sample Text";
    //private Handler handler;
    private Rect temp=new Rect();

    private long timestamp = System.currentTimeMillis();

    private Handler mainHandler; // 主线程的 Handler
    private HandlerThread backgroundThread; // 后台线程
    private Handler backgroundHandler; // 后台线程的 Handler

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 初始化主线程的 Handler
        mainHandler = new Handler(Looper.getMainLooper());

//         创建并启动后台线程
        backgroundThread = new HandlerThread("BackgroundThread");
        backgroundThread.start();
        // 初始化后台线程的 Handler
        backgroundHandler = new Handler(backgroundThread.getLooper());

        // 启动后台任务
        startBackgroundTask();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//
//                        // 在后台线程中更新数据后，通知UI线程重绘
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                PaintView.this.invalidate();  // 触发视图重绘
//                            }
//                        });
//
//                        // 等待一段时间后继续执行
//                        Thread.sleep(30);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }
    private void startBackgroundTask() {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                // 在后台线程中执行任务
                try {
                    Thread.sleep(50); // 模拟耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 通过主线程的 Handler 调用 invalidate()
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate(); // 请求重绘
                    }
                });

                // 重复执行任务
                backgroundHandler.post(this);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止后台线程
        if (backgroundThread != null) {
            backgroundThread.quit();
        }
    }

    Paint paint = new Paint();
    {
        paint.setAntiAlias(true);//用于防止边缘的锯齿
        paint.setColor(Color.RED);//设置颜色
        paint.setStyle(Paint.Style.STROKE);//设置样式为空心矩形
        paint.setStrokeWidth(5f);//设置空心矩形边框的宽度
        paint.setAlpha(1000);//设置透明度
    }

    Paint paint2 = new Paint();
    {
        paint2.setAntiAlias(true);//用于防止边缘的锯齿
        paint2.setColor(Color.RED);//设置颜色
        paint2.setStrokeWidth(3f);//设置空心矩形边框的宽度
        paint2.setTextSize(40f);
        paint2.setAlpha(1000);//设置透明度
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (TargetMode detection : this.detectionBox) {
            int x = detection.getLeftX();
            int y = detection.getLeftY();
            int maxX = x + detection.getWidth();
            int maxY = y + detection.getHeight();
            String label = null;
            if(label==null){
                int labelindex = detection.getFlawType();
                if(labelindex==-1){
                    label = "unknown";
                } else if (labelindex > 9) {
                    label = "test";
                }else {
                    label = class_label.get(labelindex);
                }
            }
            if (x >= 0 && y >= 0 && maxX < 1920 && maxY < 1080) {
                canvas.drawRect(new Rect(x, y, maxX, maxY), paint);//绘制矩形，并设置矩形框显示的位置
                canvas.drawText(label, detection.getLeftX(), detection.getLeftY() - 5, paint2);
            }
        }
        this.detectionBox = new ArrayList<>();
        //Log.d("PaintView", "Draw completed at: " + drawTime);
    }

    public void setRectParams(List<TargetMode> detectionBox) {
        this.detectionBox = detectionBox;
//        long drawTime = System.currentTimeMillis();
//        if(drawTime - this.timestamp > 100){
//            this.timestamp = drawTime;
//            invalidate();
//        }
        //long receiveTime = System.currentTimeMillis();
        //Log.d("PaintView", "Data received at: " + receiveTime);
//        invalidate();
    }
}



