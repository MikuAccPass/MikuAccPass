 package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private EditText edtSaveKey, edtSaveValue,edtSaveStation;
    private Button btnSave;
    private SharedPreferences preferences;
    private TextView tvContent;
    private String saveKey;
    private String saveValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


    }
    @Override
    protected void onStart() {
        super.onStart();

        //查找控件，绑定事件监听
        //1、找到所有需要触发的控件
        initView();


        //2、为控件设置事件监听
        initEvent();

    }
    private void initView() {
        edtSaveStation = findViewById(R.id.edt_save_station);
        edtSaveKey = findViewById(R.id.edt_save_key);
        edtSaveValue = findViewById(R.id.edt_save_value);
        btnSave = findViewById(R.id.btn_save);
        tvContent= findViewById(R.id.tv_read);
    }

    /**
     * 设置事件监听
     */
    private void initEvent() {

        //共享参数存储事件
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                saveKey = edtSaveKey.getText().toString();
                saveValue = edtSaveValue.getText().toString();
                //saveStation = edtSaveStation.getText().toString();


                /*if (saveStation.trim().length() == 0) {
                    edtSaveStation.setError("平台 不能为空");
                    return;
                }*/
                if (saveKey.trim().length() == 0) {
                    edtSaveKey.setError("用户名 不能为空");
                    return;
                }

                if (saveValue.trim().length() == 0) {
                    edtSaveValue.setError("密码 不能为空");
                    return;
                }

                //共享参数存储（键值对存储）
                //2.获取共享参数编辑器
                preferences = getSharedPreferences("share", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                int n = preferences.getInt("number", 0);
                n=n+1;
                editor.putInt("number", n);
                String p = n + "";
                //3.存储数据
                editor.putString(p, saveKey.trim());
                editor.putString("p"+p, saveValue.trim());
                //editor.putString("s"+p, saveStation.trim());

                if (saveValue.equals(preferences.getString(p, "")))
                    Toast.makeText(RecordActivity.this, "存储成功", Toast.LENGTH_SHORT).show();

                String station = preferences.getString("s"+p, "");
                String username = preferences.getString(p, "");
                String password = preferences.getString("p" + p, "");
                if (password.length() == 0 || station.length() == 0 || username.length() == 0)
                    tvContent.setText("");
                else
                    tvContent.setText("平台变更为：" + station + "\n" + "用户名变更为：" + username + "\n" + "密码变更为：" + password + "\n");

                editor.putInt("ok",1);
                String sk = saveKey.trim();
                editor.putString("usernamee",sk);//用户名信息
                editor.putString("passwordd",saveValue.trim());//密码信息
                //4.提交变更
                editor.apply();
                editor.commit();
                startActivity(new Intent(RecordActivity.this, MainActivity.class));
            }
        });
    }

}