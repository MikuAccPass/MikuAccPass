package com.example.mikuaccpass;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

public class FingerprintActivity extends Activity {
    private LockApplication lock;
    private TextView tv_finger;
    private Button btn_pin;
    private FingerprintIdentify identify;

    private static final int MAX_AVAILABLE_TIMES = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        lock = (LockApplication)getApplication();
        tv_finger = findViewById(R.id.tv_finger);
        btn_pin = findViewById(R.id.btn_pin);

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
            return;
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
                Toast succeed_toast = Toast.makeText(getApplicationContext(),"验证失败，指纹硬件可能已被暂时锁定",Toast.LENGTH_LONG);
                succeed_toast.setGravity(Gravity.CENTER,0,0);
                succeed_toast.show();
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
}
