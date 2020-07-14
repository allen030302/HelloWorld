package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static android.content.Context.NOTIFICATION_SERVICE;


public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);



        getPermissionsCamera();

        SurfaceView surfaceView;
        TextView textView;
        CameraSource cameraSource;
        BarcodeDetector barcodeDetector;

        surfaceView=(SurfaceView)view.findViewById(R.id.surfaceView);
        textView=(TextView)view.findViewById(R.id.textQR);
        barcodeDetector = new BarcodeDetector.Builder(this.getContext()).setBarcodeFormats(Barcode.QR_CODE).build();
        //cameraSource=new CameraSource.Builder(this.getActivity(),barcodeDetector).setRequestedPreviewSize(1000,1000).build();
        cameraSource = new CameraSource.Builder(this.getActivity(),barcodeDetector).setAutoFocusEnabled(true).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){

            @Override
            public void  surfaceCreated(SurfaceHolder surfaceHolder){
                if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                        !=PackageManager.PERMISSION_GRANTED)
                    return;
                try{
                    cameraSource.start(surfaceHolder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i1,int i2){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder){
                cameraSource.stop();
            }

        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
                            sendsystemmessage(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

        return view;
    }

    private Notification notification;
    private NotificationManager manager;
    long[] vibrate = {0,100,200,300};

    private void sendsystemmessage(String message){
        //Step1. 初始化NotificationManager，取得Notification服務
        NotificationManager mNotificationManager = (NotificationManager)this.getActivity().getSystemService(NOTIFICATION_SERVICE);
        //Step2. 設定當按下這個通知之後要執行的activity
        Intent notifyIntent = new Intent(this.getActivity(), OrderPage.class);
        notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent = PendingIntent.getActivity(this.getActivity(), 0, notifyIntent, 0);
        //Step3. 透過 Notification.Builder 來建構 notification，
        //並直接使用其.build() 的方法將設定好屬性的 Builder 轉換
        //成 notification，最後開始將顯示通知訊息發送至狀態列上。
        Notification notification
                = new Notification.Builder(this.getActivity())
                .setContentIntent(appIntent)
                .setSmallIcon(R.drawable.android) // 設置狀態列裡面的圖示（小圖示）　　
                .setLargeIcon(BitmapFactory.decodeResource(this.getActivity().getResources(), R.drawable.calculator)) // 下拉下拉清單裡面的圖示（大圖示）
                .setTicker("notification on status bar.") // 設置狀態列的顯示的資訊
                .setWhen(System.currentTimeMillis())// 設置時間發生時間
                .setAutoCancel(false) // 設置通知被使用者點擊後是否清除  //notification.flags = Notification.FLAG_AUTO_CANCEL;
                .setContentTitle("Notification Title") // 設置下拉清單裡的標題
                .setContentText(message)// 設置上下文內容
                .setOngoing(true)      //true使notification變為ongoing，用戶不能手動清除// notification.flags = Notification.FLAG_ONGOING_EVENT; notification.flags = Notification.FLAG_NO_CLEAR;
                .setDefaults(Notification.DEFAULT_ALL) //使用所有默認值，比如聲音，震動，閃屏等等
//                 .setDefaults(Notification.DEFAULT_VIBRATE) //使用默認手機震動提示
//                 .setDefaults(Notification.DEFAULT_SOUND) //使用默認聲音提示
//                 .setDefaults(Notification.DEFAULT_LIGHTS) //使用默認閃光提示
//                 .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND) //使用默認閃光提示 與 默認聲音提示

                .setVibrate(vibrate) //自訂震動長度
//                 .setSound(uri) //自訂鈴聲
//                 .setLights(0xff00ff00, 300, 1000) //自訂燈光閃爍 (ledARGB, ledOnMS, ledOffMS)

                .build();
        // 將此通知放到通知欄的"Ongoing"即"正在運行"組中
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        // 表明在點擊了通知欄中的"清除通知"後，此通知不清除，
        // 經常與FLAG_ONGOING_EVENT一起使用
        notification.flags = Notification.FLAG_NO_CLEAR;

        //閃爍燈光
        notification.flags = Notification.FLAG_SHOW_LIGHTS;

        // 重複的聲響,直到用戶響應。
        notification.flags = Notification.FLAG_INSISTENT;


        // 把指定ID的通知持久的發送到狀態條上.
        mNotificationManager.notify(0, notification);

        // 取消以前顯示的一個指定ID的通知.假如是一個短暫的通知，
        // 試圖將之隱藏，假如是一個持久的通知，將之從狀態列中移走.
//              mNotificationManager.cancel(0);

        //取消以前顯示的所有通知.
//              mNotificationManager.cancelAll();
    }

    public void getPermissionsCamera(){
        if(ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            new AlertDialog.Builder(this.getActivity())
                    .setCancelable(false)
                    .setTitle("需要相機權限")
                    .setMessage("你是不是傻？不給我權限我要怎麼拍照？")
                    .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                        }
                    })
                    .show();
            //ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.CAMERA},1);
        }
    }

}