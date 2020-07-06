package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private Button btnChange;
    private TextView tvstation,tvname,tvpw;

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

    private  void initView()
    {
        btnChange = findViewById(R.id.btn_change);
        tvstation = findViewById(R.id.tv_station);
        tvname= findViewById(R.id.tv_name);
        tvpw=findViewById(R.id.tv_password);

    }
    private  void initEvent(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String name=extras.getString("activity1");
            //  final String nameid=extras.getString("activity4");
            tvstation.setText(name);
            tvname.setText(extras.getString("activity2"));
            tvpw.setText(extras.getString("activity3"));
            btnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(MessageActivity.this, RecordActivity.class);
                    intent2.putExtra("data",name);
                    // intent2.putExtra("data2",nameid);
                    startActivity(intent2);
                }
            });
        }

    }
}