package com.luwu.xgo_robot.mFragment;

import static com.luwu.xgo_robot.mActivity.ControlActivity.progressHeight;
import static com.luwu.xgo_robot.mMothed.PublicMethod.XGORAM_ADDR;
import static com.luwu.xgo_robot.mMothed.PublicMethod.toOrderRange;
import static java.lang.Math.abs;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mActivity.MainActivity;
import com.luwu.xgo_robot.mView.ArmRockerView;
import com.luwu.xgo_robot.mView.VerticalSeekBar;
import com.luwu.xgo_robot.mView.VerticalSeekBarArm;

public class ArmFragment extends Fragment {
    private ArmRockerView armRockerView;
    private Button btnRefGround, btnRefBase; //参照地面，参照底座
    private ImageButton btnActor1, btnActor2, btnActor3;
    private VerticalSeekBar seekBarJaw;

    private TextView textArmValueX, textArmValueZ;
    private long saveTime1 = 0, saveTime2 = 0, saveTime3 = 0;
    private long nowTime = 0;
    private int speedRange = 80;

    private int targetRangeMin = 16;
    private int targetRangeMax = 240;
    public ArmFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_arm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        rockerTxtSpeed = view.findViewById(R.id.rockerTxtSpeed);
        armRockerView = view.findViewById(R.id.controlArmRockView);
        seekBarJaw = view.findViewById(R.id.armSeekBarJaw);
        seekBarJaw.setProgress(progressHeight);
        btnRefGround = view.findViewById(R.id.armBtnReferGround);
        btnRefGround.setActivated(true);
        btnRefBase= view.findViewById(R.id.armBtnReferBase);
        btnActor1 = view.findViewById(R.id.armBtnActor1);
        btnActor2 = view.findViewById(R.id.armBtnActor2);
        btnActor3 = view.findViewById(R.id.armBtnActor3);
        textArmValueX = view.findViewById(R.id.armTextValueX);
        textArmValueZ = view.findViewById(R.id.armTextValueZ);

        mViewListener();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //自定义控件设置监听事件
    private void mViewListener() {
        btnRefGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime2) > 200) {
                    btnRefGround.setActivated(true);
                    btnRefBase.setActivated(false);
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armRef, (byte) 0x01});
                    saveTime2 = nowTime;
                }
            }
        });
        btnRefBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime2) > 200) {
                    btnRefBase.setActivated(true);
                    btnRefGround.setActivated(false);
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armRef, (byte) 0x00});
                    saveTime2 = nowTime;
                }
            }
        });
        btnActor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime2) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.action, (byte) 0x80});
                    saveTime2 = nowTime;
                }
            }
        });
        btnActor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime2) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.action, (byte) 0x81});
                    saveTime2 = nowTime;
                }
            }
        });
        btnActor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime2) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.action, (byte) 0x82});
                    saveTime2 = nowTime;
                }
            }
        });
        armRockerView.setRockViewListener(new ArmRockerView.IRockViewListener() {
            @Override
            public void actionDown() {

            }

            @Override
            public void actionUp() {
//                flagRockLoop=false;
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime1) > 300) {//500
                    Point speed = armRockerView.getSpeed();
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armX, toRange(speed.x,-speedRange, speedRange, 0, targetRangeMax)});
                    textArmValueX.setText(String.valueOf(toHundredRange(speed.x,-speedRange, speedRange)));
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armZ, toRange(-speed.y,-speedRange, speedRange, targetRangeMin, 224)});
                    textArmValueZ.setText(String.valueOf(toHundredRange(-speed.y,-speedRange, speedRange)));
                    saveTime1 = nowTime;
                }
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime1) > 200) {//500
                    Point speed = armRockerView.getSpeed();
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armX, toRange(speed.x,-speedRange, speedRange, 0, targetRangeMax)});
                    textArmValueX.setText(String.valueOf(toHundredRange(speed.x,-speedRange, speedRange)));
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armZ, toRange(-speed.y,-speedRange, speedRange, targetRangeMin, 224)});
                    textArmValueZ.setText(String.valueOf(toHundredRange(-speed.y,-speedRange, speedRange)));
                    saveTime1 = nowTime;
                }
            }
        });

        seekBarJaw.setListener(new VerticalSeekBar.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyZ, toOrderRange(progressHeight, 0, 100)});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int JawProgress = seekBarJaw.getProgress();
                if ((nowTime - saveTime3) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.armJaw, toOrderRange(JawProgress,0,100)});
                    saveTime3 = nowTime;
                }
            }
        });
    }
    private int toHundredRange(int num, int min, int max) {
        int temp = (num - min) * 100 / (max - min);
        return  temp;
    }

    private byte toRange(int num, int min, int max, int targetMin, int targetMax) {
        int temp = (num - min) * (targetMax - targetMin) / (max - min) + targetMin;
        return (byte) temp;
    }

}
