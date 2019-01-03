package com.mls.baseProject.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Date;

/**
 * Created by CXX on 2015/5/25.
 */
public class SwipView extends FrameLayout {
    private View deleteView;
    private View contentView;
    private int deleteViewWidth;
    private int contentViewWidth;
    private int deleteViewHeight;
    private ViewDragHelper viewDragHelper;
    private String TAG = "SwipView";

    public SwipView(Context context) {
        this(context, null);
    }

    public SwipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, new MyCallBack());
    }

    private class MyCallBack extends ViewDragHelper.Callback {
        /**
         * 决定是否要拖拽当前的view
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == contentView || child == deleteView;
        }

        /**
         * 决定了View将要放置的位置
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                if (left > 0) {
                    return 0;
                }
                if (left < -deleteViewWidth) {
                    return -deleteViewWidth;
                }
            }
            return left;
        }

        /**
         * 当View位置改变时候要做的事情（伴随动画）
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == deleteView) {
                int contentLeft = contentView.getLeft() + dx;
                Log.i(TAG, "onViewPositionChanged left" + left + "  contentLeft: " + contentLeft + "  dx" + dx);
                deleteView.layout(contentViewWidth - deleteViewWidth, 0, contentViewWidth, deleteViewHeight);
                if (Math.abs(contentLeft) < deleteViewWidth) {
                    contentView.layout(contentLeft, 0, contentViewWidth + contentLeft, deleteViewHeight);
                }
            }
            if (contentView.getLeft() == 0 && mState != SwipState.Close) {
                mState = SwipState.Close;
                if (swipStateChangeListener != null) {
                    swipStateChangeListener.close(SwipView.this);
                }
            } else if (contentView.getLeft() == -deleteViewWidth && mState != SwipState.Open) {
                mState = SwipState.Open;
                if (swipStateChangeListener != null) {
                    swipStateChangeListener.open(SwipView.this);
                }
            } else if (mState != SwipState.Swiping) {
                mState = SwipState.Swiping;
                if (swipStateChangeListener != null) {
                    swipStateChangeListener.swiping(SwipView.this);
                }
            }
            invalidate();
        }

        public void closeLaseSwipView() {
            close(true);
        }

        private Date nowDate;
        private Date lastDate = new Date();

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            nowDate = new Date();
            Log.i(TAG, "时间差" + (nowDate.getTime() - lastDate.getTime()));
            if (xvel > 0) {
                close(true);
            } else if (xvel == 0 && Math.abs(contentView.getLeft()) < deleteViewWidth * 0.5) {
                close(true);
            } else {
                open(true);
            }
            lastDate = new Date();
        }

        /**
         * 设置横向拖拽的范围
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return deleteViewWidth;
        }
    }

    public void open(boolean isSmooth) {
        if (isSmooth) {
            if (viewDragHelper.smoothSlideViewTo(contentView, -deleteViewWidth, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            contentView.layout(-deleteViewWidth, 0, contentViewWidth - deleteViewWidth, 0);
        }
    }

    /**
     * 关闭滑动
     */
    public void close(boolean isSmooth) {
        if (isSmooth) {
            if (viewDragHelper.smoothSlideViewTo(contentView, 0, 0)) {
                //如果是true表示还没到指定位置
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            contentView.layout(0, 0, contentViewWidth, deleteViewHeight);
        }

    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_DOWN:

                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        deleteViewWidth = deleteView.getMeasuredWidth();
        contentViewWidth = contentView.getMeasuredWidth();
        deleteViewHeight = deleteView.getMeasuredHeight();
    }

    @Override
    public void onFinishInflate() {
        deleteView = getChildAt(0);
        contentView = getChildAt(1);
    }

    public enum SwipState {
        Open, Close, Swiping
    }

    private SwipState mState = SwipState.Close;

    public void setmState(SwipState mState) {
        this.mState = mState;
    }

    public SwipState getState() {
        return mState;
    }

    public interface SwipStateChangeListener {
        public void open(SwipView swipView);

        public void close(SwipView swipView);

        public void swiping(SwipView swipview);
    }

    private SwipStateChangeListener swipStateChangeListener;

    public void setSwipStateChangeListener(SwipStateChangeListener swipStateChangeListener) {
        this.swipStateChangeListener = swipStateChangeListener;
    }
}
