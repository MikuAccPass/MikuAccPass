package com.example.mikuaccpass;

import android.app.Application;
import android.content.SharedPreferences;

public class GlobalApplication extends Application {
    private boolean isLocked;
    private boolean back;
    private boolean screenshotPermit;
    private int pin;

    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        setLocked(true);
        setBack(false);
        setScreenshotPermit(false);

        preferences = getSharedPreferences("get_pin", MODE_PRIVATE);
        pin = preferences.getInt("Pin", -1);
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

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }
}
