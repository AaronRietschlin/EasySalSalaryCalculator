package com.asa.easysal.utils;

import com.asa.easysal.BuildConfig;
import com.google.android.gms.ads.AdRequest;

/**
 * Created by aaron on 7/19/15.
 */
public class AdUtils {

    public static void addTestAds(AdRequest.Builder adRequestBuilder) {
        if (!BuildConfig.SHOW_ADS) {
            return;
        }
        if (!BuildConfig.SHOW_PROD_AD) {
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        }
    }

}
