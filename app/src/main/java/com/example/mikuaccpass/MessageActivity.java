package com.example.mikuaccpass;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MessageActivity extends BaseActivity {

    private Button btnChange,btnDelete;
    private TextView tvstation, tvname, tvpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initEvent();
    }

    private void initView() {
        btnDelete=findViewById(R.id.btn_delete);
        btnChange = findViewById(R.id.btn_change);
        tvstation = findViewById(R.id.tv_station);
        tvname = findViewById(R.id.tv_name);
        tvpw = findViewById(R.id.tv_password);

    }

    private void initEvent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String origin_appname=extras.getString("activity0");
            final String station = extras.getString("activity1");
            final String name = extras.getString("activity2");
            final String password = extras.getString("activity3");
            //  final String nameid=extras.getString("activity4");
            tvstation.setText(origin_appname);
            tvname.setText(name);
            tvpw.setText(password);
            btnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(MessageActivity.this, ChangeActivity.class);
                    intent2.putExtra("data1", station);
                    intent2.putExtra("data2", name);
                    intent2.putExtra("data3", password);
                    // intent2.putExtra("data2",nameid);
                    startActivity(intent2);
                    finish();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder deleteAlert = new AlertDialog.Builder(MessageActivity.this)
                            .setTitle("删除")
                            .setMessage("真的要删除该账户信息吗？")
                            .setPositiveButton("覆水难收", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String xmlpath="/data/data/com.example.mikuaccpass/shared_prefs/"+station+".xml";
                                    String keypath="/data/data/com.example.mikuaccpass/shared_prefs/appkeys/"+station+"key";
                                    deleteSingleFile(xmlpath);
                                    deleteSingleFile(keypath);
                                    delInfo(station);
                                    Intent intent = new Intent();
                                    intent.setAction("action.refreshList");
                                    sendBroadcast(intent);
                                    startActivity(new Intent(MessageActivity.this,MainActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("悬崖勒马", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                    deleteAlert.show();
                }
            });
        }

    }

    public  void delInfo(String appname) {


        SharedPreferences preferences =MessageActivity.this.getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences.edit();
        int n = preferences.getInt("number",0);//n记载了数据数目的多少
        // n=n+1;
        for(int i=1;i<=n;i++)
        {
            String x=i+"";
            String t;
            t=preferences.getString(x,"");
            if(t.equals(appname))
                editor2.putString(x,"");

        }
        editor2.apply();
        editor2.commit();
    }

    public void copyname(View v) {
        ClipboardManager cmb = (ClipboardManager) getApplicationContext().getSystemService(this.CLIPBOARD_SERVICE);
        cmb.setText(tvname.getText().toString().trim()); //将内容放入粘贴管理器
        Toast.makeText(this, "用户名已经复制", Toast.LENGTH_SHORT).show();

    }

    public void copypassword(View v) {
        ClipboardManager cmb = (ClipboardManager) getApplicationContext().getSystemService(this.CLIPBOARD_SERVICE);
        cmb.setText(tvpw.getText().toString().trim());
        Toast.makeText(this, "密码已经复制", Toast.LENGTH_SHORT).show();

    }

    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(getApplicationContext(), "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}