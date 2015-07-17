package com.asa.easysal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

import com.asa.easysal.ui.EsSalaryFragment;

import timber.log.Timber;

/**
 * Created by aaron on 7/17/15.
 */
public class SnackUtils {

    @Nullable
    public static Snackbar make(@NonNull EsSalaryFragment fragment, @StringRes int text) {
        return make(fragment, text, Snackbar.LENGTH_SHORT);
    }

    @Nullable
    public static Snackbar make(@NonNull EsSalaryFragment fragment, String text) {
        return make(fragment, text, Snackbar.LENGTH_SHORT);
    }

    @Nullable
    public static Snackbar make(@NonNull EsSalaryFragment fragment, @StringRes int text, int length) {
        try {
            return Snackbar.make(fragment.getView(), text, length);
        } catch (Exception e) {
            Timber.e(e, "Tried to create a SnackBar, but an exception occurred.");
            return null;
        }
    }

    @Nullable
    public static Snackbar make(@NonNull EsSalaryFragment fragment, String text, int length) {
        try {
            return Snackbar.make(fragment.getView(), text, length);
        } catch (Exception e) {
            Timber.e(e, "Tried to create a SnackBar, but an exception occurred.");
            return null;
        }
    }

}
