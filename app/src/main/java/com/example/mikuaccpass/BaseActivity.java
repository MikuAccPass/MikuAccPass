package com.example.mikuaccpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    GlobalApplication lock;
    private static int appCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lock = (GlobalApplication) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        appCount++;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lock.isLocked()) {
            Intent finger = new Intent(this, FingerprintActivity.class);
            startActivity(finger);
        }
        if(lock.ScreenshotPermit())
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        else
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        appCount--;
        if(appCount == 0){
            lock.setLocked(true);
        }
    }
}

