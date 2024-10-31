package com.luwu.xgo_robot.mActivity;

import static com.luwu.xgo_robot.mMothed.PublicMethod.XGORAM_ADDR.updateHex;
import static com.luwu.xgo_robot.mMothed.PublicMethod.hideBottomUIMenu;
import static com.luwu.xgo_robot.mMothed.PublicMethod.isBluetoothConnect;
import static com.luwu.xgo_robot.mMothed.PublicMethod.localeLanguage;


import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.WebService.DownloadListener;
import com.luwu.xgo_robot.WebService.DownloadUtil;
import com.luwu.xgo_robot.mMothed.PublicMethod;
import com.luwu.xgo_robot.mMothed.mToast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;

public class DownloadActivity extends AppCompatActivity {
//    private Button downloadBtnDownload, downloadBtnUpdate;
    private ImageButton downloadBtnBack;
    private TextView hexVersionNow;
    private static TextView recommend;

    private static AlertDialog alertDialog = null;
    private static ProgressBar mProgressBar;

    private long mlastClickTime;
    private static final String file_url = "http://47.252.22.82/";

//    private static final String mini_file_3 = "xgomini3.bin";
//    private static final String lite_file_3 = "xgolite3.bin";
//    private static final String mini_file_4 = "xgomini4.bin";
//    private static final String lite_file_4 = "xgolite4.bin";

    private static final String mini_file = "xgomini.bin";
    private static final String lite_file = "xgolite.bin";

//    private static final String mini_file_3 = "xgomini3.bin";
//    private static final String lite_file_3 = "xgolite3.bin";
//    private static final String mini_file_4 = "xgomini4.bin";
//    private static final String lite_file_4 = "xgolite4.bin";
    private static final String version_file = "version.txt";
    private static boolean downloaded = false;

    private static boolean updated = false;

    private static final int updateHexRespondBytes = 85;

    private static final int mtu_size = 500;

