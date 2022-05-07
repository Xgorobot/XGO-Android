package com.luwu.xgo_robot.mActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mMothed.PublicMethod;

import static com.luwu.xgo_robot.mMothed.PublicMethod.hideBottomUIMenu;
import static com.luwu.xgo_robot.mMothed.PublicMethod.localeLanguage;
import static com.luwu.xgo_robot.mMothed.PublicMethod.toOrderRange;

public class MotorActivity extends AppCompatActivity {
    private ImageButton motorBtnBack;
    private Button motorBtnReset;
    private ButtonListener mButtonListener;
    private RadioGroup motorRadioGroup;
    private RadioButton motorRadio0, motorRadio1, motorRadio2, motorRadio3;
    private int motorChioce = 0;//选择哪条腿 0左前 1右前 2右后 3左后
    private long nowTime = 0;
    private long saveTime1 = 0, saveTime2 = 0, saveTime3 = 0;
    private SeekBar motorSeekBarX, motorSeekBarY, motorSeekBarZ;
    private TextView motorTextX, motorTextY, motorTextZ;
    private SeekBarListener mSeekBarListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);
        mButtonListener = new ButtonListener();
        motorBtnBack = findViewById(R.id.motorBtnBack);
        motorBtnBack.setOnClickListener(mButtonListener);
        motorBtnReset = findViewById(R.id.motorBtnReset);
        motorBtnReset.setOnClickListener(mButtonListener);
        motorRadioGroup = findViewById(R.id.motorRadioGroup);
        motorRadio0 = findViewById(R.id.motorRadio0);
        motorRadio1 = findViewById(R.id.motorRadio1);
        motorRadio2 = findViewById(R.id.motorRadio2);
        motorRadio3 = findViewById(R.id.motorRadio3);
        setRadioGropListener();
        mSeekBarListener = new SeekBarListener();
        motorSeekBarX = findViewById(R.id.motorSeekBarX);
        motorSeekBarX.setOnSeekBarChangeListener(mSeekBarListener);
        motorSeekBarY = findViewById(R.id.motorSeekBarY);
        motorSeekBarY.setOnSeekBarChangeListener(mSeekBarListener);
        motorSeekBarZ = findViewById(R.id.motorSeekBarZ);
        motorSeekBarZ.setOnSeekBarChangeListener(mSeekBarListener);
        motorTextX = findViewById(R.id.motorTextX);
        motorTextY = findViewById(R.id.motorTextY);
        motorTextZ = findViewById(R.id.motorTextZ);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideBottomUIMenu(MotorActivity.this);
    }

    private void setRadioGropListener() {
        motorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.motorRadio0:
                        motorChioce = 0;
                        break;
                    case R.id.motorRadio1:
                        motorChioce = 1;
                        break;
                    case R.id.motorRadio2:
                        motorChioce = 2;
                        break;
                    case R.id.motorRadio3:
                        motorChioce = 3;
                        break;
                }
            }
        });
    }
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.motorBtnBack:
                    finish();
                    break;
                case R.id.motorBtnReset:
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.action, (byte)0xff});
                    motorSeekBarX.setProgress(0);
                    motorSeekBarY.setProgress(43);
                    motorSeekBarZ.setProgress(20);
                    break;
            }
        }
    }
    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.motorSeekBarX:
                    motorTextX.setText(String.valueOf(progress));
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime1) > 200) {//200ms刷新
                        if (motorChioce == 0) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_13, (byte) toOrderRange(progress, -31, 31)});
                        } else if (motorChioce == 1) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_23, (byte) toOrderRange(progress, -31, 31)});
                        } else if (motorChioce == 2) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_33, (byte) toOrderRange(progress, -31, 31)});
                        } else if (motorChioce == 3) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_43, (byte) toOrderRange(progress, -31, 31)});
                        }
                        saveTime1 = nowTime;
                    }
                    break;
                case R.id.motorSeekBarY:
                    motorTextY.setText(String.valueOf(progress));
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime2) > 200) {//200ms刷新
                        if (motorChioce == 0) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_12, (byte) toOrderRange(progress, -66, 93)});
                        } else if (motorChioce == 1) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_22, (byte) toOrderRange(progress, -66, 93)});
                        } else if (motorChioce == 2) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_32, (byte) toOrderRange(progress, -66, 93)});
                        } else if (motorChioce == 3) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_42, (byte) toOrderRange(progress, -66, 93)});
                        }
                        saveTime2 = nowTime;
                    }
                    break;
                case R.id.motorSeekBarZ:
                    motorTextZ.setText(String.valueOf(progress));
                    nowTime = System.currentTimeMillis();
                    if ((nowTime - saveTime3) > 200) {//200ms刷新
                        if (motorChioce == 0) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_11, (byte) toOrderRange(progress, -65, 73)});
                        } else if (motorChioce == 1) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_21, (byte) toOrderRange(progress, -65, 73)});
                        } else if (motorChioce == 2) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_31, (byte) toOrderRange(progress, -65, 73)});
                        } else if (motorChioce == 3) {
                            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.motor_41, (byte) toOrderRange(progress, -65, 73)});
                        }
                        saveTime3 = nowTime;
                    }
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}