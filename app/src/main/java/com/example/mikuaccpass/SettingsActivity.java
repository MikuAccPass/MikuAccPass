package com.example.mikuaccpass;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.autofill.AutofillManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import static android.app.Activity.RESULT_OK;

public class SettingsActivity extends Fragment {
    private GlobalApplication global;
    private SharedPreferences preferences;
    private View root = null;
    private AutofillManager manager;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            SwitchButton fill_switch = root.findViewById(R.id.fill_switch);
            if (resultCode == RESULT_OK) {
                preferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("autofill_enable", true);
                editor.apply();

                fill_switch.setChecked(true);
            } else {
                preferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("autofill_enable", false);
                editor.apply();

                fill_switch.setChecked(false);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.activity_settings, container, false);
        global = (GlobalApplication) getActivity().getApplication();
        preferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);

        SuperTextView pin_btn = root.findViewById(R.id.pin_btn);
        pin_btn.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClick(SuperTextView superTextView) {
                Intent pin_set = new Intent(getActivity(), SetPinActivity.class);
                startActivity(pin_set);
            }
        });

        SwitchButton shot_switch = root.findViewById(R.id.shot_switch);
        shot_switch.setChecked(global.ScreenshotPermit());

        shot_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                } else {
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
                global.setScreenshotPermit(isChecked);
            }
        });

        final SwitchButton fill_switch = root.findViewById(R.id.fill_switch);
        fill_switch.setChecked(preferences.getBoolean("autofill_enable", false));
        fill_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (Build.VERSION.SDK_INT < 26) {
                        fill_switch.setChecked(false);
                        Toast fill_toast = Toast.makeText(getActivity(), "该功能需要安卓8.0以上支持", Toast.LENGTH_SHORT);
                        fill_toast.setGravity(Gravity.CENTER, 0, 0);
                        fill_toast.show();
                        return;
                    }
                    manager = getActivity().getSystemService(AutofillManager.class);
                    if (manager!= null && !manager.hasEnabledAutofillServices()) {
                        AlertDialog.Builder autofillDialog = new AlertDialog.Builder(getActivity())
                                .setCancelable(false)
                                .setTitle("自动填充服务未启动")
                                .setMessage("为了正常使用，请前往设置-系统-语言和输入法-高级-自动填充服务，选择“Miku Autofill Service”。")
                                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent set_autofill = new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE);
                                        set_autofill.setData(Uri.parse("package:com.example.android.autofill.service"));
                                        startActivityForResult(set_autofill, 1);
                                    }
                                })
                                .setNegativeButton("算了算了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        fill_switch.setChecked(false);
                                    }
                                });
                        autofillDialog.show();
                    } else {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("autofill_enable", true);
                        editor.apply();
                    }
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("autofill_enable", false);
                    editor.apply();
                }
            }
        });

        final SwitchButton fingerprint_switch = root.findViewById(R.id.fingerprint_switch);
        fingerprint_switch.setChecked(global.isFingerprint_enable());
        fingerprint_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (global.getPin() == -1) {
                    fingerprint_switch.setChecked(false);
                    Toast fill_toast = Toast.makeText(getActivity(), "请先设置Pin", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();
                    Intent pin_set = new Intent(getActivity(), SetPinActivity.class);
                    startActivity(pin_set);
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("fingerprint_enable", true);
                    editor.apply();
                    if (isChecked == (preferences.getBoolean("fingerprint_enable", false)))
                        global.setFingerprint_enable(isChecked);
                }
            }
        });
        return root;
    }
}