package com.example.mikuaccpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeActivity extends BaseActivity {

    private EditText et_username;
    private EditText et_password;
    private TextView tv_appname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        tv_appname = (TextView) findViewById(R.id.tv_appname);
        et_username = (EditText) findViewById(R.id.et_username1);
        et_password = (EditText) findViewById(R.id.et_password1);

        Intent i=getIntent();
        tv_appname.setText(i.getStringExtra("data"));//平台名从msgActivity中传进来

    }
    @SuppressLint("WrongConstant")
    public void click1(View v)
    {
        String appname = tv_appname.getText().toString().trim();//平台名不变
        String username = et_username.getText().toString().trim();//用户名需要自己输入
        String password = et_password.getText().toString().trim();//密码需要自己输入
        SharedPreferences pref = getSharedPreferences(appname,MODE_PRIVATE);
        /*int counter=2;
        while(pref.getString("username","")!=""){
            appname =appname+counter;
            counter++;
            pref=getSharedPreferences(appname,MODE_PRIVATE);
        }*/
        //构造存储appkey的名字
        String appkey="key";
        StringBuilder appkey1 = new StringBuilder(appkey);//构造一个StringBuilder对象
        appkey1.insert(0, appname);//在指定的位置1，插入指定的字符串
        appkey = appkey1.toString();
        System.out.println(appkey);


        //System.out.println(password +"---"+password_confirm);
        if (TextUtils.isEmpty(appname) ||TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "密码不能为空！", 0).show();
            return;
        }else {
            Toast.makeText(this, "保存成功！", 0).show();
            InfoStorage infostorage = null;
            infostorage.saveInfo2(this,appname,username,password,appkey);
           /*/ System.out.println("content为"+content[0]);
            System.out.println("content为"+content[1]);*/
//            SharedPreferences.Editor editor = sp.edit();
//            //putString(String key,String value)
//            editor.putString("password", MD5Utils.md5Password(password));
//            editor.commit();
//            Toast.makeText(this, "密码保存成功！", 0).show();
        }
        startActivity(new Intent(ChangeActivity.this, HomepageActivity.class));


    }
}