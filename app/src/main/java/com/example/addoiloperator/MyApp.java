package com.example.addoiloperator;

import android.app.Application;
import android.content.Intent;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this,MeachineService.class);
        startService(intent);
    }
}
