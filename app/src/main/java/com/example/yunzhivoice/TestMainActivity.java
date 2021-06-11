package com.example.yunzhivoice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestMainActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        Button btn_hecheng = findViewById(R.id.btn_hecheng);
        btn_hecheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TTSUtils.getInstance().speak("打印小票");
            }
        });
    }
}
