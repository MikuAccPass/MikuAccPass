package com.example.mikuaccpass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PinActivity extends Activity {
    private GlobalApplication lock;
    private Button btn_main;
//    private SharedPreferences preferences;
//    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        btn_main = findViewById(R.id.btn_main);
        lock = (GlobalApplication)getApplication();

//        preferences = this.getSharedPreferences("get_pin", MODE_PRIVATE);
//        final String MyPin = preferences.getString("Pin", "");

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                password = findViewById(R.id.Password);
//                if(MyPin.equals(password)) {
//                    Toast.makeText(PinActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(PinActivity.this, MainActivity.class);
//                    startActivity(i);
//                }else{
//                    password.setError("密码（Pin）错误，请重试！");
//                    return;
//                }

                lock.setLocked(false);
                PinActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
