package com.luwu.xgo_robot.mFragment;

import static com.luwu.xgo_robot.mActivity.ControlActivity.progressStride;
import static com.luwu.xgo_robot.mMothed.PublicMethod.XGORAM_ADDR;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mActivity.ControlActivity;
import com.luwu.xgo_robot.mActivity.MainActivity;
import com.luwu.xgo_robot.mMothed.PublicMethod;
import com.luwu.xgo_robot.mView.ArcProcessView;
import com.luwu.xgo_robot.mView.ArcSeekBarView;
import com.luwu.xgo_robot.mView.ButtonView;
import com.luwu.xgo_robot.mView.RockerView;

import static com.luwu.xgo_robot.mMothed.PublicMethod.toOrderRange;
import static com.luwu.xgo_robot.mView.ButtonView.DOWNPRESS;
import static com.luwu.xgo_robot.mView.ButtonView.LEFTPRESS;
import static com.luwu.xgo_robot.mView.ButtonView.RIGHTPRESS;
import static com.luwu.xgo_robot.mView.ButtonView.UPPRESS;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import static com.luwu.xgo_robot.mActivity.ControlActivity.progressStride;

import java.util.Objects;

public class MultiFragment extends Fragment {

    private int controlProductType, controlVersionNumberFirst;
    private ButtonView buttonViewLeft, buttonViewRight;
    private ArcProcessView arcProcessViewLeft, arcProcessViewRight;
    private boolean isPro = false;
    private ArcSeekBarView arcSeekBarViewLeftRB, arcSeekBarViewLeftLT, arcSeekBarViewLeftRT, arcSeekBarViewRightLB, arcSeekBarViewRightLT, arcSeekBarViewRightRT;
    private RockerView rockerViewLeft, rockerViewRight;
    private static boolean flagLoop = false;//循环查询电量
    private Handler mHandler;
    private long saveTime = 0;
    private long nowTime = 0;
    private int speedLeft, speedRight;
    private final int speedRange = 87;
    private final int progressDefault = 50;
    private ImageButton actionBtnActor1, actionBtnActor2, actionBtnActor3;
    private LinearLayout actionsMiniLayout;
    private Button actionBtnCarousel, actionBtnLiedown, actionBtnStandup;
    private ImageButton actionBtnActions;
    private PopupWindow mPop;
    private View popView;
    private ButtonClickListener mButtonClickListener;
    private Button popupActionsBtn1, popupActionsBtn2, popupActionsBtn3, popupActionsBtn4, popupActionsBtn5, popupActionsBtn6, popupActionsBtn7, popupActionsBtn8,
            popupActionsBtn9, popupActionsBtn10, popupActionsBtn11, popupActionsBtn12, popupActionsBtn13, popupActionsBtn14, popupActionsBtn15, popupActionsBtn16,
            popupActionsBtn17, popupActionsBtn18, popupActionsBtn19, popupActionsBtn20, popupActionsBtn21, popupActionsBtn22, popupActionsBtn23, popupActionsBtn24
            ;
    
    private Button popupActionsBtn0x80, popupActionsBtn0x81, popupActionsBtn0x82;
    private Button popupActionsBtnReset, popupActionsBtnCarousel;

    private LinearLayout popupBtnBack;
    private final byte[] action = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    private byte[] actionAdditional = {(byte) 0x80, (byte) 0x81, (byte) 0x82};
    public MultiFragment() {
    }

