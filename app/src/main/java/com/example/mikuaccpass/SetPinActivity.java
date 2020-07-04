package com.example.mikuaccpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPinActivity extends AppCompatActivity {

    private EditText edtpin;
    private Button apply_bt;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        preferences = getSharedPreferences("get_pin", MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        edtpin = findViewById(R.id.pin_et);
        apply_bt = findViewById(R.id.apply_btn);

        apply_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = edtpin.getText().toString();
                if (pin.trim().length() == 0) {
                    edtpin.setError("Pin值不能为空");
                    return;
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Pin", pin.trim());
                editor.apply();
                if (pin.equals(preferences.getString("Pin", "")))
                    Toast.makeText(SetPinActivity.this, "Pin值存储成功", Toast.LENGTH_SHORT).show();
            }

        });
    }
}