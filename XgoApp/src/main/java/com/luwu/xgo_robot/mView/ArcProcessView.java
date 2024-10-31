package com.luwu.xgo_robot.mView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.luwu.xgo_robot.R;

public class ArcProcessView extends View {

    private final int DEFAULT_BACKGROUND_SIZE = 400;
    private int measureWidth, measureHeight;
    private Paint mBackgroundPaint;
    private Paint ArcBackPaint, ArcProgressPaint;
    private Paint mRockerPaint;
    private int mProgress;
    private int ArcStroke;
    private final int ArcStrokeDefault = 8;
    private int ArcBackColor, ArcProgressStartColor, ArcProgressEndColor;

    public ArcProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context, attrs);//拿到自定义属性

        mBackgroundPaint = new Paint();
        ArcBackPaint = new Paint();
        ArcProgressPaint = new Paint();
        mRockerPaint = new Paint();

        mBackgroundPaint.setAntiAlias(true);
        ArcBackPaint.setAntiAlias(true);
        ArcProgressPaint.setAntiAlias(true);
        mRockerPaint.setAntiAlias(true);

        ArcBackPaint.setStyle(Paint.Style.STROKE);
        ArcProgressPaint.setStyle(Paint.Style.STROKE);

        mProgress = 0;
    }


    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProcessView);

        // Arc背景
        Drawable mArc = typedArray.getDrawable(R.styleable.ArcProcessView_ArcProcesspastBackground);
        if (null != mArc) {
            if (mArc instanceof ColorDrawable) {
                ArcBackColor = ((ColorDrawable) mArc).getColor();
            }
        }


        mArc = typedArray.getDrawable(R.styleable.ArcProcessView_ArcProcesspastStart);
        if (null != mArc) {
            if (mArc instanceof ColorDrawable) {
                ArcProgressStartColor = ((ColorDrawable) mArc).getColor();
            }
        }

        mArc = typedArray.getDrawable(R.styleable.ArcProcessView_ArcProcesspastEnd);
        if (null != mArc) {
            if (mArc instanceof ColorDrawable) {
                ArcProgressEndColor = ((ColorDrawable) mArc).getColor();
            }
        }

        ArcStroke = typedArray.getInt(R.styleable.ArcProcessView_ArcProcessArcStroke, ArcStrokeDefault);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            // 具体的值和match_parent
            measureWidth = widthSize;
        } else {
            // wrap_content
            measureWidth = DEFAULT_BACKGROUND_SIZE;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        } else {
            measureHeight = DEFAULT_BACKGROUND_SIZE;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        SweepGradient sweepGradient = new SweepGradient(measuredWidth / 2,measuredHeight / 2 , new int[]{ArcProgressStartColor, ArcProgressStartColor, ArcProgressEndColor}, new float[]{0.0f,0.5f ,1.0f});
        ArcProgressPaint.setShader(sweepGradient);
        ArcProgressPaint.setStrokeWidth(ArcStroke);
        ArcBackPaint.setColor(ArcBackColor);
        ArcBackPaint.setStrokeWidth(ArcStroke);

        RectF rectf_head=new RectF(ArcStroke / 2, ArcStroke / 2, measuredWidth - ArcStroke/2, measuredHeight - ArcStroke / 2);//确定外切矩形范围
        canvas.drawArc(rectf_head, 190, 160, false, ArcProgressPaint);
        canvas.drawArc(rectf_head, 190 + (int)(mProgress*160/100), 160 - (int)(mProgress*160/100), false, ArcBackPaint);//绘制圆弧，不含圆心
    }

    public void setProgress(int Progress){
        if (Progress <= 100 & Progress >=0) {
            mProgress = Progress;
            invalidate();
        }
    }
}
