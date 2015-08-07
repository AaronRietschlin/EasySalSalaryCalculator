package com.asa.easysal.utils;

import android.content.Context;

import com.asa.easysal.R;

import de.psdev.licensesdialog.LicensesDialog;

/**
 * Created by aaron on 8/6/15.
 */
public class NoticeUtils {

    public static void showLicenseDialog(Context context) {
        new LicensesDialog.Builder(context)
                .setNotices(R.raw.notices)
                .build().show();
    }

}
