package com.rain.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rain.lib.log.Log;
import com.rain.lib.log.LoggerManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            LoggerManager.init(getAssets().open("android-logger.properties"));
        }catch (Exception e){e.printStackTrace();}
        Log.d("111");
    }
}
