package com.asa.easysal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.asa.easysal.firebase.RemoteConfigHandler;
import com.asa.easysal.model.Injector;

public class RemoteConfigFetchService extends JobIntentService {

    private RemoteConfigHandler remoteConfigHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        remoteConfigHandler = Injector.provideRemoteConfigHandler();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        remoteConfigHandler.refresh();
    }

}
