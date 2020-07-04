package com.example.mikuaccpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    GlobalApplication lock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lock = (GlobalApplication) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lock.setBack(false);
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
        lock.setBack(true);
        if (lock.isBacked())
            lock.setLocked(true);
    }
}

