package com.mls.baseProject.widget;

/**
 * Created by ding on 2016/2/24.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mls.baseProject.R;


/**
 * To change clear icon, set
 */
public class ClearInputEditText extends TextInputEditText implements View.OnTouchListener,
        View.OnFocusChangeListener {

    private Drawable xD;

    public ClearInputEditText(Context context) {
        super(context);
        init();
    }

    public ClearInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener l) {
        this.l = l;
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener f) {
        this.f = f;
    }

    private View.OnTouchListener l;
    private View.OnFocusChangeListener f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - xD
                    .getIntrinsicWidth());
            if (tappedX) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setText("");
                }
                return true;
            }
        }
        if (l != null) {
            return l.onTouch(v, event);
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(!TextUtils.isEmpty(getText()));
        } else {
            setClearIconVisible(false);
        }
        if (f != null) {
            f.onFocusChange(v, hasFocus);
        }
    }

    private void init() {
        xD = getCompoundDrawables()[2];
        if (xD == null) {
            xD = getResources().getDrawable(getDefaultClearIconId());
        }
        xD.setBounds(0, 0, xD.getIntrinsicWidth(), xD.getIntrinsicHeight());
        setClearIconVisible(false);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setClearIconVisible(!TextUtils.isEmpty(getText()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
    }

    private int getDefaultClearIconId() {
        return R.drawable.delete_purge_s;
    }

    public void setClearIconVisible(boolean visible) {
        Drawable x = visible ? xD : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
    }

}
