package com.luwu.xgo_robot.mActivity;


import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mMothed.PublicMethod;
import com.luwu.xgo_robot.mMothed.mToast;
import com.luwu.xgo_robot.mView.ArcProcessView;
import com.luwu.xgo_robot.mView.ButtonView;
import com.luwu.xgo_robot.mView.RockerView;
import com.luwu.xgo_robot.mView.VerticalSeekBarArm;

import static com.luwu.xgo_robot.mMothed.PublicMethod.hideBottomUIMenu;
import static com.luwu.xgo_robot.mMothed.PublicMethod.localeLanguage;
import static com.luwu.xgo_robot.mMothed.PublicMethod.toOrderRange;
import static com.luwu.xgo_robot.mView.ButtonView.DOWNPRESS;
import static com.luwu.xgo_robot.mView.ButtonView.LEFTPRESS;
import static com.luwu.xgo_robot.mView.ButtonView.RIGHTPRESS;
import static com.luwu.xgo_robot.mView.ButtonView.UPPRESS;


public class RiderActivity extends AppCompatActivity {
    private ImageButton btnExit, btnMore;
    private ButtonView buttonView;
    private ArcProcessView arcProcessView;
    private RockerView rockerView;
    private SeekBar seekBarHeight;
    private Button btnHeightReset;
    private Button btnCarousel, btnActorRocking, btnActorShifting, btnActorAltitude, btnActorZigzag, btnActorLift, btnActorTrembling;
    private SeekBar seekBarR, seekBarG, seekBarB;
    private SeekBar seekBarRoll;
    public static int flagRockModeBtn = 0;//公开给摇杆的变量 0全向移动 1xyz转动 2xyz平动
    private long saveTime = 0;
    private long nowTime = 0;
    private Handler mHandler;
    private SeekBarListener mSeekBarListener;
    private int speedLeft;
    private final int speedRange = 87;
    private final int heightDefault = 50;
    private final int rollProgressDefault = 50;
    private byte progressR = 0x00, progressG = 0x00, progressB = 0x00;
    private final byte[] action = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    private PopupWindow mPop;
    private static int IMUChecked = 0; //0陀螺仪未开启，1开启
    private static View mGrayLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        btnExit = findViewById(R.id.RiderBtnExit);
        btnMore = findViewById(R.id.RiderBtnMore);
        arcProcessView = findViewById(R.id.RiderArcProcessView);
        buttonView = findViewById(R.id.RiderButtonView);
        rockerView = findViewById(R.id.RiderRockView);
        seekBarHeight = findViewById(R.id.RiderSeekBarHeight);
        btnHeightReset = findViewById(R.id.RiderBtnHeightReset);
        btnCarousel = findViewById(R.id.actionBtnCarousel);
        btnActorRocking = findViewById(R.id.actionBtnRocking);
        btnActorShifting = findViewById(R.id.actionBtnShifting);
        btnActorAltitude = findViewById(R.id.actionBtnAltitude);
        btnActorZigzag = findViewById(R.id.actionBtnZigzag);
        btnActorLift = findViewById(R.id.actionBtnLift);
        btnActorTrembling = findViewById(R.id.actionBtnTrembling);
        mGrayLayout=findViewById(R.id.riderGrayBackground);
        seekBarR = findViewById(R.id.RiderSeekBarR);
        seekBarG = findViewById(R.id.RiderSeekBarG);
        seekBarB = findViewById(R.id.RiderSeekBarB);
        seekBarRoll = findViewById(R.id.RiderSeekBarRoll);
        mSeekBarListener = new SeekBarListener();
        seekBarHeight.setOnSeekBarChangeListener(mSeekBarListener);
        seekBarRoll.setOnSeekBarChangeListener(mSeekBarListener);
        seekBarR.setOnSeekBarChangeListener(mSeekBarListener);
        seekBarG.setOnSeekBarChangeListener(mSeekBarListener);
        seekBarB.setOnSeekBarChangeListener(mSeekBarListener);
        mViewListener();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {

                } else if (msg.what == 1) {
                    speedLeft = msg.arg1;
                    arcProcessView.setProgress(speedLeft);
//                    changeSpeedView((int) sqrt(speedLeft * speedLeft + speedRight * speedRight));
                } else if (msg.what == 2) {
//                    changeSpeedView((int) sqrt(speedLeft * speedLeft + speedRight * speedRight));
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideBottomUIMenu(RiderActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void mViewListener() {
        buttonView.setButtonViewListener(new ButtonView.IButtonViewListener() {
            @Override
            public void actionDown(int num) {
                switch (num) {
                    case UPPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVx, (byte) 0xDA});//小于最大速度
                        break;
                    case DOWNPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVx, (byte) 0x25});
                        break;
                    case LEFTPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVyaw, (byte) 0xDA});
                        break;
                    case RIGHTPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVyaw, (byte) 0x25});
                        break;
                }
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 60;
                mHandler.sendMessage(msg);
            }
            public void actionUp(int num) {
                switch (num) {
                    case UPPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVx, (byte) 0x80});//停下
                        break;
                    case DOWNPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVx, (byte) 0x80});
                        break;
                    case LEFTPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVyaw, (byte) 0x80});
                        break;
                    case RIGHTPRESS:
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVyaw, (byte) 0x80});
                        break;
                }
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 0;
                mHandler.sendMessage(msg);
            }
        });

        rockerView.setRockViewListener(new RockerView.IRockViewListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
                if (flagRockModeBtn == 0) {//全向移动
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVx, (byte) 0x80});
//                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, (byte) 0x80});
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVyaw, (byte) 0x80});
                } else if (flagRockModeBtn == 1) {//xyz转动
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyPitch, (byte) 0x80});
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyRoll, (byte) 0x80});
                } else if (flagRockModeBtn == 2) {//xyz平动
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyX, (byte) 0x80});
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyY, (byte) 0x80});
                }
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 300) {//500
                    Point speed = rockerView.getSpeed();
                    if (flagRockModeBtn == 0) {//全向移动
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVx, toOrderRange(-speed.y, -speedRange, speedRange)});
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.speedVyaw, toOrderRange(-speed.x, -speedRange, speedRange)});
                    } else if (flagRockModeBtn == 1) {//xyz转动
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyPitch, toOrderRange(-speed.y, -speedRange, speedRange)});
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyRoll, toOrderRange(speed.x, -speedRange, speedRange)});
                    } else if (flagRockModeBtn == 2) {//xyz平动
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyX, toOrderRange(-speed.y, -speedRange, speedRange)});
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyY, toOrderRange(-speed.x, -speedRange, speedRange)});
                    }
                    saveTime = nowTime;
                }
                Message msg = new Message();
                msg.what = 1;
                int x = rockerView.getSpeed().x;
                int y = rockerView.getSpeed().y;
                msg.arg1 = (int) abs(sqrt(x * x + y * y));
                mHandler.sendMessage(msg);
            }
        });

        btnCarousel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCarousel.setActivated(!btnCarousel.isActivated());
                if (btnCarousel.isActivated()) {//动作轮播
                    MainActivity.addMessage(new byte[]{0x03, 0x01});
                } else {
                    MainActivity.addMessage(new byte[]{0x03, 0x00});
                }
            }
        });

        btnActorRocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAction(action[0]);
            }
        });
        btnActorShifting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAction(action[2]);
            }
        });
        btnActorAltitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAction(action[1]);
            }
        });
        btnActorZigzag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAction(action[3]);
            }
        });
        btnActorLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAction(action[4]);
            }
        });
        btnActorTrembling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAction(action[5]);
            }
        });

        btnHeightReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarHeight.setProgress(heightDefault);
                MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyZ, toOrderRange(heightDefault, 0, 100)});
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mGrayLayout.setVisibility(View.VISIBLE);
                btnMore.setActivated(true);
                controlMore();
            }
        });

        mGrayLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mGrayLayout.setVisibility(View.GONE);
            }
        });
    }

    private void sendAction(byte action){
        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.action, (byte) action});
    }

    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.RiderSeekBarHeight:
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime) > 200) {//200ms刷新
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyZ, toOrderRange(progress, 0, 100)});
                        saveTime = nowTime;
                    }
                    break;
                case R.id.RiderSeekBarR:
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime) > 50) {//50ms刷新
                        if (progress <= 10){
                            progress = 0;
                        }
                        progressR = toOrderRange(progress, 0, 100); //范围调整
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_01, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_02, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_03, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_04, progressR, progressG, progressB });
                        saveTime = nowTime;
                    }
                    break;
                case R.id.RiderSeekBarG:
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime) > 50) {//50ms刷新
                        if (progress <= 10){
                            progress = 0;
                        }
                        progressG = toOrderRange(progress, 0, 100);
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_01, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_02, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_03, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_04, progressR, progressG, progressB });
                        saveTime = nowTime;
                    }
                    break;
                case R.id.RiderSeekBarB:
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime) > 50) {//50ms刷新
                        if (progress <= 10){
                            progress = 0;
                        }
                        progressB = toOrderRange(progress, 0, 100);
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_01, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_02, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_03, progressR, progressG, progressB });
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.led_04, progressR, progressG, progressB });
                        saveTime = nowTime;
                    }
                    break;
                case R.id.RiderSeekBarRoll:
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime) > 300) {//200ms刷新
                        int speed = (int)((progress - rollProgressDefault) / 100.0f * 160.0f);
                        MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyRoll, toOrderRange(speed, -speedRange, speedRange)});
                        saveTime = nowTime;
                    }
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            switch (seekBar.getId()) {
                case R.id.RiderSeekBarRoll:
                    seekBarRoll.setProgress(rollProgressDefault);
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.bodyRoll, (byte) 0x80});
                    break;
            }
        }
    }

    private void controlMore() {
        final View popView = getLayoutInflater().inflate(R.layout.layout_popupwindow_rider, null);
        Switch IMUSwitch = popView.findViewById(R.id.RiderIMUSwitch);//动作自启动开关
        switch(IMUChecked){
            case 0:
                IMUSwitch.setChecked(false);
                break;
            case 1:
                IMUSwitch.setChecked(true);
                break;
        }
        IMUSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IMUChecked = 1;
                    MainActivity.addMessage(new byte[]{0x61, 0x01});
                    switch (localeLanguage) {
                        case "zh":
                            mToast.show(RiderActivity.this, "自稳定模式已开启");
                            break;
                        default:
                            mToast.show(RiderActivity.this, "Self stable mode on");
                    }
                } else {
                    IMUChecked = 0;
                    MainActivity.addMessage(new byte[]{0x61, 0x00});
                    switch (localeLanguage) {
                        case "zh":
                            mToast.show(RiderActivity.this, "陀螺仪已关闭");
                            break;
                        default:
                            mToast.show(RiderActivity.this, "Gyroscope off");
                    }
                }
            }
        });

        mPop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setOutsideTouchable(true);
        mPop.setFocusable(false);
        mPop.showAsDropDown(btnMore);
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                btnMore.setActivated(false);
            }
        });
    }










}
