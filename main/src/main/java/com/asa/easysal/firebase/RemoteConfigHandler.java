package com.asa.easysal.firebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.asa.easysal.BuildConfig;
import com.asa.easysal.R;
import com.asa.easysal.model.Injector;
import com.asa.easysal.widget.ButtonType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import timber.log.Timber;

public class RemoteConfigHandler {

    public static final String KEY_BUTTON_TYPE = "button_type";
    private static final long DEFAULT_CACHE_EXPIRATION = 3600;

    private FirebaseRemoteConfig remoteConfig;

    public void initialize() {
        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        remoteConfig.setConfigSettings(configSettings);
        remoteConfig.setDefaults(R.xml.remote_config_defaults);
        setDefaultButtonType();
    }

    public void refresh() {
        long cacheExpiration = getCacheExpiration();
        remoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    remoteConfig.activateFetched();
                    Timber.d("Successfully retrieved new Remote Config.");
                    setDefaultButtonType();
                } else {
                    Timber.d("Failed to retrieve new Remote Config.");
                }
            }
        });
    }

    private void setDefaultButtonType() {
        String value = remoteConfig.getString(KEY_BUTTON_TYPE);
        ButtonType buttonType = isEmpty(value) ? null : ButtonType.forString(value);
        if (buttonType == null) {
            Timber.d("Unable to retrieve button type from Remote Config.");
        } else {
            Timber.d("Button type set. ButtonType: %s", buttonType.toString());
            Injector.setButtonType(buttonType);
        }
    }

    private boolean isEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    private long getCacheExpiration() {
        return remoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled() ? 0 : DEFAULT_CACHE_EXPIRATION;
    }
}
