package com.example.mikuaccpass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPinActivity extends BaseActivity {

    private EditText edtpin;
    private Button apply_bt;
    private SharedPreferences preferences;
    private GlobalApplication global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        preferences = getSharedPreferences("security", MODE_PRIVATE);
        global = (GlobalApplication) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();

        edtpin = findViewById(R.id.pin_et);
        apply_bt = findViewById(R.id.apply_btn);

        apply_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin_str = edtpin.getText().toString();
                if (pin_str.length() == 0) {
                    edtpin.setError("Pin值不能为空");
                    return;
                }

                int pin = Integer.parseInt(pin_str);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("Pin", pin);
                editor.apply();
                if (pin == (preferences.getInt("Pin", -1))) {
                    global.setPin(pin);
                    Toast.makeText(SetPinActivity.this, "Pin设置成功", Toast.LENGTH_SHORT).show();
                    SetPinActivity.this.finish();
                }
            }
        });
    }
}