    public MultiFragment(int ProductType, int VersionNumberFirst) {
        controlProductType = ProductType;
        controlVersionNumberFirst = VersionNumberFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonClickListener = new ButtonClickListener();

        buttonViewLeft = view.findViewById(R.id.MultiButtonViewLeft);
        buttonViewRight = view.findViewById(R.id.MultiButtonViewRight);

        arcProcessViewLeft = view.findViewById(R.id.MultiArcProcessViewLeft);
        arcProcessViewRight = view.findViewById(R.id.MultiArcProcessViewRight);

        arcSeekBarViewLeftRB = view.findViewById(R.id.MultiArcSeekBarViewLeftRB);
        arcSeekBarViewLeftLT = view.findViewById(R.id.MultiArcSeekBarViewLeftLT);
        arcSeekBarViewLeftRT = view.findViewById(R.id.MultiArcSeekBarViewLeftRT);
        arcSeekBarViewRightLB = view.findViewById(R.id.MultiArcSeekBarViewRightLB);
        arcSeekBarViewRightLT = view.findViewById(R.id.MultiArcSeekBarViewRightLT);
        arcSeekBarViewRightRT = view.findViewById(R.id.MultiArcSeekBarViewRightRT);

        if (!isPro){
            arcSeekBarViewLeftRB.setVisibility(View.GONE);
            arcSeekBarViewLeftLT.setVisibility(View.GONE);
            arcSeekBarViewLeftRT.setVisibility(View.GONE);
            arcSeekBarViewRightLB.setVisibility(View.GONE);
            arcSeekBarViewRightLT.setVisibility(View.GONE);
            arcSeekBarViewRightRT.setVisibility(View.GONE);
        }

        rockerViewLeft = view.findViewById(R.id.MultiRockViewLeft);
        rockerViewRight = view.findViewById(R.id.MultiRockViewRight);

        actionBtnActor1 = view.findViewById(R.id.actionBtnActor1);
        actionBtnActor2 = view.findViewById(R.id.actionBtnActor2);
        actionBtnActor3 = view.findViewById(R.id.actionBtnActor3);

        actionsMiniLayout = view.findViewById(R.id.actionsMiniLayout);
        actionBtnCarousel = view.findViewById(R.id.actionBtnCarousel);
        actionBtnLiedown = view.findViewById(R.id.actionBtnLiedown);
        actionBtnStandup = view.findViewById(R.id.actionBtnStandup);
        actionBtnActions = view.findViewById(R.id.actionBtnActions);

//        actionBtnCarousel.setOnClickListener(mButtonClickListener);
//        actionBtnLiedown.setOnClickListener(mButtonClickListener);
//        actionBtnStandup.setOnClickListener(mButtonClickListener);

        popView = getLayoutInflater().inflate(R.layout.layout_popupactions, null);
        popupBtnBack = popView.findViewById(R.id.popupActionsBtnBack);
        popupBtnBack.setOnClickListener(mButtonClickListener);
        popupActionsBtnCarousel = popView.findViewById(R.id.popupActionsBtnCarousel);
        popupActionsBtnCarousel.setOnClickListener(mButtonClickListener);
        popupActionsBtnReset = popView.findViewById(R.id.popupActionsBtnReset);
        popupActionsBtnReset.setOnClickListener(mButtonClickListener);
        popupActionsBtn1 = popView.findViewById(R.id.popupActionsBtn1);
        popupActionsBtn1.setOnClickListener(mButtonClickListener);
        popupActionsBtn2 = popView.findViewById(R.id.popupActionsBtn2);
        popupActionsBtn2.setOnClickListener(mButtonClickListener);
        popupActionsBtn3 = popView.findViewById(R.id.popupActionsBtn3);
        popupActionsBtn3.setOnClickListener(mButtonClickListener);
        popupActionsBtn4 = popView.findViewById(R.id.popupActionsBtn4);
        popupActionsBtn4.setOnClickListener(mButtonClickListener);
//        popupActionsBtn5 = popView.findViewById(R.id.popupActionsBtn5);
//        popupActionsBtn5.setOnClickListener(mButtonClickListener);
        popupActionsBtn6 = popView.findViewById(R.id.popupActionsBtn6);
        popupActionsBtn6.setOnClickListener(mButtonClickListener);
        popupActionsBtn7 = popView.findViewById(R.id.popupActionsBtn7);
        popupActionsBtn7.setOnClickListener(mButtonClickListener);
        popupActionsBtn8 = popView.findViewById(R.id.popupActionsBtn8);
        popupActionsBtn8.setOnClickListener(mButtonClickListener);
        popupActionsBtn9 = popView.findViewById(R.id.popupActionsBtn9);
        popupActionsBtn9.setOnClickListener(mButtonClickListener);
        popupActionsBtn10 = popView.findViewById(R.id.popupActionsBtn10);
        popupActionsBtn10.setOnClickListener(mButtonClickListener);
        popupActionsBtn11 = popView.findViewById(R.id.popupActionsBtn11);
        popupActionsBtn11.setOnClickListener(mButtonClickListener);
        popupActionsBtn12 = popView.findViewById(R.id.popupActionsBtn12);
        popupActionsBtn12.setOnClickListener(mButtonClickListener);
        popupActionsBtn13 = popView.findViewById(R.id.popupActionsBtn13);
        popupActionsBtn13.setOnClickListener(mButtonClickListener);
        popupActionsBtn14 = popView.findViewById(R.id.popupActionsBtn14);
        popupActionsBtn14.setOnClickListener(mButtonClickListener);
        popupActionsBtn15 = popView.findViewById(R.id.popupActionsBtn15);
        popupActionsBtn15.setOnClickListener(mButtonClickListener);
        popupActionsBtn16 = popView.findViewById(R.id.popupActionsBtn16);
        popupActionsBtn16.setOnClickListener(mButtonClickListener);
        popupActionsBtn17 = popView.findViewById(R.id.popupActionsBtn17);
        popupActionsBtn17.setOnClickListener(mButtonClickListener);
        popupActionsBtn18 = popView.findViewById(R.id.popupActionsBtn18);
        popupActionsBtn18.setOnClickListener(mButtonClickListener);
        popupActionsBtn19 = popView.findViewById(R.id.popupActionsBtn19);
        popupActionsBtn19.setOnClickListener(mButtonClickListener);
        popupActionsBtn20 = popView.findViewById(R.id.popupActionsBtn20);
        popupActionsBtn20.setOnClickListener(mButtonClickListener);
        popupActionsBtn21 = popView.findViewById(R.id.popupActionsBtn21);
        popupActionsBtn21.setOnClickListener(mButtonClickListener);
        popupActionsBtn22 = popView.findViewById(R.id.popupActionsBtn22);
        popupActionsBtn22.setOnClickListener(mButtonClickListener);
        popupActionsBtn23 = popView.findViewById(R.id.popupActionsBtn23);
        popupActionsBtn23.setOnClickListener(mButtonClickListener);
        popupActionsBtn24 = popView.findViewById(R.id.popupActionsBtn24);
        popupActionsBtn24.setOnClickListener(mButtonClickListener);
        popupActionsBtn0x80 = popView.findViewById(R.id.popupActionsBtn0x80);
        popupActionsBtn0x80.setOnClickListener(mButtonClickListener);
        popupActionsBtn0x81 = popView.findViewById(R.id.popupActionsBtn0x81);
        popupActionsBtn0x81.setOnClickListener(mButtonClickListener);
        popupActionsBtn0x82 = popView.findViewById(R.id.popupActionsBtn0x82);
        popupActionsBtn0x82.setOnClickListener(mButtonClickListener);

        if (controlVersionNumberFirst < 3){
            popupActionsBtn20.setVisibility(View.INVISIBLE);
            popupActionsBtn21.setVisibility(View.INVISIBLE);
            popupActionsBtn22.setVisibility(View.INVISIBLE);
            popupActionsBtn23.setVisibility(View.INVISIBLE);
            popupActionsBtn24.setVisibility(View.INVISIBLE);
            popupActionsBtn0x80.setVisibility(View.INVISIBLE);
            popupActionsBtn0x81.setVisibility(View.INVISIBLE);
            popupActionsBtn0x82.setVisibility(View.INVISIBLE);

            popupActionsBtn20.setClickable(false);
            popupActionsBtn21.setClickable(false);
            popupActionsBtn22.setClickable(false);
            popupActionsBtn23.setClickable(false);
            popupActionsBtn24.setClickable(false);
            popupActionsBtn0x80.setClickable(false);
            popupActionsBtn0x81.setClickable(false);
            popupActionsBtn0x82.setClickable(false);
        }

        mViewListener();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {

                } else if (msg.what == 1) {
                    speedLeft = msg.arg1;
                    arcProcessViewLeft.setProgress(speedLeft);
//                    changeSpeedView((int) sqrt(speedLeft * speedLeft + speedRight * speedRight));
                } else if (msg.what == 2) {
                    speedRight = msg.arg1;
                    arcProcessViewRight.setProgress(speedRight);
//                    changeSpeedView((int) sqrt(speedLeft * speedLeft + speedRight * speedRight));
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        flagLoop = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        flagLoop = false;
    }

    //自定义控件设置监听事件
    private void mViewListener() {
        rockerViewLeft.setRockViewListener(new RockerView.IRockViewListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
                MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVx, (byte) 0x80});
                MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVy, (byte) 0x80});
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 300) {//500
                    Point speed = rockerViewLeft.getSpeed();
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVx, toOrderRange(-speed.y, -speedRange, speedRange)});
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVy, toOrderRange(-speed.x, -speedRange, speedRange)});
                    saveTime = nowTime;
