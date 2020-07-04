package com.example.mikuaccpass;

import android.app.Application;

public class LockApplication extends Application {
    private boolean isLocked;

    @Override
    public void onCreate() {
        super.onCreate();
        setLocked(true);
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
