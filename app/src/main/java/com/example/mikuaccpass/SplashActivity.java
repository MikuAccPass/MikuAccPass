package com.example.mikuaccpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        if (global.getPin() == -1) {
            handler.sendEmptyMessageDelayed(1, 2500);
        } else {
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(SplashActivity.this, PinActivity.class);
                    startActivity(intent);
                    //break;

                case 1:
                    Intent intent1 = new Intent();
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent1);
                default:
                    break;
            }
        }
    };


}