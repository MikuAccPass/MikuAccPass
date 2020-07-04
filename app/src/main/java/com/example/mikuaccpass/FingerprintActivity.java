package com.example.mikuaccpass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

public class FingerprintActivity extends Activity {
    private GlobalApplication lock;
    private TextView tv_finger;
    private FingerprintIdentify identify;

    private static final int MAX_AVAILABLE_TIMES = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        lock = (GlobalApplication)getApplication();
        tv_finger = findViewById(R.id.tv_finger);

        identify = new FingerprintIdentify(getApplicationContext());
        identify.setExceptionListener(new BaseFingerprint.ExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
                System.out.println("错误"+exception.getLocalizedMessage());
            }
        });
        identify.init();

        if(!identify.isFingerprintEnable()){
            tv_finger.setText("无法进行指纹验证");
            Intent intent = new Intent(FingerprintActivity.this, PinActivity.class);
            FingerprintActivity.this.finish();
        }
        identify.startIdentify(MAX_AVAILABLE_TIMES, new BaseFingerprint.IdentifyListener() {
            @Override
            public void onSucceed() {
                Toast succeed_toast = Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_LONG);
                succeed_toast.setGravity(Gravity.CENTER,0,0);
                succeed_toast.show();
                lock.setLocked(false);
                FingerprintActivity.this.finish();
            }

            @Override
            public void onNotMatch(int availableTimes) {
                Toast succeed_toast = Toast.makeText(getApplicationContext(),"指纹不匹配",Toast.LENGTH_LONG);
                succeed_toast.setGravity(Gravity.CENTER,0,0);
                succeed_toast.show();
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                tv_finger.setText("请使用Pin验证");
                Intent pin = new Intent(FingerprintActivity.this, PinActivity.class);
                startActivity(pin);
                FingerprintActivity.this.finish();
        }

            @Override
            public void onStartFailedByDeviceLocked() {
                Toast succeed_toast = Toast.makeText(getApplicationContext(),"验证失败，设备可能已被暂时锁定",Toast.LENGTH_LONG);
                succeed_toast.setGravity(Gravity.CENTER,0,0);
                succeed_toast.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        identify.cancelIdentify();
        FingerprintActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        identify.cancelIdentify();
        FingerprintActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    public void usePin(View view){
        Intent pin = new Intent(FingerprintActivity.this, PinActivity.class);
        startActivity(pin);
        FingerprintActivity.this.finish();
    }

}
