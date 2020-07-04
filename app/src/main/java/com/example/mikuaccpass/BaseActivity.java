package com.example.mikuaccpass;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    LockApplication lock;

    @Override
    protected void onResume() {
        super.onResume();
        lock = (LockApplication) getApplication();
        if(lock.isLocked()){
            Intent finger = new Intent(this,FingerprintActivity.class);
            startActivity(finger);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lock.setLocked(true);
    }
}
