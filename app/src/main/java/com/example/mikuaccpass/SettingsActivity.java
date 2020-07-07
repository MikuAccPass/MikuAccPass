package com.example.mikuaccpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

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
        Switch fill_switch = root.findViewById(R.id.fill_switch);
        fill_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast fill_toast= Toast.makeText(getActivity(), "自动填充已开启", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();

                }else{
                    Toast fill_toast = Toast.makeText(getActivity(), "自动填充已关闭", Toast.LENGTH_SHORT);
                    fill_toast.setGravity(Gravity.CENTER, 0, 0);
                    fill_toast.show();

                }
            }
        });

        //开关还缺实际功能的控制
        final Switch fingerprint_switch = root.findViewById(R.id.fingerprint_switch);
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