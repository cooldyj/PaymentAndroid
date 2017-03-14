package com.jerry.payment.mobile.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.utils.DisplayUtils;

/**
 * 带有颜色边框的RelativeLayout
 * Created by jerry on 2016/7/6.
 */
public class ColoredBorderLayout extends RelativeLayout {

    private Paint paint;

    private float mBorderThick;
    private int mBorderColor;

    public ColoredBorderLayout(Context context) {
        this(context, null);
    }

    public ColoredBorderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColoredBorderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColoredBorderLayout, defStyleAttr, 0);
        mBorderThick = typedArray.getDimensionPixelSize(R.styleable.ColoredBorderLayout_borderThick, 1);
        mBorderColor = typedArray.getColor(R.styleable.ColoredBorderLayout_borderColor, context.getResources().getColor(R.color.list_item_divider));
        typedArray.recycle();
        init(context);
    }

    private void init(Context context){
        //设置允许绘画,会调用onDraw方法
        setWillNotDraw(false);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(DisplayUtils.convertPX2DIP(context, mBorderThick));
        paint.setColor(mBorderColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
    }
}
