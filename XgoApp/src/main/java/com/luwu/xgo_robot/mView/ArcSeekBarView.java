package com.luwu.xgo_robot.mView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.luwu.xgo_robot.R;

public class ArcSeekBarView extends View {

    final int DEFAULT_BACKGROUND_Width = 200, DEFAULT_BACKGROUND_Height = 200;
    private Bitmap mBackgroundBitmap;
    private Bitmap mThumbBitmap;
    private Paint mBackgroundPaint;
    private Paint mPassPaint;
    private Paint mThumbPaint;
    private Point mBackgroundPoint;
    private Point mThumbPoint;
    private int mProgress = 50;//0-100

    private final int defaultProgress = 50;
    private int measuredHeight;
    private int measuredWidth;

    private int mBackgroundColor, mThumbColor, mPassColor, mBackgroundStroke, mThumbRadius;  // mThumbRadius >= mBackgroundStroke
    private static final int mBackgroundStrokeDefault = 10;
    private static final int mThumbRadiusDefault = 20;

    final private int isPhoto = 0, isColor = 1, isDefault = 2;//设置的背景模式 0：图片 1：颜色 2默认模式
    private int mBackgroundMode = isDefault, mPassMode = isDefault, mThumbMode = isDefault;

    final private int isRightBottom = 0, isLeftBottom = 1, isLeftTop = 2, isRightTop = 3;//圆弧 0左上 1右上  2右下 3左下

    private int ArcMode;
    private static final int ArcModeDefault = 2;

    private static final int sweepAngle = 70;

    private static final int startAngle = 10;
    private final float range = 0.50f;
    private ISeekBarListener mListener;