    private getVersionThread mGetVersionThread;
//    private static boolean getResponse = false;
//    private Button downloadTest;
//    private ImageButton downloadImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        InitView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (updated){
//            downloadBtnDownload.setVisibility(View.GONE);
//            downloadBtnUpdate.setVisibility(View.GONE);
//        } else {
//            if (downloaded) {
//                downloadBtnDownload.setVisibility(View.GONE);
//                downloadBtnUpdate.setVisibility(View.VISIBLE);
//            } else {
//                downloadBtnUpdate.setVisibility(View.GONE);
//                downloadBtnDownload.setVisibility(View.VISIBLE);
//            }
//        }
        hideBottomUIMenu(DownloadActivity.this);
    }

    protected void onDestroy() {
        super.onDestroy();
        PublicMethod.XGORAM_VALUE.versionNumber = "";
        mGetVersionThread.interrupt();
        finish();
    }


    @SuppressLint("SetTextI18n")
    private void InitView() {

        BtnListener mListener = new BtnListener();
//        downloadBtnDownload = findViewById(R.id.downloadBtnDownload);
//        downloadBtnUpdate = findViewById(R.id.downloadBtnUpdate);
        downloadBtnBack = findViewById(R.id.downloadBtnBack);
        hexVersionNow = findViewById(R.id.hexVersionNow);
        recommend = findViewById(R.id.recommend);

//        downloadBtnDownload.setOnClickListener(mListener);
//        downloadBtnUpdate.setOnClickListener(mListener);
        downloadBtnBack.setOnClickListener(mListener);

        updated = true;
        downloaded = false;
//        downloadBtnUpdate.setVisibility(View.GONE);
//        downloadBtnDownload.setVisibility(View.GONE);
//        updated = false;
//        downloaded = true;
//        downloadBtnDownload.setVisibility(View.GONE);
//        downloadBtnUpdate.setVisibility(View.VISIBLE);

        alertDialog = new AlertDialog.Builder(this).create();

        mGetVersionThread = new getVersionThread();
        mGetVersionThread.start();


    }
    private class getHexThread extends Thread {
        @Override
        public void run() {
            while (currentThread().isAlive()) {
                try {
                    System.out.println(String.valueOf(PublicMethod.XGORAM_VALUE.updateHex));
                    Thread.sleep(1000);
                } catch (Exception ignored) {

                }
            }
        }
    }

    private class getVersionThread extends Thread {
        @Override
        public void run() {
            while (currentThread().isAlive()) {
                try {
                    if (!PublicMethod.XGORAM_VALUE.versionNumber.equals("") && isBluetoothConnect ) {
                        switch(localeLanguage){
                            case "zh":
                                hexVersionNow.setText("固件版本：" + PublicMethod.XGORAM_VALUE.versionNumber);
                                break;
                            default:
                                hexVersionNow.setText("Current Firmware Version: " + PublicMethod.XGORAM_VALUE.versionNumber);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
//                            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 123);
                        }
//                        getVersion(file_url + version_file);
                        break;
                    }
                    Thread.sleep(200);
                } catch (Exception ignored) {

                }
            }
        }
    }
    private class BtnListener implements View.OnClickListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.downloadBtnDownload:
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
//                    }
////                    downloadHex(file_url + mini_file);
//                    switch (PublicMethod.XGORAM_VALUE.productType){
//                        case 0:  //mini
////                            switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
////                                case "3": downloadHex(file_url + mini_file_3);  break;
////                                case "4": downloadHex(file_url + mini_file_4);  break;
////                                default:
////                                    switch(localeLanguage){
////                                        case "zh":
////                                            mToast.show(DownloadActivity.this, "设备型号未知，请刷新");
////                                            break;
////                                        default:
////                                            mToast.show(DownloadActivity.this, "Product type is unknown, please reopen this page");
////                                    }
////                                    break;
////                            }
//                             switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
//                                case "1":
//                                    switch(localeLanguage){
//                                        case "zh":
//                                            mToast.show(DownloadActivity.this, "设备无法升级");
//                                            break;
//                                        default:
//                                            mToast.show(DownloadActivity.this, "Product can't be upgraded");
//                                    }
//                                    break;
//                                case "3": case "4":
//                                    downloadHex(file_url + mini_file);  break;
//                                default:
//                                    switch(localeLanguage){
//                                        case "zh":
//                                            mToast.show(DownloadActivity.this, "设备型号未知，请刷新");
//                                            break;
//                                        default:
//                                            mToast.show(DownloadActivity.this, "Product type is unknown, please reopen this page");
//                                    }
//                                    break;
//                            }
//                            break;
//                        case 1: //lite
////                            switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
////                                case "3": downloadHex(file_url + lite_file_3);  break;
////                                case "4": downloadHex(file_url + lite_file_4);  break;
////                                default:
////                                    switch(localeLanguage){
////                                        case "zh":
////                                            mToast.show(DownloadActivity.this, "设备型号未知，请刷新");
////                                            break;
////                                        default:
////                                            mToast.show(DownloadActivity.this, "Product type is unknown, please reopen this page");
////                                    }
////                                    break;
////                            }
//                            switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
//                                case "1":
//                                    switch(localeLanguage){
//                                        case "zh":
//                                            mToast.show(DownloadActivity.this, "设备无法升级");
//                                            break;
//                                        default:
//                                            mToast.show(DownloadActivity.this, "Product can't be upgraded");
//                                    }
//                                    break;
//                                case "3": case "4":
//                                    downloadHex(file_url + lite_file);  break;
//                                default:
//                                    switch(localeLanguage){
//                                        case "zh":
//                                            mToast.show(DownloadActivity.this, "设备型号未知，请刷新");
//                                            break;
//                                        default:
//                                            mToast.show(DownloadActivity.this, "Product type is unknown, please reopen this page");
//                                    }
//                                    break;
//                            }
//                            break;
//                        default:
//                            switch(localeLanguage){
//                                case "zh":
//                                    mToast.show(DownloadActivity.this, "设备型号未知，请刷新");
//                                    break;
//                                default:
//                                    mToast.show(DownloadActivity.this, "Product type is unknown, please reopen this page");
//                            }
//                            break;
//                    }
//
//                    break;
//                case R.id.downloadBtnUpdate:
//                    MainActivity.MsgThreadStop();
//                    MainActivity.HexThreadWork();
//                    MainActivity.addHexMessage(new byte[]{updateHex, 0x01});
//                    getResponseThread gpThread = new getResponseThread();
//                    gpThread.start();
//
//                    switch(localeLanguage){
//                        case "zh":
//                            recommend.setText("准备升级，请勿退出该页面");
//                            break;
//                        default:
//                            recommend.setText("Prepare to upgrade, Please do not exit this page");
//                    }
//
//
//                    break;
                case R.id.downloadBtnBack:
                    try{
                        MainActivity.MsgThreadWork();
                        MainActivity.HexThreadStop();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    finish();
                    break;
//                case R.id.downloadImg:
//                    MainActivity.MsgThreadStop();
//                    MainActivity.HexThreadWork();
//                    MainActivity.addHexMessage(new byte[]{updateHex, 0x01});
////                    WriteThread wt = new WriteThread();
////                    wt.start();
//                    recommend.setText("已发送");
//                    break;
//                case R.id.downloadTest:
//                    getResponseThread gpThread2 = new getResponseThread();
//                    gpThread2.start();
//                    break;
            }
        }
    }

