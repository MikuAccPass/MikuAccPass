package com.example.mikuaccpass;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
=======
import android.app.AlertDialog;
import android.content.DialogInterface;
>>>>>>> de26e704fe5e5405555efb6b9e9385cbd2d545ad
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mikuaccpass.Acount;
import com.example.mikuaccpass.AcountAdapter;
import com.example.mikuaccpass.InfoStorage;
import com.example.mikuaccpass.MessageActivity;
import com.example.mikuaccpass.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mikuaccpass.RecordActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Fragment {
    private GlobalApplication global;
    private SharedPreferences preferences;

    @Override
<<<<<<< HEAD
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
=======
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        global = (GlobalApplication)getApplication();
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
>>>>>>> de26e704fe5e5405555efb6b9e9385cbd2d545ad

        View root = inflater.inflate(R.layout.activity_settings, container, false);
        global = (GlobalApplication)getActivity().getApplication();
        preferences = getActivity().getSharedPreferences("security", Context.MODE_PRIVATE);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        Button pin_btn = root.findViewById(R.id.pin_btn);
        pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pin_set = new Intent(getActivity(), SetPinActivity.class);
                startActivity(pin_set);
            }
        });

        Switch shot_switch = root.findViewById(R.id.shot_switch);
        shot_switch.setChecked(global.ScreenshotPermit());

        shot_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }else{
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
            }
        });

        //开关还缺实际功能的控制
<<<<<<< HEAD
        Switch fill_switch = root.findViewById(R.id.fill_switch);
=======
        final Switch fill_switch = findViewById(R.id.fill_switch);
        fill_switch.setChecked(preferences.getBoolean("autofill_enable",false));
>>>>>>> de26e704fe5e5405555efb6b9e9385cbd2d545ad
        fill_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
<<<<<<< HEAD
                    Toast fill_toast= Toast.makeText(getActivity(), "自动填充已开启", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();

                }else{
                    Toast fill_toast = Toast.makeText(getActivity(), "自动填充已关闭", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();

=======
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
>>>>>>> de26e704fe5e5405555efb6b9e9385cbd2d545ad
                }
            }
        });

<<<<<<< HEAD
        //开关还缺实际功能的控制
        final Switch fingerprint_switch = root.findViewById(R.id.fingerprint_switch);
=======
        final Switch fingerprint_switch = (Switch)findViewById(R.id.fingerprint_switch);
>>>>>>> de26e704fe5e5405555efb6b9e9385cbd2d545ad
        fingerprint_switch.setChecked(global.isFingerprint_enable());
        fingerprint_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (global.getPin()==-1){
                    fingerprint_switch.setChecked(false);
                    Toast fill_toast = Toast.makeText(getActivity(), "请先设置Pin", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();
                    Intent pin_set = new Intent(getActivity(), SetPinActivity.class);
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
        return root;
    }
}