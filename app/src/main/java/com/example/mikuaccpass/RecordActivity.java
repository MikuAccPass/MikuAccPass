package com.example.mikuaccpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import java.util.Random;


public class RecordActivity extends BaseActivity {

    private EditText et_username;
    private EditText et_password;
    private EditText et_appname;
    private GlobalApplication global;
    private String[] passwordarray;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = this.getIntent();
        passwordarray = intent.getStringArrayExtra("passwordarray");

        et_appname = (EditText) findViewById(R.id.et_appname);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        global = (GlobalApplication) getApplication();
        et_appname.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getX() >= (et_appname.getWidth() - et_appname
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showListPopulWindow();
                        return true;
                    }
                }
                return false;
            }

        });
    }

    private void showListPopulWindow() {
        final String[] list = global.getString();//要填充的数据
        final ListPopupWindow listPopupWindow;
        listPopupWindow = new ListPopupWindow(RecordActivity.this);
        listPopupWindow.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
        listPopupWindow.setAnchorView(et_appname);//以哪个控件为基准，在该处以logId为基准
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et_appname.setText(list[i]);//把选择的选项内容展示在EditText上
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来
    }

    @SuppressLint("WrongConstant")
    public void click(View v) {
        String appname = et_appname.getText().toString().trim();
        String origin_appname = appname;
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        SharedPreferences pref = getSharedPreferences(appname, MODE_PRIVATE);
        int counter = 2;
        while (pref.getString("username", "") != "") {
            appname = origin_appname + counter;
            counter++;
            pref = getSharedPreferences(appname, MODE_PRIVATE);
        }
        //构造存储appkey的名字
        String appkey = "key";
        StringBuilder appkey1 = new StringBuilder(appkey);//构造一个StringBuilder对象
        appkey1.insert(0, appname);//在指定的位置1，插入指定的字符串
        appkey = appkey1.toString();
        System.out.println(appkey);

        boolean sign=false;
        int arrarylength=passwordarray.length;
        for(int i=1;i<=arrarylength-1;i++){
            if(password.equals(passwordarray[i])){sign=true;break;}
        }

        //System.out.println(password +"---"+password_confirm);
        if (TextUtils.isEmpty(appname) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "密码不能为空！", 0).show();
            return;
        }
        else if (sign) {
            Toast.makeText(this, "该密码已经存在，请修改密码！", 0).show();
            et_password.setTextColor(0xffff0000);
            et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
//            System.out.println("该密码已经存在，请修改密码");
            return;
        }
        else {
            Toast.makeText(this, "保存成功！", 0).show();
            InfoStorage infostorage = null;
            infostorage.saveInfo(this, appname, username, password, appkey, origin_appname);
           /*/ System.out.println("content为"+content[0]);
            System.out.println("content为"+content[1]);*/
//            SharedPreferences.Editor editor = sp.edit();
//            //putString(String key,String value)
//            editor.putString("password", MD5Utils.md5Password(password));
//            editor.commit();
//            Toast.makeText(this, "密码保存成功！", 0).show();
        }
        startActivity(new Intent(RecordActivity.this, MainActivity.class));
        finish();
    }

    //随机密码填充
    public void RandomNum(View v) {
        String password = genRandomNum();
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