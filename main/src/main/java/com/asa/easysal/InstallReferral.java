package com.asa.easysal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InstallReferral extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO - Possibly log this. Using it to kick off fetching.
        context.startService(new Intent(context, RemoteConfigFetchService.class));
    }

}
