        package com.example.mikuaccpass;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.Nullable;

        public class MainActivity extends BaseActivity {
            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView (R.layout.activity_main);

                if(global.getPin()!=-1)
                {
                    Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(i);
                }
            }

    @Override
    protected void onResume() {
        super.onResume();

        if (global.getPin()==-1){
            AlertDialog.Builder pinDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("欢迎使用Miku密码库")
                    .setMessage("为了安全使用，请设置用于验证的Pin码")
                    .setPositiveButton("乖乖就范", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent pin_set = new Intent(MainActivity.this,SetPinActivity.class);
                            startActivity(pin_set);
                        }
                    })
                    .setNegativeButton("宁可不用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.finish();
                        }
                    });
            pinDialog.show();
        }
    }
}