    public ArcSeekBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context, attrs);//拿到自定义属性

        mBackgroundPaint = new Paint();
        mPassPaint = new Paint();
        mThumbPaint = new Paint();

        mBackgroundPaint.setAntiAlias(true);
        mPassPaint.setAntiAlias(true);
        mThumbPaint.setAntiAlias(true);

        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mPassPaint.setStyle(Paint.Style.STROKE);

        mThumbPoint = new Point();
        mBackgroundPoint = new Point();
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (mProgress > 100) {
            mProgress = 100;
        } else if (mProgress < 0) {
            mProgress = 0;
        }
    }

    /**
     * update progress with view
     * @param progress
     */
    public void updateProgress(int progress) {
        setProgress(progress);
        invalidate();//view重绘
    }

    public void setListener(ISeekBarListener listener) {
        this.mListener = listener;
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBarView);

        Drawable mBackground = typedArray.getDrawable(R.styleable.ArcSeekBarView_ArcSeekBarBackground);
        if (null != mBackground) {
            // 设置了背景
            if (mBackground instanceof BitmapDrawable) {
                // 设置了一张图片
                mBackgroundBitmap = ((BitmapDrawable) mBackground).getBitmap();
                mBackgroundMode = isPhoto;
            } else if (mBackground instanceof ColorDrawable) {
                // 色值
                mBackgroundColor = ((ColorDrawable) mBackground).getColor();
                mBackgroundMode = isColor;
            } else {
                mBackgroundMode = isDefault;
            }
        } else{
            mBackgroundMode = isDefault;
        }

        Drawable mPass = typedArray.getDrawable(R.styleable.ArcSeekBarView_ArcSeekBarPass);
        if (null != mBackground) {
            // 设置了背景
            if (mBackground instanceof ColorDrawable) {
                // 色值
                mPassColor = ((ColorDrawable) mPass).getColor();
                mPassMode = isColor;
            } else {
                mPassMode = isDefault;
            }
        }

        // 按钮背景
        Drawable mThumb = typedArray.getDrawable(R.styleable.ArcSeekBarView_ArcSeekBarThumb);
        if (null != mThumb) {
            // 设置了按钮背景
            if (mThumb instanceof BitmapDrawable) {
                // 图片
                mThumbBitmap = ((BitmapDrawable) mThumb).getBitmap();
                mThumbMode = isPhoto;
            } else if (mThumb instanceof ColorDrawable) {
                // 色值
                mThumbColor = ((ColorDrawable) mThumb).getColor();
                mThumbMode = isColor;
            }  else {
                // 其他形式
                mThumbMode = isDefault;
            }
        } else {
            mThumbMode = isDefault;
        }

        ArcMode = typedArray.getInt(R.styleable.ArcSeekBarView_ArcMode, ArcModeDefault) % 4;
        mBackgroundStroke = typedArray.getInt(R.styleable.ArcSeekBarView_ArcSeekBarBackgroundStroke, mBackgroundStrokeDefault);
        mThumbRadius = typedArray.getInt(R.styleable.ArcSeekBarView_ArcSeekBarThumbRadius, mThumbRadiusDefault);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth, measureHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            // 具体的值和match_parent
            measureWidth = widthSize;
        } else {
            // wrap_content
            measureWidth = DEFAULT_BACKGROUND_Width;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        } else {
            measureHeight = DEFAULT_BACKGROUND_Height;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
        int offset = Math.max(mThumbRadius,mBackgroundStroke/2);
        switch (ArcMode){
            case isRightBottom:
                mBackgroundPoint.set(0, 0);
                break;
            case isLeftBottom:
                mBackgroundPoint.set(measuredWidth, 0);
                break;
            case isLeftTop:
                mBackgroundPoint.set(measuredWidth, measuredHeight);
                break;
            case isRightTop:
                mBackgroundPoint.set(0, measuredHeight);
                break;
        }

        // 背景
        if (mBackgroundMode == isColor) {
            mBackgroundPaint.setColor(mBackgroundColor);
            mBackgroundPaint.setStrokeWidth(mBackgroundStroke);
            RectF rectf_head = new RectF(-measuredWidth + offset + mBackgroundPoint.x, -measuredHeight + offset+ mBackgroundPoint.y, measuredWidth - offset+ mBackgroundPoint.x, measuredHeight - offset + mBackgroundPoint.y);
            canvas.drawArc(rectf_head, ArcMode * 90 + startAngle, sweepAngle, false, mBackgroundPaint);
        } else if (mBackgroundMode == isPhoto) {
            Rect src = new Rect(0, 0, mBackgroundBitmap.getWidth(), mBackgroundBitmap.getHeight());
            Rect dst = new Rect(0, 0, measuredWidth, measuredHeight);
            canvas.drawBitmap(mBackgroundBitmap, src, dst, mBackgroundPaint);
        } else if (mBackgroundMode == isDefault) {
            mBackgroundPaint.setColor(Color.BLUE);
            mBackgroundPaint.setStrokeWidth(mBackgroundStroke);
            RectF rectf_head = new RectF(-measuredWidth + offset, -measuredHeight + offset, measuredWidth - offset, measuredHeight - offset);
            canvas.drawArc(rectf_head, ArcMode * 90 + startAngle, sweepAngle, false, mBackgroundPaint);
        }

        // pass
        double radius = (double)(mProgress / 100.0 * sweepAngle + startAngle + ArcMode * 90);
        mPassPaint.setColor(mBackgroundColor);
        if (mPassMode == isColor) {
            mPassPaint.setColor(mPassColor);
        } else if (mPassMode == isDefault) {
            mPassPaint.setColor(Color.YELLOW);
        }
        mPassPaint.setStrokeWidth(mBackgroundStroke);
        RectF rectf_head = new RectF(-measuredWidth + offset + mBackgroundPoint.x, -measuredHeight + offset+ mBackgroundPoint.y, measuredWidth - offset+ mBackgroundPoint.x, measuredHeight - offset + mBackgroundPoint.y);
        System.out.println((int)radius - (ArcMode * 90 + 45));
        System.out.println((int)radius);
        canvas.drawArc(rectf_head, ArcMode * 90 + 45 , (int)radius - (ArcMode * 90 + 45) , false, mPassPaint);

        // 原点
        double radian = Math.toRadians(radius);
        mThumbPoint.set((int)(Math.cos(radian) * (measuredWidth  - offset)) + mBackgroundPoint.x, (int)( Math.sin(radian) * (measuredHeight  - offset))+ mBackgroundPoint.y);
        if (mThumbMode == isColor) {
            mThumbPaint.setColor(mThumbColor);
            canvas.drawCircle(mThumbPoint.x, mThumbPoint.y, mThumbRadius, mThumbPaint);
        } else if (mThumbMode == isPhoto) {
            Rect src = new Rect(0, 0, mThumbBitmap.getWidth(), mThumbBitmap.getHeight());
            Rect dst = new Rect(mThumbPoint.x - mThumbBitmap.getWidth()/2, mThumbPoint.y - mThumbBitmap.getHeight()/2, mThumbPoint.x + mThumbBitmap.getWidth()/2, mThumbPoint.y + mThumbBitmap.getHeight()/2);
            canvas.drawBitmap(mThumbBitmap, src, dst, mThumbPaint);
        } else if (mThumbMode == isDefault) {
            mThumbPaint.setColor(Color.WHITE);
            canvas.drawCircle(mThumbPoint.x, mThumbPoint.y, mThumbRadius, mThumbPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float moveX = event.getX();
        float moveY = event.getY();
        int progress;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) {
                    mListener.actionDown();
                }
                progress = getThumbProgress(mBackgroundPoint, new Point((int) moveX, (int) moveY), Math.min(measuredWidth/2, measuredHeight/2), range);
                if (progress >= 0 & progress <= 100){
                    mProgress = progress;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.actionUp();
                }
                progress = getThumbProgress(mBackgroundPoint, new Point((int) moveX, (int) moveY), Math.min(measuredWidth/2, measuredHeight/2), range);
                if (progress >= 0 & progress <= 100){
                    mProgress = progress;
                    invalidate();
                }
//                mProgress = defaultProgress;
                invalidate();
//                }
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d("Tag", "onTouchEvent: "+mProgress);
                if (mListener != null) {
                    mListener.actionMove();
                }
                progress = getThumbProgress(mBackgroundPoint, new Point((int) moveX, (int) moveY), Math.min(measuredWidth/2, measuredHeight/2), range);
                if (progress >= 0 & progress <= 100){
                    mProgress = progress;
                    invalidate();
                }
                break;
        }

        return true;
    }
    private int getThumbProgress(Point centerPoint, Point touchPoint, float backgroundRadius, float range) {
        // 两点在X轴的距离
        float lenX = (float) (touchPoint.x - centerPoint.x);
        // 两点在Y轴距离
        float lenY = (float) (touchPoint.y - centerPoint.y);
        // 两点距离
        float lenXY = (float) Math.sqrt((double) (lenX * lenX + lenY * lenY));
        // 计算弧度
        double radian = Math.acos(lenX / lenXY) * (touchPoint.y < centerPoint.y ? -1 : 1);

        double angle = radian2Angle(radian);


        if (angle > ArcMode * 90  & angle < ArcMode * 90 + 90){
            int thumbX = (int)(measuredWidth / 2 + Math.cos(angle) * (measuredWidth - mBackgroundStroke) / 2);
            int thumbY = (int)(measuredHeight / 2 + Math.sin(angle) * (measuredHeight - mBackgroundStroke) / 2);
            float lenThumb = (float) Math.sqrt((double) (Math.pow((thumbX - centerPoint.x),2)  + Math.pow((thumbY - centerPoint.y), 2)));
            if (lenXY/lenThumb >= (1.0f - range) & lenXY/lenThumb <= (1.0f + range)){
                double angleT = (double) (angle - ArcMode * 90);
                if (angleT <= startAngle){
                    angleT = 0.0;
                } else if (angleT >= startAngle + sweepAngle){
                    angleT = (double) sweepAngle;
                } else {
                    angleT = angleT - startAngle;
                }
                return (int) (angleT / sweepAngle * 100);
            } else {
                return -1;
            }

        } else {
            return -1;
        }
    }

    private double radian2Angle(double radian) {
        double tmp = Math.round(radian / Math.PI * 180);
        return tmp >= 0 ? tmp : 360 + tmp;
    }


    public interface ISeekBarListener {
        void actionDown();

        void actionUp();

        void actionMove();
    }

}
