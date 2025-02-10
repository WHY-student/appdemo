package com.gdu.demo.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class PaintView extends AppCompatImageView {

    List<DetectionBox> detectionBox = new ArrayList<>();
    List<String> class_label = new ArrayList<>();
    {
        class_label.add("car");
        class_label.add("bus");
        class_label.add("new1");
        class_label.add("new2");
        class_label.add("unknown");
    }
//    private String text = "Sample Text";

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
//        paint2.setStyle(Paint.Style.STROKE);//设置样式为空心矩形
        paint2.setStrokeWidth(3f);//设置空心矩形边框的宽度
        paint2.setTextSize(40f);
//        paint.setFakeBoldText(false);
//        paint.setTypeface(Typeface.DEFAULT);
        paint2.setAlpha(1000);//设置透明度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (DetectionBox detection : this.detectionBox) {
            int x = detection.getX();
            int y = detection.getY();
            int maxX = x + detection.getW();
            int maxY = y + detection.getH();
            String label = detection.getLabelName();;
//            if(labelIndex < 0){
//                label = "unknown";
//            }
//            else {
//                label = class_label.get(labelIndex);
//            }
            if (x >= 0 && y >= 0 && maxX < 1920 && maxY < 1080) {
                canvas.drawRect(new Rect(x, y, maxX, maxY), paint);//绘制矩形，并设置矩形框显示的位置
                canvas.drawText(label, detection.getX(), detection.getY() - 5, paint2);
            }
            //            canvas.drawRect(new Rect(100,100,200,200), paint);//绘制矩形，并设置矩形框显示的位置
        }
//        System.out.println("绘制成功");
//        canvas.drawRect(new Rect(100,100,200,200), paint);//绘制矩形，并设置矩形框显示的位置
//        canvas.drawRect(new Rect(100,100,200,200), paint);//绘制矩形，并设置矩形框显示的位置
    }

    public void setRectParams(List<DetectionBox> detectionBox) {
        this.detectionBox = detectionBox;
//        String output = "";
//        for (DetectionBox detection : this.detectionBox) {
//            // 读取每个字段的值
//            int x = detection.getX();
//            int y = detection.getY();
//            int w = detection.getW();
//            int h = detection.getH();
//            int confidence = detection.getConfidence();
//            int labelIndex = detection.getLabelIndex();
//            int modelID = detection.getModelID();
//
//            // 打印或处理这些值
////                                System.out.println("Detection:");
////                                System.out.println("X: " + x);
////                                System.out.println("Y: " + y);
////                                System.out.println("Width: " + w);
////                                System.out.println("Height: " + h);
////                                System.out.println("Confidence: " + confidence);
////                                System.out.println("Label Index: " + labelIndex);
////                                System.out.println("Model ID: " + modelID);
//            output = output + String.format("x: %d, y: %d, w: %d, h: %d, conf: %d, label: %d, model_id: %d \n", x, y, w, h, confidence, labelIndex, modelID);
//        }


        invalidate();
//        return output;
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        invalidate(); // 触发重绘
    }
}



