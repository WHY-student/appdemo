package com.gdu.demo.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;

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
        class_label.add("car");
        class_label.add("bus");
        class_label.add("new1");
        class_label.add("new2");
        class_label.add("unknown");
    }
//    private String text = "Sample Text";
    private Handler handler;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

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
                } else if (labelindex > 4) {
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
    }

    public void setRectParams(List<TargetMode> detectionBox) {
        this.detectionBox = detectionBox;
        invalidate();
    }
}



