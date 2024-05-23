package com.luwu.xgo_robot.mActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luwu.xgo_robot.AppContext;
import com.luwu.xgo_robot.BlueTooth.BleActivity;
import com.luwu.xgo_robot.BlueTooth.BleConnectedActivity;
import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mMothed.PublicMethod;
import com.luwu.xgo_robot.mMothed.mToast;

import java.util.Locale;

import static com.luwu.xgo_robot.mActivity.PrivacyActivity.HTML_TEXT;
import static com.luwu.xgo_robot.mActivity.PrivacyActivity.HTML_TEXT_EN;
import static com.luwu.xgo_robot.mActivity.PrivacyActivity.HTML_TEXT_TITLE;
import static com.luwu.xgo_robot.mActivity.PrivacyActivity.HTML_TEXT_TITLE_EN;
import static com.luwu.xgo_robot.mMothed.PublicMethod.hideBottomUIMenu;
import static com.luwu.xgo_robot.mMothed.PublicMethod.isBluetoothConnect;
import static com.luwu.xgo_robot.mMothed.PublicMethod.localeLanguage;


public class MainActivity extends AppCompatActivity {
//    BannerViewLayout mBanner;
//    PageIndicator mIndicator;
//    OnBannerListener mOnBannerListener = new OnBannerListener();
//    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener();

    private ImageButton mainBtnConnectImg;
    private Button mainBtnConnectText;
    private ImageButton mainBtnSetting, mainBtnAbout, mainBtnDebug, mainBtnDownload;
    private TextView mainTextSetting, mainTextAbout, mainTextDebug, mainTextDownload;

    private ImageButton mainBtnStart;
    private MyBtnListener myBtnListener;
    private long mExitTime = 0;//点击两次返回键返回
//    private String mainLanguage = "";
    private Handler mHandler;
//    private static getProductTypeThread getProductType; //处理产品型号读取线程. 停用
    private static askVersionNumberThread getVersionNumber;; //处理版本号读取线程

