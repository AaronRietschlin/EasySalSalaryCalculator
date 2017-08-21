package com.asa.easysal.model;

import com.asa.easysal.firebase.RemoteConfigHandler;
import com.asa.easysal.widget.ButtonType;

public class Injector {

    private static RemoteConfigHandler remoteConfigHandler;
    private static ButtonType buttonType;


    public static RemoteConfigHandler provideRemoteConfigHandler() {
        if (remoteConfigHandler == null) {
            remoteConfigHandler = new RemoteConfigHandler();
        }
        return remoteConfigHandler;
    }

    public static ButtonType provideButtonType() {
        return buttonType;
    }

    public static void setButtonType(ButtonType newType) {
        buttonType = newType;
    }

}
