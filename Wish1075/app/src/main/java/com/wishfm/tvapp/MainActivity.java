package com.wishfm.tvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent("com.huawei.appmarket.appmarket.intent.action.AppDetail.withid");
//        intent.setPackage("com.wishfm.tvapp");
//        intent.putExtra("appId", "C103780375");
//        startActivity(intent);
    }
}