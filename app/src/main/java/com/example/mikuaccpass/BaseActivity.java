package com.example.mikuaccpass;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    LockApplication lock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lock = (LockApplication) getApplication();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        lock.setBack(true);
        if (lock.isBacked())
            lock.setLocked(true);
    }
}

