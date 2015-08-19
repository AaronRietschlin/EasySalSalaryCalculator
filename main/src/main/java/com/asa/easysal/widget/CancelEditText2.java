package com.asa.easysal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.asa.easysal.R;
import com.asa.easysal.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aaron on 8/18/15.
 */
public class CancelEditText2 extends FrameLayout {

    @Bind(R.id.cancel_layout_field_text_input_field)
    EditText mField;
    @Bind(R.id.cancel_layout_field_text_input_layout)
    TextInputLayout mTextInputLayout;
    @Bind(R.id.cancel_layout_img_clear)
    ImageView mImageClear;

    private CancelAnimator mAnimator;

    public CancelEditText2(Context context) {
        super(context);
        init(context);
    }

    public CancelEditText2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CancelEditText2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CancelEditText2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.cancel_layout_2, this, true);
        ButterKnife.bind(this);

        mAnimator = CancelAnimator.getAnimator();

        // Tint the drawable
        Resources res = getResources();
        int color;
        if (Utils.isMarshmallowOrHigher()) {
            color = res.getColor(R.color.money_color_primary, context.getTheme());
        } else {
            color = res.getColor(R.color.money_color_primary);
        }
        Drawable drawable = mImageClear.getDrawable();
        if (drawable != null) {
            DrawableCompat.setTint(drawable, color);
        }

        mField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    resetError();
                }
                showOrHideCancel();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void resetError() {
        if (getParent() instanceof TextInputLayout) {
            ((TextInputLayout) getParent()).setError(" ");
        }
    }

    private void showOrHideCancel() {
        setCancelVisible(mField.getText().length() > 0);
    }

    private void setCancelVisible(boolean visible) {
        if (visible) {
            mAnimator.show(mImageClear);
        } else {
            mAnimator.hide(mImageClear);
        }
    }

    public TextInputLayout getTextInputLayout() {
        return mTextInputLayout;
    }

    public EditText getEditText() {
        return mField;
    }

    @OnClick(R.id.cancel_layout_img_clear)
    public void onClickImage() {
        mField.setText("");
        setCancelVisible(false);
    }
}
