package com.example.mikuaccpass;

import android.app.Application;
import android.content.SharedPreferences;

public class GlobalApplication extends Application {
    private boolean isLocked;
    private boolean screenshotPermit;
    private int pin;
    private boolean fingerprint_enable;

    private SharedPreferences preferences;

    public boolean isFingerprint_enable() {
        return fingerprint_enable;
    }

    public void setFingerprint_enable(boolean fingerprint_enable) {
        this.fingerprint_enable = fingerprint_enable;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isLocked = true;
        screenshotPermit=false;

        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        pin = preferences.getInt("Pin", -1);
        if(pin == -1)
            isLocked = false;

        fingerprint_enable = preferences.getBoolean("fingerprint_enable",false);
    }

    public void setLocked(boolean locked) {
        if(pin != -1)
            isLocked = locked;
        else
            isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
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
