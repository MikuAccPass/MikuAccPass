package com.example.mikuaccpass;

import android.app.Application;
import android.content.SharedPreferences;





import java.util.Arrays;
import java.util.List;



import java.util.Arrays;
import java.util.List;

import com.xuexiang.xui.XUI;
public class GlobalApplication extends Application {
    private boolean isLocked;
    private boolean screenshotPermit;
    private int pin;
    private boolean fingerprint_enable;
    final String[] list_elements = {"alipay", "bilibili", "dian_ping","du","hupu","instagram","jd","keep","meituan","nice","qq","steam","taobao","tieba","tiktok","wechat","weibo","zhihu"};
    List<String> list= Arrays.asList(list_elements);

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
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
        isLocked = true;
        screenshotPermit=false;

        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        pin = preferences.getInt("Pin", -1);
        if(pin == -1)
            isLocked = false;

        fingerprint_enable = preferences.getBoolean("fingerprint_enable",false);
    }

    public String[] getString(){
      return list_elements;
    }
    public void setLocked(boolean locked) {
        if(pin != -1)
            isLocked = locked;
        else
            isLocked = false;
    }

    public String getElement(String appname){

        appname.toLowerCase();
     if (list.contains(appname))
        return appname;
     else return "ic_launcher_background";
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
