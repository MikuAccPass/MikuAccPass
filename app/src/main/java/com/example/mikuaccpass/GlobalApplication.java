package com.example.mikuaccpass;

import android.app.Application;

public class GlobalApplication extends Application {
    private boolean isLocked;
    private boolean back;
    private boolean screenshotPermit;

    @Override
    public void onCreate() {
        super.onCreate();
        setLocked(true);
        setBack(false);
        setScreenshotPermit(false);
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

    public boolean ScreenshotPermit() {
        return screenshotPermit;
    }


    public void setScreenshotPermit(boolean screenshotPermit) {
        this.screenshotPermit = screenshotPermit;
    }
}
