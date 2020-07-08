package com.example.mikuaccpass;

import android.app.Application;

import com.xuexiang.xui.XUI;

public class XUIInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
        XUI.getInstance().initFontStyle("zt.ttf");
    }
}
