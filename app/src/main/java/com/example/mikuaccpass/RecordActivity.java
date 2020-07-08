package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class RecordActivity extends BaseActivity {

    private EditText et_username;
    private EditText et_password;
    private EditText et_appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        et_appname = (EditText) findViewById(R.id.et_appname);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);


    }
    @SuppressLint("WrongConstant")
    public void click(View v)
    {
        String appname = et_appname.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        SharedPreferences pref = getSharedPreferences(appname,MODE_PRIVATE);
        int counter=2;
        while(pref.getString("username","")!=""){
            appname =appname+counter;
            counter++;
            pref=getSharedPreferences(appname,MODE_PRIVATE);
        }
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
            infostorage.saveInfo(this,appname,username,password,appkey);
           /*/ System.out.println("content为"+content[0]);
            System.out.println("content为"+content[1]);*/
//            SharedPreferences.Editor editor = sp.edit();
//            //putString(String key,String value)
//            editor.putString("password", MD5Utils.md5Password(password));
//            editor.commit();
//            Toast.makeText(this, "密码保存成功！", 0).show();
        }
        startActivity(new Intent(RecordActivity.this, MainActivity.class));
    }

    //随机密码填充
    public void RandomNum(View v){
        String password=genRandomNum();
        et_password.setText(password);

    }
    //获取随机密码
    public String genRandomNum() {
        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 8) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}