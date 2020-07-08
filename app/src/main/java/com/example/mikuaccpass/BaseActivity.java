package com.example.mikuaccpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.autofill.AutofillManager;
import android.widget.Toast;

import androidx.annotation.Nullable;;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    GlobalApplication global;
    private static int appCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = (GlobalApplication) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        appCount++;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getSystemService(AutofillManager.class).hasEnabledAutofillServices()) {
            Toast.makeText(BaseActivity.this, "服务已启用", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BaseActivity.this, "服务未启用", Toast.LENGTH_SHORT).show();
        }

        if (global.isLocked() && global.getPin() != -1) {
            if (global.isFingerprint_enable()) {
                Intent finger = new Intent(this, FingerprintActivity.class);
                startActivity(finger);
            } else {
                Intent pin = new Intent(this, PinActivity.class);
                startActivity(pin);
            }
        }
        if (global.ScreenshotPermit()) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        appCount--;
        if (appCount == 0) {
            global.setLocked(true);
        }
    }
}

