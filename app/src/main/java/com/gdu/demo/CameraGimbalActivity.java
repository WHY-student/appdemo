package com.gdu.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.gdu.battery.ConnectionState;
import com.gdu.camera.SettingsDefinitions;
import com.gdu.camera.StorageState;
import com.gdu.common.GlobalVariable;
import com.gdu.common.error.GDUError;
import com.gdu.config.GduConfig;
import com.gdu.demo.views.PaintView;
import com.gdu.demo.views.TargetBox;
import com.gdu.drone.TargetMode;
import com.gdu.gimbal.GimbalState;
import com.gdu.sdk.base.BaseProduct;
import com.gdu.sdk.camera.GDUCamera;
import com.gdu.sdk.camera.SystemState;
import com.gdu.sdk.camera.VideoFeeder;
import com.gdu.sdk.codec.GDUCodecManager;
import com.gdu.sdk.codec.ImageProcessingManager;
import com.gdu.sdk.gimbal.GDUGimbal;
import com.gdu.demo.ourgdu.ourGDUAircraft;
import com.gdu.sdk.util.CommonCallbacks;
import com.gdu.battery.BatteryState;
import com.gdu.sdk.battery.GDUBattery;
//import com.gdu.sdk.vision.GDUVision;
import com.gdu.demo.ourgdu.ourGDUVision;
import com.gdu.sdk.flightcontroller.FlightControllerState;
import com.gdu.sdk.flightcontroller.GDUFlightController;
//import com.gdu.sdk.products.GDUAircraft;
//import com.gdu.sdk.vision.GDUVision;
import com.gdu.sdk.vision.OnTargetDetectListener;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * 云台和相机测试
 */

public class CameraGimbalActivity extends Activity implements TextureView.SurfaceTextureListener {

    private final String OUTPATH = GduConfig.BaseDirectory + "/local/";//本地副本的保存路径
    private VideoFeeder.VideoDataListener videoDataListener = null;
    private GDUCodecManager codecManager = null;
    private GDUBattery mBattery;
    private GDUFlightController mGDUFlightController;
    private ourGDUVision gduVision;
    private OnTargetDetectListener listener;

    private TextureView mGduPlayView;
    //private TextView mInfoTextView;
    //private TextView mStorageInfoTextView;
    private PaintView panitView;
    private TextView mVersionTextView;

    private TextView mMissionInfoTextView;
    private TextView horizenDis;
    private TextView vercalDis;
    private TextView horizenV;
    private TextView vercalV;

    private TextView startState;

//    private TargetBox targetBox;

    //private TextView mGimbalStateTextView;
    private Context mContext;
    private GDUCamera mGDUCamera;
    private BaseProduct product;
    private GDUGimbal mGDUGimbal;

    private ImageProcessingManager mImageProcessingManager;
    private ImageView mYUVImageView;

    private TextView mBatteryPercentTextView;

    private ScheduledExecutorService scheduler;
    private MqttClient client;
    private Handler handler;
    private String host = "tcp://145.192.1.111:1883";     // TCP协议
    //    private String userName = "aibox";
//    private String passWord = "aibox123456";
    private String mqtt_id = "aibox_1";
    private String mqtt_sub_topic = "aibox/0x02/0x84/0x0038/flag/2";
    //    private String mqtt_pub_topic = "esp135";
    private int chacktimes=0;
    private Button changeMode;

    private HandlerThread backgroundThread;

    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_camera_gimbal);
        initView();
        initData();
        initListener();
//        Mqtt_init();
//        startReconnect();

//        timeShow();

        handler = new Handler(Objects.requireNonNull(Looper.myLooper()));