//                    leftRockPoint = rockerViewLeft.getSpeed();
                }
                Message msg = new Message();
                msg.what = 1;
                int x = (int) (rockerViewLeft.getSpeed().x / 0.8f);
                int y = (int) (rockerViewLeft.getSpeed().y / 0.8f);
                msg.arg1 = (int) abs(sqrt(x * x + y * y));
                mHandler.sendMessage(msg);
            }
        });
        rockerViewRight.setRockViewListener(new RockerView.IRockViewListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
                MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, (byte) 0x80});
                Message msg = new Message();
                msg.what = 2;
                msg.arg1 = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 300) {
                    Point speed = rockerViewRight.getSpeed();
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, toOrderRange(-speed.x, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
                Message msg = new Message();
                msg.what = 2;
                int x = (int) (rockerViewRight.getSpeed().x / 0.8f);
                int y = 0;
                msg.arg1 = (int) abs(sqrt(x * x + y * y));
                mHandler.sendMessage(msg);
            }
        });

        buttonViewLeft.setButtonViewListener(new ButtonView.IButtonViewListener() {
            @Override
            public void actionDown(int num) {
                switch (num) {
                    case UPPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVx, toOrderRange(128 + (int) (progressStride / 100.0 * 127.0),0, 255)});//小于最大速度
                        break;
                    case DOWNPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVx, toOrderRange(128 - (int) (progressStride / 100.0 * 128.0),0, 255)});
                        break;
                    case LEFTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVy, toOrderRange(128 + (int) (progressStride / 100.0 * 127.0),0, 255)});
                        break;
                    case RIGHTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVy, toOrderRange(128 - (int) (progressStride / 100.0 * 128.0),0, 255)});
                        break;
                }
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = progressStride;
                mHandler.sendMessage(msg);
            }

            @Override
            public void actionUp(int num) {
                System.out.println(num);
                switch (num) {
                    case UPPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVx, (byte) 0x80});//停下
                        break;
                    case DOWNPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVx, (byte) 0x80});
                        break;
                    case LEFTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVy, (byte) 0x80});
                        break;
                    case RIGHTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVy, (byte) 0x80});
                        break;
                }
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 0;
                mHandler.sendMessage(msg);
            }
        });
        buttonViewRight.setButtonViewListener(new ButtonView.IButtonViewListener() {
            @Override
            public void actionDown(int num) {
                switch (num) {
                    case LEFTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, toOrderRange(128 + (int) (progressStride / 100.0 * 127.0),0, 255)});
                        break;
                    case RIGHTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, toOrderRange(128 - (int) (progressStride / 100.0 * 128.0),0, 255)});
                        break;
                }
                Message msg = new Message();
                msg.what = 2;
                msg.arg1 = progressStride;
                mHandler.sendMessage(msg);
            }

            @Override
            public void actionUp(int num) {
                switch (num) {
                    case LEFTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, (byte) 0x80});
                        break;
                    case RIGHTPRESS:
                        MainActivity.addMessage(new byte[]{XGORAM_ADDR.speedVyaw, (byte) 0x80});
                        break;
                }
                Message msg = new Message();
                msg.what = 2;
                msg.arg1 = 0;
                mHandler.sendMessage(msg);
            }
        });

        arcSeekBarViewLeftLT.setListener(new ArcSeekBarView.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
//                arcSeekBarViewLeftLT.setProgress(progressDefault);
//                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyX, (byte) 0x80});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int speed = (int)((arcSeekBarViewLeftLT.getProgress() - progressDefault) / 100.0f * 160.0f);
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyX, toOrderRange(-speed, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
            }
        });

        arcSeekBarViewLeftRT.setListener(new ArcSeekBarView.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
//                arcSeekBarViewLeftRT.setProgress(progressDefault);
//                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyY, (byte) 0x80});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int speed = (int)((arcSeekBarViewLeftRT.getProgress() - progressDefault) / 100.0f * 160.0f);
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyY, toOrderRange(-speed, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
            }
        });

        arcSeekBarViewLeftRB.setListener(new ArcSeekBarView.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
//                arcSeekBarViewLeftRB.setProgress(progressDefault);
//                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyZ, (byte) 0x80});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int speed = (int)((arcSeekBarViewLeftRB.getProgress() - progressDefault) / 100.0f * 160.0f);
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyZ, toOrderRange(-speed, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
            }
        });

        arcSeekBarViewRightRT.setListener(new ArcSeekBarView.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
//                arcSeekBarViewRightRT.setProgress(progressDefault);
//                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyPitch, (byte) 0x80});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int speed = (int)((arcSeekBarViewRightRT.getProgress() - progressDefault) / 100.0f * 160.0f);
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyPitch, toOrderRange(-speed, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
            }
        });

        arcSeekBarViewRightLT.setListener(new ArcSeekBarView.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
//                arcSeekBarViewRightLT.setProgress(progressDefault);
//                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyRoll, (byte) 0x80});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int speed = (int)((arcSeekBarViewRightLT.getProgress() - progressDefault) / 100.0f * 160.0f);
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyRoll, toOrderRange(-speed, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
            }
        });

        arcSeekBarViewRightLB.setListener(new ArcSeekBarView.ISeekBarListener() {
            @Override
            public void actionDown() {
            }

            @Override
            public void actionUp() {
//                arcSeekBarViewRightLB.setProgress(progressDefault);
//                MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyYaw, (byte) 0x80});
            }

            @Override
            public void actionMove() {
                nowTime = System.currentTimeMillis();
                int speed = (int)((arcSeekBarViewRightLB.getProgress() - progressDefault) / 100.0f * 160.0f);
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.bodyYaw, toOrderRange(-speed, -speedRange, speedRange)});
                    saveTime = nowTime;
                }
            }
        });



        actionBtnActor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.action, (byte) 0x80});
                    saveTime = nowTime;
                }
            }
        });
        actionBtnActor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.action, (byte) 0x81});
                    saveTime = nowTime;
                }
            }
        });
        actionBtnActor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 200) {
                    MainActivity.addMessage(new byte[]{XGORAM_ADDR.action, (byte) 0x82});
                    saveTime = nowTime;
                }
            }
        });


        actionBtnActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 200) {
                    ControlActivity.mGrayLayout.setVisibility(View.VISIBLE);
                    actionsMiniLayout.setVisibility(View.GONE);
                    actionsMore();
                }
            }
        });

        actionsMiniLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTime = System.currentTimeMillis();
                if ((nowTime - saveTime) > 200) {
                    ControlActivity.mGrayLayout.setVisibility(View.VISIBLE);
                    actionsMiniLayout.setVisibility(View.GONE);
                    actionsMore();
                }
            }
        });
    }

    private void actionsMore() {
//        popView = getLayoutInflater().inflate(R.layout.layout_popupactions, null);
        View rootView = getLayoutInflater().inflate(R.layout.fragment_multi, null);

        mPop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, (int)(Objects.requireNonNull(this.getView()).getHeight() * 0.7));
        mPop.setOutsideTouchable(true);
        mPop.setFocusable(false);
        mPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ControlActivity.mGrayLayout.setVisibility(View.GONE);
                actionsMiniLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.popupActionsBtnBack:
                    mPop.dismiss();
                    actionsMiniLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.popupActionsBtnCarousel:
                    actionBtnCarousel.setActivated(!actionBtnCarousel.isActivated());
                    popupActionsBtnCarousel.setActivated(!popupActionsBtnCarousel.isActivated());
                    if (actionBtnCarousel.isActivated()) {//动作轮播
                        MainActivity.addMessage(new byte[]{0x03, 0x01});
                    } else {
                        MainActivity.addMessage(new byte[]{0x03, 0x00});
                    }
                    break;
                case R.id.popupActionsBtnReset:
                    MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.action, (byte)0xff});
                    popupActionsBtnCarousel.setActivated(false);
                    actionBtnCarousel.setActivated(false);
                    break;
                case R.id.actionBtnLiedown:
                case R.id.popupActionsBtn1:
                    sendAction(action[0]);
                    break;
                case R.id.actionBtnStandup:
                case R.id.popupActionsBtn2:
                    sendAction(action[1]);
                    break;
                case R.id.popupActionsBtn3:
                    sendAction(action[2]);
                    break;
                case R.id.popupActionsBtn4:
                    sendAction(action[3]);
                    break;
