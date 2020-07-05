package com.example.mikuaccpass;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {
    private EditText edtSaveKey, edtSaveValue,edtSaveStation;
    private Button btnSave;
    private SharedPreferences preferences;
    private TextView tvContent;
    private String saveKey;
    private String saveValue;
    private String saveStation;
    private  List<Acount> acountlist = new ArrayList<>();

   /*private String[] data = {"apple","bannner","orange","watermelon","pear","grape",
           "pineapple","strawberry","cherry","mango","apple","bannner","orange",
          "watermelon","pear","grape", "pineapple","strawberry","cherry","mango"
   };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        preferences=getSharedPreferences("share",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        initFruits();//初始化水果数据

        AcountAdapter adapter = new AcountAdapter(HomepageActivity.this,
                R.layout.listview, acountlist);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        //  MainActivity.this,android.R.layout.simple_list_item_1,data);
        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        edtSaveStation = findViewById(R.id.edt_save_station);
        edtSaveKey = findViewById(R.id.edt_save_key);
        edtSaveValue = findViewById(R.id.edt_save_value);
        btnSave = findViewById(R.id.btn_save);
        tvContent= findViewById(R.id.tv_read);

        //注册监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Acount fruit = acountlist.get(position);
                if(fruit.getName()=="点击创建")
                {
                    startActivity(new Intent(HomepageActivity.this, RecordActivity.class));
                }
                else {
                }
               /* Toast.makeText(MainActivity.this,fruit.getName(),
                        Toast.LENGTH_LONG).show();*/
                //if(position==listView.getFooterViewsCount()) {
                // startActivity(new Intent(MainActivity.this, RecordActivity.class));
                //String p=Integer.toString(n);
                // }
                //else {

                // }
            }
        });
    }


    private void initFruits()
    {
        SharedPreferences.Editor editor = preferences.edit();
        int n=preferences.getInt("number",0);
        editor.putInt("number",n);
        Acount a = new Acount("default","点击创建", "",R.drawable.ic_launcher_background);
        acountlist.add(a);
        for (int i=1;i<=n;i++)
        {
            String s = i+"";
            String s1= "p"+i;
            String usrname2=preferences.getString(s,"");
            String pasword2=preferences.getString(s1,"");
            Acount x = new Acount("default",usrname2,pasword2, R.drawable.ic_launcher_background);
            acountlist.add(x);
        }
        editor.apply();
    }
}
