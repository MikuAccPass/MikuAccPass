package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    private Button btn_main=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_main=(Button) findViewById(R. id.btn_main);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView (R. layout.main);
            }
        });

    }
}

