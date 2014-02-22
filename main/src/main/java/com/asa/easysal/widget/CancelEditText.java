package com.asa.easysal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

	private Drawable mDrawable;

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
			mDrawable = getResources().getDrawable(cancelDrawableResourceId);

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

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mDrawable != null
				&& event.getX() > getWidth() - getPaddingRight()
						- mDrawable.getIntrinsicWidth()) {
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
			setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}
}
