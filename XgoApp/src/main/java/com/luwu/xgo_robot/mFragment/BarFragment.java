package com.luwu.xgo_robot.mFragment;

import static com.luwu.xgo_robot.mMothed.PublicMethod.XGORAM_ADDR;
import static com.luwu.xgo_robot.mMothed.PublicMethod.XGORAM_VALUE;
import static com.luwu.xgo_robot.mMothed.PublicMethod.toOrderRange;

import static java.lang.Math.sqrt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mActivity.MainActivity;
import com.luwu.xgo_robot.mView.VerticalSeekBarArm;

public class BarFragment extends Fragment {


    private VerticalSeekBarArm seekBarJaw, seekBarX, seekBarZ;
    private ImageView buttonTxtBattery, buttonTxtSpeed;
    private Button btnRef;
    private TextView textArmJaw, textArmX, textArmZ;
    private final static int armIniProgress = 50;
    private static int refNum = 0;
    private static boolean flagLoop = false;//循环查询电量
    private getBatteryThread batteryThread;
    private Handler mHandler;
    private long saveTime = 0;
    private long nowTime = 0;
    private int speedLeft, speedRight;
    private int speedRange = 87;

    public BarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout
                .fragment_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        buttonTxtBattery = view.findViewById(R.id.buttonTxtBattery);
//        buttonTxtSpeed = view.findViewById(R.id.buttonTxtSpeed);

        seekBarJaw = view.findViewById(R.id.armJawSeekBar);
        seekBarJaw.setProgress(armIniProgress);
        seekBarX = view.findViewById(R.id.armXSeekBar);
        seekBarX.setProgress(armIniProgress);
        seekBarZ = view.findViewById(R.id.armZSeekBar);
        seekBarZ.setProgress(armIniProgress);

        textArmJaw = view.findViewById(R.id.armTextJaw);
        textArmJaw.setText(String.valueOf(armIniProgress));
        textArmX = view.findViewById(R.id.armTextX);
        textArmX.setText(String.valueOf(armIniProgress));
        textArmZ = view.findViewById(R.id.armTextZ);
        textArmZ.setText(String.valueOf(armIniProgress));

        btnRef = view.findViewById(R.id.buttonBtnRef);

        mViewListener();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    changeBatteryView(XGORAM_VALUE.battery);
                } else if (msg.what == 1) {
                    speedLeft = msg.arg1;
//                    changeSpeedView((int) sqrt(speedLeft * speedLeft + speedRight * speedRight));
                } else if (msg.what == 2) {
                    speedRight = msg.arg1;
//                    changeSpeedView((int) sqrt(speedLeft * speedLeft + speedRight * speedRight));
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        flagLoop = true;
        batteryThread = new getBatteryThread();
        batteryThread.start();//开始查询电池电量
    }

    @Override
    public void onPause() {
        super.onPause();
        flagLoop = false;
    }

    //自定义控件设置监听事件
    private void mViewListener() {
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 200) {
                    refNum = 1-refNum;
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armRef, (byte) refNum});
                    saveTime = nowTime;
                }
            }
        });
        seekBarJaw.setListener(new VerticalSeekBarArm.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int JawProgress = seekBarJaw.getProgress();
                textArmJaw.setText(String.valueOf(JawProgress));
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armJaw, toOrderRange(JawProgress,0,100)});
                    saveTime = nowTime;
                }
            }
        });

        seekBarX.setListener(new VerticalSeekBarArm.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int xProgress = seekBarX.getProgress();
                textArmX.setText(String.valueOf(xProgress));
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armX, toOrderRange(xProgress,0,100)});
                    saveTime = nowTime;
                }
            }
        });

        seekBarZ.setListener(new VerticalSeekBarArm.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int zProgress = seekBarZ.getProgress();
                textArmZ.setText(String.valueOf(zProgress));
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armZ, toOrderRange(zProgress,0,100)});
                    saveTime = nowTime;
                }
            }
        });

    }

    /**
     * 手动更新（非seekbar）progress
     */
