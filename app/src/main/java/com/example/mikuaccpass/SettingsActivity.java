package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        Button pin_btn = (Button)findViewById(R.id.pin_btn);
        pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, SetPinActivity.class);
                startActivity(i);
            }
        });
//开关还缺实际功能的控制
        Switch shot_switch = (Switch)findViewById(R.id.shot_switch);
        shot_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast shot_toast= Toast.makeText(SettingsActivity.this, "允许屏幕截图", Toast.LENGTH_SHORT);
                    shot_toast.setGravity(Gravity.CENTER, 0, 0);
                    shot_toast.show();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }else{
                    Toast shot_toast = Toast.makeText(SettingsActivity.this, "禁止屏幕截图", Toast.LENGTH_SHORT);
                    shot_toast.setGravity(Gravity.CENTER, 0, 0);
                    shot_toast.show();
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
            }
        });

        Switch fill_switch = (Switch)findViewById(R.id.fill_switch);
        fill_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast fill_toast= Toast.makeText(SettingsActivity.this, "自动填充已开启", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();

                }else{
                    Toast fill_toast = Toast.makeText(SettingsActivity.this, "自动填充已关闭", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();

                }
            }
        });

        Switch fingerprint_switch = (Switch)findViewById(R.id.fingerprint_switch);
        fingerprint_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast fingerprint_toast= Toast.makeText(SettingsActivity.this, "指纹验证已开启", Toast.LENGTH_SHORT);
                    fingerprint_toast.setGravity(Gravity.CENTER, 0, 0);
                    fingerprint_toast.show();

                }else{
                    Toast fingerprint_toast = Toast.makeText(SettingsActivity.this, "指纹验证已关闭", Toast.LENGTH_SHORT);
                    fingerprint_toast.setGravity(Gravity.CENTER, 0, 0);
                    fingerprint_toast.show();

                }
            }
        });
    }
}