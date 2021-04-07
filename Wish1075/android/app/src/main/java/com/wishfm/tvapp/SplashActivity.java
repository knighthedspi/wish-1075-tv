package com.wishfm.tvapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

        Intent intent = new Intent("com.huawei.appmarket.intent.action.AppDetail");
        intent.setPackage("com.huawei.appmarket");
        intent.putExtra("APP_PACKAGENAME", "com.wishfm.tvapp");
        startActivity(intent);

        finish();
    }
}