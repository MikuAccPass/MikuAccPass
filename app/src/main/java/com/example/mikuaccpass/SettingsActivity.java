package com.example.mikuaccpass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.autofill.AutofillManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity {
    private GlobalApplication global;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        global = (GlobalApplication)getApplication();
        preferences = getSharedPreferences("setting", MODE_PRIVATE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        Button pin_btn = findViewById(R.id.pin_btn);
        pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pin_set = new Intent(SettingsActivity.this, SetPinActivity.class);
                startActivity(pin_set);
            }
        });

        Switch shot_switch = findViewById(R.id.shot_switch);
        shot_switch.setChecked(global.ScreenshotPermit());

        shot_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }else{
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
            }
        });

        //开关还缺实际功能的控制
        final Switch fill_switch = findViewById(R.id.fill_switch);
        fill_switch.setChecked(preferences.getBoolean("autofill_enable",false));
        fill_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(Build.VERSION.SDK_INT<26){
                        fill_switch.setChecked(false);
                        Toast fill_toast = Toast.makeText(SettingsActivity.this, "该功能需要安卓8.0以上支持", Toast.LENGTH_SHORT);
                        fill_toast.setGravity(Gravity.CENTER, 0, 0);
                        fill_toast.show();
                        return;
                    }
                    if(!getSystemService(AutofillManager.class).hasEnabledAutofillServices()){
                        AlertDialog.Builder pinDialog = new AlertDialog.Builder(SettingsActivity.this)
                                .setCancelable(false)
                                .setTitle("自动填充服务未启动")
                                .setMessage("为了正常使用，请前往设置-系统-语言和输入法-高级-自动填充服务，选择“Miku Autofill Service”。")
                                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE));
                                    }
                                })
                                .setNegativeButton("算了算了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        fill_switch.setChecked(false);
                                    }
                                });
                        pinDialog.show();
                    }
                    else {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("autofill_enable", true);
                        editor.apply();
                    }
                }else{
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("autofill_enable", false);
                    editor.apply();
                }
            }
        });

        final Switch fingerprint_switch = (Switch)findViewById(R.id.fingerprint_switch);
        fingerprint_switch.setChecked(global.isFingerprint_enable());
        fingerprint_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (global.getPin()==-1){
                    fingerprint_switch.setChecked(false);
                    Toast fill_toast = Toast.makeText(SettingsActivity.this, "请先设置Pin", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();
                    Intent pin_set = new Intent(SettingsActivity.this, SetPinActivity.class);
                    startActivity(pin_set);
                }
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("fingerprint_enable", true);
                    editor.apply();
                    if (isChecked == (preferences.getBoolean("fingerprint_enable", false)))
                        global.setFingerprint_enable(isChecked);
                }
            }
        });
    }
}