//            @SuppressLint("SetTextI18n")
//            public void handleMessage(Message msg) {
//                try {
//                    super.handleMessage(msg);
//                    switch (msg.what) {
//                        case 1: //开机校验更新回传
//                            break;
//                        case 2:  // 反馈回传
//
//                            break;
//                        case 3:  //MQTT 收到消息回传   UTF8Buffer msg=new UTF8Buffer(object.toString());
//
////                        System.out.println(msg.obj.toString());   // 显示MQTT数据
//                            try {
//                                ObjectMapper objectMapper = new ObjectMapper();
//
//                                TargetBox targetBoxString = objectMapper.readValue(msg.obj.toString(), TargetBox.class);
//
//                                String encode = targetBoxString.getPayload();
//
//                                String decode = new String(android.util.Base64.decode(encode, android.util.Base64.DEFAULT));
//
////                            Toast.makeText(mContext, "output1", Toast.LENGTH_SHORT).show();
//                                DetectionInformation detectionInformation = objectMapper.readValue(decode, DetectionInformation.class);
////                            Toast.makeText(mContext, "output2", Toast.LENGTH_SHORT).show();
////                            String output = "";
////                                panitView.setRectParams(detectionInformation.getDetections());
////                            for (DetectionBox detection : detectionInformation.getDetections()) {
////                                // 读取每个字段的值
////                                int x = detection.getX();
////                                int y = detection.getY();
////                                int w = detection.getW();
////                                int h = detection.getH();
////                                int confidence = detection.getConfidence();
////                                int labelIndex = detection.getLabelIndex();
////                                int modelID = detection.getModelID();
////
////                                // 打印或处理这些值
//////                                System.out.println("Detection:");
//////                                System.out.println("X: " + x);
//////                                System.out.println("Y: " + y);
//////                                System.out.println("Width: " + w);
//////                                System.out.println("Height: " + h);
//////                                System.out.println("Confidence: " + confidence);
//////                                System.out.println("Label Index: " + labelIndex);
//////                                System.out.println("Model ID: " + modelID);
////                                output = output + String.format("x: %d, y: %d, w: %d, h: %d, conf: %d, label: %d, model_id: %d \n", x, y, w, h, confidence, labelIndex, modelID);
////                            }
////                            Toast.makeText(mContext, "output: " + output, Toast.LENGTH_SHORT).show();
//                            } catch (Exception e) {
//                                Toast.makeText(mContext, "解码Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                            break;
//                        case 30:  //连接失败
//                            Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
//                            break;
//                        case 31:   //连接成功
//                            Toast.makeText(mContext, "连接成功", Toast.LENGTH_SHORT).show();
//                            try {
//                                client.subscribe(mqtt_sub_topic, 1);
//                                Toast.makeText(mContext, "订阅成功", Toast.LENGTH_SHORT).show();
//                            } catch (MqttException e) {
//                                e.printStackTrace();
//                                Toast.makeText(mContext, "订阅失败", Toast.LENGTH_SHORT).show();
//                            }
//                            break;
//                        default:
//                            break;
//                    }
//                }catch (Exception e){
//                    Toast.makeText(mContext, "消息处理 Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        };
        /* -------------------------------------------------------------------------------------- */
        backgroundThread = new HandlerThread("BackgroundThread");
        backgroundThread.start();
        // 初始化后台线程的 Handler
        backgroundHandler = new Handler(backgroundThread.getLooper());

