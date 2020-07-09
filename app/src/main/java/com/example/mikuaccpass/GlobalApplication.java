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
    final String[] list_elements = {"支付宝", "bilibili", "大众点评", "毒", "虎扑", "instagram", "京东", "keep", "美团", "nice", "qq", "steam", "淘宝", "百度贴吧", "抖音", "微信", "微博", "知乎"};
    List<String> list = Arrays.asList(list_elements);

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
        screenshotPermit = false;

        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        pin = preferences.getInt("Pin", -1);
        if (pin == -1)
            isLocked = false;

        fingerprint_enable = preferences.getBoolean("fingerprint_enable", false);
    }

    public String[] getString() {
        return list_elements;
    }

    public void setLocked(boolean locked) {
        if (pin != -1)
            isLocked = locked;
        else
            isLocked = false;
    }

    public String getElement(String appname) {

        //appname.toLowerCase();
        if(appname.equals("支付宝"))
            return "alipay";
        else if(appname.equals("bilibili"))
            return "bilibili";
        else if(appname.equals("大众点评"))
            return "dian_ping";
        else if(appname.equals("毒"))
            return "du";
        else if(appname.equals("虎扑"))
            return "hupu";
        else if(appname.equals("instagram"))
            return "instagram";
        else if(appname.equals("京东"))
            return "jd";
        else if(appname.equals("keep"))
            return "keep";
        else if(appname.equals("美团"))
            return "meituan";
        else if(appname.equals("nice"))
            return "nice";
        else if(appname.equals("qq"))
            return "qq";
        else if(appname.equals("steam"))
            return "steam";
        else if(appname.equals("淘宝"))
            return "taobao";
        else if(appname.equals("百度贴吧"))
            return "tieba";
        else if(appname.equals("抖音"))
            return "tiktok";
        else if(appname.equals("微信"))
            return"wechat";
        else if(appname.equals("微博"))
            return "weibo";
        else if(appname.equals("知乎"))
            return "zhihu";
        else if(list.contains(appname))
            return appname;
        else return "application";
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
