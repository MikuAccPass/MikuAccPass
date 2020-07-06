package com.example.mikuaccpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class SplashActivity extends BaseActivity {




    //private static int SPLASH_DISPLAY_LENGHT= 2000;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    startActivity(new Intent(SplashActivity.this, PinActivity.class));
                    finish();
                    //break;
                case 1:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                   // break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_splash);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, SetPinActivity.class);
//                startActivity(intent);
//                SplashActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGHT);
        init();
    }

    private void init(){
        if(global.getPin()==-1){
            handler.sendEmptyMessageDelayed(1, 2500);
        }else{
            handler.sendEmptyMessageDelayed(0, 2500);
        }
    }
}