//    public void updateProgress(){
//        seekBar.updateProgress(progress);
//        textHeight.setText(String.valueOf(progress));
//    }

    private class getBatteryThread extends Thread {
        @Override
        public void run() {
            while (flagLoop) {
                //查询电池电量并更新
                MainActivity.addMessageRead(new byte[]{XGORAM_ADDR.battery, 0x01});
//                MainActivity.addMessageRead(new byte[]{XGORAM_ADDR.versions, 0x01});
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessageDelayed(message, 200);//200ms以后拿结果
                try {
                    sleep(5000);//5s更新一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void changeSpeedView(int speed) {//范围 0-100
        if (speed <= 0) {
            buttonTxtSpeed.setImageResource(R.drawable.speed0);
        } else if (speed <= 7) {
            buttonTxtSpeed.setImageResource(R.drawable.speed1);
        } else if (speed <= 14) {
            buttonTxtSpeed.setImageResource(R.drawable.speed2);
        } else if (speed <= 21) {
            buttonTxtSpeed.setImageResource(R.drawable.speed3);
        } else if (speed <= 28) {
            buttonTxtSpeed.setImageResource(R.drawable.speed4);
        } else if (speed <= 35) {
            buttonTxtSpeed.setImageResource(R.drawable.speed5);
        } else if (speed <= 42) {
            buttonTxtSpeed.setImageResource(R.drawable.speed6);
        } else if (speed <= 49) {
            buttonTxtSpeed.setImageResource(R.drawable.speed7);
        } else if (speed <= 56) {
            buttonTxtSpeed.setImageResource(R.drawable.speed8);
        } else if (speed <= 63) {
            buttonTxtSpeed.setImageResource(R.drawable.speed9);
        } else if (speed <= 70) {
            buttonTxtSpeed.setImageResource(R.drawable.speed10);
        } else if (speed <= 77) {
            buttonTxtSpeed.setImageResource(R.drawable.speed11);
        } else if (speed <= 84) {
            buttonTxtSpeed.setImageResource(R.drawable.speed12);
        } else if (speed <= 91) {
            buttonTxtSpeed.setImageResource(R.drawable.speed13);
        } else {
            buttonTxtSpeed.setImageResource(R.drawable.speed14);
        }
    }

    private void changeBatteryView(int battery) {//范围 0-100
        if (battery <= 0) {
            buttonTxtBattery.setImageResource(R.drawable.buttery0);
        } else if (battery <= 7) {
            buttonTxtBattery.setImageResource(R.drawable.buttery1);
        } else if (battery <= 14) {
            buttonTxtBattery.setImageResource(R.drawable.buttery2);
        } else if (battery <= 21) {
            buttonTxtBattery.setImageResource(R.drawable.buttery3);
        } else if (battery <= 28) {
            buttonTxtBattery.setImageResource(R.drawable.buttery4);
        } else if (battery <= 35) {
            buttonTxtBattery.setImageResource(R.drawable.buttery5);
        } else if (battery <= 42) {
            buttonTxtBattery.setImageResource(R.drawable.buttery6);
        } else if (battery <= 49) {
            buttonTxtBattery.setImageResource(R.drawable.buttery7);
        } else if (battery <= 56) {
            buttonTxtBattery.setImageResource(R.drawable.buttery8);
        } else if (battery <= 63) {
            buttonTxtBattery.setImageResource(R.drawable.buttery9);
        } else if (battery <= 70) {
            buttonTxtBattery.setImageResource(R.drawable.buttery10);
        } else if (battery <= 77) {
            buttonTxtBattery.setImageResource(R.drawable.buttery11);
        } else if (battery <= 84) {
            buttonTxtBattery.setImageResource(R.drawable.buttery12);
        } else if (battery <= 91) {
            buttonTxtBattery.setImageResource(R.drawable.buttery13);
        } else {
            buttonTxtBattery.setImageResource(R.drawable.buttery14);
        }
    }
}