//        检测状态
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                while (true){

                    StringBuilder s = new StringBuilder();
                    if(GlobalVariable.gpsAbnormal!=0){
                        s.append("GPS信号弱       ");
                    }
                    if(GlobalVariable.batteryAbnormal!=0){
                        s.append("电池异常");
                    }
//                    Log.i("State", s.toString());
                    show(startState, s.toString());

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


    }

    private void initData() {
        try {
            VideoFeeder.getInstance().getPrimaryVideoFeed().addVideoDataListener(videoDataListener);
        } catch (Exception ignored) {
            Toast.makeText(mContext, "VideoFeeder Error: " + ignored.getMessage() ,Toast.LENGTH_SHORT).show();
        }
        initCamera();
        initGimbal();
        initBattery();
        initFlightContral();
        initGduvision();

        mGDUCamera.getDisplayMode(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.DisplayMode>() {
            @Override
            public void onSuccess(SettingsDefinitions.DisplayMode displayMode) {
                if(displayMode == SettingsDefinitions.DisplayMode.THERMAL_ONLY){
                    changeMode.setText("切换为可见光");
                }else{
                    changeMode.setText("切换为红外");
                }

            }

            @Override
            public void onFailure(GDUError var1) {
                toast("发送失败");
            }
        });
//        setupListener();
//        mImageProcessingManager = new ImageProcessingManager(mContext);
    }

    private void initBattery() {
        mBattery = SdkDemoApplication.getAircraftInstance().getBattery();
        if (mBattery != null) {
            mBattery.setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState state) {
                    show(mBatteryPercentTextView, String.format("电量:%s%%", state.getChargeRemainingInPercent()));
                }
            });
            /*int percent = mBatteryState.getChargeRemainingInPercent();
            String percentText="电量百分比"+percent+"%";
            showText(mBatteryPercentTextView, percentText);*/
        }
    }

    private void initGimbal() {
        mGDUGimbal = (GDUGimbal) ((ourGDUAircraft) SdkDemoApplication.getProductInstance()).getGimbal();
        if (mGDUGimbal == null) {
            toast("云台未识别，相关功能可能出现异常");
            return;
        }
        mGDUGimbal.setStateCallback(new GimbalState.Callback() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onUpdate(GimbalState state) {
//                StringBuilder s = new StringBuilder();
//                s.append(" Attitude pitch ");
//                s.append(state.getAttitudeInDegrees().pitch);
//                s.append(" Attitude roll ");
//                s.append(state.getAttitudeInDegrees().roll);
//                s.append(" Attitude yaw ");
//                s.append(state.getAttitudeInDegrees().yaw);
//                s.append(" isCalibrating ");
//                s.append(state.isCalibrating());
                show(horizenDis, String.format("yaw: %.1f°", state.getAttitudeInDegrees().yaw));
            }
        });
    }

    private void initFlightContral(){
        product = SdkDemoApplication.getProductInstance();
        if (product == null || !product.isConnected()) {
            return;
        }
        else {
            mGDUFlightController = ((ourGDUAircraft) product).getFlightController();
            mGDUFlightController.setStateCallback(new FlightControllerState.Callback() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onUpdate(FlightControllerState flightControllerState) {
                    try {
//                        toast("飞行状态更新");

                        float x_ver = flightControllerState.getVelocityX();
                        float y_ver = flightControllerState.getVelocityY();
                        float z_ver = flightControllerState.getVelocityZ();
                        float ver = (float) Math.sqrt(x_ver * x_ver + y_ver * y_ver);
                        float dis = flightControllerState.getDistance();
                        float hei = flightControllerState.getAircraftLocation().getAltitude();
                        //LocationCoordinate3D  flyInf=flightControllerState.getAircraftLocation();
                        //float hei=flyInf.getAltitude();
//                        show(horizenDis, String.format("总飞行距离：%.3fm", dis / 100));
                        show(vercalDis, String.format("飞行高度:%.3fm", hei / 100));
                        show(horizenV, String.format("水平速度：%.1fm/s", ver / 100));
                        show(vercalV, String.format("垂直速度:%.1fm/s", z_ver / 100));
                    }catch (Exception e){
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
    private void initGduvision(){
        gduVision =(ourGDUVision) ((ourGDUAircraft) SdkDemoApplication.getProductInstance()).getGduVision();
        if (gduVision==null){
            toast("gduVision出现异常");
            return;
        }
        else{
            try {
                gduVision.setOnTargetDetectListener(new OnTargetDetectListener() {
                    long startTime=System.currentTimeMillis();
                    @Override
                    public void onTargetDetecting(List<TargetMode> list) {
                        if (list == null) {
                            toast("没有检测物体");
                        } else {
                            long midTime=System.currentTimeMillis();
                            long durTime=midTime-startTime;
                            Log.d("mideTime","从开始到检测到物体时间"+durTime);

//                            toast("检测到物体");
//                            TargetMode mode = list.get(0);
//                            List<DetectionBox> detectionInformationList = new ArrayList<>();
//                            for(TargetMode mode: list){
//                                DetectionBox objectDetectionBox = new DetectionBox();
//                                objectDetectionBox.setX(mode.getLeftX());
//                                objectDetectionBox.setY(mode.getLeftY());
//                                objectDetectionBox.setW(mode.getWidth());
//                                objectDetectionBox.setH(mode.getHeight());
//                                objectDetectionBox.setConfidence(mode.getTargetConfidence());
//                                objectDetectionBox.setLabelIndex(mode.getFlawType());
//                                detectionInformationList.add(objectDetectionBox);
//                            }

                            panitView.setRectParams(list);
                            //long endTime=System.currentTimeMillis();
                            //long ver= endTime -startTime;
                            //Log.d("delaytime","延长时间"+ver);

                        }
                    }

                    @Override
                    public void onTargetDetectFailed(int i) {
                        toast("检测失败");

                    }

                    @Override
                    public void onTargetDetectStart() {
                        toast("检测开始");

                    }

                    @Override
                    public void onTargetDetectFinished() {
                        toast("检测结束");

                    }
                });
            }
            catch (Exception ignored){
                toast("添加监视器错误");
            }
        }

    }
    private void initCamera() {
        mGDUCamera = (GDUCamera) ((ourGDUAircraft) SdkDemoApplication.getProductInstance()).getCamera();
        if (mGDUCamera != null) {
            mGDUCamera.setSystemStateCallback(new SystemState.Callback() {
                @Override
                public void onUpdate(SystemState systemState) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" isPhotoStored ");
                    sb.append(systemState.isPhotoStored());
                    sb.append(" hasError ");
                    sb.append(systemState.isHasError());
                    sb.append(" isRecording ");
                    sb.append(systemState.isRecording());
                    sb.append(" mode ");
                    sb.append(systemState.getMode());
                    sb.append(" time ");
                    sb.append(systemState.getCurrentVideoRecordingTimeInSeconds());
                    //show(mInfoTextView, sb.toString());
                }
            });
            mGDUCamera.setStorageStateCallBack(new StorageState.Callback() {
                @Override
                public void onUpdate(StorageState state) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" isFormatting ");
                    sb.append(state.isFormatting());
                    sb.append(" isFormatted ");
                    sb.append(state.isFormatted());
                    sb.append(" TotalSpace ");
                    sb.append(state.getTotalSpace());
                    sb.append(" RemainingSpace ");
                    sb.append(state.getRemainingSpace());
                    //show(mStorageInfoTextView, sb.toString());
                }
            });
        }
    }
    private void setupListener(){

    }

    private void initListener() {

    }


    private void initView() {
        try{
            mGduPlayView = findViewById(R.id.video_texture_view);
            mGduPlayView.setOpaque(false);
            //        toast("初始化识别");

            panitView = findViewById(R.id.paint_view);
            //        toast("框view识别");
            //mInfoTextView = (TextView) findViewById(R.id.camera_info_textview);
            //mStorageInfoTextView = (TextView) findViewById(R.id.camera_storage_info_textview);
            //mVersionTextView = (TextView) findViewById(R.id.version_textview);

            horizenDis=findViewById(R.id.hor_dist);
            vercalDis=findViewById(R.id.ver_dist);
            horizenV=findViewById(R.id.hor_v);
            vercalV=findViewById(R.id.ver_v);

            mYUVImageView = findViewById(R.id.yuv_imageview);
            //mGimbalStateTextView = (TextView) findViewById(R.id.gimbal_info_textview);
            mBatteryPercentTextView = findViewById(R.id.planebattery_state3);
            if (mGduPlayView != null) {
                mGduPlayView.setSurfaceTextureListener(this);
                videoDataListener = new VideoFeeder.VideoDataListener() {
                    @Override
                    public void onReceive(byte[] bytes, int size) {
                        if (null != codecManager) {
//                            codecManager.sendDataToDecoder(bytes, size);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 延迟后执行的操作
                                        codecManager.sendDataToDecoder(bytes, size);
                                }
                            }, 700);  // 延迟 2000 毫秒（2秒）
                        }
                    }
                };
            }

            changeMode = findViewById(R.id.rgb_mode);

            startState = findViewById(R.id.not_start_state);

        }catch (Exception e){
            Toast.makeText(mContext, "initView Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void toast(final String toast) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void show(TextView textView, final String toast) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(toast);
//                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rgb_mode:
                try {
                    int width = mGduPlayView.getWidth();
                    int height = mGduPlayView.getHeight();
                    show(horizenDis, String.format("video width：%d", width));
                    show(vercalDis, String.format("video height：%d", height));

                    chacktimes++;
                    if (chacktimes % 2 == 0) {
                        mGDUCamera.setDisplayMode(SettingsDefinitions.DisplayMode.THERMAL_ONLY, new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(GDUError error) {
                                if (error == null) {
//                                    initData();
                                    toast("设置成红外");
                                } else {
                                    toast("发送失败");
                                }
                            }
                        });
                        changeMode.setText("切换为可见光");


                    } else {
                        mGDUCamera.setDisplayMode(SettingsDefinitions.DisplayMode.ZL, new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(GDUError error) {
                                if (error == null) {
//                                    initData();
                                    toast("设置成可见光");
                                } else {
                                    toast("发送失败");
                                }
                            }
                        });
                        changeMode.setText("切换为红外");
                    }
                }
                catch (Exception e){
                    Toast.makeText(mContext, "changeMode Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.wide_field_mode:
                try {
                    mGDUCamera.setDisplayMode(SettingsDefinitions.DisplayMode.PIP, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(GDUError error) {
                            if (error == null) {
//                            initData();
                                toast("设置成分屏");
                            } else {
                                toast("发送失败");
                            }
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(mContext, "setPipView Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fouseadj_mode:
                try{
                    mGDUCamera.setDisplayMode(SettingsDefinitions.DisplayMode.ZL , new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(GDUError error) {
                            if (error == null) {
//                            initData();
                                toast("设置成可见光变焦");
                            } else {
                                toast("发送失败");
                            }
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(mContext, "changeFocus Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fixfoused_mode:
                try{
                    mGDUCamera.setDisplayMode(SettingsDefinitions.DisplayMode.WAL  , new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(GDUError error) {
                            if (error == null) {
//                            initData();
                                toast("设置成可见光定焦");
                            } else {
                                toast("发送失败");
                            }
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(mContext, "setFocus Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.start_go_home:
                mGDUFlightController.startGoHome(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(GDUError gduError) {
                        if(gduError == null){
                            toast("发送返航指令");
                        } else {
                            toast("发送失败");
                        }
                    }
                });
                break;
            case R.id.start_landing:

                mGDUFlightController.startLanding(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(GDUError gduError) {
                        if(gduError == null){
                            toast("发送降落指令");
                        } else {
                            toast("发送失败");
                        }
                    }
                });
                break;

            /*case R.id.btn_set_hd_liveview_enabled:
                mGDUCamera.setHDLiveViewEnabled(false, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(GDUError error) {
                        if (error == null) {
                            toast("设置成功");
                        } else {
                            toast("设置失败");
                        }
                    }
                });
                break;


            case R.id.btn_get_hd_liveview_enabled:
                mGDUCamera.getHDLiveViewEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        toast("获取成功： " + aBoolean);
                    }

                    @Override
                    public void onFailure(GDUError gduError) {
                        toast("获取失败： ");
                    }
                });
                break;*/
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                mGduPlayView.beginRecord("/mnt/sdcard/gdu","ron.mp4");
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (codecManager != null) {
            codecManager.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (codecManager != null) {
            codecManager.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (codecManager != null) {
            codecManager.onDestroy();
        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (codecManager == null) {
            codecManager = new GDUCodecManager(mContext, surface, width, height);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

//        if (codecManager != null) {
//            codecManager.enabledYuvData(true);
//            byte[] data = codecManager.getRgbaData();
//            RonLog.LogD("test onSurfaceTextureUpdated " + (data != null ? data.length : 0));
//        }
    }

    // MQTT初始化
    private void Mqtt_init()
    {
        try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, mqtt_id,
                    new MemoryPersistence());
            //MQTT的连接设置


            //设置回调
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("connectionLost----------");
                    //startReconnect();
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }
                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    try {
                        //subscribe后得到的消息会执行到这里面
                        //                    System.out.println("messageArrived----------");
                        Message msg = new Message();
                        msg.what = 3;   //收到消息标志位
                        //                    msg.obj = topicName + "---" +message.toString();
                        msg.obj = message.toString();
                        handler.sendMessage(msg);    // hander 回传
                        //                    toast(message.toString());
                    }
                    catch (Exception e){
                        toast(e.toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MQTT连接函数
    private void Mqtt_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    toast("0");
                    if(!(client.isConnected()) )  //如果还未连接
                    {
                        MqttConnectOptions options = new MqttConnectOptions();
                        //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
                        options.setCleanSession(false);
                        //设置连接的用户名
                        //            options.setUserName(userName);
                        //            //设置连接的密码
                        //            options.setPassword(passWord.toCharArray());
                        // 设置超时时间 单位为秒
                        options.setConnectionTimeout(10);
                        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
                        options.setKeepAliveInterval(600);

                        client.connect(options);
                        Message msg = new Message();
                        msg.what = 31;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 30;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    // MQTT重新连接函数
    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    Mqtt_connect();
                }
            }
        }, 0*1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    // 订阅函数    (下发任务/命令)
    private void publishmessageplus(String topic,String message2)
    {
        if (client == null || !client.isConnected()) {
            return;
        }
        MqttMessage message = new MqttMessage();
        message.setPayload(message2.getBytes());
        try {
            client.publish(topic,message);
        } catch (MqttException e) {

            e.printStackTrace();
        }
    }
    /* ========================================================================================== */
}
