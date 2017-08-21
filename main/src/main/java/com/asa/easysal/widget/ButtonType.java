package com.asa.easysal.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Corresponds to "button_type" key for remote config. See {@code R.xml.remote_config_defaults}.
 */
public enum ButtonType {

    BORDERLESS,
    FULL_WIDTH;

    @Nullable
    public static ButtonType forString(@NonNull String string) {
        ButtonType[] types = ButtonType.values();
        for (ButtonType type : types) {
            if (type.toString().toLowerCase().equals(string.toLowerCase())) {
                return type;
            }
        }
        return null;
    }

}
