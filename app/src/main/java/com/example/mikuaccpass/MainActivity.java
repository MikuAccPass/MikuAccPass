package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    LockApplication lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.main);
    }

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