//    private void downloadHex(String file_url) {
//        //下载相关
//        String TAG = "Download";
//        String external_dir = this.getExternalFilesDir(null).getAbsolutePath();
//        DownloadUtil downloadUtil = new DownloadUtil(external_dir);
//        downloadUtil.downloadFile(file_url, new DownloadListener() {
//            @Override
//            public void onStart() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        switch(localeLanguage){
//                            case "zh":
//                                mToast.show(DownloadActivity.this, "开始下载，请勿切换页面");
//                                break;
//                            default:
//                                mToast.show(DownloadActivity.this, "Start Downloading, Please do not exit this page");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onProgress(final int currentLength) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        downloadBtnDownload.setText(String.valueOf(currentLength) + "%");
////                        switch(localeLanguage){
////                            case "zh":
////                                mToast.show(DownloadActivity.this, "下载进度：" + String.valueOf(currentLength) + "%");
////                                break;
////                            default:
////                                mToast.show(DownloadActivity.this, "Downloading, " + String.valueOf(currentLength) + "%");
////                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFinish(final String localPath) {
//                Log.e(TAG, "onFinish: " + localPath);
//                downloaded = true;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        switch(localeLanguage){
//                            case "zh":
//                                mToast.show(DownloadActivity.this, "下载完成");
//                                break;
//                            default:
//                                mToast.show(DownloadActivity.this, "Download Finishing");
//                        }
//                        if (downloaded) {
//                            downloadBtnUpdate.setVisibility(View.VISIBLE);
//                            downloadBtnDownload.setVisibility(View.GONE);
//                            System.out.println("update visible");
//                        } else {
//                            downloadBtnDownload.setVisibility(View.VISIBLE);
//                            downloadBtnUpdate.setVisibility(View.GONE);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(final String erroInfo) {
//                Log.e(TAG, "onFailure: " + erroInfo);
//                downloaded = false;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        switch(localeLanguage){
//                            case "zh":
//                                mToast.show(DownloadActivity.this, "下载失败");
//                                break;
//                            default:
//                                mToast.show(DownloadActivity.this, "Download Fail");
//                        }
//                        if (downloaded) {
//                            downloadBtnDownload.setVisibility(View.GONE);
//                            downloadBtnUpdate.setVisibility(View.VISIBLE);
//                        } else {
//                            downloadBtnUpdate.setVisibility(View.GONE);
//                            downloadBtnDownload.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//            }
//        });
//    }
    private void getVersion(String file_url) {
        //下载相关
        String TAG = "Download";
        String external_dir = this.getExternalFilesDir(null).getAbsolutePath();
        DownloadUtil downloadUtil = new DownloadUtil(external_dir);
        downloadUtil.downloadFileRead(file_url, new DownloadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(final int currentLength) {

            }

            @Override
            public void onFinish(final String localPath) {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
//                        switch(localeLanguage){
//                            case "zh":
//                                mToast.show(DownloadActivity.this, "已获取最新版本号");
//                                break;
//                            default:
//                                mToast.show(DownloadActivity.this, "Got the latest version number");
//                        }
                        String version_latest = null;
                        int i;
                        int x_index;
                        switch (PublicMethod.XGORAM_VALUE.productType){
                            case 0: // mini
                                switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
                                    case "3": case "4":
                                        i = localPath.indexOf("xgomini:");
                                        x_index = localPath.indexOf('x', i+8);// xgolite:3.1.4xgolite:4.0.4
                                        if (i != -1 && x_index !=-1) {
                                            version_latest = "M-" + localPath.substring(i+8, x_index);
                                        } else if (i != -1){
                                            version_latest = "M-" + localPath.substring(i+8);
                                        }
                                        break;
                                    default:

                                        break;
                                }

                                break;

                            case 1: //lite
                                switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){

                                    case "3": case "4":
                                        i = localPath.indexOf("xgolite:");
                                        x_index = localPath.indexOf('x', i+8);// example, xgolite3:3.1.4xgolite4:4.0.4xgomini3:3.1.4xgomini4:4.0.4
                                        if (i != -1 && x_index !=-1) {
                                            version_latest = "L-" + localPath.substring(i+8, x_index);
                                        } else if (i != -1){
                                            version_latest = "L-" + localPath.substring(i+8);
                                        }
                                        break;
                                    default:

                                        break;
                                }

                                break;
                            case 2: //rider
                                switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
                                    case "3": case "4":
                                        i = localPath.indexOf("xgorider:");
                                        x_index = localPath.indexOf('x', i+8);// xgolite:3.1.4xgolite:4.0.4
                                        if (i != -1 && x_index !=-1) {
                                            version_latest = "R-" + localPath.substring(i+8, x_index);
                                        } else if (i != -1){
                                            version_latest = "R-" + localPath.substring(i+8);
                                        }
                                        break;
                                    default:

                                        break;
                                }

                            default:
                                break;
                        }
                        if (version_latest!=null){
//                            if (!PublicMethod.XGORAM_VALUE.versionNumber.contains(version_latest)){
//                                updated = false;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (updated) {
//                                            downloadBtnDownload.setVisibility(View.GONE);
//                                            downloadBtnUpdate.setVisibility(View.GONE);
//                                        } else {
//                                            if (downloaded) {
//                                                downloadBtnDownload.setVisibility(View.GONE);
//                                                downloadBtnUpdate.setVisibility(View.VISIBLE);
//                                            } else {
//                                                downloadBtnUpdate.setVisibility(View.GONE);
//                                                downloadBtnDownload.setVisibility(View.VISIBLE);
//                                            }
//                                        }
//                                    }
//                                });
//                                switch(localeLanguage){
//                                    case "zh":
//                                        recommend.setText("最新版本为" + version_latest + "， 建议升级至最新版本");
//                                        break;
//                                    default:
//                                        recommend.setText("Latest Firmware Version: " + version_latest + ", recommend to upgrade to the latest version\n");
//                                }
//                            } else {
//                                updated = true;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (updated) {
//                                            downloadBtnDownload.setVisibility(View.GONE);
//                                            downloadBtnUpdate.setVisibility(View.GONE);
//                                        } else {
//                                            if (downloaded) {
//                                                downloadBtnDownload.setVisibility(View.GONE);
//                                                downloadBtnUpdate.setVisibility(View.VISIBLE);
//                                            } else {
//                                                downloadBtnUpdate.setVisibility(View.GONE);
//                                                downloadBtnDownload.setVisibility(View.VISIBLE);
//                                            }
//                                        }
//                                    }
//                                });
//                                switch (localeLanguage) {
//                                    case "zh":
//                                        recommend.setText("已经是最新版本");
//                                        break;
//                                    default:
//                                        recommend.setText("Current firmware is the latest version");
//                                }
//                            }
                        }
                        else {
                            switch(localeLanguage){
                                case "zh":
                                    recommend.setText("无法获取最新版本信息");
                                    break;
                                default:
                                    recommend.setText("Getting the latest version information failed");
                            }
                        }
                    }
                });




            }

            @Override
            public void onFailure(final String erroInfo) {
                Log.e(TAG, "onFailure: " + erroInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch(localeLanguage){
                            case "zh":
                                mToast.show(DownloadActivity.this, "获取最新版本号失败");
                                break;
                            default:
                                mToast.show(DownloadActivity.this, "Fail to get the latest version number");
                        }
                    }
                });


            }
        });
    }

