package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends BaseActivity {
    private EditText edtSaveKey, edtSaveValue,edtSaveStation;
    private Button btnSave;
    private SharedPreferences preferences;
    private TextView tvContent;
    private String saveKey;
    private String saveValue;
    private String saveStation;
    private FloatingActionButton btnplus,btnSetting;
    private  List<Acount> acountList = new ArrayList<>();

   /*private String[] data = {"apple","bannner","orange","watermelon","pear","grape",
           "pineapple","strawberry","cherry","mango","apple","bannner","orange",
          "watermelon","pear","grape", "pineapple","strawberry","cherry","mango"
   };*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        preferences=getSharedPreferences("share",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        initFruits();//初始化水果数据

        AcountAdapter adapter = new AcountAdapter(HomepageActivity.this,
                R.layout.listview, acountList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        //  MainActivity.this,android.R.layout.simple_list_item_1,data);
        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        btnSetting=findViewById(R.id.imageButton);
        btnplus = findViewById(R.id.btn_plus);

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepageActivity.this, RecordActivity.class));
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepageActivity.this, SettingsActivity.class));
            }
        });

        //注册监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Acount fruit = acountList.get(position);
                String station=fruit.getStation();
                String name=fruit.getName();
                String password=fruit.getPassword();
                //   String nameid=fruit.getNameid();
                Intent intent1 = new Intent(HomepageActivity.this, MessageActivity.class);
                intent1.putExtra("activity1", station);
                intent1.putExtra("activity2", name);
                intent1.putExtra("activity3", password);
                // intent1.putExtra("activity4", nameid);

                // intent1.putExtra("activity4", position);
                startActivity(intent1);
               /*if()
               {

               }*/
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
        for (int i=1;i<=n;i++)
        {
            String appname=i+"";
            appname = preferences.getString(appname,"");//获取appname为命名的相关sharepreference文件夹名字
            SharedPreferences pref=getSharedPreferences(appname,MODE_PRIVATE);
            String appkey = pref.getString("appkey","");
            InfoStorage infostorage = null;
            String[] content=infostorage.readInfo(this,appname,appkey);
            Acount x = new Acount(appname,content[0],content[1], R.drawable.ic_launcher_background);
           acountList.add(x);
        }
        editor.apply();
    }

}