    private static getVersionThread mGetVersionThread;
//    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateLocale();
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        InitView();
        privacyFirst();
    }

    @Override
    protected void onResume() {
        updateLocale();
        super.onResume();
        hideBottomUIMenu(MainActivity.this);
        SharedPreferences info = getSharedPreferences("xgo_setting", MODE_PRIVATE);
        String setting_develop = info.getString("setting_develop", "no");
        if (setting_develop.equals("yes")) {
            findViewById(R.id.mainLayoutDeveloper).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.mainLayoutDeveloper).setVisibility(View.GONE);
        }

        getVersionNumber = new askVersionNumberThread();
        getVersionNumber.start();
        mGetVersionThread = new getVersionThread();
        mGetVersionThread.start();


        if (AppContext.getmBleClient().isConnected()){
            mainBtnConnectImg.setImageDrawable(getResources().getDrawable((R.drawable.main_bluetooth_connect)));
            mainBtnConnectText.setText(AppContext.getmBleClient().getBleNameConnected());
        } else {
            mainBtnConnectImg.setImageDrawable(getResources().getDrawable((R.drawable.main_bluetooth_dis)));
            mainBtnConnectText.setText(R.string.pls_connect_bt);
        }
        switch (localeLanguage){
            case "zh":
                mainTextSetting.setText("设置");
                mainTextAbout.setText("关于");
                mainTextDebug.setText("标定");
                mainTextDownload.setText("下载");
                break;
            default:
                mainTextSetting.setText("Setting");
                mainTextAbout.setText("About");
                mainTextDebug.setText("Calibrate");
                mainTextDownload.setText("Hex");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getVersionNumber.interrupt();
        mGetVersionThread.interrupt();
        finish();
    }



    //控件初始化
    private void InitView() {
        myBtnListener = new MyBtnListener();

        mainBtnConnectImg = findViewById(R.id.mainBtnConnectImg);
        mainBtnConnectText = findViewById(R.id.mainBtnConnectText);
        mainBtnSetting = findViewById(R.id.mainBtnSetting);
        mainBtnAbout = findViewById(R.id.mainBtnAbout);
        mainBtnDebug = findViewById(R.id.mainBtnDebug);
        mainBtnDownload = findViewById(R.id.mainBtnDownload);
        mainBtnStart = findViewById(R.id.mainBtnStart);
        mainTextSetting = findViewById(R.id.mainTextSetting);
        mainTextAbout = findViewById(R.id.mainTextAbout);
        mainTextDebug = findViewById(R.id.mainTextDebug);
        mainTextDownload = findViewById(R.id.mainTextDownload);

        mainBtnConnectImg.setOnClickListener(myBtnListener);
        mainBtnConnectText.setOnClickListener(myBtnListener);
        mainBtnSetting.setOnClickListener(myBtnListener);
        mainBtnAbout.setOnClickListener(myBtnListener);
        mainBtnDebug.setOnClickListener(myBtnListener);
        mainBtnDownload.setOnClickListener(myBtnListener);
        mainBtnStart.setOnClickListener(myBtnListener);
//        mainBtnStart.setVisibility(View.GONE);

//        mBanner = findViewById(R.id.main_banner);
//        mIndicator = findViewById(R.id.main_indicator);
//        mBanner.setOnPageChangeListener(mOnPageChangeListener);
//        mBanner.setOnBannerListener(mOnBannerListener);
//        List<Drawable> url = new ArrayList<>();
//
//        //url.add(this.getResources().getDrawable(R.drawable.main_beta));
//        switch(localeLanguage){
//            case "zh":
//                mainLanguage = "zh";
//                url.add(this.getResources().getDrawable(R.drawable.main_actor));
//                url.add(this.getResources().getDrawable(R.drawable.main_body));
//                url.add(this.getResources().getDrawable(R.drawable.main_leg));
////                url.add(this.getResources().getDrawable(R.drawable.main_motor));
//                break;
//            default:
//                mainLanguage = "en";
//                url.add(this.getResources().getDrawable(R.drawable.main_actor_en));
//                url.add(this.getResources().getDrawable(R.drawable.main_body_en));
//                url.add(this.getResources().getDrawable(R.drawable.main_leg_en));
////                url.add(this.getResources().getDrawable(R.drawable.main_motor_en));
//        }
//        int current = (url.size() - 1)/2;
//        mBanner.setBannerView(url).setStartView(current).start();
//        mIndicator.setNumPages(url.size());
//        mIndicator.setCurrentPage(current);
    }

    //监听事件
    private class MyBtnListener implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainBtnConnectImg:
                case R.id.mainBtnConnectText:
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {//设备不支持蓝牙
                        switch(localeLanguage){
                            case "zh":
                                mToast.show(MainActivity.this, "该设备不支持蓝牙");
                                break;
                            default:
                                mToast.show(MainActivity.this, "The device does not support Bluetooth");
                        }
                        break;
                    }
                    if (!AppContext.getmBleClient().isConnected()) {
//                        if (DeviceUtil.isTabletDevice(MainActivity.this)) {
//                            Intent intent3 = new Intent(MainActivity.this, BleSearchActivity.class);
//                            startActivity(intent3);
//                        } else {
//                            Intent intent3 = new Intent(MainActivity.this, BleActivity.class);
//                            startActivity(intent3);
//                        }
                        // 在ble_activity中进行权限申请，再根据需求进入ble_search
                        Intent intent3 = new Intent(MainActivity.this, BleActivity.class);
                        startActivity(intent3);
                    } else {
                        Intent intent4 = new Intent(MainActivity.this, BleConnectedActivity.class);
                        startActivity(intent4);
                    }
                    break;
                case R.id.mainBtnSetting:
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
//                    intent = new Intent(MainActivity.this, RiderActivity.class);
//                    startActivity(intent);
                    break;
                case R.id.mainBtnAbout:
                    intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mainBtnDebug:
                    intent = new Intent(MainActivity.this, DebugActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mainBtnDownload:
                    intent = new Intent(MainActivity.this, DownloadActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mainBtnStart:
                    if (!PublicMethod.XGORAM_VALUE.versionNumber.equals("") & isBluetoothConnect) {
                        switch (PublicMethod.XGORAM_VALUE.productType) {
                            case 0:
                            case 1:
                                intent = new Intent(MainActivity.this, ControlActivity.class);
                                intent.putExtra("productType", PublicMethod.XGORAM_VALUE.productType);
                                intent.putExtra("versionNumberFirst", Integer.parseInt(PublicMethod.XGORAM_VALUE.versionNumber.substring(2,3)));
                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(MainActivity.this, RiderActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                default:
                    break;
            }
        }

    }



    //供外界调用
    public static void MsgThreadStop(){
        AppContext.getmBleClient().MsgThreadStop();
    }

    public static void MsgThreadWork(){
        AppContext.getmBleClient().MsgThreadWork();

    }

    public static void HexThreadStop(){
        AppContext.getmBleClient().HexThreadStop();
    }

    public static void HexThreadWork(){
        AppContext.getmBleClient().HexThreadWork();

    }


    public static int getMsgListState(){
        return AppContext.getmBleClient().getMsgListLength();
    }

    public static int getHexListState(){
        return AppContext.getmBleClient().getHexListLength();
    }

    public static void sendHugeMessage(byte[] msg){AppContext.getmBleClient().sendHugeMessage(msg);}

    public static void addHexMessage(byte[] msg){AppContext.getmBleClient().addHexMessage(msg);}

    public static void addMessage(byte[] msg) {
        AppContext.getmBleClient().addMessage(msg);
    }

    public static void addMessageRespond(byte[] msg) {
        AppContext.getmBleClient().addMessageRespond(msg);
    }

    public static void addMessageRead(byte[] msg) {
//        System.out.println(Arrays.toString(msg));
        AppContext.getmBleClient().addMessageRead(msg);
    }

    //消息会被覆盖 不靠谱 仅在TestBtActivity中使用
    public static byte[] getMessageRespond() {
        return AppContext.getmBleClient().getMessageRespond();
    }

    //消息会被覆盖 不靠谱 仅在仅在TestBtActivity中使用
    public static byte[] getMessageRead() {
        return AppContext.getmBleClient().getMessageRead();
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent enent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SharedPreferences info = getSharedPreferences("xgo_setting", MODE_PRIVATE);
            String setting_close = info.getString("setting_close", "no");
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if ((adapter != null) && (adapter.isEnabled())) {
                    if (isBluetoothConnect) {
                        if (setting_close.equals("yes")) {
                            switch(localeLanguage){
                                case "zh":
                                    mToast.show(MainActivity.this, "再按一次退出app，将关闭蓝牙");
                                    break;
                                default:
                                    mToast.show(MainActivity.this, "Press again to exit the app and turn off Bluetooth");
                            }
                        } else {
                            switch(localeLanguage){
                                case "zh":
                                    mToast.show(MainActivity.this, "再按一次退出app，将断开连接");
                                    break;
                                default:
                                    mToast.show(MainActivity.this, "Press again to exit the app and disconnect");
                            }
                        }
                    } else {
                        if (setting_close.equals("yes")) {
                            switch(localeLanguage){
                                case "zh":
                                    mToast.show(MainActivity.this, "再按一次退出app，将关闭蓝牙");
                                    break;
                                default:
                                    mToast.show(MainActivity.this, "Press again to exit the app and turn off Bluetooth");
                            }
                        } else {
                            switch(localeLanguage){
                                case "zh":
                                    mToast.show(MainActivity.this, "再按一次退出app");
                                    break;
                                default:
                                    mToast.show(MainActivity.this, "Press again to exit the app");
                            }
                        }
                    }
                } else {
                    switch(localeLanguage){
                        case "zh":
                            mToast.show(MainActivity.this, "再按一次退出app");
                            break;
                        default:
                            mToast.show(MainActivity.this, "Press again to exit the app");
                    }
                }
                mExitTime = System.currentTimeMillis();
            } else {
                if (setting_close.equals("yes")) {
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    if (adapter != null) {
                        if (adapter.isEnabled()) {
                            adapter.disable();//关闭蓝牙
                        }
                    }
                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, enent);
    }

    //ViewPage的监听事件
//    public class OnBannerListener implements BannerViewLayout.OnBannerListener {
//        @Override
//        public void OnBannerClick(final int position) {
//            updateLocale();
//            Intent intent;
//            if (position == 0-1) {
//                intent = new Intent(MainActivity.this, ProgramActivity.class);
//                startActivity(intent);
//            } else if (position == 1-1) {
//                intent = new Intent(MainActivity.this, ActorActivity.class);
//                startActivity(intent);
//            } else if (position == 2-1) {
//                intent = new Intent(MainActivity.this, ControlActivity.class);
//                startActivity(intent);
//            } else if (position == 3-1) {
//                intent = new Intent(MainActivity.this, LegActivity.class);
//                startActivity(intent);
//            } else if (position == 4-1) {
//                intent = new Intent(MainActivity.this, MotorActivity.class);
//                startActivity(intent);
//            }
//            //mToast.show(MainActivity.this, "current position is " + position);
//        }
//    }
//
//    public class OnPageChangeListener implements ViewPager.OnPageChangeListener {
//        @Override
//        public void onPageScrolled(final int i, final float v, final int i1) {
//
//        }
//
//        @Override
//        public void onPageSelected(final int position) {
//            if (mIndicator != null) mIndicator.setCurrentPage(position);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(final int i) {
//
//        }
//    }
    private void updateLocale(){
        SharedPreferences languageInfo = getSharedPreferences("xgo_setting", MODE_PRIVATE);
        String setting_language = languageInfo.getString("setting_language", "auto");
        if (setting_language.equals("zh")) {
            localeLanguage = "zh";
        } else if(setting_language.equals("en")) {
            localeLanguage = "en";
        } else {//auto
            localeLanguage = Locale.getDefault().getLanguage();
            if (!localeLanguage.equals("zh")){
                localeLanguage = "en";
            }
        }
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        if (!configuration.locale.getLanguage().equals(localeLanguage)){
            String language = localeLanguage;
            if (localeLanguage.equals("zh")) {
                configuration.setLocale(Locale.CHINESE); // 设置为中文
            } else {
                configuration.setLocale(Locale.ENGLISH); // 设置为英文
                localeLanguage = "en";
            }
            DisplayMetrics metrics = new DisplayMetrics();
            resources.updateConfiguration(configuration, metrics); // 更新配置文件
        } else {

        }
    }



    private void privacyFirst(){
        SharedPreferences info = getSharedPreferences("xgo_setting", MODE_PRIVATE);
        String privacy_first = info.getString("privacy_first","yes");
        if (privacy_first.equals("yes")){
            final SharedPreferences.Editor edit = info.edit();
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setContentView(R.layout.layout_privacy_first);
                window.setGravity(Gravity.CENTER);
                Button btnCancel = window.findViewById(R.id.privacyCancelBtn);
                TextView btnAgree = window.findViewById(R.id.privacyAgreeBtn);
                TextView privacyFirstTextTitle = window.findViewById(R.id.privacyFirstTextTitle);
                TextView privacyFirstText = window.findViewById(R.id.privacyFirstText);
                switch(PublicMethod.localeLanguage){
                    case "zh":
                        privacyFirstTextTitle.setText(Html.fromHtml(HTML_TEXT_TITLE));
                        privacyFirstText.setText(Html.fromHtml(HTML_TEXT));
                        break;
                    default:
                        privacyFirstTextTitle.setText(Html.fromHtml(HTML_TEXT_TITLE_EN));
                        privacyFirstText.setText(Html.fromHtml(HTML_TEXT_EN));
                }
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit.putString("privacy_first", "yes");
                        edit.commit();
                        alertDialog.cancel();
                        finish();
                    }
                });
                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit.putString("privacy_first", "no");
                        edit.commit();
                        alertDialog.cancel();
                    }
                });
            }
        }
    }

    // 读取设备类型
