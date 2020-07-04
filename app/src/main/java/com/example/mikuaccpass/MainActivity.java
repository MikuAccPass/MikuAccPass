package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_main=null;
    LockApplication lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_main=(Button) findViewById(R. id.btn_main);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView (R. layout.main);
            }
        });
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