//                case R.id.popupActionsBtn5:
//                    sendAction(action[4]);
//                    break;
                case R.id.popupActionsBtn6:
                    sendAction(action[5]);
                    break;
                case R.id.popupActionsBtn7:
                    sendAction(action[6]);
                    break;
                case R.id.popupActionsBtn8:
                    sendAction(action[7]);
                    break;
                case R.id.popupActionsBtn9:
                    sendAction(action[8]);
                    break;
                case R.id.popupActionsBtn10:
                    sendAction(action[9]);
                    break;
                case R.id.popupActionsBtn11:
                    sendAction(action[10]);
                    break;
                case R.id.popupActionsBtn12:
                    sendAction(action[11]);
                    break;
                case R.id.popupActionsBtn13:
                    sendAction(action[12]);
                    break;
                case R.id.popupActionsBtn14:
                    sendAction(action[13]);
                    break;
                case R.id.popupActionsBtn15:
                    sendAction(action[14]);
                    break;
                case R.id.popupActionsBtn16:
                    sendAction(action[15]);
                    break;
                case R.id.popupActionsBtn17:
                    sendAction(action[16]);
                    break;
                case R.id.popupActionsBtn18:
                    sendAction(action[17]);
                    break;
                case R.id.popupActionsBtn19:
                    sendAction(action[18]);
                    break;
                case R.id.popupActionsBtn20:
                    sendAction(action[19]);
                    break;
                case R.id.popupActionsBtn21:
                    sendAction(action[20]);
                    break;
                case R.id.popupActionsBtn22:
                    sendAction(action[21]);
                    break;
                case R.id.popupActionsBtn23:
                    sendAction(action[22]);
                    break;
                case R.id.popupActionsBtn24:
                    sendAction(action[23]);
                    break;

                case R.id.popupActionsBtn0x80:
                    sendAction(actionAdditional[0]);
                    break;
                case R.id.popupActionsBtn0x81:
                    sendAction(actionAdditional[1]);
                    break;
                case R.id.popupActionsBtn0x82:
                    sendAction(actionAdditional[2]);
                    break;
            }
        }
        private void sendAction(byte action){
            MainActivity.addMessage(new byte[]{PublicMethod.XGORAM_ADDR.action, (byte) action});
        }
    }
    public void setPro(boolean isControlPro){
        isPro = isControlPro;
        if (isControlPro){
            arcSeekBarViewLeftRB.setVisibility(View.VISIBLE);
            arcSeekBarViewLeftLT.setVisibility(View.VISIBLE);
            arcSeekBarViewLeftRT.setVisibility(View.VISIBLE);
            arcSeekBarViewRightLB.setVisibility(View.VISIBLE);
            arcSeekBarViewRightLT.setVisibility(View.VISIBLE);
            arcSeekBarViewRightRT.setVisibility(View.VISIBLE);

        } else {
            arcSeekBarViewLeftRB.setVisibility(View.GONE);
            arcSeekBarViewLeftLT.setVisibility(View.GONE);
            arcSeekBarViewLeftRT.setVisibility(View.GONE);
            arcSeekBarViewRightLB.setVisibility(View.GONE);
            arcSeekBarViewRightLT.setVisibility(View.GONE);
            arcSeekBarViewRightRT.setVisibility(View.GONE);
        }
    }


}
