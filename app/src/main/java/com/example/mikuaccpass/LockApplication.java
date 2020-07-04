package com.example.mikuaccpass;

import android.app.Application;

public class LockApplication extends Application {
    private boolean isLocked;
    private boolean back;

    @Override
    public void onCreate() {
        super.onCreate();
        setLocked(true);
        setBack(false);
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isBacked() {
        return back;
    }
}
