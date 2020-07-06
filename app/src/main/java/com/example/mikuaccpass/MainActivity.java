package com.example.mikuaccpass;

import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, HomepageActivity.class);
        startActivity(i);

    }
}