//    private class getResponseThread extends Thread {
//        @Override
//        public void run() {
//            while (currentThread().isAlive()) {
////                System.out.println(String.valueOf(PublicMethod.XGORAM_VALUE.updateHex));
//                if (PublicMethod.XGORAM_VALUE.updateHex == updateHexRespondBytes) {
//                    PublicMethod.XGORAM_VALUE.updateHex = 0;
//                    try {
//                        sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (updateHex()){
//                        try {
//                            try {
//                                sleep(1500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            MainActivity.MsgThreadWork();
//                            MainActivity.HexThreadStop();
//
//                            switch(localeLanguage){
//                                case "zh":
//                                    recommend.setText("升级成功");
//                                    break;
//                                default:
//                                    recommend.setText("Upgrade finished");
//                            }
//                            updated = true;
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    downloadBtnUpdate.setVisibility(View.GONE);
//                                    downloadBtnDownload.setVisibility(View.GONE);
//                                }
//
//                            });
//                            sleep(3000);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    switch(localeLanguage){
//                                        case "zh":
//                                            mToast.show(DownloadActivity.this, "固件升级成功，机器狗将自动重启，请重新连接蓝牙");
//                                            break;
//                                        default:
//                                            mToast.show(DownloadActivity.this, "Firmware upgrade success. Robot will restart, please reconnect.");
//                                    }
//                                }
//                            });
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//                } else {
//                    try {
//                        sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    @SuppressLint("SetTextI18n")
    private boolean updateHex() {
        if (isBluetoothConnect) {
//            switch(localeLanguage){
//                case "zh":
//                    recommend.setText("开始升级，请勿退出该页面");
//                    break;
//                default:
//                    recommend.setText("Start to upgrade, please don't exit");
//            }

            //        while(true){
            //            if (MainActivity.getMsgListState() == 0){

//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                    if (PublicMethod.XGORAM_VALUE.updateHex == updateHexRespondBytes) {
//                        mToast.show(DownloadActivity.this, "成功");
//                        break;
//                    } else {
//                        Thread.sleep(1000);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            try {
                String file_name;
                switch (PublicMethod.XGORAM_VALUE.productType) {


                    case 0:
                        switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
                            case "3": case "4": file_name = mini_file;  break;
                            default:
                                switch(localeLanguage){
                                    case "zh":
                                        recommend.setText("设备型号未知，请刷新");
                                        break;
                                    default:
                                        recommend.setText("Product type is unknown, please reopen this page");
                                }
                                return false;
                        }
                        break;
                    case 1:
                        switch (PublicMethod.XGORAM_VALUE.versionNumber.substring(2, 3)){
                            case "3": case "4": file_name = lite_file;  break;
                            default:
                                switch(localeLanguage){
                                    case "zh":
                                        recommend.setText("设备型号未知，请刷新");
                                        break;
                                    default:
                                        recommend.setText("Product type is unknown, please reopen this page");
                                }
                                return false;
                        }
                        break;
                    default:
                        switch(localeLanguage){
                            case "zh":
                                recommend.setText("设备型号未知，请刷新");
                                break;
                            default:
                                recommend.setText("Product type is unknown, please reopen this page");
                        }
                        return false;
                }


                // file
                String file_path = this.getExternalFilesDir(null).getAbsolutePath() + "/DownloadFile/" + file_name;
                File file = new File(file_path);
                if (file.exists() && file.isFile()) {

                    FileInputStream fis = new FileInputStream(file);
                    FileChannel fc = fis.getChannel();
                    long wholeLength = fc.size();
                    DataInputStream inputStream = new DataInputStream(fis);
                    //                    InputStream inputStream = getResources().openRawResource(R.raw.xgomini);


                    byte[] data = new byte[mtu_size];
                    int n = -1;
                    long i = -1;
                    boolean showTag = true;
                    long sentSize = 0;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.show();
                            alertDialog.setCancelable(false);
                            Window window = alertDialog.getWindow();
                            if (window != null) {
                                window.setContentView(R.layout.layout_progress);
                                window.setGravity(Gravity.CENTER);
                                mProgressBar = window.findViewById(R.id.progressProgressBar);
                            }
                        }
                    });

                    while ((sentSize < wholeLength) && (n = inputStream.read(data, 0, Math.min(mtu_size, (int) (wholeLength - sentSize)))) != -1) {
                        i++;
                        MainActivity.sendHugeMessage(data);
                        Thread.sleep(12);
//                        if ((mtu_size * 100L * i / wholeLength) % 10 == 0) {
//                            if (showTag) {
//                                showTag = false;
                                int current = (int) (mtu_size * 100L * i / wholeLength);
                                //                                    DownloadActivity.this.runOnUiThread(new Runnable() {
                                //                                        public void run() {
                                //                                            switch (localeLanguage) {
                                //                                                case "zh":
                                //                                                    recommend.setText("固件烧录中，请勿退出该页面：" + current);
                                //                                                    break;
                                //                                                default:
                                //                                                    recommend.setText("Updating, please keep this view: " + current);
                                //                                            }
                                //                                        }
                                //                                    });
//                                switch(localeLanguage){
//                                    case "zh":
//                                        recommend.setText("升级中，请勿退出该页面 " + current);
//                                        break;
//                                    default:
//                                        recommend.setText("Upgrading, please do not exit this page " + current);
//                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(current);
                                    }
                                });

                                //                                    Thread.sleep(1000);
//                            }
//                        } else {
//                            showTag = true;
//                        }
                        sentSize += data.length;
//                        System.out.println(i + " : " + data.length);

                        if ((int) (wholeLength - sentSize) < mtu_size) {
                            data = new byte[(int) (wholeLength - sentSize)];
                        }
                    }
                    inputStream.close();
                    fis.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.cancel();
                        }

                    });

                    return true;
                } else {

                    switch(localeLanguage){
                        case "zh":
                            recommend.setText("文件不存在");
                            break;
                        default:
                            recommend.setText("File is not exist");
                    }
                    return false;
                }

            } catch (Exception e) {
                alertDialog.cancel();
                e.printStackTrace();
            }
            //                break;
            //            }

            //        }

        } else {
            switch(localeLanguage){
                case "zh":
                    recommend.setText("请连接XGO");
                    break;
                default:
                    recommend.setText("Please connect XGO");
            }
            return false;
        }
        return false;
    }

    //标定点击间隔为1s
    private boolean isFastDoubleClick(){
        long time = System.currentTimeMillis();
        long timeD = time - this.mlastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;   }
        this.mlastClickTime = time;
        return false;
    }

}
