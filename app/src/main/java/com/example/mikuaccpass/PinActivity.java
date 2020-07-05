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
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        btn_main = findViewById(R.id.btn_main);
        lock = (GlobalApplication)getApplication();
        password = findViewById(R.id.Password);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pin_input = Integer.parseInt(password.getText().toString());
                if(pin_input == lock.getPin()) {
                    Toast.makeText(PinActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                    lock.setLocked(false);
                    PinActivity.this.finish();
                }else{
                    password.setError("错误的Pin");
                    return;
                }
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
