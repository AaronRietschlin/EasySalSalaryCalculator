package com.asa.easysal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.asa.easysal.R;
import com.asa.easysal.Utils;

/**
 * A cancelabled EditText. Based off of ClearEditText from OAK.
 */
public class CancelEditText extends EditText {

    private Drawable mDrawableClear;
    private Drawable mDrawableEmpty;

    public CancelEditText(Context context) {
        super(context);
    }

    public CancelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        build(attrs);
    }

    public CancelEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        build(attrs);
    }

    private void build(AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        // init the empty drawable
        mDrawableEmpty = getDrawable(R.drawable.ic_action_clear_empty);

        int cancelDrawableResourceId = 0;
        int theme = Utils.getThemeResource(getContext());
        if (theme == 0) {
            return;
        } else if (theme == R.style.DarkTheme) {
            cancelDrawableResourceId = R.drawable.ic_action_clear_light;
        } else {
            cancelDrawableResourceId = R.drawable.ic_action_clear;
        }

        if (cancelDrawableResourceId != 0) {
            mDrawableClear = getDrawable(cancelDrawableResourceId);

            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    showOrHideCancel();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            showOrHideCancel();
        }
    }

    private Drawable getDrawable(@DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return getResources().getDrawable(drawableRes, getContext().getTheme());
        } else {
            return getResources().getDrawable(drawableRes);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mDrawableClear != null
                && event.getX() > getWidth() - getPaddingRight()
                - mDrawableClear.getIntrinsicWidth()) {
            setText("");
            setCancelVisible(false);
        }
        return super.onTouchEvent(event);
    }

    private void showOrHideCancel() {
        setCancelVisible(getText().length() > 0);
    }

    private void setCancelVisible(boolean visible) {
        if (visible) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableClear, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableEmpty, null);
        }
    }
}
