package com.example.yunzhivoice;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.addoiloperator.MeachineService;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        TTSUtils.getInstance().init();

        Intent intent = new Intent(this, MeachineService.class);
        startService(intent);
    }

    public static Context getContext(){
        return context;
    }
}
