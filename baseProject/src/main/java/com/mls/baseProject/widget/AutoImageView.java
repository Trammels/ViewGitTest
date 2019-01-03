package com.mls.baseProject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;

import com.mls.baseProject.R;

public class AutoImageView extends ImageView {


    private final int TOUCH_ADDITION = 0;
    private int mTouchAdditionBottom = 0;
    private int mTouchAdditionLeft = 0;
    private int mTouchAdditionRight = 0;
    private int mTouchAdditionTop = 0;
    private int mPreviousLeft = -1;
    private int mPreviousRight = -1;
    private int mPreviousBottom = -1;
    private int mPreviousTop = -1;


    protected Paint mPaint;

    protected boolean isPressed = false;

    public AutoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        init(context, attrs);

    }

    public AutoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);

    }

    public AutoImageView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LargeTouchableAreaView);
        int addition = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_addition, TOUCH_ADDITION);
        mTouchAdditionBottom = addition;
        mTouchAdditionLeft = addition;
        mTouchAdditionRight = addition;
        mTouchAdditionTop = addition;
        mTouchAdditionBottom = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionBottom,
                mTouchAdditionBottom);
        mTouchAdditionLeft = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionLeft,
                mTouchAdditionLeft);
        mTouchAdditionRight = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionRight,
                mTouchAdditionRight);
        mTouchAdditionTop = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionTop,
                mTouchAdditionTop);
        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (left != mPreviousLeft || top != mPreviousTop
                || right != mPreviousRight || bottom != mPreviousBottom) {
            mPreviousLeft = left;
            mPreviousTop = top;
            mPreviousRight = right;
            mPreviousBottom = bottom;
            final View parent = (View) this.getParent();
            parent.setTouchDelegate(new TouchDelegate(new Rect(left
                    - mTouchAdditionLeft, top - mTouchAdditionTop, right
                    + mTouchAdditionRight, bottom + mTouchAdditionBottom), this));
        }
    }


}
