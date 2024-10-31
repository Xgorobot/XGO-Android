package com.luwu.xgo_robot.mView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.luwu.xgo_robot.R;

public class DeadlineSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private Paint mDeadlinePaint;
    private int mDeadlineLocate;
    private int DeadlineWidth = 4;
    private int DeadlineColor = Color.WHITE;

    public DeadlineSeekBar(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        initAttribute(context, attrs);
        mDeadlinePaint = new Paint();
        mDeadlinePaint.setColor(DeadlineColor);
        mDeadlinePaint.setAntiAlias(true);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DeadlineSeekBar);
        mDeadlineLocate = typedArray.getInt(R.styleable.DeadlineSeekBar_DeadlineLocate, 0);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int lineLeft = (getWidth() -  getPaddingLeft() - getPaddingRight()) * mDeadlineLocate / 100 + getPaddingLeft();
        int lineTop = 0;
        int lineBottom = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            lineTop = getHeight()/2 - getMinHeight()/2;
            lineBottom = lineTop + getMinHeight();
        }
        canvas.drawRect(lineLeft,lineTop,lineLeft + DeadlineWidth, lineBottom, mDeadlinePaint);
    }
}