//    private class getProductTypeThread extends Thread {
//        @Override
//        public void run() {
//            while (currentThread().isAlive()) {
//                MainActivity.addMessageRead(new byte[]{PublicMethod.XGORAM_ADDR.productType, 0x01});
//                Message message = new Message();
////                message.what = 0;
//                mHandler.sendMessageDelayed(message, 200);//200ms以后拿结果
//                try {
//                    sleep(5000);//5s更新一次
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    // 读取版本号
    private class askVersionNumberThread extends Thread {
        @Override
        public void run() {
            while (currentThread().isAlive()) {
                MainActivity.addMessageRead(new byte[]{PublicMethod.XGORAM_ADDR.versionNumber, 0x01});
                Message message = new Message();
//                message.what = 0;
                mHandler.sendMessageDelayed(message, 200);//200ms以后拿结果
                try {
                    sleep(5000);//5s更新一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class getVersionThread extends Thread {
        @Override
        public void run() {
            while (currentThread().isAlive()) {
                try {
                    if (isBluetoothConnect){
                        if (!PublicMethod.XGORAM_VALUE.versionNumber.equals("") && isBluetoothConnect ) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mainBtnStart.setVisibility(View.VISIBLE);
                                    mainBtnStart.setClickable(true);
                                    mainBtnStart.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.animator.zoom_out_in));
                                }
                            });
                            break;
                        } else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mainBtnStart.setVisibility(View.VISIBLE);
                                    mainBtnStart.setClickable(false);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainBtnStart.clearAnimation();
                                mainBtnStart.setVisibility(View.GONE);
                            }
                        });
                    }
                    Thread.sleep(200);
                } catch (Exception ignored) {

                }
            }
        }
    }
}
